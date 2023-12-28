package stexfires.record;

import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A {@link TextRecord} consists of {@link TextField}s, each of which contains a text.
 * It can also contain a {@code category} and a {@code recordId}.
 * <p>
 * It must be {@code immutable} and {@code thread-safe}.
 * The implementation can be a 'Java' {@code record}.
 * The indexes of the contained fields must be ascending and start with {@code 0}.
 *
 * @see TextField
 * @see TextRecords
 * @see TextField#FIRST_FIELD_INDEX
 * @since 0.1
 */
public interface TextRecord {

    TextField[] arrayOfFields();

    default List<TextField> listOfFields() {
        return switch (size()) {
            case 0 -> Collections.emptyList();
            case 1 -> Collections.singletonList(fieldAtOrElseThrow(TextField.FIRST_FIELD_INDEX));
            default -> Arrays.asList(arrayOfFields());
        };
    }

    default List<TextField> listOfFieldsReversed() {
        if (size() == 0) {
            return Collections.emptyList();
        } else if (size() == 1) {
            return Collections.singletonList(fieldAtOrElseThrow(TextField.FIRST_FIELD_INDEX));
        } else {
            var fieldList = Arrays.asList(arrayOfFields());
            Collections.reverse(fieldList);
            return fieldList;
        }
    }

    default Stream<TextField> streamOfFields() {
        return switch (size()) {
            case 0 -> Stream.empty();
            case 1 -> Stream.of(fieldAtOrElseThrow(TextField.FIRST_FIELD_INDEX));
            default -> Arrays.stream(arrayOfFields());
        };
    }

    default Stream<@Nullable String> streamOfTexts() {
        return streamOfFields().map(TextField::text);
    }

    default Stream<Optional<String>> streamOfTextsAsOptional() {
        return streamOfFields().map(TextField::asOptional);
    }

    @Nullable String category();

    default boolean hasCategory() {
        return category() != null;
    }

    default String categoryOrElseThrow() throws NullPointerException {
        String category = category();
        if (category == null) {
            throw new NullPointerException("No category! " + this);
        }
        return category;
    }

    default Optional<String> categoryAsOptional() {
        return Optional.ofNullable(category());
    }

    default Stream<String> categoryAsStream() {
        return Stream.ofNullable(category());
    }

    @Nullable Long recordId();

    default boolean hasRecordId() {
        return recordId() != null;
    }

    default Long recordIdOrElseThrow() throws NullPointerException {
        Long recordId = recordId();
        if (recordId == null) {
            throw new NullPointerException("No recordId! " + this);
        }
        return recordId;
    }

    default Optional<Long> recordIdAsOptional() {
        return Optional.ofNullable(recordId());
    }

    default OptionalLong recordIdAsOptionalLong() {
        Long recordId = recordId();
        return recordId != null ? OptionalLong.of(recordId) : OptionalLong.empty();
    }

    default Stream<Long> recordIdAsStream() {
        return Stream.ofNullable(recordId());
    }

    default LongStream recordIdAsLongStream() {
        Long recordId = recordId();
        return recordId != null ? LongStream.of(recordId) : LongStream.empty();
    }

    default @Nullable String recordIdAsString() {
        Long recordId = recordId();
        return recordId != null ? recordId.toString() : null;
    }

    /**
     * Returns the number of contained fields.
     * It must not be negative.
     *
     * @return number of contained fields
     */
    int size();

    default boolean isNotEmpty() {
        return size() > 0;
    }

    default boolean isEmpty() {
        return size() <= 0;
    }

    default boolean isValidIndex(int index) {
        return (index >= 0) && (index < size());
    }

    /**
     * Returns the field at the specified index.
     * If the index is out of range, {@code null} is returned.
     *
     * @param index index of the field to return
     * @return the field at the specified index. If the index is out of range, {@code null} is returned
     */
    @Nullable TextField fieldAt(int index);

    default TextField fieldAtOrElseThrow(int index) throws NullPointerException {
        TextField field = fieldAt(index);
        if (field == null) {
            throw new NullPointerException("No field at index " + index + "! " + this);
        }
        return field;
    }

    default Optional<TextField> fieldAtAsOptional(int index) {
        return Optional.ofNullable(fieldAt(index));
    }

    default @Nullable TextField firstField() {
        return fieldAt(TextField.FIRST_FIELD_INDEX);
    }

    default TextField firstFieldOrElseThrow() throws NullPointerException {
        return fieldAtOrElseThrow(TextField.FIRST_FIELD_INDEX);
    }

    default @Nullable TextField lastField() {
        return fieldAt(size() - 1);
    }

    default TextField lastFieldOrElseThrow() throws NullPointerException {
        return fieldAtOrElseThrow(size() - 1);
    }

    default @Nullable String textAt(int index) {
        TextField field = fieldAt(index);
        if (field == null) {
            return null;
        }
        return field.text();
    }

    default String textAtOrElse(int index, String otherText) {
        Objects.requireNonNull(otherText);
        String textAt = textAt(index);
        return textAt != null ? textAt : otherText;
    }

    default Optional<String> textAtAsOptional(int index) {
        return Optional.ofNullable(textAt(index));
    }

    default @Nullable String firstText() {
        return textAt(TextField.FIRST_FIELD_INDEX);
    }

    default @Nullable String lastText() {
        return textAt(size() - 1);
    }

}
