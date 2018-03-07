package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DispatcherConsumer<T extends Record> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    protected final BiPredicate<Integer, ? super T> predicate;
    protected final List<? extends RecordConsumer<? super T>> recordConsumers;

    public DispatcherConsumer(BiPredicate<Integer, ? super T> predicate,
                              List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(recordConsumers);
        this.predicate = predicate;
        this.recordConsumers = new ArrayList<>(recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> all(List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                true;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byRecord(Function<? super T, Integer> recordIndexFunction,
                                                                    List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIndexFunction.apply(record), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byCategory(Function<String, Integer> categoryIndexFunction,
                                                                      List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(categoryIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(categoryIndexFunction.apply(record.getCategory()), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byRecordId(Function<Long, Integer> recordIdIndexFunction,
                                                                      List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordIdIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIdIndexFunction.apply(record.getRecordId()), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> bySize(List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                record.size() == index;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byIndexPredicate(IntPredicate indexPredicate,
                                                                            List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(indexPredicate);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                indexPredicate.test(index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byIndexSupplier(Supplier<Integer> indexSupplier,
                                                                           List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(indexSupplier.get(), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byBooleanSupplier(Supplier<Boolean> booleanSupplier,
                                                                             List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(booleanSupplier);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                booleanSupplier.get();
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends Record> DispatcherConsumer<T> byFilters(List<? extends RecordFilter<? super T>> recordFilters,
                                                                     List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordFilters);
        Objects.requireNonNull(recordConsumers);
        List<RecordFilter<? super T>> localFilters = new ArrayList<>(recordFilters);
        BiPredicate<Integer, T> predicate = (index, record) ->
                index < localFilters.size() && localFilters.get(index).isValid(record);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    @Override
    public final void consume(T record) {
        synchronized (lock) {
            for (int index = 0; index < recordConsumers.size(); index++) {
                if (predicate.test(index, record)) {
                    recordConsumers.get(index).consume(record);
                }
            }
        }
    }

}
