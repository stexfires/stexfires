package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.PairRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToPairMapper implements RecordMapper<Record, PairRecord> {

    protected final int firstIndex;
    protected final int secondIndex;

    public ToPairMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public PairRecord map(Record record) {
        return new PairRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(firstIndex), record.getValueAt(secondIndex));
    }

}
