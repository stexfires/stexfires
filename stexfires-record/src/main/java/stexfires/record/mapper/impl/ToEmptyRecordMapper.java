package stexfires.record.mapper.impl;

import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.mapper.RecordMapper;

/**
 * @since 0.1
 */
public final class ToEmptyRecordMapper<T extends TextRecord> implements RecordMapper<T, EmptyRecord> {

    public ToEmptyRecordMapper() {
    }

    @Override
    public EmptyRecord map(T record) {
        return TextRecords.empty();
    }

}
