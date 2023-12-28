package stexfires.record.mapper;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class IdentityMapper<T extends TextRecord> implements RecordMapper<T, T> {

    public IdentityMapper() {
    }

    @Override
    public final T map(T record) {
        return record;
    }

}
