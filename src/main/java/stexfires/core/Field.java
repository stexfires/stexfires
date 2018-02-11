package stexfires.core;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class Field {

    private final int index;
    private final boolean last;
    private final String value;

    private final int hashCode;

    @SuppressWarnings("MagicNumber")
    public Field(int index, boolean last, String value) {
        if (index < Fields.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal index! index=" + index);
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

    public String getValueOrElse(String other) {
        return value != null ? value : other;
    }

    public Optional<String> getValueAsOptional() {
        return Optional.ofNullable(value);
    }

    public boolean valueEquals(String other) {
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

    public int length() {
        return value != null ? value.length() : 0;
    }

    public Stream<String> stream() {
        if (value == null) {
            return Stream.empty();
        }
        return Stream.of(value);
    }

    @Override
    public boolean equals(Object obj) {
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
