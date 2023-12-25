package stexfires.util;

import java.util.Comparator;
import java.util.Objects;

/**
 * Enum with two types for comparing {@code null} values: {@code FIRST, LAST}.
 *
 * @see java.util.Comparator#nullsFirst(Comparator)
 * @see java.util.Comparator#nullsLast(Comparator)
 * @since 0.1
 */
public enum SortNulls {

    /**
     * Sort {@code null} values first.
     *
     * @see java.util.Comparator#nullsFirst(Comparator)
     */
    FIRST,

    /**
     * Sort {@code null} values last.
     *
     * @see java.util.Comparator#nullsLast(Comparator)
     */
    LAST;

    /**
     * Returns a {@code Comparator} that wraps the given {@code Comparator} and sorts {@code null} values according to this {@code SortNulls}.
     *
     * @param comparator the {@code Comparator} to wrap. Must not be {@code null}.
     * @return a {@code Comparator} that wraps the given {@code Comparator} and sorts {@code null} values according to this {@code SortNulls}.
     * @see java.util.Comparator#nullsFirst(Comparator)
     * @see java.util.Comparator#nullsLast(Comparator)
     */
    public final <T> Comparator<T> wrap(Comparator<T> comparator) {
        Objects.requireNonNull(comparator);
        return this == SortNulls.FIRST ?
                Comparator.nullsFirst(comparator) :
                Comparator.nullsLast(comparator);
    }

    /**
     * Returns the opposite {@code SortNulls}.
     *
     * @return the opposite {@code SortNulls}.
     */
    public final SortNulls reverse() {
        return this == SortNulls.FIRST ? SortNulls.LAST : SortNulls.FIRST;
    }

}
