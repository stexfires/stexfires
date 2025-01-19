package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.function.BooleanUnaryOperator;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * A RecordFilter is a filter (boolean-valued function) for a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #isValid(stexfires.record.TextRecord)}.
 *
 * @see java.util.function.Predicate
 * @see java.util.stream.Stream#filter(Predicate)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordFilter<T extends TextRecord> {

    static <T extends TextRecord> RecordFilter<T> ofPredicate(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate::test;
    }

    static <T extends TextRecord> RecordFilter<T> ofFunction(Function<T, Boolean> function) {
        Objects.requireNonNull(function);
        // The "apply" function must not return "null"!
        return function::apply;
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

    static <T extends TextRecord> RecordFilter<T> not(RecordFilter<? super T> recordFilter) {
        Objects.requireNonNull(recordFilter);
        return record -> !recordFilter.isValid(record);
    }

    static <T extends TextRecord> RecordFilter<T> isEmpty() {
        return TextRecord::isEmpty;
    }

    static <T extends TextRecord> RecordFilter<T> isNotEmpty() {
        return TextRecord::isNotEmpty;
    }

    static <T extends TextRecord> RecordFilter<T> hasCategory() {
        return TextRecord::hasCategory;
    }

    static <T extends TextRecord> RecordFilter<T> hasRecordId() {
        return TextRecord::hasRecordId;
    }

    boolean isValid(T record);

    default Predicate<T> asPredicate() {
        return this::isValid;
    }

    default Function<T, Boolean> asFunction() {
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

    default RecordFilter<T> andThen(BooleanUnaryOperator booleanUnaryOperator) {
        Objects.requireNonNull(booleanUnaryOperator);
        return record -> booleanUnaryOperator.applyAsBoolean(isValid(record));
    }

}
