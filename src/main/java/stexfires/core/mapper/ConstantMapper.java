package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMapper<T extends Record, R extends Record> implements RecordMapper<T, R> {

    protected final R constantRecord;

    public ConstantMapper(R constantRecord) {
        Objects.requireNonNull(constantRecord);
        this.constantRecord = constantRecord;
    }

    @Override
    public R map(T record) {
        return constantRecord;
    }

}
