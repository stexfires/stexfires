package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UnaryGroupModifier<T extends Record> extends GroupModifier<T, T> {

    public UnaryGroupModifier(Function<? super T, ?> groupByClassifier,
                              Function<List<T>, ? extends T> aggregateFunction) {
        super(groupByClassifier, aggregateFunction);
    }

    public UnaryGroupModifier(Function<? super T, ?> groupByClassifier,
                              Predicate<List<? super T>> havingPredicate,
                              Function<List<T>, ? extends T> aggregateFunction) {
        super(groupByClassifier, havingPredicate, aggregateFunction);
    }

    public static <T extends Record> UnaryGroupModifier<T> first(Function<? super T, ?> groupByClassifier) {
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = list -> list.get(0);
        return new UnaryGroupModifier<>(groupByClassifier, aggregateFunction);
    }

    public static <T extends Record> UnaryGroupModifier<T> last(Function<? super T, ?> groupByClassifier) {
        // The list contains always at least one record.
        Function<List<T>, T> aggregateFunction = list -> list.get(list.size() - 1);
        return new UnaryGroupModifier<>(groupByClassifier, aggregateFunction);
    }

    public static <T extends Record> UnaryGroupModifier<T> max(Function<? super T, ?> groupByClassifier,
                                                               Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        // The list contains always at least one record.
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Function<List<T>, T> aggregateFunction = list -> list.stream().max(comparator).get();
        return new UnaryGroupModifier<>(groupByClassifier, aggregateFunction);
    }

    public static <T extends Record> UnaryGroupModifier<T> min(Function<? super T, ?> groupByClassifier,
                                                               Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        // The list contains always at least one record.
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Function<List<T>, T> aggregateFunction = list -> list.stream().min(comparator).get();
        return new UnaryGroupModifier<>(groupByClassifier, aggregateFunction);
    }

}
