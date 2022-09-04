package stexfires.record;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
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
public record Field(int index, int maxIndex, @Nullable String value)
        implements Serializable {

    public Field {
        if (index < Fields.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
        if (index > maxIndex) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
    }

    public boolean isFirstField() {
        return index == Fields.FIRST_FIELD_INDEX;
    }

    public boolean isLastField() {
        return index == maxIndex;
    }

    public int recordSize() {
        return maxIndex + 1;
    }

    @Override
    public @Nullable String value() {
        return value;
    }

    public String orElse(@Nullable String other) {
        return value != null ? value : other;
    }

    public Optional<String> asOptional() {
        return Optional.ofNullable(value);
    }

    public boolean isNotNull() {
        return value != null;
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isEmpty() {
        return value != null && value.isEmpty();
    }

    public boolean isNullOrEmpty() {
        return value == null || value.isEmpty();
    }

    /**
     * @return the length of value or '0' for 'null' value
     * @see java.lang.String#length()
     */
    public int length() {
        return value != null ? value.length() : 0;
    }

    public Stream<String> stream() {
        return Stream.ofNullable(value);
    }

}
