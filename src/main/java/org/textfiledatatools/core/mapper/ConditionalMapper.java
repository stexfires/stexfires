package org.textfiledatatools.core.mapper;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalMapper<T extends Record, R extends Record> implements RecordMapper<T, R> {

    protected final RecordFilter<? super T> condition;
    protected final RecordMapper<? super T, ? extends R> trueMapper;
    protected final RecordMapper<? super T, ? extends R> falseMapper;

    public ConditionalMapper(RecordFilter<? super T> condition,
                             RecordMapper<? super T, ? extends R> trueMapper,
                             RecordMapper<? super T, ? extends R> falseMapper) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMapper);
        Objects.requireNonNull(falseMapper);
        this.condition = condition;
        this.trueMapper = trueMapper;
        this.falseMapper = falseMapper;
    }

    @Override
    public R map(T record) {
        return condition.isValid(record) ? trueMapper.map(record) : falseMapper.map(record);
    }

}
