package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMapper<R extends Record> implements RecordMapper<Record, R> {

    protected final R constantRecord;

    public ConstantMapper(R constantRecord) {
        Objects.requireNonNull(constantRecord);
        this.constantRecord = constantRecord;
    }

    @Override
    public R map(Record record) {
        return constantRecord;
    }

}
