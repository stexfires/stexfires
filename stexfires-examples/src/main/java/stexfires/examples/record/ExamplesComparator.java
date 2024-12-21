package stexfires.examples.record;

import stexfires.record.*;
import stexfires.record.comparator.FieldComparators;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.SortNulls;

import java.util.Comparator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesComparator {

    private ExamplesComparator() {
    }

    private static Stream<ManyFieldsRecord> generateStreamManyValuesRecord() {
        return Stream.of(
                new ManyFieldsRecord("C4", 6L, "value0"),
                new ManyFieldsRecord(null, 2L, "value0", "value1b"),
                new ManyFieldsRecord("C1", 5L, "value0", "value1a", "value2"),
                new ManyFieldsRecord("C2", 0L, "value0b"),
                new ManyFieldsRecord(null, 2L, "value0a"),
                new ManyFieldsRecord(null, 1L, "value0c"),
                new ManyFieldsRecord("C0", (Long) null, "value0"),
                new ManyFieldsRecord("C4", 7L),
                new ManyFieldsRecord("C4", 8L, null, null, null, null),
                new ManyFieldsRecord()
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueFieldsRecord("key4", "value5"),
                new KeyValueFieldsRecord("key2", "value2"),
                new KeyValueFieldsRecord("key0", "value1"),
                new KeyValueFieldsRecord("key2", "value3"),
                new KeyValueFieldsRecord("key5", null),
                new KeyValueFieldsRecord("key1", "value0")
        );
    }

    private static void printComparatorRecord(String title, Comparator<? super ManyFieldsRecord> recordComparator) {
        System.out.println("--" + title);
        TextRecordStreams.sortAndConsume(generateStreamManyValuesRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorKeyValueRecord(String title, Comparator<? super KeyValueRecord> recordComparator) {
        System.out.println("--" + title);
        TextRecordStreams.sortAndConsume(generateStreamKeyValueRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorField(String title, Comparator<TextField> fieldComparator) {
        System.out.println("--" + title);
        TextFields.printLines(TextFields.sortFields(generateStreamManyValuesRecord().flatMap(TextRecord::streamOfFields), fieldComparator));
    }

    private static void showRecordComparators() {
        System.out.println("-showRecordComparators---");

        // category
        printComparatorRecord("category: nullsFirst(naturalOrder)", RecordComparators.category(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("category: reverseOrder, SortNulls.LAST", RecordComparators.category(Comparator.reverseOrder(), SortNulls.LAST));

        // recordId
        printComparatorRecord("recordId: nullsFirst(naturalOrder)", RecordComparators.recordId(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("recordId: reverseOrder, SortNulls.LAST", RecordComparators.recordId(Comparator.reverseOrder(), SortNulls.LAST));
        printComparatorRecord("recordId: SortNulls.LAST", RecordComparators.recordId(SortNulls.LAST));

        // size
        printComparatorRecord("size", RecordComparators.size());

        // field
        printComparatorRecord("field: getFieldAt(1), length, SortNulls.LAST", RecordComparators.field(record -> record.fieldAt(1), FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("fieldAt: 1, length, SortNulls.LAST", RecordComparators.fieldAt(1, FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("firstField: length, SortNulls.LAST", RecordComparators.firstField(FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("lastField: length, SortNulls.LAST", RecordComparators.lastField(FieldComparators.length(), SortNulls.LAST));

        // value
        printComparatorRecord("value: getValueAt(1), naturalOrder(), SortNulls.LAST", RecordComparators.text(record -> record.textAt(1), Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("textAt: 1, naturalOrder(), SortNulls.LAST", RecordComparators.textAt(1, Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("firstValue: naturalOrder(), SortNulls.LAST", RecordComparators.firstText(Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("lastValue: naturalOrder(), SortNulls.LAST", RecordComparators.lastText(Comparator.naturalOrder(), SortNulls.LAST));

        // KeyValueFieldsRecord
        printComparatorKeyValueRecord("key: naturalOrder()", RecordComparators.key(Comparator.naturalOrder()));
        printComparatorKeyValueRecord("value: naturalOrder(), SortNulls.LAST", RecordComparators.value(Comparator.naturalOrder(), SortNulls.LAST));

        // Combined
        printComparatorRecord("Combined: category(reversed(), SortNulls.LAST), recordId(SortNulls.LAST)", RecordComparators.category(Comparator.reverseOrder(), SortNulls.LAST).thenComparing(RecordComparators.recordId(SortNulls.LAST)));
    }

    private static void showFieldComparators() {
        System.out.println("-showFieldComparators---");

        printComparatorField("index", FieldComparators.index());
        printComparatorField("first", FieldComparators.isFirstField());
        printComparatorField("last", FieldComparators.isLastField());
        printComparatorField("text: nullsLast(naturalOrder())", FieldComparators.text(Comparator.nullsLast(Comparator.naturalOrder())));
        printComparatorField("text: naturalOrder(), SortNulls.LAST", FieldComparators.text(Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorField("length", FieldComparators.length());
    }

    public static void main(String... args) {
        showRecordComparators();
        showFieldComparators();
    }

}
