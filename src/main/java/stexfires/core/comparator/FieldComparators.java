package stexfires.core.comparator;

import stexfires.core.Field;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.*;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link Field}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.comparator.NULLS
 * @see java.util.Comparator
 * @since 0.1
 */
public final class FieldComparators {

    private FieldComparators() {
    }

    public static Comparator<Field> index() {
        return comparingInt(Field::getIndex);
    }

    public static Comparator<Field> length() {
        return comparingInt(Field::length);
    }

    public static Comparator<Field> value(Comparator<String> comparator,
                                          NULLS nulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Field::getValue,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

}
