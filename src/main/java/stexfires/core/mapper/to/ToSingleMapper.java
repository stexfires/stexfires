package stexfires.core.mapper.to;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.record.SingleRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToSingleMapper implements RecordMapper<Record, SingleRecord> {

    protected final int valueIndex;

    public ToSingleMapper() {
        this(Fields.FIRST_FIELD_INDEX);
    }

    public ToSingleMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public SingleRecord map(Record record) {
        return new SingleRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(valueIndex));
    }

}
