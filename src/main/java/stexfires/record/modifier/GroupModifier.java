package stexfires.record.modifier;

import stexfires.record.KeyRecord;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.message.RecordMessage;
import stexfires.util.NumberCheckType;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Aggregator / Group By.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public class GroupModifier<T extends TextRecord, R extends TextRecord> implements RecordStreamModifier<T, R> {

    private final Function<? super T, ?> groupByFunction;
    private final Predicate<List<? super T>> havingPredicate;
    private final Function<List<T>, ? extends R> aggregateFunction;

    public GroupModifier(Function<? super T, ?> groupByFunction,
                         Function<List<T>, ? extends R> aggregateFunction) {
        this(groupByFunction, list -> true, aggregateFunction);
    }

    public GroupModifier(Function<? super T, ?> groupByFunction,
                         Predicate<List<? super T>> havingPredicate,
                         Function<List<T>, ? extends R> aggregateFunction) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(havingPredicate);
        Objects.requireNonNull(aggregateFunction);
        this.groupByFunction = groupByFunction;
        this.havingPredicate = havingPredicate;
        this.aggregateFunction = aggregateFunction;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByCategory() {
        return TextRecord::category;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByMessage(RecordMessage<? super T> recordMessage) {
        return recordMessage.asFunction();
    }

    public static <T extends KeyRecord> Function<? super T, String> groupByKey() {
        return KeyRecord::key;
    }

    public static <T extends ValueRecord> Function<? super T, String> groupByValue() {
        return ValueRecord::value;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByTextAt(int index) {
        return r -> r.textAt(index);
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSize(IntPredicate sizePredicate) {
        Objects.requireNonNull(sizePredicate);
        return list -> sizePredicate.test(list.size());
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSize(NumberCheckType numberCheckType) {
        Objects.requireNonNull(numberCheckType);
        return list -> numberCheckType.checkInt(list.size());
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSizeEqualTo(int size) {
        return list -> list.size() == size;
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSizeGreaterThan(int size) {
        return list -> list.size() > size;
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> categoryFunction,
                                                                                         Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(categoryFunction.apply(list), null,
                valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValues(Function<List<T>, String> categoryFunction,
                                                                                         Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valuesFunction);
        return list -> new ManyFieldsRecord(categoryFunction.apply(list), null,
                valuesFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValues(Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        return list -> new ManyFieldsRecord(valuesFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValuesWithMessage(RecordMessage<? super T> valuesMessage) {
        Objects.requireNonNull(valuesMessage);
        return list -> new ManyFieldsRecord(list.stream()
                                                .map(valuesMessage.asFunction())
                                                .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValuesWithMessage(RecordMessage<? super T> categoryMessage,
                                                                                                    RecordMessage<? super T> valuesMessage) {
        Objects.requireNonNull(categoryMessage);
        Objects.requireNonNull(valuesMessage);
        return list -> new ManyFieldsRecord(categoryMessage.createMessage(list.get(0)), null,
                list.stream()
                    .map(valuesMessage.asFunction())
                    .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, String> categoryOfFirstElement() {
        return list -> list.get(0).category();
    }

    public static <T extends TextRecord> Function<List<T>, String> messageOfFirstElement(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return list -> recordMessage.createMessage(list.get(0));
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> collectValues(Collector<String, ?, Optional<String>> valueCollector,
                                                                                       String nullValue) {
        return list -> list.stream()
                           .flatMap(TextRecord::streamOfFields)
                           .collect(Collectors.collectingAndThen(
                                   Collectors.groupingBy(
                                           TextField::index,
                                           TreeMap::new,
                                           Collectors.mapping(
                                                   TextField::text,
                                                   valueCollector)),
                                   map -> map.values()
                                             .stream()
                                             .map(o -> o.orElse(nullValue))
                                             .collect(Collectors.toList())));
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> maxValuesNullsFirst(String nullValue) {
        return collectValues(Collectors.maxBy(Comparator.nullsFirst(Comparator.naturalOrder())),
                nullValue);
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> minValuesNullsLast(String nullValue) {
        return collectValues(Collectors.minBy(Comparator.nullsLast(Comparator.naturalOrder())),
                nullValue);
    }

    @Override
    public final Stream<R> modify(Stream<T> recordStream) {
        return recordStream
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(groupByFunction), r -> r.values().stream()))
                .filter(havingPredicate)
                .map(aggregateFunction);
    }

}
