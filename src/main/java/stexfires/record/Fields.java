package stexfires.record;

import stexfires.record.mapper.fieldvalue.FieldValueMapper;
import stexfires.record.mapper.fieldvalue.IdentityFieldValueMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@link Field}s.
 *
 * @author Mathias Kalb
 * @see Field
 * @see TextRecord
 * @see stexfires.record.mapper.fieldvalue.FieldValueMapper
 * @since 0.1
 */
public final class Fields {

    public static final int FIRST_FIELD_INDEX = 0;
    public static final String DEFAULT_FIELD_TEXT_DELIMITER = ", ";

    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    private Fields() {
    }

    public static Field[] emptyArray() {
        return EMPTY_FIELD_ARRAY;
    }

    public static Field[] newArray(Collection<String> texts) {
        Objects.requireNonNull(texts);
        Field[] fields = new Field[texts.size()];
        int index = FIRST_FIELD_INDEX;
        for (String text : texts) {
            fields[index] = new Field(index, texts.size() - 1, text);
            index++;
        }
        return fields;
    }

    public static Field[] newArray(Stream<String> values) {
        Objects.requireNonNull(values);
        return newArray(values.collect(Collectors.toList()));
    }

    @SuppressWarnings({"Convert2streamapi", "OverloadedVarargsMethod"})
    public static Field[] newArray(String... texts) {
        Objects.requireNonNull(texts);
        Field[] fields = new Field[texts.length];
        for (int index = FIRST_FIELD_INDEX; index < texts.length; index++) {
            fields[index] = new Field(index, texts.length - 1, texts[index]);
        }
        return fields;
    }

    @SuppressWarnings("Convert2streamapi")
    public static Field[] newArray(int length, Supplier<String> textSupplier) {
        if (length < 0) {
            throw new IllegalArgumentException("Illegal length! length=" + length);
        }
        Objects.requireNonNull(textSupplier);
        Field[] fields = new Field[length];
        for (int index = FIRST_FIELD_INDEX; index < length; index++) {
            fields[index] = new Field(index, length - 1, textSupplier.get());
        }
        return fields;
    }

    public static List<String> collectTexts(TextRecord record) {
        Objects.requireNonNull(record);
        return collectTexts(record.streamOfFields(), new IdentityFieldValueMapper());
    }

    public static List<String> collectTexts(TextRecord record, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(fieldValueMapper);
        return collectTexts(record.streamOfFields(), fieldValueMapper);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static List<String> collectTexts(Field[] fields) {
        Objects.requireNonNull(fields);
        return collectTexts(Arrays.stream(fields), new IdentityFieldValueMapper());
    }

    public static List<String> collectTexts(Field[] fields, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldValueMapper);
        return collectTexts(Arrays.stream(fields), fieldValueMapper);
    }

    public static List<String> collectTexts(Stream<Field> fields) {
        Objects.requireNonNull(fields);
        return collectTexts(fields, new IdentityFieldValueMapper());
    }

    public static List<String> collectTexts(Stream<Field> fields, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldValueMapper);
        return fields.map(fieldValueMapper::mapToValue).collect(Collectors.toList());
    }

    public static List<Field> collectFields(Stream<Field> fields) {
        Objects.requireNonNull(fields);
        return fields.collect(Collectors.toList());
    }

    public static String joinTexts(TextRecord record) {
        Objects.requireNonNull(record);
        return joinTexts(record.streamOfFields(), DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(TextRecord record, CharSequence delimiter) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(delimiter);
        return joinTexts(record.streamOfFields(), delimiter);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static String joinTexts(Field[] fields) {
        Objects.requireNonNull(fields);
        return joinTexts(Arrays.stream(fields), DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(Field[] fields, CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return joinTexts(Arrays.stream(fields), delimiter);
    }

    public static String joinTexts(Stream<Field> fields) {
        Objects.requireNonNull(fields);
        return joinTexts(fields, DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(Stream<Field> fields, CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return fields.map(Field::text).collect(Collectors.joining(delimiter));
    }

    public static Stream<String> mapToValue(Stream<Field> fields, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldValueMapper);
        return fields.map(fieldValueMapper::mapToValue);
    }

    public static Stream<Field> sortFields(Stream<Field> fields, Comparator<Field> fieldComparator) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldComparator);
        return fields.sorted(fieldComparator);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLines(Stream<Field> fields) {
        Objects.requireNonNull(fields);
        fields.forEachOrdered(System.out::println);
    }

}
