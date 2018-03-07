package stexfires.core.mapper.to;

import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.record.EmptyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToEmptyMapper<T extends Record> implements RecordMapper<T, EmptyRecord> {

    public ToEmptyMapper() {
    }

    @Override
    public final EmptyRecord map(T record) {
        return Records.empty();
    }

}
