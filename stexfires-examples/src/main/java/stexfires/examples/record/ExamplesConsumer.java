package stexfires.examples.record;

import stexfires.record.*;
import stexfires.record.consumer.*;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.*;
import stexfires.record.message.SizeMessage;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesConsumer {

    private ExamplesConsumer() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ValueFieldRecord("value1"),
                new ValueFieldRecord("value2"),
                new KeyValueFieldsRecord("key", "value"),
                new ManyFieldsRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord(),
                TextRecords.empty()
        );
    }

    private static Stream<ValueRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new ValueFieldRecord("value1"),
                new ValueFieldRecord(null, 2L, "value2"),
                new ValueFieldRecord("category", 3L, "value3")
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueFieldsRecord("key1", "value1"),
                new KeyValueFieldsRecord(null, 2L, "key2", "value2"),
                new KeyValueFieldsRecord("category", 3L, "key3", "value3")
        );
    }

    private static void printConsumer(String title, RecordConsumer<TextRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStream(), recordConsumer);
    }

    private static void printConsumerOneValueRecord(String title, RecordConsumer<? super ValueRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStreamOneValueRecord(), recordConsumer);
    }

    private static void printConsumerKeyValueRecord(String title, RecordConsumer<? super KeyValueRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStreamKeyValueRecord(), recordConsumer);
    }

    private static void showAppendableConsumer() {
        System.out.println("-showAppendableConsumer---");

        StringBuilder builder = new StringBuilder(10);
        printConsumer("constructor StringBuilder",
                new AppendableConsumer<>(builder, new SizeMessage<>()));
        System.out.println(builder);

        StringBuffer buffer = new StringBuffer(10);
        printConsumer("constructor StringBuffer",
                new AppendableConsumer<>(buffer, new SizeMessage<>()));
        System.out.println(buffer);

        StringWriter writer = new StringWriter(10);
        printConsumer("constructor StringWriter",
                new AppendableConsumer<>(writer, new SizeMessage<>()));
        System.out.println(writer);
    }

    private static void showCollectionConsumer() {
        System.out.println("-showCollectionConsumer---");

        List<String> constructor = new ArrayList<>();
        printConsumer("constructor toString",
                new CollectionConsumer<>(constructor, TextRecord::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        printConsumerOneValueRecord("constructor values",
                new CollectionConsumer<>(values, ValueRecord::value));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        printConsumerKeyValueRecord("constructor keys",
                new CollectionConsumer<>(keys, KeyRecord::key));
        System.out.println(keys);
    }

    private static void showConditionalConsumer() {
        System.out.println("-showConditionalConsumer---");

        printConsumer("constructor ValueFieldRecord",
                new ConditionalConsumer<>(
                        ClassFilter.equalTo(ValueFieldRecord.class),
                        new SystemOutConsumer<>(),
                        new NullConsumer<>()));

        printConsumerKeyValueRecord("constructor category null",
                new ConditionalConsumer<>(
                        CategoryFilter.isNull(),
                        new SystemOutConsumer<>(),
                        new NullConsumer<>()));
    }

    private static void showDispatcherConsumer() {
        System.out.println("-showDispatcherConsumer---");

        List<SystemOutConsumer<TextRecord>> recordConsumersSize = new ArrayList<>();
        recordConsumersSize.add(new SystemOutConsumer<>("Size 0: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 1: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 2: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 3: "));

        printConsumer("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        printConsumerOneValueRecord("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        printConsumerKeyValueRecord("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        List<ClassFilter<TextRecord>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(ValueFieldRecord.class));
        recordFilters.add(ClassFilter.equalTo(TwoFieldsRecord.class));

        List<RecordConsumer<? super TextRecord>> recordConsumersFilter = new ArrayList<>();
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter EmptyRecord:  "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter ValueFieldRecord: "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter TwoFieldsRecord:   "));

        printConsumer("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));

        printConsumerOneValueRecord("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));

        printConsumerKeyValueRecord("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));
    }

    private static void showMapConsumer() {
        System.out.println("-showMapConsumer---");

        Map<String, String> keyValueMap = HashMap.newHashMap(1);
        printConsumerKeyValueRecord("constructor",
                new MapConsumer<>(keyValueMap, KeyValueRecord::key, KeyValueRecord::value));
        System.out.println(keyValueMap);
    }

    private static void showNullConsumer() {
        System.out.println("-showNullConsumer---");

        printConsumer("constructor",
                new NullConsumer<>());

        printConsumerKeyValueRecord("constructor",
                new NullConsumer<>());
    }

    private static void showPrintStreamConsumer() {
        System.out.println("-showPrintStreamConsumer---");

        printConsumer("constructor false",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>(), false));
        System.out.println();
        printConsumer("constructor true",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordConsumer() {
        System.out.println("-showRecordConsumer---");

        printConsumer("concat 2",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>()));
        printConsumer("concat 3",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>(), new SystemOutConsumer<>()));
        printConsumer("andThen",
                new NullConsumer<>().andThen(new SystemOutConsumer<>()));
    }

    private static void showStringWriterConsumer() {
        System.out.println("-showStringWriterConsumer---");

        StringWriterConsumer<TextRecord> consumer = new StringWriterConsumer<>(new SizeMessage<>());
        printConsumer("constructor",
                consumer);
        System.out.println(consumer.getString());
    }

    private static void showSystemErrConsumer() {
        System.out.println("-showSystemErrConsumer---");

        printConsumer("constructor",
                new SystemErrConsumer<>());
        printConsumer("constructor prefix",
                new SystemErrConsumer<>("--"));
        printConsumer("constructor SizeMessage",
                new SystemErrConsumer<>(new SizeMessage<>()));
        printConsumer("constructor SizeMessage false",
                new SystemErrConsumer<>(new SizeMessage<>(), false));
        System.err.println();
    }

    private static void showSystemOutConsumer() {
        System.out.println("-showSystemOutConsumer---");

        printConsumer("constructor",
                new SystemOutConsumer<>());
        printConsumer("constructor prefix",
                new SystemOutConsumer<>("--"));
        printConsumer("constructor SizeMessage",
                new SystemOutConsumer<>(new SizeMessage<>()));
        printConsumer("constructor SizeMessage false",
                new SystemOutConsumer<>(new SizeMessage<>(), false));
        System.out.println();
    }

    private static void showWriterConsumer() {
        System.out.println("-showWriterConsumer---");

        try {
            try (WriterConsumer<ValueRecord, StringWriter> consumer = new WriterConsumer<>(new StringWriter(), new SizeMessage<>())) {
                printConsumerOneValueRecord("constructor",
                        consumer);
                System.out.println(consumer.getWriter());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    public static void main(String... args) {
        showAppendableConsumer();
        showCollectionConsumer();
        showConditionalConsumer();
        showDispatcherConsumer();
        showMapConsumer();
        showNullConsumer();
        showPrintStreamConsumer();
        showRecordConsumer();
        showStringWriterConsumer();
        showSystemErrConsumer();
        showSystemOutConsumer();
        showWriterConsumer();
    }

}
