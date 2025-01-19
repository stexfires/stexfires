package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.record.mapper.RecordMapper;

import java.util.*;

/**
 * @since 0.1
 */
public class MappingFilter<T extends TextRecord, V extends TextRecord> implements RecordFilter<T> {

    private final RecordMapper<? super T, ? extends V> recordMapper;
    private final RecordFilter<? super V> recordFilter;

    public MappingFilter(RecordMapper<? super T, ? extends V> recordMapper,
                         RecordFilter<? super V> recordFilter) {
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordFilter);
        this.recordMapper = recordMapper;
        this.recordFilter = recordFilter;
    }

    @Override
    public final boolean isValid(T record) {
        return recordFilter.isValid(recordMapper.map(record));
    }

}
