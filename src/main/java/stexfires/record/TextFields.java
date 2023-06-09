package stexfires.record;

import org.jetbrains.annotations.NotNull;
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
 * @author Mathias Kalb
 * @see TextField
 * @see TextRecord
 * @see stexfires.record.mapper.field.FieldTextMapper
 * @since 0.1
 */
public final class TextFields {

    public static final int FIRST_FIELD_INDEX = 0;
    public static final String DEFAULT_FIELD_TEXT_DELIMITER = ", ";

    private static final TextField[] EMPTY_FIELD_ARRAY = new TextField[0];

    private TextFields() {
    }

    public static @NotNull TextField[] emptyArray() {
        return EMPTY_FIELD_ARRAY;
    }

    public static @NotNull TextField[] newArray(@NotNull Collection<String> texts) {
        Objects.requireNonNull(texts);
        TextField[] fields = new TextField[texts.size()];
        int index = FIRST_FIELD_INDEX;
        for (String text : texts) {
            fields[index] = new TextField(index, texts.size() - 1, text);
            index++;
        }
        return fields;
    }

    public static @NotNull TextField[] newArray(@NotNull Stream<String> texts) {
        Objects.requireNonNull(texts);
        return newArray(texts.collect(Collectors.toList()));
    }

    @SuppressWarnings({"Convert2streamapi"})
    public static @NotNull TextField[] newArray(String... texts) {
        Objects.requireNonNull(texts);
        TextField[] fields = new TextField[texts.length];
        for (int index = FIRST_FIELD_INDEX; index < texts.length; index++) {
            fields[index] = new TextField(index, texts.length - 1, texts[index]);
        }
        return fields;
    }

    @SuppressWarnings({"Convert2streamapi", "ExplicitArrayFilling"})
    public static @NotNull TextField[] newArray(int length, @NotNull Supplier<String> textSupplier) {
        if (length < 0) {
            throw new IllegalArgumentException("Illegal length! length=" + length);
        }
        Objects.requireNonNull(textSupplier);
        TextField[] fields = new TextField[length];
        for (int index = FIRST_FIELD_INDEX; index < length; index++) {
            fields[index] = new TextField(index, length - 1, textSupplier.get());
        }
        return fields;
    }

    public static List<String> collectTexts(@NotNull TextRecord record) {
        Objects.requireNonNull(record);
        return collectTexts(record.streamOfFields(), new IdentityFieldTextMapper());
    }

    public static List<String> collectTexts(@NotNull TextRecord record, @NotNull FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(fieldTextMapper);
        return collectTexts(record.streamOfFields(), fieldTextMapper);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static List<String> collectTexts(@NotNull TextField[] fields) {
        Objects.requireNonNull(fields);
        return collectTexts(Arrays.stream(fields), new IdentityFieldTextMapper());
    }

    public static List<String> collectTexts(@NotNull TextField[] fields, @NotNull FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return collectTexts(Arrays.stream(fields), fieldTextMapper);
    }

    public static List<String> collectTexts(@NotNull Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return collectTexts(fields, new IdentityFieldTextMapper());
    }

    public static List<String> collectTexts(@NotNull Stream<TextField> fields, @NotNull FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return fields.map(fieldTextMapper::mapToText).collect(Collectors.toList());
    }

    public static List<TextField> collectFields(@NotNull Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return fields.collect(Collectors.toList());
    }

    public static String joinTexts(@NotNull TextRecord record) {
        Objects.requireNonNull(record);
        return joinTexts(record.streamOfFields(), DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(@NotNull TextRecord record, @NotNull CharSequence delimiter) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(delimiter);
        return joinTexts(record.streamOfFields(), delimiter);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static String joinTexts(@NotNull TextField[] fields) {
        Objects.requireNonNull(fields);
        return joinTexts(Arrays.stream(fields), DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(@NotNull TextField[] fields, @NotNull CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return joinTexts(Arrays.stream(fields), delimiter);
    }

    public static String joinTexts(@NotNull Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        return joinTexts(fields, DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public static String joinTexts(@NotNull Stream<TextField> fields, @NotNull CharSequence delimiter) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(delimiter);
        return fields.map(TextField::text).collect(Collectors.joining(delimiter));
    }

    public static Stream<String> mapToTexts(@NotNull Stream<TextField> fields, @NotNull FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldTextMapper);
        return fields.map(fieldTextMapper::mapToText);
    }

    public static Stream<TextField> sortFields(@NotNull Stream<TextField> fields, @NotNull Comparator<TextField> fieldComparator) {
        Objects.requireNonNull(fields);
        Objects.requireNonNull(fieldComparator);
        return fields.sorted(fieldComparator);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLines(@NotNull Stream<TextField> fields) {
        Objects.requireNonNull(fields);
        fields.forEachOrdered(System.out::println);
    }

}
