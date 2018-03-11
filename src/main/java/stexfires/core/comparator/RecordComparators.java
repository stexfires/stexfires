package stexfires.core.comparator;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.ValueRecord;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.*;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link Record}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.comparator.NULLS
 * @see java.util.Comparator
 * @since 0.1
 */
public final class RecordComparators {

    private RecordComparators() {
    }

    public static <T extends Record> Comparator<T> category(Comparator<String> comparator,
                                                            NULLS nulls) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getCategory,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> category(Comparator<String> comparator) {
        return category(comparator, NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> category(NULLS nulls) {
        return category(naturalOrder(), nulls);
    }

    public static <T extends Record> Comparator<T> category() {
        return category(naturalOrder(), NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> category(Collator collator,
                                                            NULLS nulls) {
        Objects.requireNonNull(collator);
        return category(collator::compare, nulls);
    }

    public static <T extends Record> Comparator<T> category(Collator collator) {
        Objects.requireNonNull(collator);
        return category(collator::compare, NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> recordId(Comparator<Long> comparator,
                                                            NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getRecordId,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> recordId(Comparator<Long> comparator) {
        return recordId(naturalOrder(), NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> recordId(NULLS nulls) {
        return recordId(naturalOrder(), nulls);
    }

    public static <T extends Record> Comparator<T> recordId() {
        return recordId(naturalOrder(), NULLS.FIRST);
    }

    public static <T extends Record> Comparator<T> size() {
        return comparingInt(Record::size);
    }

    public static <T extends Record> Comparator<T> firstField(Comparator<Field> comparator,
                                                              NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getFirstField,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> lastField(Comparator<Field> comparator,
                                                             NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getLastField,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> fieldAt(int index,
                                                           Comparator<Field> comparator,
                                                           NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(record -> record.getFieldAt(index),
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> firstValue(Comparator<String> comparator,
                                                              NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getValueOfFirstField,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> lastValue(Comparator<String> comparator,
                                                             NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(Record::getValueOfLastField,
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends Record> Comparator<T> valueAt(int index,
                                                           Comparator<String> comparator,
                                                           NULLS nulls) {

        Objects.requireNonNull(comparator);
        Objects.requireNonNull(nulls);
        return comparing(record -> record.getValueAt(index),
                nulls == NULLS.FIRST ? nullsFirst(comparator) : nullsLast(comparator));
    }

    public static <T extends KeyRecord> Comparator<T> valueOfKeyField(Comparator<String> comparator) {
        Objects.requireNonNull(comparator);
        return comparing(KeyRecord::getValueOfKeyField, comparator);
    }

    public static <T extends ValueRecord> Comparator<T> valueOfValueField(Comparator<String> comparator,
                                                                          NULLS nulls) {
        Objects.requireNonNull(comparator);
        if (nulls == NULLS.FIRST) {
            return comparing(ValueRecord::getValueOfValueField, nullsFirst(comparator));
        }
        return comparing(ValueRecord::getValueOfValueField, nullsLast(comparator));
    }

}
