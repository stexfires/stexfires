package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class GroupModifier<T extends Record, R extends Record> implements RecordStreamModifier<T, R> {

    protected Function<? super T, ?> groupByClassifier;
    protected Predicate<List<? super T>> havingPredicate;
    protected Function<List<T>, R> aggregateFunction;

    public GroupModifier(Function<? super T, ?> groupByClassifier,
                         Function<List<T>, R> aggregateFunction) {
        this(groupByClassifier, list -> true, aggregateFunction);
    }

    public GroupModifier(Function<? super T, ?> groupByClassifier,
                         Predicate<List<? super T>> havingPredicate,
                         Function<List<T>, R> aggregateFunction) {
        Objects.requireNonNull(groupByClassifier);
        Objects.requireNonNull(havingPredicate);
        Objects.requireNonNull(aggregateFunction);
        this.groupByClassifier = groupByClassifier;
        this.havingPredicate = havingPredicate;
        this.aggregateFunction = aggregateFunction;
    }

    public static <T extends Record> Function<? super T, ?> groupByCategory() {
        return Record::getCategory;
    }

    public static <T extends Record> Predicate<List<? super T>> sizeGreaterThan(int size) {
        return list -> list.size() > size;
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
