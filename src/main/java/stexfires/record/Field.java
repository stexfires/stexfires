package stexfires.record;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A {@link Field} is part of a {@link TextRecord}.
 * <p>
 * It consists of a {@link String} value, an index and the maxIndex of the {@link TextRecord}.
 * <p>
 * It is {@code immutable} and {@code thread-safe}.
 * The string value can be {@code null}.
 * The index of the first field is always {@code 0}.
 * The index of the last field is always identical with the maxIndex.
 * The index must never be less than {@code 0} or greater than the maxIndex.
 *
 * @author Mathias Kalb
 * @see TextRecord
 * @see Fields
 * @see Fields#FIRST_FIELD_INDEX
 * @since 0.1
 */
public record Field(int index, int maxIndex, String value) {

    public Field(int index, int maxIndex, @Nullable String value) {
        if (index < Fields.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
        if (index > maxIndex) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
        this.index = index;
        this.maxIndex = maxIndex;
        this.value = value;
    }

    public boolean isFirstField() {
        return index == Fields.FIRST_FIELD_INDEX;
    }

    public boolean isLastField() {
        return index == maxIndex;
    }

    public boolean hasValue() {
        return value != null;
    }

    public String valueOrElse(@Nullable String other) {
        return value != null ? value : other;
    }

    public Optional<String> valueAsOptional() {
        return Optional.ofNullable(value);
    }

    public boolean valueEquals(@Nullable String other) {
        return Objects.equals(value, other);
    }

    public boolean valueIsNull() {
        return value == null;
    }

    public boolean valueIsEmpty() {
        return value != null && value.isEmpty();
    }

    public boolean valueIsNullOrEmpty() {
        return value == null || value.isEmpty();
    }

    /**
     * @return the length of value or '0' for 'null' value
     * @see java.lang.String#length()
     */
    public int valueLength() {
        return value != null ? value.length() : 0;
    }

    public Stream<String> valueAsStream() {
        return Stream.ofNullable(value);
    }

}
