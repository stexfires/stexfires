package stexfires.record.modifier;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * @since 0.1
 */
public class UnaryGroupModifier<T extends TextRecord> extends GroupModifier<T, T> {

    public UnaryGroupModifier(Function<? super T, ?> groupByFunction,
                              Function<List<T>, ? extends T> aggregateFunction) {
        super(groupByFunction, aggregateFunction);
    }

    public UnaryGroupModifier(Function<? super T, ?> groupByFunction,
                              Predicate<List<? super T>> havingPredicate,
                              Function<List<T>, ? extends T> aggregateFunction) {
        super(groupByFunction, havingPredicate, aggregateFunction);
    }

    public static <T extends TextRecord> UnaryGroupModifier<T> first(Function<? super T, ?> groupByFunction) {
        Objects.requireNonNull(groupByFunction);
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = List::getFirst;
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord> UnaryGroupModifier<T> last(Function<? super T, ?> groupByFunction) {
        Objects.requireNonNull(groupByFunction);
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = List::getLast;
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord> UnaryGroupModifier<T> max(Function<? super T, ?> groupByFunction,
                                                                   Comparator<? super T> recordComparator) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(recordComparator);
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = list -> list.stream()
                                                             .max(recordComparator).orElseThrow();
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord> UnaryGroupModifier<T> min(Function<? super T, ?> groupByFunction,
                                                                   Comparator<? super T> recordComparator) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(recordComparator);
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = list -> list.stream()
                                                             .min(recordComparator).orElseThrow();
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord> UnaryGroupModifier<T> reduce(Function<? super T, ?> groupByFunction,
                                                                      BinaryOperator<T> accumulator) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(accumulator);
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = list -> list.stream()
                                                             .reduce(accumulator).orElseThrow();
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord, R extends T> UnaryGroupModifier<T> collect(Function<? super T, ?> groupByFunction,
                                                                                    Collector<? super T, ?, R> collector) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(collector);
        // The list contains always at least one record.
        Function<List<T>, R> aggregateFunction = list -> list.stream()
                                                             .collect(collector);
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

    public static <T extends TextRecord, R extends T> UnaryGroupModifier<T> collect(Function<? super T, ?> groupByFunction,
                                                                                    Collector<? super T, ?, Optional<R>> collector,
                                                                                    @Nullable R nullValue) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(collector);
        // The list contains always at least one record.
        Function<List<T>, @Nullable R> aggregateFunction = list -> list.stream()
                                                                       .collect(collector).orElse(nullValue);
        return new UnaryGroupModifier<>(groupByFunction, aggregateFunction);
    }

}
