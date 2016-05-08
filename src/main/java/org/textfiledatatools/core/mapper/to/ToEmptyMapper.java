package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.EmptyRecord;

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
