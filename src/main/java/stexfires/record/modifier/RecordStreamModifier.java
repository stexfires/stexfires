package stexfires.record.modifier;

import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A RecordStreamModifier modifies a {@link Stream} of TextRecords.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * It uses the following intermediate operations to modify the stream: map, peek, skip, limit, distinct, filter, sorted, parallel, sequential, unordered
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #modify(Stream)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream
 * @since 0.1
 */
@FunctionalInterface
public interface RecordStreamModifier<T extends TextRecord, R extends TextRecord> {

    static <T extends TextRecord, R extends TextRecord> RecordStreamModifier<T, R> of(Function<Stream<T>, Stream<R>> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    static <T extends TextRecord, V extends TextRecord, R extends TextRecord> RecordStreamModifier<T, R> concat(RecordStreamModifier<T, V> firstRecordStreamModifier,
                                                                                                                RecordStreamModifier<V, R> secondRecordStreamModifier) {
        Objects.requireNonNull(firstRecordStreamModifier);
        Objects.requireNonNull(secondRecordStreamModifier);
        return recordStream -> secondRecordStreamModifier.modify(firstRecordStreamModifier.modify(recordStream));
    }

    static <T extends TextRecord, V extends TextRecord, R extends TextRecord> RecordStreamModifier<T, R> concat(RecordStreamModifier<T, V> firstRecordStreamModifier,
                                                                                                                RecordStreamModifier<V, V> secondRecordStreamModifier,
                                                                                                                RecordStreamModifier<V, R> thirdRecordStreamModifier) {
        Objects.requireNonNull(firstRecordStreamModifier);
        Objects.requireNonNull(secondRecordStreamModifier);
        Objects.requireNonNull(thirdRecordStreamModifier);
        return recordStream -> thirdRecordStreamModifier.modify(secondRecordStreamModifier.modify(firstRecordStreamModifier.modify(recordStream)));
    }

    Stream<R> modify(Stream<T> recordStream);

    default Function<Stream<T>, Stream<R>> asFunction() {
        return this::modify;
    }

    default <V extends TextRecord> RecordStreamModifier<V, R> compose(RecordStreamModifier<V, T> before) {
        Objects.requireNonNull(before);
        return recordStream -> modify(before.modify(recordStream));
    }

    default <V extends TextRecord> RecordStreamModifier<T, V> andThen(RecordStreamModifier<R, V> after) {
        Objects.requireNonNull(after);
        return recordStream -> after.modify(modify(recordStream));
    }

}
