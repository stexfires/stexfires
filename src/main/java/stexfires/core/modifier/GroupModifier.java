package stexfires.core.modifier;

import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;
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
        return TextRecord::getCategory;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByMessage(RecordMessage<? super T> recordMessage) {
        return recordMessage.asFunction();
    }

    public static <T extends KeyRecord> Function<? super T, String> groupByKeyField() {
        return KeyRecord::getValueOfKeyField;
    }

    public static <T extends ValueRecord> Function<? super T, String> groupByValueField() {
        return ValueRecord::getValueOfValueField;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByValueAt(int index) {
        return r -> r.getValueAt(index);
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
        return list -> new SingleRecord(categoryFunction.apply(list), null,
                valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return list -> new SingleRecord(valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValues(Function<List<T>, String> categoryFunction,
                                                                                         Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valuesFunction);
        return list -> new StandardRecord(categoryFunction.apply(list), null,
                valuesFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValues(Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        return list -> new StandardRecord(valuesFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValuesWithMessage(RecordMessage<? super T> valuesMessage) {
        Objects.requireNonNull(valuesMessage);
        return list -> new StandardRecord(list.stream()
                                              .map(valuesMessage.asFunction())
                                              .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToValuesWithMessage(RecordMessage<? super T> categoryMessage,
                                                                                                    RecordMessage<? super T> valuesMessage) {
        Objects.requireNonNull(categoryMessage);
        Objects.requireNonNull(valuesMessage);
        return list -> new StandardRecord(categoryMessage.createMessage(list.get(0)), null,
                list.stream()
                    .map(valuesMessage.asFunction())
                    .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, String> categoryOfFirstElement() {
        return list -> list.get(0).getCategory();
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
                                           Field::getIndex,
                                           TreeMap::new,
                                           Collectors.mapping(
                                                   Field::getValue,
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
