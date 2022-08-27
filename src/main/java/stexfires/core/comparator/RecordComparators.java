package stexfires.core.comparator;

import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.KeyRecord;
import stexfires.core.ValueRecord;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link stexfires.core.TextRecord}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.comparator.NULLS
 * @see stexfires.core.comparator.FieldComparators
 * @see stexfires.core.comparator.StringComparators
 * @see java.util.Comparator
 * @since 0.1
 */
public final class RecordComparators {

    private RecordComparators() {
    }

    public static <T extends TextRecord> Comparator<T> category(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(TextRecord::category, comparator);
    }

    public static <T extends TextRecord> Comparator<T> category(Comparator<String> comparator,
                                                                NULLS nulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(TextRecord::category, nulls.wrappedComparator(comparator));
    }

    public static <T extends TextRecord> Comparator<T> recordId(Comparator<Long> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(TextRecord::recordId, comparator);
    }

    public static <T extends TextRecord> Comparator<T> recordId(Comparator<Long> comparator,
                                                                NULLS nulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(TextRecord::recordId, nulls.wrappedComparator(comparator));
    }

    public static <T extends TextRecord> Comparator<T> recordId(NULLS nulls) {
        Objects.requireNonNull(nulls);
        Comparator<Long> naturalOrderComparator = naturalOrder();
        return comparing(TextRecord::recordId, nulls.wrappedComparator(naturalOrderComparator));
    }

    public static <T extends TextRecord> Comparator<T> size() {
        return comparingInt(TextRecord::size);
    }

    public static <T extends TextRecord> Comparator<T> field(Function<? super T, Field> fieldFunction,
                                                             Comparator<Field> comparator,
                                                             NULLS nulls) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(fieldFunction, nulls.wrappedComparator(comparator));
    }

    public static <T extends TextRecord> Comparator<T> fieldAt(int index,
                                                               Comparator<Field> comparator,
                                                               NULLS nulls) {
        return field(record -> record.fieldAt(index), comparator, nulls);
    }

    public static <T extends TextRecord> Comparator<T> firstField(Comparator<Field> comparator,
                                                                  NULLS nulls) {
        return field(TextRecord::firstField, comparator, nulls);
    }

    public static <T extends TextRecord> Comparator<T> lastField(Comparator<Field> comparator,
                                                                 NULLS nulls) {
        return field(TextRecord::lastField, comparator, nulls);
    }

    public static <T extends TextRecord> Comparator<T> value(Function<? super T, String> valueFunction,
                                                             Comparator<String> comparator,
                                                             NULLS nulls) {
        Objects.requireNonNull(valueFunction);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(valueFunction, nulls.wrappedComparator(comparator));
    }

    public static <T extends TextRecord> Comparator<T> valueAt(int index,
                                                               Comparator<String> comparator,
                                                               NULLS nulls) {
        return value(record -> record.valueAt(index), comparator, nulls);
    }

    public static <T extends TextRecord> Comparator<T> valueOfFirstField(Comparator<String> comparator,
                                                                         NULLS nulls) {
        return value(TextRecord::valueOfFirstField, comparator, nulls);
    }

    public static <T extends TextRecord> Comparator<T> valueOfLastField(Comparator<String> comparator,
                                                                        NULLS nulls) {
        return value(TextRecord::valueOfLastField, comparator, nulls);
    }

    public static <T extends KeyRecord> Comparator<T> valueOfKeyField(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(KeyRecord::valueOfKeyField, comparator);
    }

    public static <T extends ValueRecord> Comparator<T> valueOfValueField(Comparator<String> comparator,
                                                                          NULLS nulls) {
        return value(ValueRecord::valueOfValueField, comparator, nulls);
    }

}
