package stexfires.core.mapper.to;

import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.record.EmptyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToEmptyMapper implements RecordMapper<Record, EmptyRecord> {

    @Override
    public EmptyRecord map(Record record) {
        return Records.empty();
    }

}
