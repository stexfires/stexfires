package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToConstantMapper<R extends Record> implements RecordMapper<Record, R> {

    private final R constantRecord;

    public ToConstantMapper(R constantRecord) {
        Objects.requireNonNull(constantRecord);
        this.constantRecord = constantRecord;
    }

    @Override
    public R map(Record record) {
        return constantRecord;
    }

}
