package org.textfiledatatools.core.mapper;

import org.textfiledatatools.core.Record;

import java.util.function.Function;

/**
 * A UnaryRecordMapper maps a {@link Record} to another record of the same type.
 * <p>
 * It should not throw a RuntimeException (like NullPointerException).
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #map(T)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.UnaryOperator
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface UnaryRecordMapper<T extends Record> extends RecordMapper<T, T> {
}
