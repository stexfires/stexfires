package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * An UnaryRecordStreamModifier modifies a {@link Stream} of Records.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #modify(Stream)}.
 * <p>
 * It uses the following intermediate operations to modify the stream: map, peek, skip, limit, distinct, filter, sorted, parallel, sequential, unordered
 *
 * @author Mathias Kalb
 * @see java.util.function.UnaryOperator
 * @see java.util.stream.Stream
 * @since 0.1
 */
@FunctionalInterface
public interface UnaryRecordStreamModifier<T extends Record> extends RecordStreamModifier<T, T> {

    static <T extends Record> UnaryRecordStreamModifier<T> of(UnaryOperator<Stream<T>> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        return unaryOperator::apply;
    }

    default UnaryOperator<Stream<T>> asUnaryOperator() {
        return this::modify;
    }

}
