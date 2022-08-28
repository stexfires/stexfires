package stexfires.util;

import java.util.Comparator;
import java.util.Objects;

/**
 * Enum with two types for comparing {@code null} values: {@code FIRST, LAST}.
 *
 * @author Mathias Kalb
 * @see java.util.Comparator#nullsFirst(Comparator)
 * @see java.util.Comparator#nullsLast(Comparator)
 * @since 0.1
 */
public enum SortNulls {

    FIRST, LAST;

    public final <T> Comparator<T> wrappedComparator(Comparator<T> comparator) {
        Objects.requireNonNull(comparator);
        return this == SortNulls.FIRST ?
                Comparator.nullsFirst(comparator) :
                Comparator.nullsLast(comparator);
    }

}
