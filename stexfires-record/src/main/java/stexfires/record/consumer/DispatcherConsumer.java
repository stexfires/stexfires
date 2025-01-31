package stexfires.record.consumer;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class DispatcherConsumer<T extends TextRecord> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    private final BiPredicate<Integer, ? super T> predicate;
    private final List<? extends RecordConsumer<? super T>> recordConsumers;

    public DispatcherConsumer(BiPredicate<Integer, ? super T> predicate,
                              List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(recordConsumers);
        this.predicate = predicate;
        this.recordConsumers = List.copyOf(recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> all(List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                true;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byRecord(Function<? super T, Integer> recordIndexFunction,
                                                                        List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIndexFunction.apply(record), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byCategory(Function<@Nullable String, Integer> categoryIndexFunction,
                                                                          List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(categoryIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(categoryIndexFunction.apply(record.category()), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byRecordId(Function<@Nullable Long, Integer> recordIdIndexFunction,
                                                                          List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordIdIndexFunction);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIdIndexFunction.apply(record.recordId()), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> bySize(List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                record.size() == index;
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byIndexPredicate(IntPredicate indexPredicate,
                                                                                List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(indexPredicate);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                indexPredicate.test(index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byIndexSupplier(Supplier<Integer> indexSupplier,
                                                                               List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(indexSupplier.get(), index);
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byBooleanSupplier(Supplier<Boolean> booleanSupplier,
                                                                                 List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(booleanSupplier);
        Objects.requireNonNull(recordConsumers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                booleanSupplier.get();
        return new DispatcherConsumer<>(predicate, recordConsumers);
    }

    public static <T extends TextRecord> DispatcherConsumer<T> byFilters(List<? extends RecordFilter<? super T>> recordFilters,
                                                                         List<? extends RecordConsumer<? super T>> recordConsumers) {
        Objects.requireNonNull(recordFilters);
        Objects.requireNonNull(recordConsumers);
        var recordFiltersCopy = List.copyOf(recordFilters);
        BiPredicate<Integer, T> predicate = (index, record) ->
                (index < recordFiltersCopy.size()) && recordFiltersCopy.get(index).isValid(record);
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
