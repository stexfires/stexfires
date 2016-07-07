package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;

import java.util.List;
import java.util.Objects;
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

    public static <T extends Record> Function<List<T>, Record> aggregateToValues(Function<List<T>, String> categoryFunction,
                                                                                 Function<List<T>, List<String>> valuesFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valuesFunction);
        return list -> new StandardRecord(categoryFunction.apply(list), null, valuesFunction.apply(list));
    }

    @Override
    public Stream<R> modify(Stream<T> recordStream) {
        return recordStream
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(groupByClassifier),
                        r -> r.values().stream()))
                .filter(havingPredicate)
                .map(aggregateFunction);
    }

}
