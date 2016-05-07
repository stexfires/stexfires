package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.SingleRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToSingleMapper implements RecordMapper<Record, SingleRecord> {

    protected final int valueIndex;

    public ToSingleMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public SingleRecord map(Record record) {
        return new SingleRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(valueIndex));
    }

}
