package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A RecordStreamModifier modifies a {@link Stream} of Records.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #modify(Stream)}.
 * <p>
 * It uses the following intermediate operations to modify the stream: map, peek, skip, limit, distinct, filter, sorted, parallel, sequential, unordered
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream
 * @since 0.1
 */
@FunctionalInterface
public interface RecordStreamModifier<T extends Record, R extends Record> {

    static <T extends Record, R extends Record> RecordStreamModifier<T, R> of(Function<Stream<T>, Stream<R>> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    static <T extends Record, V extends Record, R extends Record> RecordStreamModifier<T, R> concat(RecordStreamModifier<T, V> firstRecordStreamModifier,
                                                                                                    RecordStreamModifier<V, R> secondRecordStreamModifier) {
        Objects.requireNonNull(firstRecordStreamModifier);
        Objects.requireNonNull(secondRecordStreamModifier);
        return (Stream<T> recordStream) -> secondRecordStreamModifier.modify(firstRecordStreamModifier.modify(recordStream));
    }

    Stream<R> modify(Stream<T> recordStream);

    default Function<Stream<T>, Stream<R>> asFunction() {
        return this::modify;
    }

    default <V extends Record> RecordStreamModifier<T, V> andThen(RecordStreamModifier<R, V> after) {
        Objects.requireNonNull(after);
        return (Stream<T> recordStream) -> after.modify(modify(recordStream));
    }

}
