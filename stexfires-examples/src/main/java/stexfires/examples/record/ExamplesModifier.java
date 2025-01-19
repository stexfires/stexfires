package stexfires.examples.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.RecordIdFilter;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.mapper.impl.ToTwoFieldsRecordMapper;
import stexfires.record.message.*;
import stexfires.record.modifier.*;
import stexfires.util.SortNulls;
import stexfires.util.function.NumberPredicates;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static stexfires.record.modifier.GroupModifier.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesModifier {

    private ExamplesModifier() {
    }

    private static Stream<ValueRecord> generateStreamOneValueRecord() {
        return Stream.of(
                new ValueFieldRecord("C0", 0L, "value0"),
                new ValueFieldRecord("C0", 1L, "value0"),
                new ValueFieldRecord("C1", 2L, "value0"),
                new ValueFieldRecord("C1", 3L, "value1"),
                new ValueFieldRecord("C2", 4L, "value2"),
                new ValueFieldRecord("C0", 5L, "value1"),
                new ValueFieldRecord("C0", 6L, "value2")
        );
    }

    private static Stream<ValueRecord> generateStreamOneValueRecordGroup() {
        return Stream.of(
                new ValueFieldRecord("A", 0L, "a1"),
                new ValueFieldRecord("A", 1L, "a2"),
                new ValueFieldRecord("B", 2L, "b1"),
                new ValueFieldRecord("B", 3L, "b2"),
                new ValueFieldRecord("C", 4L, "c1"),
                new ValueFieldRecord("B", 5L, "b3"),
                new ValueFieldRecord("D", 6L, "d1"),
                new ValueFieldRecord("A", 7L, "a3"),
                new ValueFieldRecord("A", 8L, "a4")
        );
    }

    private static Stream<ManyFieldsRecord> generateStreamManyValuesRecord() {
        return Stream.of(
                new ManyFieldsRecord("C0", 0L, "B", "3", null, null),
                new ManyFieldsRecord("C0", 1L, "Z", "7", "a", null),
                new ManyFieldsRecord("C0", 2L, "A", "2", "a", null),
                new ManyFieldsRecord("C0", 3L, "C", "2", "b", null),
                new ManyFieldsRecord("C1", 4L, "X", "0", "c", null)
        );
    }

    private static void printModifierOneValueRecord(String title, RecordStreamModifier<ValueRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamOneValueRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printModifierOneValueRecordGroup(String title, RecordStreamModifier<ValueRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamOneValueRecordGroup(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printModifierManyValuesRecord(String title, RecordStreamModifier<ManyFieldsRecord, ? extends TextRecord> recordModifier) {
        System.out.println("--" + title);
        TextRecordStreams.modifyAndConsume(generateStreamManyValuesRecord(), recordModifier, new SystemOutConsumer<>());
    }

    private static void printPivotManyValuesRecord(String title, PivotModifier<ManyFieldsRecord> recordModifier, ManyFieldsRecord... records) {
        System.out.println("--" + title);
        RecordSystemOutUtil.printlnRecordStream(recordModifier.modify(Stream.of(records)));
    }

    private static void printPivotKeyValueRecord(String title, PivotModifier<KeyValueRecord> recordModifier, KeyValueRecord... records) {
        System.out.println("--" + title);
        RecordSystemOutUtil.printlnRecordStream(recordModifier.modify(Stream.of(records)));
    }

    private static void printUnaryGroup(String title, UnaryGroupModifier<ValueRecord> recordModifier) {
        System.out.println("--" + title);
        Stream<ValueRecord> recordStream = Stream.of(
                new ValueFieldRecord("A", 1L, "a1"),
                new ValueFieldRecord("A", 2L, "a2"),
                new ValueFieldRecord("B", 3L, "b1"),
                new ValueFieldRecord("B", 4L, "b2"),
                new ValueFieldRecord("A", 5L, "a3"),
                new ValueFieldRecord("B", 6L, "b3"),
                new ValueFieldRecord("A", 7L, "a4"),
                new ValueFieldRecord("B", 8L, "b4"),
                new ValueFieldRecord("A", 9L, "a5"),
                new ValueFieldRecord("A", 0L, "a0"));
        RecordSystemOutUtil.printlnRecordStream(recordModifier.modify(recordStream));
    }

    private static void printUnpivot(String title, UnpivotModifier<ManyFieldsRecord, ? extends TextRecord> recordModifier, ManyFieldsRecord... records) {
        System.out.println("--" + title);
        RecordSystemOutUtil.printlnRecordStream(recordModifier.modify(Stream.of(records)));
    }

    private static void showDistinctModifier() {
        System.out.println("-showDistinctModifier---");

        printModifierOneValueRecord("constructor category",
                new DistinctModifier<>(NotNullRecordMessage.wrapRecordMessage(new CategoryMessage<>(), "category is missing")));
        printModifierOneValueRecord("constructor recordId",
                new DistinctModifier<>(NotNullRecordMessage.wrapRecordMessage(new RecordIdMessage<>(), "recordId is missing")));
        printModifierOneValueRecord("constructor valueField",
                new DistinctModifier<>(NotNullRecordMessage.wrapRecordMessage(new TextMessage<>(ValueRecord::valueField), "valueField is missing")));
        printModifierOneValueRecord("constructor CompareMessageBuilder",
                new DistinctModifier<>(new CompareMessageBuilder().category().texts()));
    }

    private static void showFilterModifier() {
        System.out.println("-showFilterModifier---");

        printModifierOneValueRecord("constructor category",
                new FilterModifier<>(CategoryFilter.equalTo("C1")));
        printModifierManyValuesRecord("constructor recordId",
                new FilterModifier<>(RecordIdFilter.equalTo(1L)));
    }

    private static void showGroupModifier() {
        System.out.println("-showGroupModifier---");

        printModifierOneValueRecordGroup("constructor category; size > 1; aggregateToValue size",
                new GroupModifier<>(
                        groupByCategory(),
                        havingSizeGreaterThan(1),
                        aggregateToValue(
                                categoryOfFirstElement(),
                                list -> String.valueOf(list.size())
                        )));
        printModifierOneValueRecordGroup("constructor CategoryMessage; size < 4; aggregateToValues",
                new GroupModifier<>(
                        groupByMessage(NotNullRecordMessage.wrapRecordMessage(new CategoryMessage<>(), "category is missing")),
                        havingSize(NumberPredicates.PrimitiveIntPredicates.lessThan(4)),
                        aggregateToTexts(
                                messageOfFirstElement(new CategoryMessage<>()),
                                list -> list.stream().map(ValueRecord::value).toList()
                        )));
        printModifierOneValueRecordGroup("constructor category; aggregateToValuesWithMessage",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToTextsWithMessage(
                                new CategoryMessage<>(),
                                TextMessage.value()
                        )));

        printModifierManyValuesRecord("constructor category; aggregateToValues maxValuesNullsFirst",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToTexts(
                                categoryOfFirstElement(),
                                maxTextNullsFirst("<missing value>")
                        )));

        printModifierManyValuesRecord("constructor category; aggregateToValues minValuesNullsLast",
                new GroupModifier<>(
                        groupByCategory(),
                        aggregateToTexts(
                                categoryOfFirstElement(),
                                minTextNullsLast(null)
                        )));

        printModifierOneValueRecord("constructor valueField; aggregateToValue category",
                new GroupModifier<ValueRecord, TextRecord>(
                        groupByValue(),
                        aggregateToValue(
                                messageOfFirstElement(TextMessage.value()),
                                list -> list.stream().map(TextRecord::category).collect(Collectors.joining(","))
                        )));
    }

    private static void showIdentityModifier() {
        System.out.println("-showIdentityModifier---");

        printModifierOneValueRecord("constructor",
                new IdentityModifier<>());
    }

    private static void showLogFilterModifier() {
        System.out.println("-showLogFilterModifier---");

        printModifierOneValueRecord("constructor CategoryFilter",
                new LogFilterModifier<>(CategoryFilter.equalTo("C1"),
                        new SystemOutLogger<>(new CategoryMessage<>().prepend("Valid: ")),
                        new SystemOutLogger<>(new ShortMessage<>().prepend("Not valid: "))
                ));
    }

    private static void showLogModifier() {
        System.out.println("-showLogModifier---");

        printModifierOneValueRecord("constructor",
                new LogModifier<>(new SystemOutLogger<>(new ShortMessage<>().prepend("Log: "))));
    }

    private static void showMapModifier() {
        System.out.println("-showMapModifier---");

        printModifierOneValueRecord("constructor ToTwoFieldsRecordMapper",
                new MapModifier<>(new ToTwoFieldsRecordMapper<>(ValueRecord::value, ValueRecord::value)));
    }

    private static void showPivotModifier() {
        System.out.println("-showPivotModifier---");

        List<String> valueClasses = new ArrayList<>();
        valueClasses.add("en");
        valueClasses.add("de");
        valueClasses.add("fr");
        valueClasses.add("jp");

        String nullValue = "--------";
        String nullValueShort = "--";

        printPivotManyValuesRecord("Pivot 1.1 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(1, 2, nullValue,
                        0, valueClasses),
                new ManyFieldsRecord(null, 1L, "jp", "key1"),
                new ManyFieldsRecord(null, 2L, "en", "key1", "value1en"),
                new ManyFieldsRecord(null, 3L, "en", "key2", "value2en"),
                new ManyFieldsRecord(null, 4L, "en", "key3"),
                new ManyFieldsRecord(null, 5L, "en", "key4", "value4en"),
                new ManyFieldsRecord(null, 6L, "de", "key1", "value1de"),
                new ManyFieldsRecord(null, 7L, "de", "key2", "value2de"),
                new ManyFieldsRecord(null, 8L, "de", "key3", "value3de"),
                new ManyFieldsRecord(null, 9L, "fr", "key2", "value2fr")
        );

        printPivotKeyValueRecord("Pivot 1.2 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(KeyValueRecord::key, KeyValueRecord::value, nullValue,
                        KeyValueRecord::category, valueClasses
                ),
                new KeyValueFieldsRecord("jp", 1L, "key1", null),
                new KeyValueFieldsRecord("en", 2L, "key1", "value1en"),
                new KeyValueFieldsRecord("en", 3L, "key2", "value2en"),
                new KeyValueFieldsRecord("en", 4L, "key3", null),
                new KeyValueFieldsRecord("en", 5L, "key4", "value4en"),
                new KeyValueFieldsRecord("de", 6L, "key1", "value1de"),
                new KeyValueFieldsRecord("de", 7L, "key2", "value2de"),
                new KeyValueFieldsRecord("de", 8L, "key3", "value3de"),
                new KeyValueFieldsRecord("fr", 9L, "key2", "value2fr")
        );

        printPivotManyValuesRecord("Pivot 1.3 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(TextRecord::category, r -> r.textAt(1), nullValue,
                        r -> r.textAt(0), valueClasses),
                new ManyFieldsRecord("key1", 1L, "jp"),
                new ManyFieldsRecord("key1", 2L, "en", "value1en"),
                new ManyFieldsRecord("key2", 3L, "en", "value2en"),
                new ManyFieldsRecord("key3", 4L, "en"),
                new ManyFieldsRecord("key4", 5L, "en", "value4en"),
                new ManyFieldsRecord("key1", 6L, "de", "value1de"),
                new ManyFieldsRecord("key2", 7L, "de", "value2de"),
                new ManyFieldsRecord("key3", 8L, "de", "value3de"),
                new ManyFieldsRecord("key2", 9L, "fr", "value2fr")
        );

        printPivotManyValuesRecord("Pivot 2 pivotWithIndexes",
                PivotModifier.pivotWithIndexes(0, 3, nullValueShort,
                        1, 2),
                new ManyFieldsRecord(null, 1L, "key1", "A1", "B1", "no"),
                new ManyFieldsRecord(null, 2L, "key1", "A2", "B2", "no"),
                new ManyFieldsRecord(null, 3L, "key1", "A3", "B3", "no"),
                new ManyFieldsRecord(null, 3L, "key1", "A4", "B4", "no"),
                new ManyFieldsRecord(null, 4L, "key2", "C1", "D1"),
                new ManyFieldsRecord(null, 5L, "key2", "C2", "D2"),
                new ManyFieldsRecord(null, 6L, "key2", "C3", "D3"),
                new ManyFieldsRecord(null, 5L, "key3", "E1", "F1"),
                new ManyFieldsRecord(null, 5L, "key4", "G1"),
                new ManyFieldsRecord(null, 5L, "key4", "H1"),
                new ManyFieldsRecord(null, 6L, "key5", null, "I2", "I3")
        );

        printPivotKeyValueRecord("Pivot 3 constructor classification",
                new PivotModifier<>(
                        KeyValueRecord::key, KeyValueRecord::key, r -> Stream.empty(),
                        KeyValueRecord::value, nullValue, KeyValueRecord::category, valueClasses
                ),
                new KeyValueFieldsRecord("jp", 1L, "key1", null),
                new KeyValueFieldsRecord("en", 2L, "key1", "value1en"),
                new KeyValueFieldsRecord("en", 3L, "key2", "value2en"),
                new KeyValueFieldsRecord("en", 4L, "key3", null),
                new KeyValueFieldsRecord("en", 5L, "key4", "value4en"),
                new KeyValueFieldsRecord("de", 6L, "key1", "value1de"),
                new KeyValueFieldsRecord("de", 7L, "key2", "value2de"),
                new KeyValueFieldsRecord("de", 8L, "key3", "value3de"),
                new KeyValueFieldsRecord("fr", 9L, "key2", "value2fr")
        );

        List<String> valueClassifications = new ArrayList<>();
        valueClassifications.add("Type1");
        valueClassifications.add("Type2");
        valueClassifications.add("Type3");
        valueClassifications.add("Type4");

        printPivotManyValuesRecord("Pivot 4 pivotWithClassifications",
                PivotModifier.pivotWithClassifications(0, 2, nullValue,
                        1, valueClassifications),
                new ManyFieldsRecord("key2", "Type1", "Value2.1", "not relevant"),
                new ManyFieldsRecord("key1", "Type1", "Value1.1", "not relevant"),
                new ManyFieldsRecord("key2", "Type3", "Value2.3", "not relevant"),
                new ManyFieldsRecord("key1", "Type2", "Value1.2", "not relevant"),
                new ManyFieldsRecord("key3", "Type1", "Value3.1", "not relevant"),
                new ManyFieldsRecord("key1", "Type3", "Value1.3", "not relevant"),
                new ManyFieldsRecord("key2", "Type2", "Value2.2", "not relevant"),
                new ManyFieldsRecord("key3", "Type3", "Value3.3", "not relevant"),
                new ManyFieldsRecord("key4"),
                new ManyFieldsRecord("key5", "Type5", "Value5.5", "not relevant")
        );
    }

    private static void showRecordStreamModifier() {
        System.out.println("-showRecordStreamModifier---");

        printModifierOneValueRecord("concat 2",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new DistinctModifier<>(NotNullRecordMessage.wrapRecordMessage(new CategoryMessage<>(), "category is missing"))));
        printModifierOneValueRecord("concat 3",
                RecordStreamModifier.concat(
                        new FilterModifier<>(CategoryFilter.equalTo("C1")),
                        new IdentityModifier<>(),
                        new SkipLimitModifier<>(1L, 1L)));
        printModifierOneValueRecord("compose",
                new DistinctModifier<ValueRecord>(NotNullRecordMessage.wrapRecordMessage(new CategoryMessage<>(), "category is missing"))
                        .compose(new FilterModifier<>(CategoryFilter.equalTo("C1"))));
        printModifierOneValueRecord("andThen",
                new FilterModifier<ValueRecord>(CategoryFilter.equalTo("C1"))
                        .andThen(new DistinctModifier<>(NotNullRecordMessage.wrapRecordMessage(new CategoryMessage<>(), "category is missing"))));
    }

    private static void showSkipLimitModifier() {
        System.out.println("-showSkipLimitModifier---");

        printModifierOneValueRecord("constructor",
                new SkipLimitModifier<>(2L, 3L));
        printModifierOneValueRecord("skip",
                SkipLimitModifier.skip(4L));
        printModifierOneValueRecord("limit",
                SkipLimitModifier.limit(2L));
    }

    private static void showSortModifier() {
        System.out.println("-showSortModifier---");

        printModifierOneValueRecord("constructor category",
                new SortModifier<>(RecordComparators.category(Comparator.naturalOrder(), SortNulls.FIRST)));
        printModifierOneValueRecord("constructor valueField",
                new SortModifier<>(RecordComparators.value(Comparator.naturalOrder(), SortNulls.FIRST)));
    }

    private static void showUnaryGroupModifier() {
        System.out.println("-showUnaryGroupModifier---");

        printUnaryGroup("first",
                UnaryGroupModifier.first(TextRecord::category));

        printUnaryGroup("last",
                UnaryGroupModifier.last(TextRecord::category));

        printUnaryGroup("min recordId",
                UnaryGroupModifier.min(TextRecord::category, RecordComparators.recordId(SortNulls.FIRST)));

        printUnaryGroup("max recordId",
                UnaryGroupModifier.max(TextRecord::category, RecordComparators.recordId(SortNulls.FIRST)));

        printUnaryGroup("reduce maxBy recordId",
                UnaryGroupModifier.reduce(TextRecord::category,
                        BinaryOperator.maxBy(RecordComparators.recordId(SortNulls.FIRST))));

        printUnaryGroup("collect maxBy recordId",
                UnaryGroupModifier.collect(TextRecord::category,
                        Collectors.maxBy(RecordComparators.recordId(SortNulls.FIRST)),
                        null));

        printUnaryGroup("collect reducing maxBy recordId",
                UnaryGroupModifier.collect(TextRecord::category,
                        Collectors.reducing(BinaryOperator.maxBy(RecordComparators.recordId(SortNulls.FIRST))),
                        null));
    }

    private static void showUnpivotModifier() {
        System.out.println("-showUnpivotModifier---");

        printUnpivot("Unpivot 1.1 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyFieldsRecord(r.firstText(), "S 1", r.textAt(1)),
                        new ManyFieldsRecord(r.firstText(), "S 3", r.textAt(3))
                )),
                new ManyFieldsRecord("k1", "a1", "a2", "a3"),
                new ManyFieldsRecord("k2", "b1", "b2", "b3"),
                new ManyFieldsRecord("k3", "c1", "c2"),
                new ManyFieldsRecord("k4", "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 1.2 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyFieldsRecord(r.category(), "S 1", r.textAt(0)),
                        new ManyFieldsRecord(r.category(), "S 3", r.textAt(2))
                )),
                new ManyFieldsRecord("k1", 1L, "a1", "a2", "a3"),
                new ManyFieldsRecord("k2", 2L, "b1", "b2", "b3"),
                new ManyFieldsRecord("k3", 3L, "c1", "c2"),
                new ManyFieldsRecord("k4", 4L, "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 1.3 constructor",
                new UnpivotModifier<>(r -> Stream.of(
                        new ManyFieldsRecord(r.category(), r.recordId(), "S 1", r.textAt(0)),
                        new ManyFieldsRecord(r.category(), r.recordId(), "S 3", r.textAt(2))
                )),
                new ManyFieldsRecord("k1", 1L, "a1", "a2", "a3"),
                new ManyFieldsRecord("k2", 2L, "b1", "b2", "b3"),
                new ManyFieldsRecord("k3", 3L, "c1", "c2"),
                new ManyFieldsRecord("k4", 4L, "d1", "d2", "d3")
        );

        printUnpivot("Unpivot 2.1 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, String::valueOf, true, 1, 2, 3),
                new ManyFieldsRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyFieldsRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyFieldsRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyFieldsRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyFieldsRecord("k5", "e1"),
                new ManyFieldsRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyFieldsRecord("cat7", 7L, "k7", "g1"),
                new ManyFieldsRecord("cat8", 8L, "k8")
        );

        printUnpivot("Unpivot 2.2 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, i -> "Index: " + i, false, 2, 3),
                new ManyFieldsRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyFieldsRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyFieldsRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyFieldsRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyFieldsRecord("k5", "e1"),
                new ManyFieldsRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyFieldsRecord("cat7", 7L, "k7", "g1"),
                new ManyFieldsRecord("cat8", 8L, "k8")
        );

        Map<Integer, String> map23 = new TreeMap<>();
        map23.put(1, "S 1");
        map23.put(2, "S 2");
        map23.put(3, "S 3");
        printUnpivot("Unpivot 2.3 oneRecordPerValue",
                UnpivotModifier.oneRecordPerValue(0, true, map23),
                new ManyFieldsRecord("cat1", 1L, "k1", "a1", "a2", "a3"),
                new ManyFieldsRecord("cat2", 2L, "k2", "b1", "b2", "b3"),
                new ManyFieldsRecord("cat3", 3L, "k3", "c1", "c2", "c3"),
                new ManyFieldsRecord("cat4", 4L, "k4", "d1", "d2", "d3"),
                new ManyFieldsRecord("k5", "e1"),
                new ManyFieldsRecord("cat6", 6L, "k6", "f1", null, "f2"),
                new ManyFieldsRecord("cat7", 7L, "k7", "g1"),
                new ManyFieldsRecord("cat8", 8L, "k8")
        );

        List<Integer> keyIndexes24 = new ArrayList<>();
        keyIndexes24.add(1);
        keyIndexes24.add(0);
        Map<Integer, String> map24 = new TreeMap<>();
        map24.put(2, "S 1");
        map24.put(4, "S 3");
        printUnpivot("Unpivot 2.4 oneRecordPerValue two keys",
                UnpivotModifier.oneRecordPerValue(keyIndexes24, map24::get, false, map24.keySet()),
                new ManyFieldsRecord("cat1", 1L, "k1", "k1b", "a1", "a2", "a3"),
                new ManyFieldsRecord("cat2", 2L, "k2", "k2b", "b1", "b2", "b3"),
                new ManyFieldsRecord("cat3", 3L, "k3", "k3b", "c1", "c2", "c3"),
                new ManyFieldsRecord("cat4", 4L, "k4", "k4b", "d1", "d2", "d3"),
                new ManyFieldsRecord("k5", "k5b", "e1"),
                new ManyFieldsRecord("cat6", 6L, "k6", "k6b", "f1", null, "f2"),
                new ManyFieldsRecord("cat7", 7L, "k7", "k7b", "g1"),
                new ManyFieldsRecord("cat8", 8L, "k8", "k8b")
        );

        List<@Nullable Integer> keyValues3 = new ArrayList<>();
        keyValues3.add(6);
        keyValues3.add(0);
        List<@Nullable Integer> valueIndexes31 = new ArrayList<>();
        valueIndexes31.add(1);
        valueIndexes31.add(4);
        List<@Nullable Integer> valueIndexes32 = new ArrayList<>();
        valueIndexes32.add(2);
        valueIndexes32.add(5);
        List<@Nullable Integer> valueIndexes33 = new ArrayList<>();
        valueIndexes33.add(3);
        valueIndexes33.add(null);
        printUnpivot("Unpivot 3 oneRecordPerValues two keys, many values",
                UnpivotModifier.oneRecordPerValues(keyValues3,
                        String::valueOf,
                        valueIndexes31, valueIndexes32, valueIndexes33),
                new ManyFieldsRecord("cat1", 1L, "k1", "a1 1", "a2 1", "a3 1", "b1 1", "b2 1", "k1b"),
                new ManyFieldsRecord("cat2", 2L, "k2", "a1 2", "a2 2", "a3 2", "b1 2", "b2 2", "k2b"),
                new ManyFieldsRecord("cat3", 3L, "k3", "a1 3", null, "a3 3", "b1 3", "b2 3", "k3b")
        );
    }

    public static void main(String... args) {
        showDistinctModifier();
        showFilterModifier();
        showGroupModifier();
        showIdentityModifier();
        showLogFilterModifier();
        showLogModifier();
        showMapModifier();
        showPivotModifier();
        showRecordStreamModifier();
        showSkipLimitModifier();
        showSortModifier();
        showUnaryGroupModifier();
        showUnpivotModifier();
    }

}
