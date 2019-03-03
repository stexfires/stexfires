package stexfires.core.comparator;

import stexfires.core.Field;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link Field}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.comparator.NULLS
 * @see stexfires.core.comparator.RecordComparators
 * @see stexfires.core.comparator.StringComparators
 * @see java.util.Comparator
 * @since 0.1
 */
public final class FieldComparators {

    private FieldComparators() {
    }

    public static Comparator<Field> index() {
        return comparingInt(Field::getIndex);
    }

    public static Comparator<Field> first() {
        return comparing(Field::isFirst);
    }

    public static Comparator<Field> last() {
        return comparing(Field::isLast);
    }

    public static Comparator<Field> value(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(Field::getValue, comparator);
    }

    public static Comparator<Field> value(Comparator<String> comparator,
                                          NULLS nulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Field::getValue, nulls.wrappedComparator(comparator));
    }

    public static Comparator<Field> length() {
        return comparingInt(Field::length);
    }

}
