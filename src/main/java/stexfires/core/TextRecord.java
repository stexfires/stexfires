package stexfires.core;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A {@link TextRecord} contains {@link Field}s with values.
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

    List<Field> listOfFields();

    Stream<Field> streamOfFields();

    String getCategory();

    default boolean hasCategory() {
        return getCategory() != null;
    }

    default String getCategoryOrElse(@Nullable String other) {
        return getCategory() != null ? getCategory() : other;
    }

    default Optional<String> getCategoryAsOptional() {
        return Optional.ofNullable(getCategory());
    }

    default Stream<String> streamOfCategory() {
        return Stream.ofNullable(getCategory());
    }

    Long getRecordId();

    default boolean hasRecordId() {
        return getRecordId() != null;
    }

    default OptionalLong getRecordIdAsOptionalLong() {
        return hasRecordId() ? OptionalLong.of(getRecordId()) : OptionalLong.empty();
    }

    default Optional<Long> getRecordIdAsOptional() {
        return Optional.ofNullable(getRecordId());
    }

    default LongStream streamOfRecordId() {
        if (getRecordId() == null) {
            return LongStream.empty();
        }
        return LongStream.of(getRecordId());
    }

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isValidIndex(int index) {
        return (index >= 0) && (index < size());
    }

    Field getFieldAt(int index);

    default Field getFirstField() {
        return getFieldAt(0);
    }

    default Field getLastField() {
        return getFieldAt(size() - 1);
    }

    @SuppressWarnings("ReturnOfNull")
    default String getValueAt(int index) {
        return isValidIndex(index) ? getFieldAt(index).value() : null;
    }

    default String getValueAtOrElse(int index, @Nullable String other) {
        return getValueAt(index) != null ? getValueAt(index) : other;
    }

    @SuppressWarnings("ReturnOfNull")
    default String getValueOfFirstField() {
        return isEmpty() ? null : getFirstField().value();
    }

    @SuppressWarnings("ReturnOfNull")
    default String getValueOfLastField() {
        return isEmpty() ? null : getLastField().value();
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
