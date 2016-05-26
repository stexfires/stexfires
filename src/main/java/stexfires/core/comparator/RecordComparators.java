package stexfires.core.comparator;

import stexfires.core.Record;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.ValueRecord;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.*;

/**
 * This class consists of {@code static} utility methods for constructing comparators for {@link Record}s.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordComparators {

    private RecordComparators() {
    }

    public static <T extends Record> Comparator<T> size() {
        return comparingInt(Record::size);
    }

    public static <T extends Record> Comparator<T> recordId() {
        return recordId(NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> recordId(NULLS nulls) {
        if (nulls == NULLS.FIRST) {
            return comparing(Record::getRecordId, nullsFirst(naturalOrder()));
        }
        return comparing(Record::getRecordId, nullsLast(naturalOrder()));
    }

    public static <T extends Record> Comparator<T> category(Comparator<String> comparator) {
        return category(comparator, NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> category(Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(Record::getCategory, nullsFirst(comparator));
        }
        return comparing(Record::getCategory, nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> category() {
        return category(Comparator.naturalOrder(), NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> category(NULLS nulls) {
        return category(Comparator.naturalOrder(), nulls);
    }

    public static <T extends Record> Comparator<T> category(Collator collator) {
        Objects.requireNonNull(collator);
        return category(collator::compare, NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> category(Collator collator, NULLS nulls) {
        Objects.requireNonNull(collator);
        return category(collator::compare, nulls);
    }

    public static <T extends Record> Comparator<T> valueAt(int index, Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing((record) -> record.getValueAt(index), nullsFirst(comparator));
        }
        return comparing((record) -> record.getValueAt(index), nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> valueAtOrElse(int index, String other, Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing((record) -> record.getValueAtOrElse(index, other), nullsFirst(comparator));
        }
        return comparing((record) -> record.getValueAtOrElse(index, other), nullsLast(comparator));
    }

    public static <T extends KeyRecord> Comparator<T> key(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(KeyRecord::getValueOfKeyField, comparator);
    }

    public static <T extends ValueRecord> Comparator<T> value(Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(ValueRecord::getValueOfValueField, nullsFirst(comparator));
        }
        return comparing(ValueRecord::getValueOfValueField, nullsLast(comparator));
    }

    public enum NULLS {
        FIRST, LAST
    }

}
