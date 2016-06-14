package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.mapper.RecordMapper;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MapModifier<T extends Record, R extends Record> implements RecordStreamModifier<T, R> {

    protected final RecordMapper<? super T, ? extends R> recordMapper;

    public MapModifier(RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordMapper);
        this.recordMapper = recordMapper;
    }

    @Override
    public Stream<R> modify(Stream<T> recordStream) {
        return recordStream.map(recordMapper::map);
    }

}
