package stexfires.record.mapper;

import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.fieldvalue.FieldValueMapper;
import stexfires.record.message.RecordMessage;
import stexfires.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @see stexfires.record.mapper.AddValueMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @since 0.1
 */
public class ValuesMapper<T extends TextRecord> extends FunctionMapper<T> {

    public ValuesMapper(Function<? super T, Collection<String>> valuesFunction) {
        super(TextRecord::category, TextRecord::recordId, valuesFunction);
    }

    public static <T extends TextRecord> ValuesMapper<T> identity() {
        return new ValuesMapper<>(Fields::collectValues);
    }

    public static <T extends TextRecord> ValuesMapper<T> recordFieldFunction(BiFunction<TextRecord, Field, String> recordFieldFunction) {
        Objects.requireNonNull(recordFieldFunction);
        return new ValuesMapper<>(record ->
                record.streamOfFields()
                      .map(field -> recordFieldFunction.apply(record, field))
                      .collect(Collectors.toList()));
    }

    /**
     * @see stexfires.record.mapper.fieldvalue.IndexedFieldValueMapper
     */
    public static <T extends TextRecord> ValuesMapper<T> mapAllFields(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValuesMapper<>(record ->
                record.streamOfFields()
                      .map(fieldValueMapper::mapToValue)
                      .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> ValuesMapper<T> mapOneField(Function<? super T, Field> fieldFunction,
                                                                     FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldValueMapper);
        return new ValuesMapper<>(record ->
                Strings.list(fieldValueMapper.mapToValue(fieldFunction.apply(record))));
    }

    public static <T extends TextRecord> ValuesMapper<T> size(int size, String fillingValue) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal size! size=" + size);
        }
        return new ValuesMapper<>(record -> {
            if (size < record.size()) {
                return
                        record.streamOfFields()
                              .map(Field::value)
                              .limit(size)
                              .collect(Collectors.toList());
            } else if (size > record.size()) {
                List<String> newValues = new ArrayList<>(size);
                for (int index = 0; index < size; index++) {
                    if (record.isValidIndex(index)) {
                        newValues.add(record.valueAt(index));
                    } else {
                        newValues.add(fillingValue);
                    }
                }
                return newValues;
            }
            return Fields.collectValues(record);
        });
    }

    public static <T extends TextRecord> ValuesMapper<T> reverseValues() {
        return new ValuesMapper<>(record -> {
            List<String> newValues = Fields.collectValues(record);
            Collections.reverse(newValues);
            return newValues;
        });
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> ValuesMapper<T> createMessages(RecordMessage<? super T>... recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new ValuesMapper<>(r -> Arrays.stream(recordMessages)
                                             .map(recordMessage -> recordMessage.createMessage(r))
                                             .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> ValuesMapper<T> createMessages(Collection<RecordMessage<? super T>> recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new ValuesMapper<>(r -> recordMessages.stream()
                                                     .map(recordMessage -> recordMessage.createMessage(r))
                                                     .collect(Collectors.toList()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> ValuesMapper<T> applyFunctions(Function<? super T, String>... valueFunctions) {
        Objects.requireNonNull(valueFunctions);
        return new ValuesMapper<>(r -> Arrays.stream(valueFunctions)
                                             .map(stringFunction -> stringFunction.apply(r))
                                             .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> ValuesMapper<T> applyFunctions(Collection<Function<? super T, String>> valueFunctions) {
        Objects.requireNonNull(valueFunctions);
        return new ValuesMapper<>(r -> valueFunctions.stream()
                                                     .map(stringFunction -> stringFunction.apply(r))
                                                     .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> ValuesMapper<T> add(Function<? super T, String> valueFunction) {
        return new ValuesMapper<>(record -> {
            List<String> newValues = new ArrayList<>(record.size() + 1);
            newValues.addAll(Fields.collectValues(record));
            newValues.add(valueFunction.apply(record));
            return newValues;
        });
    }

    public static <T extends TextRecord> ValuesMapper<T> remove(int index) {
        return new ValuesMapper<>(record ->
                record.streamOfFields()
                      .filter(field -> field.index() != index)
                      .map(Field::value)
                      .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> ValuesMapper<T> remove(Predicate<Field> fieldPredicate) {
        Objects.requireNonNull(fieldPredicate);
        return new ValuesMapper<>(record ->
                record.streamOfFields()
                      .filter(fieldPredicate.negate())
                      .map(Field::value)
                      .collect(Collectors.toList()));
    }

}
