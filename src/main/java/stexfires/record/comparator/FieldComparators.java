package stexfires.record.comparator;

import stexfires.record.Field;
import stexfires.util.SortNulls;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link Field}s.
 *
 * @author Mathias Kalb
 * @see stexfires.record.comparator.RecordComparators
 * @see stexfires.util.SortNulls
 * @see stexfires.util.StringComparators
 * @see java.util.Comparator
 * @since 0.1
 */
public final class FieldComparators {

    private FieldComparators() {
    }

    public static Comparator<Field> index() {
        return comparingInt(Field::index);
    }

    public static Comparator<Field> maxIndex() {
        return comparingInt(Field::maxIndex);
    }

    public static Comparator<Field> isFirstField() {
        return comparing(Field::isFirstField);
    }

    public static Comparator<Field> isLastField() {
        return comparing(Field::isLastField);
    }

    public static Comparator<Field> value(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(Field::value, comparator);
    }

    public static Comparator<Field> value(Comparator<String> comparator,
                                          SortNulls sortNulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(Field::value, sortNulls.wrappedComparator(comparator));
    }

    public static Comparator<Field> valueLength() {
        return comparingInt(Field::valueLength);
    }

}
