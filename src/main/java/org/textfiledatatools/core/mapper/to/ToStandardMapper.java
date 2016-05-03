package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Fields;
import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.StandardRecord;

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
