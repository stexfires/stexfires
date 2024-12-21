package stexfires.record;

import org.jspecify.annotations.Nullable;

import java.util.*;
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

    /**
     * Returns the optional category.
     * It has the type {@code String} and can be {@code null} or empty.
     * The meaning and uniqueness of the category is not specified.
     *
     * @return the optional category
     */
    @Nullable
    String category();

    /**
     * Returns whether the category is present.
     *
     * @return {@code true} if the category is present, otherwise {@code false}
     */
    default boolean hasCategory() {
        return category() != null;
    }

    /**
     * Returns the category or throws a {@code NullPointerException} if the category is {@code null}.
     *
     * @return the category
     * @throws NullPointerException if the category is {@code null}
     */
    default String categoryOrElseThrow() throws NullPointerException {
        String category = category();
        if (category == null) {
            throw new NullPointerException("No category! " + this);
        }
        return category;
    }

    /**
     * Returns the category as an {@code Optional}.
     *
     * @return the category as an {@code Optional}
     */
    default Optional<String> categoryAsOptional() {
        return Optional.ofNullable(category());
    }

    /**
     * Returns the category as a {@code Stream}.
     * If the category is {@code null}, an empty {@code Stream} is returned.
     *
     * @return the category as a {@code Stream}
     */
    default Stream<String> categoryAsStream() {
        return Stream.ofNullable(category());
    }

    /**
     * Returns the optional recordId.
     * It has the type {@code Long} and can be {@code null}.
     * The meaning and uniqueness of the recordId is not specified.
     * All values in the range of {@code Long} are allowed.
     *
     * @return the optional recordId
     */
    @Nullable
    Long recordId();

    /**
     * Returns whether the recordId is present.
     *
     * @return {@code true} if the recordId is present, otherwise {@code false}
     */
    default boolean hasRecordId() {
        return recordId() != null;
    }

    /**
     * Returns the recordId or throws a {@code NullPointerException} if the recordId is {@code null}.
     *
     * @return the recordId
     * @throws NullPointerException if the recordId is {@code null}
     */
    default Long recordIdOrElseThrow() throws NullPointerException {
        Long recordId = recordId();
        if (recordId == null) {
            throw new NullPointerException("No recordId! " + this);
        }
        return recordId;
    }

    /**
     * Returns the recordId as an {@code Optional}.
     *
     * @return the recordId as an {@code Optional}
     */
    default Optional<Long> recordIdAsOptional() {
        return Optional.ofNullable(recordId());
    }

    /**
     * Returns the recordId as an {@code OptionalLong}.
     *
     * @return the recordId as an {@code OptionalLong}
     */
    default OptionalLong recordIdAsOptionalLong() {
        Long recordId = recordId();
        return recordId != null ? OptionalLong.of(recordId) : OptionalLong.empty();
    }

    /**
     * Returns the recordId as a {@code Stream}.
     * If the recordId is {@code null}, an empty {@code Stream} is returned.
     *
     * @return the recordId as a {@code Stream}
     */
    default Stream<Long> recordIdAsStream() {
        return Stream.ofNullable(recordId());
    }

    /**
     * Returns the recordId as a {@code LongStream}.
     * If the recordId is {@code null}, an empty {@code LongStream} is returned.
     *
     * @return the recordId as a {@code LongStream}
     */
    default LongStream recordIdAsLongStream() {
        Long recordId = recordId();
        return recordId != null ? LongStream.of(recordId) : LongStream.empty();
    }

    /**
     * Returns the recordId as a {@code String}.
     * If the recordId is {@code null}, {@code null} is returned.
     *
     * @return the recordId as a nullable {@code String}
     */
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

    /**
     * Returns whether the number of contained fields is greater than zero.
     *
     * @return {@code true} if the number of contained fields is greater than zero, otherwise {@code false}
     */
    default boolean isNotEmpty() {
        return size() > 0;
    }

    /**
     * Returns whether the number of contained fields is zero.
     *
     * @return {@code true} if the number of contained fields is zero, otherwise {@code false}
     */
    default boolean isEmpty() {
        return size() <= 0;
    }

    /**
     * Returns whether the specified index is a valid index.
     * It must be greater than or equal to zero and less than the number of contained fields.
     *
     * @param index index to be tested
     * @return {@code true} if the specified index is a valid index, otherwise {@code false}
     */
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
    @Nullable
    TextField fieldAt(int index);

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
