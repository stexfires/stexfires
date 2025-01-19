package stexfires.record;

import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

/**
 * A {@link TextField} is part of a {@link TextRecord}.
 * <p>
 * It consists of a text, an index and the maxIndex of the {@link TextRecord}.
 * <p>
 * It is {@code immutable} and {@code thread-safe}.
 * The {@code text} can be {@code null}.
 * The {@code index} of the first field is always {@code 0}.
 * The {@code index} of the last field is always identical with the {@code maxIndex}.
 * The {@code index} must never be less than {@code 0} or greater than the {@code maxIndex}.
 *
 * @see TextRecord
 * @see TextFields
 * @see TextField#FIRST_FIELD_INDEX
 * @since 0.1
 */
public record TextField(int index, int maxIndex, @Nullable String text)
        implements Serializable, Comparable<TextField> {

    public static final int FIRST_FIELD_INDEX = 0;

    public TextField {
        if (index < FIRST_FIELD_INDEX) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
        if (index > maxIndex) {
            throw new IllegalArgumentException("Illegal field index! index=" + index + " maxIndex=" + maxIndex);
        }
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * The comparison is based on the {@code index} of the {@code TextField}.
     * It returns {@code 0} if the {@code index} of the {@code TextField}s are equal.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     */
    @Override
    public int compareTo(TextField o) {
        Objects.requireNonNull(o);
        return Integer.compare(index, o.index);
    }

    public boolean isFirstField() {
        return index == FIRST_FIELD_INDEX;
    }

    public boolean isLastField() {
        return index == maxIndex;
    }

    public int recordSize() {
        return maxIndex + 1;
    }

    @Override
    public @Nullable String text() {
        return text;
    }

    public String orElse(String nonNullText) {
        Objects.requireNonNull(nonNullText);
        return (text != null) ? text : nonNullText;
    }

    public String orElseThrow() throws NullPointerException {
        if (text == null) {
            throw new NullPointerException("No text! " + this);
        }
        return text;
    }

    public Optional<String> asOptional() {
        return Optional.ofNullable(text);
    }

    public boolean isNotNull() {
        return text != null;
    }

    public boolean isNull() {
        return text == null;
    }

    public boolean isEmpty() {
        return (text != null) && text.isEmpty();
    }

    public boolean isNullOrEmpty() {
        return (text == null) || text.isEmpty();
    }

    /**
     * @return the length of the text or '0' if the text is 'null'
     * @see java.lang.String#length()
     */
    public int length() {
        return (text != null) ? text.length() : 0;
    }

    public Stream<String> stream() {
        return Stream.ofNullable(text);
    }

}
