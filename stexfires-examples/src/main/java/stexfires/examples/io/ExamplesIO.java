package stexfires.examples.io;

import stexfires.data.CollectionDataTypeFormatter;
import stexfires.data.CollectionDataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordIOStreams;
import stexfires.io.consumer.StringWritableRecordConsumer;
import stexfires.io.container.RecordContainer;
import stexfires.io.container.RecordContainerLarge;
import stexfires.io.container.UnpackResult;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.TextFilter;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.CategoryMapper;
import stexfires.record.message.RecordMessage;
import stexfires.record.modifier.SortModifier;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.SortNulls;
import stexfires.util.function.StringUnaryOperators;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static stexfires.examples.record.RecordSystemOutUtil.RECORD_CONSUMER;
import static stexfires.examples.record.RecordSystemOutUtil.printlnOptionalRecord;
import static stexfires.examples.record.RecordSystemOutUtil.printlnRecord;
import static stexfires.examples.record.RecordSystemOutUtil.printlnRecordList;
import static stexfires.io.RecordIOStreams.*;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "HardcodedLineSeparator", "ReturnOfNull"})
public final class ExamplesIO {

    private ExamplesIO() {
    }

    private static ManyFieldsRecord generateRecord() {
        return new ManyFieldsRecord("sampleCategory", 42L, "value0", "value1", "value2");
    }

    private static Stream<TextRecord> generateRecordStream() {
        return Stream.of(
                TextRecords.empty(),
                new ManyFieldsRecord(),
                new ValueFieldRecord(null),
                new ValueFieldRecord(""),
                new ValueFieldRecord("value"),
                new ValueFieldRecord("cat", 1_234_567_890L, "value"),
                new TwoFieldsRecord("first", "second"),
                new TwoFieldsRecord("cat", 1_234_567_890L, "first", "second"),
                new ManyFieldsRecord("value0", "value1"),
                new ManyFieldsRecord("cat", 1_234_567_890L, "value0", null, "value2", "value3")
        );
    }

    private static void showRead() throws UncheckedProducerException {
        System.out.println("-showRead---");

        var fileSpec = SingleValueFileSpec.producerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                1, 0, SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                true
        );

        String sourceString = "###Value####\na\nb\n\nd\n";
        CategoryMapper<ValueRecord> categoryMapper = CategoryMapper.constant("new_category");
        SortModifier<ValueRecord> sortModifier = new SortModifier<>(RecordComparators.recordId(SortNulls.FIRST).reversed());

