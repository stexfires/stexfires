package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ConstantProducer;
import stexfires.record.producer.DividingProducer;
import stexfires.record.producer.KeyValueRecordProducer;
import stexfires.record.producer.RecordProducer;
import stexfires.record.producer.ValueRecordProducer;
import stexfires.util.Strings;
import stexfires.util.function.Suppliers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesProducer {

    private ExamplesProducer() {
    }

    private static void printProducer(RecordProducer<? extends TextRecord> recordProducer) {
        TextRecordStreams.printLines(TextRecordStreams.produce(recordProducer));
    }

    private static void showConstantProducer() {
        System.out.println("-showConstantProducer---");

        long streamSize = 2L;

        printProducer(new ConstantProducer<>(streamSize, new KeyValueFieldsRecord("key1", "value1")));
        printProducer(new ConstantProducer<>(streamSize, TextRecords.empty()));
        printProducer(new ConstantProducer<>(streamSize, new ValueFieldRecord("value1")));
        printProducer(new ConstantProducer<>(streamSize, new TwoFieldsRecord("value1", "value2")));
        printProducer(new ConstantProducer<>(streamSize, new KeyValueFieldsRecord("key1", "value1")));
        printProducer(new ConstantProducer<>(streamSize, new KeyValueCommentFieldsRecord("key1", "value1", "comment1")));
        printProducer(new ConstantProducer<>(streamSize, new ManyFieldsRecord(Strings.list("value1", "value2"))));
        printProducer(new ConstantProducer<>(streamSize, new ManyFieldsRecord("value1", "value2", "value3")));
    }

    private static void showDividingProducer() {
        System.out.println("-showDividingProducer---");

        int recordSize = 2;
        String category = "category";

        printProducer(new DividingProducer(recordSize, "A", "B", "C", "D", "E"));
        printProducer(new DividingProducer(category, TextRecords.recordIdSequence(), recordSize, "A", "B", "C", "D", "E", "F"));
        printProducer(new DividingProducer(category, Suppliers.sequenceAsLong(100L), recordSize, "A", "B", "C"));
    }

    private static void showKeyValueProducer() {
        System.out.println("-showKeyValueProducer---");

        Map<String, Integer> keyValueMap = HashMap.newHashMap(3);
        keyValueMap.put("A", 1);
        keyValueMap.put("B", 2);
        keyValueMap.put("C", null);

        String category = "category";

        printProducer(new KeyValueRecordProducer(keyValueMap));
        printProducer(new KeyValueRecordProducer(category, keyValueMap));
        printProducer(new KeyValueRecordProducer(category, Suppliers.sequenceAsLong(100L), keyValueMap));
        printProducer(new KeyValueRecordProducer(category, TextRecords.recordIdSequence(), keyValueMap,
                Strings::toNullableString, i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    private static void showSingleProducer() {
        System.out.println("-showSingleProducer---");

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(null);

        String category = "category";

        printProducer(new ValueRecordProducer(values));
        printProducer(new ValueRecordProducer(category, values));
        printProducer(new ValueRecordProducer(category, Suppliers.sequenceAsLong(100L), values));
        printProducer(new ValueRecordProducer(category, TextRecords.recordIdSequence(), values,
                i -> i == null ? "<null>" : "#" + i.hashCode()));
    }

    public static void main(String... args) {
        showConstantProducer();
        showDividingProducer();
        showKeyValueProducer();
        showSingleProducer();
    }

}
