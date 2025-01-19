package stexfires.record.modifier;

import stexfires.record.TextRecord;
import stexfires.record.mapper.RecordMapper;

import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public class MapModifier<T extends TextRecord, R extends TextRecord> implements RecordStreamModifier<T, R> {

    private final RecordMapper<? super T, ? extends R> recordMapper;

    public MapModifier(RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordMapper);
        this.recordMapper = recordMapper;
    }

    @Override
    public final Stream<R> modify(Stream<T> recordStream) {
        return recordStream.map(recordMapper::map);
    }

}
