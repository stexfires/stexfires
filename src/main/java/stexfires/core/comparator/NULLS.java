package stexfires.core.comparator;

import java.util.Comparator;

/**
 * Enum with two types for comparing NULLS: {@code FIRST, LAST}.
 *
 * @author Mathias Kalb
 * @see stexfires.core.comparator.RecordComparators
 * @see stexfires.core.comparator.FieldComparators
 * @see java.util.Comparator#nullsFirst(Comparator)
 * @see java.util.Comparator#nullsLast(Comparator)
 * @since 0.1
 */
public enum NULLS {

    FIRST, LAST

}
