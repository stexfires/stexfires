package stexfires.examples.record;

import stexfires.record.*;
import stexfires.record.consumer.NullConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.*;
import stexfires.record.logger.*;
import stexfires.record.message.SizeMessage;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesLogger {

    private ExamplesLogger() {
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

    private static void printLogger(String title, RecordLogger<TextRecord> recordLogger) {
        System.out.println("--" + title);
        TextRecordStreams.log(generateStream(), recordLogger)
                         .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void printLoggerOneValueRecord(String title, RecordLogger<? super ValueRecord> recordLogger) {
        System.out.println("--" + title);
        TextRecordStreams.log(generateStreamOneValueRecord(), recordLogger)
                         .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void printLoggerKeyValueRecord(String title, RecordLogger<? super KeyValueRecord> recordLogger) {
        System.out.println("--" + title);
        TextRecordStreams.log(generateStreamKeyValueRecord(), recordLogger)
                         .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void showAppendableLogger() {
        System.out.println("-showAppendableLogger---");

        StringBuilder builder = new StringBuilder(10);
        printLogger("constructor StringBuilder",
                new AppendableLogger<>(builder, new SizeMessage<>()));
        System.out.println(builder);

        StringBuffer buffer = new StringBuffer(10);
        printLogger("constructor StringBuffer",
                new AppendableLogger<>(buffer, new SizeMessage<>()));
        System.out.println(buffer);

        StringWriter writer = new StringWriter(10);
        printLogger("constructor StringWriter",
                new AppendableLogger<>(writer, new SizeMessage<>()));
        System.out.println(writer);
    }

    private static void showCollectionLogger() {
        System.out.println("-showCollectionLogger---");

        List<String> constructor = new ArrayList<>();
        printLogger("constructor",
                new CollectionLogger<>(constructor, TextRecord::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        printLoggerOneValueRecord("constructor values",
                new CollectionLogger<>(values, ValueRecord::value));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        printLoggerKeyValueRecord("constructor keys",
                new CollectionLogger<>(keys, KeyRecord::key));
        System.out.println(keys);
    }

    private static void showConditionalLogger() {
        System.out.println("-showConditionalLogger---");

        printLogger("constructor",
                new ConditionalLogger<>(
                        ClassFilter.equalTo(ManyFieldsRecord.class),
                        new SystemOutLogger<>(),
                        new NullLogger<>()));

        printLoggerKeyValueRecord("constructor category null",
                new ConditionalLogger<>(
                        CategoryFilter.isNull(),
                        new SystemOutLogger<>(),
                        new NullLogger<>()));
    }

    private static void showDispatcherLogger() {
        System.out.println("-showDispatcherLogger---");

        List<SystemOutLogger<TextRecord>> recordLoggersSize = new ArrayList<>();
        recordLoggersSize.add(new SystemOutLogger<>("Size 0: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 1: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 2: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 3: "));

        printLogger("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        printLoggerOneValueRecord("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        printLoggerKeyValueRecord("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        List<ClassFilter<TextRecord>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(ValueFieldRecord.class));
        recordFilters.add(ClassFilter.equalTo(TwoFieldsRecord.class));

        List<RecordLogger<? super TextRecord>> recordLoggersFilter = new ArrayList<>();
        recordLoggersFilter.add(new SystemOutLogger<>("Filter EmptyRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter ValueFieldRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter TwoFieldsRecord: "));

        printLogger("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));

        printLoggerOneValueRecord("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));

        printLoggerKeyValueRecord("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));
    }

    private static void showLimitedLogger() {
        System.out.println("-showLimitedLogger---");

        printLogger("constructor 0",
                new LimitedLogger<>(new SystemOutLogger<>(), 0));
        printLogger("constructor 2",
                new LimitedLogger<>(new SystemOutLogger<>(), 2));
    }

    private static void showNullLogger() {
        System.out.println("-showNullLogger---");

        printLogger("constructor",
                new NullLogger<>());

        printLoggerKeyValueRecord("constructor",
                new NullLogger<>());
    }

    private static void showPrintStreamLogger() {
        System.out.println("-showPrintStreamLogger---");

        printLogger("constructor false",
                new PrintStreamLogger<>(System.out, new SizeMessage<>(), false));
        System.out.println();
        printLogger("constructor true",
                new PrintStreamLogger<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordLogger() {
        System.out.println("-showRecordLogger---");

        printLogger("concat 2",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>()));
        printLogger("concat 3",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>(), new SystemOutLogger<>()));
        printLogger("andThen",
                new NullLogger<>().andThen(new SystemOutLogger<>()));
    }

    private static void showSystemErrLogger() {
        System.out.println("-showSystemErrLogger---");

        printLogger("constructor",
                new SystemErrLogger<>());
        printLogger("constructor prefix",
                new SystemErrLogger<>("--"));
        printLogger("constructor SizeMessage",
                new SystemErrLogger<>(new SizeMessage<>()));
        printLogger("constructor SizeMessage false",
                new SystemErrLogger<>(new SizeMessage<>(), false));
        System.err.println();
    }

    private static void showSystemOutLogger() {
        System.out.println("-showSystemOutLogger---");

        printLogger("constructor",
                new SystemOutLogger<>());
        printLogger("constructor prefix",
                new SystemOutLogger<>("--"));
        printLogger("constructor SizeMessage",
                new SystemOutLogger<>(new SizeMessage<>()));
        printLogger("constructor SizeMessage false",
                new SystemOutLogger<>(new SizeMessage<>(), false));
        System.out.println();
    }

    public static void main(String... args) {
        showAppendableLogger();
        showCollectionLogger();
        showConditionalLogger();
        showDispatcherLogger();
        showLimitedLogger();
        showNullLogger();
        showPrintStreamLogger();
        showRecordLogger();
        showSystemErrLogger();
        showSystemOutLogger();
    }

}
