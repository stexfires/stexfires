package stexfires.core.modifier;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Aggregator / Group By.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public class GroupModifier<T extends Record, R extends Record> implements RecordStreamModifier<T, R> {

    protected final Function<? super T, ?> groupByClassifier;
    protected final Predicate<List<? super T>> havingPredicate;
    protected final Function<List<T>, ? extends R> aggregateFunction;

    public GroupModifier(Function<? super T, ?> groupByClassifier,
                         Function<List<T>, ? extends R> aggregateFunction) {
        this(groupByClassifier, list -> true, aggregateFunction);
    }

    public GroupModifier(Function<? super T, ?> groupByClassifier,
                         Predicate<List<? super T>> havingPredicate,
                         Function<List<T>, ? extends R> aggregateFunction) {
        Objects.requireNonNull(groupByClassifier);
        Objects.requireNonNull(havingPredicate);
        Objects.requireNonNull(aggregateFunction);
        this.groupByClassifier = groupByClassifier;
        this.havingPredicate = havingPredicate;
        this.aggregateFunction = aggregateFunction;
    }

    public static <T extends Record> Function<? super T, String> groupByCategory() {
        return Record::getCategory;
    }

    public static <T extends Record> Function<? super T, String> groupByMessage(RecordMessage<? super T> recordMessage) {
        return recordMessage.asFunction();
    }

    public static <T extends KeyRecord> Function<? super T, String> groupByKey() {
        return KeyRecord::getValueOfKeyField;
    }

    public static <T extends ValueRecord> Function<? super T, String> groupByValue() {
        return ValueRecord::getValueOfValueField;
    }

    public static <T extends Record> Function<? super T, String> groupByValue(int index) {
        return r -> r.getValueAt(index);
    }

    public static <T extends Record> Predicate<List<? super T>> havingSize(IntPredicate sizePredicate) {
        Objects.requireNonNull(sizePredicate);
        return list -> sizePredicate.test(list.size());
    }

    public static <T extends Record> Predicate<List<? super T>> havingSizeGreaterThan(int size) {
        return list -> list.size() > size;
    }

    public static <T extends Record> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> categoryFunction,
                                                                                     Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valueFunction);
        return list -> new SingleRecord(categoryFunction.apply(list), null, valueFunction.apply(list));
    }

    public static <T extends Record> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return list -> new SingleRecord(valueFunction.apply(list));
    }

    public static <T extends Record> Function<List<T>, Record> aggregateToValues(Function<List<T>, String> categoryFunction,
                                                                                 Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valuesFunction);
        return list -> new StandardRecord(categoryFunction.apply(list), null, valuesFunction.apply(list));
    }

    public static <T extends Record> Function<List<T>, Record> aggregateToValues(Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        return list -> new StandardRecord(valuesFunction.apply(list));
    }

    public static <T extends Record> Function<List<T>, List<String>> maxValuesFunction(String nullValue) {
        return list -> list.stream()
                           .map(Record::streamOfFields)
                           .flatMap(Function.identity())
                           .collect(Collectors.collectingAndThen(
                                   Collectors.groupingBy(Field::getIndex, TreeMap::new,
                                           Collectors.mapping(Field::getValue,
                                                   Collectors.maxBy(Comparator.nullsFirst(Comparator.naturalOrder())))),
                                   map -> map.values().stream()
                                             .map(o -> o.orElse(nullValue))
                                             .collect(Collectors.toList())));
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(int keyIndex,
                                                                            int maxRecordsPerGroup,
                                                                            String nullValue,
                                                                            Integer... valueIndexes) {
        return pivotModifier(keyIndex, maxRecordsPerGroup, nullValue, Arrays.asList(valueIndexes));
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(int keyIndex,
                                                                            int maxRecordsPerGroup,
                                                                            String nullValue,
                                                                            List<Integer> valueIndexes) {
        if (maxRecordsPerGroup < 0) {
            throw new IllegalArgumentException("maxRecordsPerGroup=" + maxRecordsPerGroup);
        }
        Objects.requireNonNull(valueIndexes);
        int newRecordSize = 1 + maxRecordsPerGroup * valueIndexes.size();
        return new GroupModifier<>(r -> r.getValueAt(keyIndex), GroupModifier.aggregateToValues(
                GroupModifier.pivotValuesFunction(
                        r -> Stream.of(r.getValueAt(keyIndex)),
                        newRecordSize,
                        nullValue,
                        valueIndexes)));
    }

    public static <T extends Record> Function<List<T>, List<String>> pivotValuesFunction(
            Function<T, Stream<String>> newValuesOfFirstRecord,
            int newRecordSize,
            String nullValue,
            List<Integer> valueIndexes) {
        Objects.requireNonNull(newValuesOfFirstRecord);
        if (newRecordSize < 0) {
            throw new IllegalArgumentException("newRecordSize=" + newRecordSize);
        }
        Objects.requireNonNull(valueIndexes);
        return list ->
                Stream.concat(
                        Stream.concat(
                                newValuesOfFirstRecord.apply(list.get(0)),
                                list.stream()
                                    .map(r -> valueIndexes.stream().map(i -> r.getValueAtOrElse(i, nullValue)))
                                    .flatMap(Function.identity())),
                        Stream.generate(() -> nullValue))
                      .limit(newRecordSize)
                      .collect(Collectors.toList());
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(int keyIndex,
                                                                            int valueIndex,
                                                                            String nullValue,
                                                                            int valueClassIndex,
                                                                            String... valueClasses) {
        return pivotModifier(
                keyIndex,
                valueIndex,
                nullValue,
                valueClassIndex,
                Arrays.asList(valueClasses)
        );
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(int keyIndex,
                                                                            int valueIndex,
                                                                            String nullValue,
                                                                            int valueClassIndex,
                                                                            List<String> valueClasses) {
        Objects.requireNonNull(valueClasses);
        return pivotModifier(
                r -> r.getValueAt(keyIndex),
                r -> r.getValueAt(valueIndex),
                nullValue,
                r -> r.getValueAt(valueClassIndex),
                valueClasses
        );
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(Function<T, String> key,
                                                                            Function<T, String> newValue,
                                                                            String nullValue,
                                                                            Function<T, String> valueClass,
                                                                            List<String> valueClasses) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(newValue);
        Objects.requireNonNull(valueClass);
        Objects.requireNonNull(valueClasses);
        return pivotModifier(
                key,
                r -> null,
                r -> Stream.of(key.apply(r)),
                newValue,
                nullValue,
                valueClass,
                valueClasses
        );
    }

    public static <T extends Record> GroupModifier<T, Record> pivotModifier(Function<T, String> groupByClassifier,
                                                                            Function<T, String> newCategoryOfFirstRecord,
                                                                            Function<T, Stream<String>> newValuesOfFirstRecord,
                                                                            Function<T, String> newValue,
                                                                            String nullValue,
                                                                            Function<T, String> valueClass,
                                                                            List<String> valueClasses) {
        Objects.requireNonNull(groupByClassifier);
        Objects.requireNonNull(newCategoryOfFirstRecord);
        Objects.requireNonNull(newValuesOfFirstRecord);
        Objects.requireNonNull(newValue);
        Objects.requireNonNull(valueClass);
        Objects.requireNonNull(valueClasses);
        return new GroupModifier<>(
                groupByClassifier,
                GroupModifier.aggregateToValues(
                        list -> newCategoryOfFirstRecord.apply(list.get(0)),
                        GroupModifier.pivotValuesFunction(
                                newValuesOfFirstRecord,
                                newValue,
                                nullValue,
                                valueClass,
                                valueClasses
                        )));
    }

    public static <T extends Record> Function<List<T>, List<String>> pivotValuesFunction(
            Function<T, Stream<String>> newValuesOfFirstRecord,
            Function<T, String> newValue,
            String nullValue,
            Function<T, String> valueClass,
            List<String> valueClasses) {
        Objects.requireNonNull(newValuesOfFirstRecord);
        Objects.requireNonNull(newValue);
        Objects.requireNonNull(valueClass);
        Objects.requireNonNull(valueClasses);
        Function<List<T>, Stream<String>> newKeys = list ->
                newValuesOfFirstRecord.apply(list.get(0));
        Function<List<T>, Stream<String>> newValues = list ->
                valueClasses.stream()
                            .map(vc ->
                                    list.stream()
                                        .filter(r -> vc.equals(valueClass.apply(r)))
                                        .map(newValue)
                                        .map(v -> v == null ? nullValue : v)
                                        .findFirst()
                                        .orElse(nullValue));
        return list -> Stream.concat(newKeys.apply(list), newValues.apply(list))
                             .collect(Collectors.toList());
    }

    @Override
    public Stream<R> modify(Stream<T> recordStream) {
        return recordStream
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(groupByClassifier), r -> r.values().stream()))
                .filter(havingPredicate)
                .map(aggregateFunction);
    }

}
