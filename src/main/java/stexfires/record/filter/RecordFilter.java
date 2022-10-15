package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.record.mapper.RecordMapper;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A RecordFilter is a filter (boolean-valued function) for a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #isValid(stexfires.record.TextRecord)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Predicate
 * @see java.util.stream.Stream#filter(Predicate)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordFilter<T extends TextRecord> {

    static <T extends TextRecord> RecordFilter<T> of(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate::test;
    }

    static <T extends TextRecord, V extends TextRecord> RecordFilter<T> mapAndFilter(RecordMapper<? super T, ? extends V> recordMapper,
                                                                                     RecordFilter<? super V> recordFilter) {
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordFilter);
        return record -> recordFilter.isValid(recordMapper.map(record));
    }

    static <T extends TextRecord> RecordFilter<T> concatAnd(RecordFilter<? super T> firstRecordFilter,
                                                            RecordFilter<? super T> secondRecordFilter) {
        Objects.requireNonNull(firstRecordFilter);
        Objects.requireNonNull(secondRecordFilter);
        return record -> firstRecordFilter.isValid(record) && secondRecordFilter.isValid(record);
    }

    static <T extends TextRecord> RecordFilter<T> concatAnd(Stream<RecordFilter<T>> recordFilters) {
        Objects.requireNonNull(recordFilters);
        return recordFilters.reduce(r -> true, RecordFilter::and);
    }

    static <T extends TextRecord> RecordFilter<T> concatOr(RecordFilter<? super T> firstRecordFilter,
                                                           RecordFilter<? super T> secondRecordFilter) {
        Objects.requireNonNull(firstRecordFilter);
        Objects.requireNonNull(secondRecordFilter);
        return record -> firstRecordFilter.isValid(record) || secondRecordFilter.isValid(record);
    }

    static <T extends TextRecord> RecordFilter<T> concatOr(Stream<RecordFilter<T>> recordFilters) {
        Objects.requireNonNull(recordFilters);
        return recordFilters.reduce(r -> false, RecordFilter::or);
    }

    boolean isValid(T record);

    default Predicate<T> asPredicate() {
        return this::isValid;
    }

    default RecordFilter<T> and(RecordFilter<? super T> other) {
        Objects.requireNonNull(other);
        return record -> isValid(record) && other.isValid(record);
    }

    default RecordFilter<T> negate() {
        return record -> !isValid(record);
    }

    default RecordFilter<T> or(RecordFilter<? super T> other) {
        Objects.requireNonNull(other);
        return record -> isValid(record) || other.isValid(record);
    }

}
