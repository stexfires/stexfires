package stexfires.record.comparator;

import java.util.Comparator;
import java.util.Objects;

/**
 * Enum with two types for comparing NULLS: {@code FIRST, LAST}.
 *
 * @author Mathias Kalb
 * @see stexfires.record.comparator.RecordComparators
 * @see stexfires.record.comparator.FieldComparators
 * @see java.util.Comparator#nullsFirst(Comparator)
 * @see java.util.Comparator#nullsLast(Comparator)
 * @since 0.1
 */
public enum NULLS {

    FIRST, LAST;

    public final <T> Comparator<T> wrappedComparator(Comparator<T> comparator) {
        Objects.requireNonNull(comparator);
        return this == NULLS.FIRST ?
                Comparator.nullsFirst(comparator) :
                Comparator.nullsLast(comparator);
    }

}
