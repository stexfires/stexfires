package org.textfiledatatools.core;

import org.textfiledatatools.core.record.KeyRecord;
import org.textfiledatatools.core.record.ValueRecord;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.*;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordComparators {

    public enum NULLS {
        FIRST, LAST
    }

    private RecordComparators() {
    }

    public static Comparator<Record> size() {
        return comparingInt(Record::size);
    }

    public static Comparator<Record> recordId() {
        return recordId(NULLS.FIRST);
    }

    public static Comparator<Record> recordId(NULLS nulls) {
        if (nulls == NULLS.FIRST) {
            return comparing(Record::getRecordId, nullsFirst(naturalOrder()));
        }
        return comparing(Record::getRecordId, nullsLast(naturalOrder()));
    }

    public static Comparator<Record> category(Comparator<String> comparator) {
        return category(comparator, NULLS.FIRST);
    }

    public static Comparator<Record> category(Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(Record::getCategory, nullsFirst(comparator));
        }
        return comparing(Record::getCategory, nullsLast(comparator));
    }

    public static Comparator<Record> category() {
        return category(Comparator.naturalOrder(), NULLS.FIRST);
    }

    public static Comparator<Record> category(NULLS nulls) {
        return category(Comparator.naturalOrder(), nulls);
    }

    public static Comparator<Record> category(Collator collator) {
        Objects.requireNonNull(collator);
        return category(collator::compare, NULLS.FIRST);
    }

    public static Comparator<Record> category(Collator collator, NULLS nulls) {
        Objects.requireNonNull(collator);
        return category(collator::compare, nulls);
    }

    public static Comparator<Record> valueAt(int index, Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing((record) -> record.getValueAt(index), nullsFirst(comparator));
        }
        return comparing((record) -> record.getValueAt(index), nullsLast(comparator));
    }

    public static Comparator<KeyRecord> key(Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(KeyRecord::getValueOfKeyField, nullsFirst(comparator));
        }
        return comparing(KeyRecord::getValueOfKeyField, nullsLast(comparator));
    }

    public static Comparator<ValueRecord> value(Comparator<String> comparator, NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(ValueRecord::getValueOfValueField, nullsFirst(comparator));
        }
        return comparing(ValueRecord::getValueOfValueField, nullsLast(comparator));
    }

}
