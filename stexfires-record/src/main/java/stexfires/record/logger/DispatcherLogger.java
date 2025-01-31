package stexfires.record.logger;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class DispatcherLogger<T extends TextRecord> implements RecordLogger<T> {

    protected final Object lock = new Object();

    private final BiPredicate<Integer, ? super T> predicate;
    private final List<? extends RecordLogger<? super T>> recordLoggers;

    public DispatcherLogger(BiPredicate<Integer, ? super T> predicate,
                            List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(recordLoggers);
        this.predicate = predicate;
        this.recordLoggers = List.copyOf(recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> all(List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                true;
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byRecord(Function<? super T, Integer> recordIndexFunction,
                                                                      List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(recordIndexFunction);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIndexFunction.apply(record), index);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byCategory(Function<@Nullable String, Integer> categoryIndexFunction,
                                                                        List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(categoryIndexFunction);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(categoryIndexFunction.apply(record.category()), index);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byRecordId(Function<@Nullable Long, Integer> recordIdIndexFunction,
                                                                        List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(recordIdIndexFunction);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(recordIdIndexFunction.apply(record.recordId()), index);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> bySize(List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                record.size() == index;
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byIndexPredicate(IntPredicate indexPredicate,
                                                                              List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(indexPredicate);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                indexPredicate.test(index);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byIndexSupplier(Supplier<Integer> indexSupplier,
                                                                             List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                Objects.equals(indexSupplier.get(), index);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byBooleanSupplier(Supplier<Boolean> booleanSupplier,
                                                                               List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(booleanSupplier);
        Objects.requireNonNull(recordLoggers);
        BiPredicate<Integer, T> predicate = (index, record) ->
                booleanSupplier.get();
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    public static <T extends TextRecord> DispatcherLogger<T> byFilters(List<? extends RecordFilter<? super T>> recordFilters,
                                                                       List<? extends RecordLogger<? super T>> recordLoggers) {
        Objects.requireNonNull(recordFilters);
        Objects.requireNonNull(recordLoggers);
        var recordFiltersCopy = List.copyOf(recordFilters);
        BiPredicate<Integer, T> predicate = (index, record) ->
                (index < recordFiltersCopy.size()) && recordFiltersCopy.get(index).isValid(record);
        return new DispatcherLogger<>(predicate, recordLoggers);
    }

    @Override
    public final void log(T record) {
        try {
            synchronized (lock) {
                for (int index = 0; index < recordLoggers.size(); index++) {
                    if (predicate.test(index, record)) {
                        recordLoggers.get(index).log(record);
                    }
                }
            }
        } catch (NullPointerException | UnsupportedOperationException | ClassCastException | IllegalArgumentException |
                 IllegalStateException e) {
            // Ignore Exception
        }
    }

}
