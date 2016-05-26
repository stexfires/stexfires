package stexfires.core.mapper.to;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.record.StandardRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToStandardMapper implements RecordMapper<Record, StandardRecord> {

    @Override
    public StandardRecord map(Record record) {
        if (record instanceof StandardRecord) {
            return (StandardRecord) record;
        }
        return new StandardRecord(record.getCategory(), record.getRecordId(),
                Fields.collectValues(record));
    }

}
