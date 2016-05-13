package org.textfiledatatools.core;

import java.text.Collator;
import java.util.Comparator;

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
        return category(collator::compare, NULLS.FIRST);
    }

    public static Comparator<Record> category(Collator collator, NULLS nulls) {
        return category(collator::compare, nulls);
    }

}
