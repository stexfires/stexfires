package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Function;

/**
 * A RecordMapper maps a {@link Record} to another record.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a functional interface whose functional method is {@link #map(Record)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface RecordMapper<T extends Record, R extends Record> {

    static <T extends Record, R extends Record> RecordMapper<T, R> of(Function<T, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    static <T extends Record, V extends Record, R extends Record> RecordMapper<T, R> concat(RecordMapper<? super T, ? extends V> firstRecordMapper,
                                                                                            RecordMapper<? super V, ? extends R> secondRecordMapper) {
        Objects.requireNonNull(firstRecordMapper);
        Objects.requireNonNull(secondRecordMapper);
        return record -> secondRecordMapper.map(firstRecordMapper.map(record));
    }

    static <T extends Record, V extends Record, R extends Record> RecordMapper<T, R> concat(RecordMapper<? super T, ? extends V> firstRecordMapper,
                                                                                            RecordMapper<? super V, ? extends V> secondRecordMapper,
                                                                                            RecordMapper<? super V, ? extends R> thirdRecordMapper) {
        Objects.requireNonNull(firstRecordMapper);
        Objects.requireNonNull(secondRecordMapper);
        Objects.requireNonNull(thirdRecordMapper);
        return record -> thirdRecordMapper.map(secondRecordMapper.map(firstRecordMapper.map(record)));
    }

    R map(T record);

    default Function<T, R> asFunction() {
        return this::map;
    }

    default <V extends Record> RecordMapper<V, R> compose(RecordMapper<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return record -> map(before.map(record));
    }

    default <V extends Record> RecordMapper<T, V> andThen(RecordMapper<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return record -> after.map(map(record));
    }

}
