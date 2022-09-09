package stexfires.record.mapper;

import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.field.FieldTextMapper;
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
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @since 0.1
 */
public class TextsMapper<T extends TextRecord> extends FunctionMapper<T> {

    public TextsMapper(Function<? super T, Collection<String>> textsFunction) {
        super(TextRecord::category, TextRecord::recordId, textsFunction);
    }

    public static <T extends TextRecord> TextsMapper<T> identity() {
        return new TextsMapper<>(Fields::collectTexts);
    }

    public static <T extends TextRecord> TextsMapper<T> recordFieldFunction(BiFunction<TextRecord, Field, String> recordFieldFunction) {
        Objects.requireNonNull(recordFieldFunction);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(field -> recordFieldFunction.apply(record, field))
                      .collect(Collectors.toList()));
    }

    /**
     * @see stexfires.record.mapper.field.IndexedFieldTextMapper
     */
    public static <T extends TextRecord> TextsMapper<T> mapAllFields(FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(fieldTextMapper::mapToText)
                      .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> TextsMapper<T> mapOneField(Function<? super T, Field> fieldFunction,
                                                                    FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldTextMapper);
        return new TextsMapper<>(record ->
                Strings.list(fieldTextMapper.mapToText(fieldFunction.apply(record))));
    }

    public static <T extends TextRecord> TextsMapper<T> size(int size, String fillingText) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal size! size=" + size);
        }
        return new TextsMapper<>(record -> {
            if (size < record.size()) {
                return
                        record.streamOfFields()
                              .map(Field::text)
                              .limit(size)
                              .collect(Collectors.toList());
            } else if (size > record.size()) {
                List<String> newTexts = new ArrayList<>(size);
                for (int index = 0; index < size; index++) {
                    if (record.isValidIndex(index)) {
                        newTexts.add(record.textAt(index));
                    } else {
                        newTexts.add(fillingText);
                    }
                }
                return newTexts;
            }
            return Fields.collectTexts(record);
        });
    }

    public static <T extends TextRecord> TextsMapper<T> reverseTexts() {
        return new TextsMapper<>(record -> {
            List<String> newTexts = Fields.collectTexts(record);
            Collections.reverse(newTexts);
            return newTexts;
        });
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> TextsMapper<T> createMessages(RecordMessage<? super T>... recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new TextsMapper<>(r -> Arrays.stream(recordMessages)
                                            .map(recordMessage -> recordMessage.createMessage(r))
                                            .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> TextsMapper<T> createMessages(Collection<RecordMessage<? super T>> recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new TextsMapper<>(r -> recordMessages.stream()
                                                    .map(recordMessage -> recordMessage.createMessage(r))
                                                    .collect(Collectors.toList()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> TextsMapper<T> applyFunctions(Function<? super T, String>... textFunctions) {
        Objects.requireNonNull(textFunctions);
        return new TextsMapper<>(r -> Arrays.stream(textFunctions)
                                            .map(stringFunction -> stringFunction.apply(r))
                                            .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> TextsMapper<T> applyFunctions(Collection<Function<? super T, String>> textFunctions) {
        Objects.requireNonNull(textFunctions);
        return new TextsMapper<>(r -> textFunctions.stream()
                                                   .map(stringFunction -> stringFunction.apply(r))
                                                   .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> TextsMapper<T> add(Function<? super T, String> textFunction) {
        return new TextsMapper<>(record -> {
            List<String> newTexts = new ArrayList<>(record.size() + 1);
            newTexts.addAll(Fields.collectTexts(record));
            newTexts.add(textFunction.apply(record));
            return newTexts;
        });
    }

    public static <T extends TextRecord> TextsMapper<T> remove(int index) {
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .filter(field -> field.index() != index)
                      .map(Field::text)
                      .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> TextsMapper<T> remove(Predicate<Field> fieldPredicate) {
        Objects.requireNonNull(fieldPredicate);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .filter(fieldPredicate.negate())
                      .map(Field::text)
                      .collect(Collectors.toList()));
    }

}
