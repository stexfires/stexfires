package org.textfiledatatools.core;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int index;
    private final boolean last;
    private final String value;

    public Field(int index, boolean last, String value) {
        if (index < Records.FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Negative index not allowed!");
        }
        this.index = index;
        this.last = last;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public boolean isFirst() {
        return index == Records.FIRST_FIELD_INDEX;
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

    public boolean isValueEquals(String other) {
        return Objects.equals(value, other);
    }

    public boolean isNullValue() {
        return value == null;
    }

    public boolean isEmptyValue() {
        return value != null && value.isEmpty();
    }

    public boolean isNullOrEmptyValue() {
        return value == null || value.isEmpty();
    }

    public int getLength() {
        return value != null ? value.length() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field that = (Field) o;

        if (index != that.index) return false;
        if (last != that.last) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (last ? 1 : 0);
        result = 31 * result + (value != null ? value.hashCode() : 1); // Replaced 0 with 1
        return result;
    }

    @Override
    public String toString() {
        return "Field{" + index + ", " + last + ", " + (value != null ? "'" + value + "'" : "null") + "}";
    }

}
