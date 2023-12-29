package stexfires.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.mapper.field.IdentityFieldTextMapper;

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
 * for operating on {@link TextField}s.
 *
 * @see TextField
 * @see TextRecord
 * @see stexfires.record.mapper.field.FieldTextMapper
 * @since 0.1
 */
public final class TextFields {

    public static final String DEFAULT_FIELD_TEXT_DELIMITER = ", ";

    private static final TextField[] EMPTY_FIELD_ARRAY = new TextField[0];

    private TextFields() {
    }

    public static TextField[] emptyArray() {
        return EMPTY_FIELD_ARRAY;
    }

    public static TextField[] newArray(Collection<@Nullable String> texts) {
        Objects.requireNonNull(texts);
        TextField[] fields = new TextField[texts.size()];
        int index = TextField.FIRST_FIELD_INDEX;
        for (String text : texts) {
            fields[index] = new TextField(index, texts.size() - 1, text);
            index++;
        }
        return fields;
    }

    public static TextField[] newArray(Stream<String> texts) {
        Objects.requireNonNull(texts);
        return newArray(texts.toList());
    }

    @SuppressWarnings({"Convert2streamapi"})
    public static TextField[] newArray(String... texts) {
        Objects.requireNonNull(texts);
        TextField[] fields = new TextField[texts.length];
        for (int index = TextField.FIRST_FIELD_INDEX; index < texts.length; index++) {
            fields[index] = new TextField(index, texts.length - 1, texts[index]);
        }
        return fields;
    }

    @SuppressWarnings({"Convert2streamapi", "ExplicitArrayFilling"})
    public static TextField[] newArray(int length, Supplier<String> textSupplier) {
        if (length < 0) {
            throw new IllegalArgumentException("Illegal length! length=" + length);
        }
        Objects.requireNonNull(textSupplier);
        TextField[] fields = new TextField[length];
        for (int index = TextField.FIRST_FIELD_INDEX; index < length; index++) {
            fields[index] = new TextField(index, length - 1, textSupplier.get());
        }
        return fields;
    }

    public static List<@Nullable String> collectTexts(TextRecord record) {
        Objects.requireNonNull(record);
        return collectTexts(record.streamOfFields(), new IdentityFieldTextMapper());
    }

    public static List<@Nullable String> collectTexts(TextRecord record, FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(fieldTextMapper);
        return collectTexts(record.streamOfFields(), fieldTextMapper);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static List<@Nullable String> collectTexts(TextField[] fields) {
        Objects.requireNonNull(fields);
        return collectTexts(Arrays.stream(fields), new IdentityFieldTextMapper());
    }

    public static List<@Nullable String> collectTexts(TextField[] fields, FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return collectTexts(Arrays.stream(fields), fieldTextMapper);
    }

    public static List<@Nullable String> collectTexts(Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return collectTexts(fields, new IdentityFieldTextMapper());
    }

    public static List<@Nullable String> collectTexts(Stream<TextField> fields, FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return fields.map(fieldTextMapper::mapToText).toList();
    }

    public static List<TextField> collectFields(Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return fields.toList();
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
    public static String joinTexts(TextField[] fields) {
        Objects.requireNonNull(fields);
        return joinTexts(Arrays.stream(fields), DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(TextField[] fields, CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return joinTexts(Arrays.stream(fields), delimiter);
    }

    public static String joinTexts(Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return joinTexts(fields, DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(Stream<TextField> fields, CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return fields.map(TextField::text).collect(Collectors.joining(delimiter));
    }

    public static Stream<String> mapToTexts(Stream<TextField> fields, FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return fields.map(fieldTextMapper::mapToText);
    }

    public static Stream<TextField> sortFields(Stream<TextField> fields, Comparator<TextField> fieldComparator) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldComparator);
        return fields.sorted(fieldComparator);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLines(Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        fields.forEachOrdered(System.out::println);
    }

}
