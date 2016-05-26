package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An UnaryRecordMapper maps a {@link Record} to another record of the same type.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #map(Record)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.UnaryOperator
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface UnaryRecordMapper<T extends Record> extends RecordMapper<T, T> {

    static <T extends Record> UnaryRecordMapper<T> of(UnaryOperator<T> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        return unaryOperator::apply;
    }

}
