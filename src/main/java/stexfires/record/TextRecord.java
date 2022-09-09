package stexfires.record;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A {@link TextRecord} consists of {@link Field}s, each of which contains a text.
 * It can also contain a category and a record id.
 * <p>
 * It must be {@code immutable} and {@code thread-safe}.
 * The field index must start with {@code 0}.
 *
 * @author Mathias Kalb
 * @see Field
 * @see TextRecords
 * @see Fields#FIRST_FIELD_INDEX
 * @since 0.1
 */
public interface TextRecord {

    Field[] arrayOfFields();

    default List<Field> listOfFields() {
        return switch (size()) {
            case 0 -> Collections.emptyList();
            case 1 -> Collections.singletonList(firstField());
            default -> Arrays.asList(arrayOfFields());
        };
    }

    default List<Field> listOfFieldsReversed() {
        if (size() == 0) {
            return Collections.emptyList();
        } else if (size() == 1) {
            return Collections.singletonList(firstField());
        } else {
            var fieldList = Arrays.asList(arrayOfFields());
            Collections.reverse(fieldList);
            return fieldList;
        }
    }

    default Stream<Field> streamOfFields() {
        return switch (size()) {
            case 0 -> Stream.empty();
            case 1 -> Stream.of(firstField());
            default -> Arrays.stream(arrayOfFields());
        };
    }

    String category();

    default boolean hasCategory() {
        return category() != null;
    }

    default String categoryOrElse(@Nullable String other) {
        return category() != null ? category() : other;
    }

    default Optional<String> categoryAsOptional() {
        return Optional.ofNullable(category());
    }

    default Stream<String> categoryAsStream() {
        return Stream.ofNullable(category());
    }

    Long recordId();

    default boolean hasRecordId() {
        return recordId() != null;
    }

    default OptionalLong recordIdAsOptionalLong() {
        return hasRecordId() ? OptionalLong.of(recordId()) : OptionalLong.empty();
    }

    default Optional<Long> recordIdAsOptional() {
        return Optional.ofNullable(recordId());
    }

    default LongStream recordIdAsStream() {
        if (recordId() == null) {
            return LongStream.empty();
        }
        return LongStream.of(recordId());
    }

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isValidIndex(int index) {
        return (index >= 0) && (index < size());
    }

    Field fieldAt(int index);

    default Field firstField() {
        return fieldAt(0);
    }

    default Field lastField() {
        return fieldAt(size() - 1);
    }

    @SuppressWarnings("ReturnOfNull")
    default String textAt(int index) {
        return isValidIndex(index) ? fieldAt(index).text() : null;
    }

    default String textAtOrElse(int index, @Nullable String other) {
        return textAt(index) != null ? textAt(index) : other;
    }

    @SuppressWarnings("ReturnOfNull")
    default String firstText() {
        return isEmpty() ? null : firstField().text();
    }

    @SuppressWarnings("ReturnOfNull")
    default String lastText() {
        return isEmpty() ? null : lastField().text();
    }

    /**
     * Compare class, category, recordId and all fields.
     */
    @Override
    boolean equals(@Nullable Object obj);

    @Override
    int hashCode();

    @Override
    String toString();

}