        System.out.println("---producer(sourceString) read andCollect");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollect(Collectors.toList())));

        System.out.println("---producer(sourceString) read andCollectMapped");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollectMapped(Collectors.toList(), categoryMapper)));

        System.out.println("---producer(sourceString) read andCollectModified");
        printlnRecordList(
                read(fileSpec.producer(sourceString), andCollectModified(Collectors.toList(), sortModifier)));

        System.out.println("---producer(sourceString) read andConsume");
        read(fileSpec.producer(sourceString), andConsume(RECORD_CONSUMER));

        System.out.println("---producer(sourceString) read andConsumeMapped");
        read(fileSpec.producer(sourceString), andConsumeMapped(RECORD_CONSUMER, categoryMapper));

        System.out.println("---producer(sourceString) read andConsumeModified");
        read(fileSpec.producer(sourceString), andConsumeModified(RECORD_CONSUMER, sortModifier));

        System.out.println("---producer(sourceString) read andFindFirst");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirst()));

        System.out.println("---producer(sourceString) read andFindFirstMapped");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirstMapped(categoryMapper)));

        System.out.println("---producer(sourceString) read andFindFirstModified");
        printlnOptionalRecord(
                read(fileSpec.producer(sourceString), andFindFirstModified(sortModifier)));

        System.out.println("---producer(sourceString) readAndConsume");
        readAndConsume(fileSpec.producer(sourceString), RECORD_CONSUMER);

        System.out.println("---readFromString andCollect");
        printlnRecordList(
                readFromString(fileSpec, sourceString, andCollect(Collectors.toList())));

        System.out.println("---newRecordDataTypeParser");
        printlnRecord(
                newRecordDataTypeParser(fileSpec, null, null).parse(sourceString));
    }

    private static void showWrite() throws IOException, UncheckedConsumerException {
        System.out.println("-showWrite---");

        var fileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX, LineSeparator.LF,
                "###Value###",
                "######",
                false);

        var fileSpecCompact = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX, LineSeparator.LF,
                SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE,
                SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                true);

        ValueFieldRecord valueFieldRecord1 = new ValueFieldRecord("category_ValueFieldRecord", 1L, "value_ValueFieldRecord");
        ValueFieldRecord valueFieldRecord2 = new ValueFieldRecord("category_ValueFieldRecord", 2L, (String) null);
        ValueFieldRecord valueFieldRecord3 = new ValueFieldRecord(null, null, (String) null);
        KeyValueFieldsRecord keyValueFieldsRecord10 = new KeyValueFieldsRecord("category_KeyValueFieldsRecord", 10L, "key_KeyValueFieldsRecord", "value_KeyValueFieldsRecord");
        KeyValueFieldsRecord keyValueFieldsRecord11 = new KeyValueFieldsRecord("category_KeyValueFieldsRecord", 11L, "key_KeyValueFieldsRecord", null);

        List<ValueRecord> listValueRecords = List.of(valueFieldRecord1, valueFieldRecord2, valueFieldRecord3, keyValueFieldsRecord10, keyValueFieldsRecord11);

        System.out.println("---writeStreamIntoString");
        System.out.println(writeStreamIntoString(fileSpec,
                true, listValueRecords.stream()));
        System.out.println(writeStreamIntoString(fileSpec,
                false, TextRecordStreams.of(keyValueFieldsRecord10)));
        System.out.println(writeStreamIntoString(fileSpec,
                true, TextRecordStreams.of(valueFieldRecord1, keyValueFieldsRecord10)));

        System.out.println("---writeRecordIntoString");
        System.out.println(writeRecordIntoString(fileSpec,
                true, valueFieldRecord1));
        System.out.println(writeRecordIntoString(fileSpec,
                false, valueFieldRecord3));
        System.out.println(writeRecordIntoString(fileSpec,
                true, keyValueFieldsRecord10));

        System.out.println("---newRecordDataTypeFormatter");
        System.out.println(newRecordDataTypeFormatter(fileSpec,
                true, null).format(valueFieldRecord1));

        System.out.println("---writeRecordIntoStringMessage");
        RecordMessage<ValueRecord> mapper = writeRecordIntoStringMessage(fileSpecCompact, true)
                .andThen(StringUnaryOperators.surround("<", ">"));
        System.out.println(
                TextRecordStreams.collectMessages(listValueRecords.stream(), mapper)
        );

        System.out.println("---writeStream StringWritableRecordConsumer");
        try (var stringWritableRecordConsumer = new StringWritableRecordConsumer<>(fileSpec)) {
            System.out.println(
                    writeStream(stringWritableRecordConsumer, listValueRecords.stream())
                            .consumedString(false));
        }
        System.out.println("---writeRecord StringWritableRecordConsumer");
        try (var stringWritableRecordConsumer = new StringWritableRecordConsumer<>(fileSpec)) {
            System.out.println(
                    writeRecord(stringWritableRecordConsumer, keyValueFieldsRecord10)
                            .consumedString(true));
        }

        System.out.println("---writeStreamIntoRecord");
        printlnRecord(writeStreamIntoRecord(
                fileSpecCompact,
                true,
                TextRecordStreams.filter(listValueRecords.stream(),
                        TextFilter.isNotNull(ValueRecord::valueField))));
    }

    private static void showList() {
        System.out.println("-showList---");

        System.out.println("-storeInList");
        generateRecordStream().map(RecordIOStreams::storeInList)
                              .forEachOrdered(System.out::println);

        System.out.println("-restoreFromList (storeInList)");
        generateRecordStream().map(RecordIOStreams::storeInList)
                              .map(RecordIOStreams::restoreFromList)
                              .forEachOrdered(RecordSystemOutUtil::printlnRecord);

        System.out.println("-restoreFromList (special list)");
        List<String> list0 = new ArrayList<>();
        list0.add("");
        list0.add("");
        list0.add("value0");
        list0.add(null);
        list0.add("value2");
        list0.add("");
        printlnRecord(restoreFromList(list0));

        List<String> list1 = new ArrayList<>();
        list1.add("cat");
        list1.add("1");
        printlnRecord(restoreFromList(list1));
    }

    private static void showMap() {
        System.out.println("-showMap---");

        System.out.println("-storeInMap");
        generateRecordStream().map(RecordIOStreams::storeInMap)
                              .forEachOrdered(System.out::println);

        System.out.println("-restoreFromMap (storeInMap)");
        generateRecordStream().map(RecordIOStreams::storeInMap)
                              .map(RecordIOStreams::restoreFromMap)
                              .forEachOrdered(RecordSystemOutUtil::printlnRecord);

        System.out.println("-restoreFromMap (special map)");
        Map<String, Object> map0 = HashMap.newHashMap(3);
        map0.put(CATEGORY_KEY, "");
        map0.put(RECORD_ID_KEY, 1L);
        map0.put(TEXTS_KEY, List.of("value0", "value1"));
        printlnRecord(restoreFromMap(map0));

        Map<String, Object> map1 = HashMap.newHashMap(3);
        map1.put(CATEGORY_KEY, Boolean.TRUE);
        map1.put(RECORD_ID_KEY, BigDecimal.valueOf(123.456d));
        map1.put(TEXTS_KEY, List.of(4, 5.6f, 7.89d));
        printlnRecord(restoreFromMap(map1));

        Map<String, Object> map2 = HashMap.newHashMap(3);
        map2.put(CATEGORY_KEY, 123);
        map2.put(RECORD_ID_KEY, 456);
        map2.put(TEXTS_KEY, Set.of(7, 8, 9));
        printlnRecord(restoreFromMap(map2));

        Map<String, Object> map3 = HashMap.newHashMap(3);
        printlnRecord(restoreFromMap(map3));

        Map<String, Object> map4 = HashMap.newHashMap(3);
        map4.put(TEXTS_KEY, List.of("value"));
        printlnRecord(restoreFromMap(map4));
    }

    private static void showFormattedStringList() {
        System.out.println("-showFormattedStringList---");

        TextRecord record = generateRecord();

        // to String List
        List<String> stringList = storeInList(generateRecord());
        System.out.println(stringList);

        // from String List
        printlnRecord(restoreFromList(stringList));

        // format String List
        String formattedStringList = CollectionDataTypeFormatter.withDelimiter(";", "[", "]", StringDataTypeFormatter.identity(), () -> null)
                                                                .format(stringList);
        System.out.println(formattedStringList);

        // Parse String List
        List<String> parsedStringList = CollectionDataTypeParser.withDelimiterAsList(";", "[", "]", StringDataTypeParser.identity(), () -> null, () -> null)
                                                                .parse(formattedStringList);
        printlnRecord(restoreFromList(parsedStringList));
    }

    private static void showRecordContainer() {
        System.out.println("-showRecordContainer---");

        TextRecord record = generateRecord();

        RecordContainer recordContainerLarge = new RecordContainerLarge();
        TextRecord packedLarge = recordContainerLarge.pack(record);
        UnpackResult unpackedLarge = recordContainerLarge.unpack(packedLarge);

        printlnRecord(packedLarge);
        printlnOptionalRecord(unpackedLarge.record());
    }

    private static void showRecordDataType() {
        System.out.println("-showRecordDataType---");

        TextRecord record = generateRecord();

        System.out.println(
                newRecordDataTypeFormatter(";", "[", "]", null)
                        .format(record));

        printlnRecord(
                newRecordDataTypeParser(";", "[", "]", null, null)
                        .parse("[sampleCategory;42;value0;value1;value2]"));
        printlnRecord(
                newRecordDataTypeParser(";", "[", "]", null, null)
                        .parse("[category_ValueFieldRecord;1;value_ValueFieldRecord]"));
        printlnRecord(
                newRecordDataTypeParser(", ", null, null, null, null)
                        .parse(", , value0, value1, value2"));
    }

    private static void showRecordSplitAndCollect() {
        System.out.println("-showRecordSplitAndCollect---");

        TextRecord record = generateRecord();

        Function<TextRecord, Stream<ValueRecord>> splitIntoValueRecords =
                splitIntoValueRecords(
                        (r, f) -> r.category(),
                        (r, f) -> r.recordId());
        Function<TextRecord, Stream<KeyValueRecord>> splitIntoKeyValueRecords =
                splitIntoKeyValueRecords(
                        (r, f) -> r.category(),
                        (r, f) -> r.recordIdAsOptional().map(id -> id * 100L + f.index()).orElse(null),
                        (r, f) -> "key_" + f.index());
        Function<TextRecord, Stream<KeyValueCommentRecord>> splitIntoKeyValueCommentRecords =
                splitIntoKeyValueCommentRecords(
                        (r, f) -> "key_" + f.index(),
                        (r, f) -> r.getClass().getSimpleName() + " (" + f.index() + "/" + f.maxIndex() + ")");

        System.out.println("-split apply");
        splitIntoValueRecords.apply(record).forEachOrdered(RECORD_CONSUMER::consume);
        splitIntoKeyValueRecords.apply(record).forEachOrdered(RECORD_CONSUMER::consume);
        splitIntoKeyValueCommentRecords.apply(record).forEachOrdered(RECORD_CONSUMER::consume);

        System.out.println("-split stream flatMap");
        TextRecordStreams.of(record).flatMap(splitIntoValueRecords).forEachOrdered(RECORD_CONSUMER::consume);
        TextRecordStreams.of(record).flatMap(splitIntoKeyValueRecords).forEachOrdered(RECORD_CONSUMER::consume);
        TextRecordStreams.of(record).flatMap(splitIntoKeyValueCommentRecords).forEachOrdered(RECORD_CONSUMER::consume);

        System.out.println("-collect empty");
        printlnRecord(Stream.<ValueRecord>empty().collect(
                collectValueRecords(
                        list -> list.stream().findFirst().map(ValueRecord::category).orElse(null),
                        list -> list.stream().findFirst().map(ValueRecord::recordId).orElse(null))));
        System.out.println("-collect ValueFieldRecord");
        printlnRecord(Stream.of(new ValueFieldRecord("c", 1L, "v0")).collect(
                collectValueRecords(
                        list -> list.stream().findFirst().map(ValueRecord::category).orElse(null),
                        list -> list.stream().findFirst().map(ValueRecord::recordId).orElse(null))));
        System.out.println("-collect ValueRecord");
        printlnRecord(TextRecordStreams.of(record).flatMap(splitIntoValueRecords).collect(
                collectValueRecords(
                        list -> list.stream().findFirst().map(ValueRecord::category).orElse(null),
                        list -> list.stream().findFirst().map(ValueRecord::recordId).orElse(null))));
        System.out.println("-collect KeyValueRecord");
        printlnRecord(TextRecordStreams.of(record).flatMap(splitIntoKeyValueRecords).collect(
                collectKeyValueRecords(
                        list -> null,
                        list -> null)));
        System.out.println("-collect KeyValueCommentRecord");
        printlnRecord(TextRecordStreams.of(record).flatMap(splitIntoKeyValueCommentRecords).collect(
                collectKeyValueCommentRecords(
                        list -> "new category",
                        list -> list.stream().findFirst().map(ValueRecord::recordId).orElse(null))));
    }

    public static void main(String... args) {
        try {
            showRead();
            showWrite();
            showList();
            showMap();
            showFormattedStringList();
            showRecordContainer();
            showRecordDataType();
            showRecordSplitAndCollect();
        } catch (IOException | UncheckedProducerException | UncheckedConsumerException e) {
            e.printStackTrace();
        }
    }

}
