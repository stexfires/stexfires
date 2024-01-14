package stexfires.record.mapper;

import stexfires.record.TextRecord;

import java.util.Objects;

/**
 * @since 0.1
 */
public class ConstantMapper<T extends TextRecord, R extends TextRecord> implements RecordMapper<T, R> {

    private final R constantRecord;

    public ConstantMapper(R constantRecord) {
        Objects.requireNonNull(constantRecord);
        this.constantRecord = constantRecord;
    }

    @Override
    public final R map(T record) {
        return constantRecord;
    }

}
