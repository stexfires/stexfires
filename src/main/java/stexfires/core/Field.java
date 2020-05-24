package stexfires.core;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A Field is part of a {@link Record}.
 * <p>
 * It consists of a {@link String} value, an index
 * and a {@code boolean} indicating whether it is the last field of the {@code record}.
 * <p>
 * It is {@code immutable} and {@code thread-safe}.
 * The string value can be {@code null}.
 * The index of the first field is always {@code 0}.
 *
 * @author Mathias Kalb
 * @see stexfires.core.Record
 * @see stexfires.core.Fields
 * @see stexfires.core.Fields#FIRST_FIELD_INDEX
 * @since 0.1
 */
public final class Field {

    private final int index;
    private final boolean last;
    private final String value;

    private final int hashCode;

    @SuppressWarnings("MagicNumber")
    public Field(int index, boolean last, @Nullable String value) {
        if (index < Fields.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal field index! index=" + index);
        }
        this.index = index;
        this.last = last;
        this.value = value;

        // Calculate hashCode
        int calcHashCode = 31 * index + (last ? 1 : 0);
        if (value != null) {
            calcHashCode = 31 * calcHashCode + value.hashCode();
        }
        hashCode = calcHashCode;
    }

    public Field(int index, boolean last) {
        this(index, last, null);
    }

    public int getIndex() {
        return index;
    }

    public boolean isFirst() {
        return index == Fields.FIRST_FIELD_INDEX;
    }

    public boolean isLast() {
        return last;
    }

    public String getValue() {
        return value;
    }

    public String getValueOrElse(@Nullable String other) {
        return value != null ? value : other;
    }

    public Optional<String> getValueAsOptional() {
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
    public int length() {
        return value != null ? value.length() : 0;
    }

    public Stream<String> stream() {
        return Stream.ofNullable(value);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Field field = (Field) obj;
        return index == field.index &&
                last == field.last &&
                Objects.equals(value, field.value);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "Field{" + index + ", " + last + ", " + (value != null ? "'" + value + "'" : "null") + "}";
    }

}
