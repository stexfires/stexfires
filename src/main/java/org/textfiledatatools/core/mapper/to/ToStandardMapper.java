package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.StandardRecord;

/**
 * Created by Mathias Kalb on 20.01.2016.
 */
public class ToStandardMapper implements RecordMapper<Record, StandardRecord> {

    @Override
    public StandardRecord map(Record record) {
        if (record instanceof StandardRecord) {
            return (StandardRecord) record;
        }
        return new StandardRecord(record.getCategory(), record.getRecordId(),
                Records.collectFieldValuesToList(record));
    }

}
