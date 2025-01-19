package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.message.RecordMessage;
import stexfires.util.Strings;

import java.util.*;
import java.util.function.*;

/**
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @since 0.1
 */
public class TextsMapper<T extends TextRecord> extends FunctionMapper<T> {

    public TextsMapper(Function<? super T, Collection<@Nullable String>> textsFunction) {
        super(TextRecord::category, TextRecord::recordId, textsFunction);
    }

    public static <T extends TextRecord> TextsMapper<T> identity() {
        return new TextsMapper<>(TextFields::collectTexts);
    }

    public static <T extends TextRecord> TextsMapper<T> recordFieldFunction(BiFunction<TextRecord, TextField, @Nullable String> recordFieldFunction) {
        Objects.requireNonNull(recordFieldFunction);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(field -> recordFieldFunction.apply(record, field))
                      .toList());
    }

    /**
     * @see stexfires.record.mapper.field.IndexedFieldTextMapper
     */
    public static <T extends TextRecord> TextsMapper<T> mapAllFields(FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(fieldTextMapper::mapToText)
                      .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> mapOneField(Function<? super T, TextField> fieldFunction,
                                                                    FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldTextMapper);
        return new TextsMapper<>(record ->
                Strings.listOfNullable(fieldTextMapper.mapToText(Objects.requireNonNull(fieldFunction.apply(record)))));
    }

    public static <T extends TextRecord> TextsMapper<T> mapOneField(int index,
                                                                    FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return new TextsMapper<>(record -> record.isValidIndex(index) ?
                Strings.listOfNullable(fieldTextMapper.mapToText(Objects.requireNonNull(record.fieldAt(index)))) : Collections.emptyList());
    }

    public static <T extends TextRecord> TextsMapper<T> size(int size, @Nullable String fillingText) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal size! size=" + size);
        }
        return new TextsMapper<>(record -> {
            if (size < record.size()) {
                return
                        record.streamOfFields()
                              .map(TextField::text)
                              .limit(size)
                              .toList();
            } else if (size > record.size()) {
                List<@Nullable String> newTexts = new ArrayList<>(size);
                for (int index = 0; index < size; index++) {
                    if (record.isValidIndex(index)) {
                        newTexts.add(record.textAt(index));
                    } else {
                        newTexts.add(fillingText);
                    }
                }
                return newTexts;
            }
            return TextFields.collectTexts(record);
        });
    }

    public static <T extends TextRecord> TextsMapper<T> reverseTexts() {
        return new TextsMapper<>(record ->
                record.listOfFieldsReversed().stream().map(TextField::text).toList());
    }

    public static <T extends TextRecord> TextsMapper<T> createMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new TextsMapper<>(record ->
                Strings.listOfNullable(recordMessage.createMessage(record)));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> TextsMapper<T> createMessages(RecordMessage<? super T>... recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new TextsMapper<>(r -> Arrays.stream(recordMessages)
                                            .map(recordMessage -> recordMessage.createMessage(r))
                                            .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> createMessages(Collection<RecordMessage<? super T>> recordMessages) {
        Objects.requireNonNull(recordMessages);
        return new TextsMapper<>(r -> recordMessages.stream()
                                                    .map(recordMessage -> recordMessage.createMessage(r))
                                                    .toList());
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> TextsMapper<T> applyTextOperators(UnaryOperator<@Nullable String>... unaryOperators) {
        Objects.requireNonNull(unaryOperators);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(field -> (unaryOperators.length > field.index())
                              ? unaryOperators[field.index()].apply(field.text())
                              : field.text())
                      .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> applyTextOperators(List<UnaryOperator<@Nullable String>> unaryOperators) {
        Objects.requireNonNull(unaryOperators);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .map(field -> (unaryOperators.size() > field.index())
                              ? unaryOperators.get(field.index()).apply(field.text())
                              : field.text())
                      .toList());
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> TextsMapper<T> applyRecordFunctions(Function<? super T, @Nullable String>... textFunctions) {
        Objects.requireNonNull(textFunctions);
        return new TextsMapper<>(r -> Arrays.stream(textFunctions)
                                            .map(stringFunction -> stringFunction.apply(r))
                                            .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> applyRecordFunctions(Collection<Function<? super T, @Nullable String>> textFunctions) {
        Objects.requireNonNull(textFunctions);
        return new TextsMapper<>(r -> textFunctions.stream()
                                                   .map(stringFunction -> stringFunction.apply(r))
                                                   .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> add(Function<? super T, @Nullable String> textFunction) {
        return new TextsMapper<>(record -> {
            List<@Nullable String> newTexts = new ArrayList<>(record.size() + 1);
            newTexts.addAll(TextFields.collectTexts(record));
            newTexts.add(textFunction.apply(record));
            return newTexts;
        });
    }

    public static <T extends TextRecord> TextsMapper<T> remove(int index) {
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .filter(field -> field.index() != index)
                      .map(TextField::text)
                      .toList());
    }

    public static <T extends TextRecord> TextsMapper<T> remove(Predicate<TextField> fieldPredicate) {
        Objects.requireNonNull(fieldPredicate);
        return new TextsMapper<>(record ->
                record.streamOfFields()
                      .filter(fieldPredicate.negate())
                      .map(TextField::text)
                      .toList());
    }

}
