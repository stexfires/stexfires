package stexfires.examples.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.impl.*;
import stexfires.record.producer.*;
import stexfires.util.Strings;
import stexfires.util.supplier.SequenceSupplier;

import java.util.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesProducer {

    private ExamplesProducer() {
    }

    private static void printProducer(RecordProducer<? extends TextRecord> recordProducer) {
        RecordSystemOutUtil.printlnRecordStream(TextRecordStreams.produce(recordProducer));
    }

    private static void showCollectionProducer() {
        System.out.println("-showCollectionProducer---");

        printProducer(new CollectionProducer<TextRecord>(Set.of(
                new ValueFieldRecord("set", 0L, "value0"),
                new ValueFieldRecord("set", 1L, "value1"),
                new ValueFieldRecord("set", 2L, "value2"))));
        printProducer(new CollectionProducer<ValueRecord>(List.of(
                new ValueFieldRecord("list", 0L, "value0"),
                new ValueFieldRecord("list", 1L, "value1"),
                new ValueFieldRecord("list", 2L, "value2"))));
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

        printProducer(new DividingProducer(recordSize));
        printProducer(new DividingProducer(recordSize, "A"));
        printProducer(new DividingProducer(recordSize, (String) null));
        printProducer(new DividingProducer(recordSize, "A", "B", null, "D", "E"));
        printProducer(new DividingProducer(category, TextRecords.recordIdSequence(), recordSize, "A", "B", null, "D", "E", "F"));
        printProducer(new DividingProducer(category, SequenceSupplier.asLong(100L), recordSize, "A", "B", "C"));
        printProducer(new DividingProducer(category, SequenceSupplier.asLong(100L), 10, "A", null, "C"));
    }

    private static void showKeyValueRecordProducer() {
        System.out.println("-showKeyValueRecordProducer---");

        Map<String, @Nullable Integer> keyValueMap = HashMap.newHashMap(3);
        keyValueMap.put("A", 1);
        keyValueMap.put("B", 2);
        keyValueMap.put("C", null);
        keyValueMap.put("D", 4);

        String category = "category";

        printProducer(new KeyValueRecordProducer(keyValueMap));

        printProducer(new KeyValueRecordProducer(category, keyValueMap));
        printProducer(new KeyValueRecordProducer(null, keyValueMap));

        printProducer(new KeyValueRecordProducer(category, SequenceSupplier.asLong(100L), keyValueMap));
        printProducer(new KeyValueRecordProducer(null, SequenceSupplier.asLong(100L), keyValueMap));

        printProducer(new KeyValueRecordProducer(category, TextRecords.recordIdSequence(), keyValueMap,
                Strings::toNullableString, i -> i == null ? "<NULL>" : "#" + i.hashCode()));
    }

    private static void showValueRecordProducer() {
        System.out.println("-showValueRecordProducer---");

        List<@Nullable Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(null);
        values.add(4);

        String category = "category";

        printProducer(new ValueRecordProducer(values));

        printProducer(new ValueRecordProducer(category, values));
        printProducer(new ValueRecordProducer(null, values));

        printProducer(new ValueRecordProducer(category, SequenceSupplier.asLong(100L), values));
        printProducer(new ValueRecordProducer(null, SequenceSupplier.asLong(100L), values));

        printProducer(new ValueRecordProducer(category, TextRecords.recordIdSequence(), values,
                i -> i == null ? "<NULL>" : "#" + i.hashCode()));
    }

    public static void main(String... args) {
        showCollectionProducer();
        showConstantProducer();
        showDividingProducer();
        showKeyValueRecordProducer();
        showValueRecordProducer();
    }

}
