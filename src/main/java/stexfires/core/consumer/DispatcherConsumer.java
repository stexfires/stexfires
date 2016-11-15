package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DispatcherConsumer<T extends Record> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    protected final BiPredicate<Integer, ? super T> predicate;
    protected final List<RecordConsumer<? super T>> recordConsumers;

    public DispatcherConsumer(BiPredicate<Integer, ? super T> predicate,
                              List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(recordConsumers);
        this.predicate = predicate;
        this.recordConsumers = new ArrayList<>(recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> all(List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (Integer index, T record)
                -> true;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byRecord(Function<? super T, Integer> recordIndexFunction,
                                                                    List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (Integer index, T record) ->
                Objects.equals(recordIndexFunction.apply(record), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byCategory(Function<String, Integer> categoryIndexFunction,
                                                                      List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(categoryIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (Integer index, T record) ->
                Objects.equals(categoryIndexFunction.apply(record.getCategory()), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> bySize(List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (Integer index, T record) ->
                record.size() == index;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byFilters(List<RecordFilter<? super T>> recordFilters,
                                                                     List<RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordFilters);
        Objects.requireNonNull(recordConsumers);
        List<RecordFilter<? super T>> localFilters = new ArrayList<>(recordFilters);
        BiPredicate<Integer, T> predicate = (Integer index, T record) ->
                index < localFilters.size() && localFilters.get(index).isValid(record);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    @Override
    public void consume(T record) {
        synchronized (lock) {
            for (int index = 0; index < recordConsumers.size(); index++) {
                if (predicate.test(index, record)) {
                    recordConsumers.get(index).consume(record);
                }
            }
        }
    }

}
