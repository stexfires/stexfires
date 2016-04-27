package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.PairRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToPairMapper implements RecordMapper<Record, PairRecord> {

    private final int firstValueIndex;
    private final int secondValueIndex;

    public ToPairMapper(int firstValueIndex, int secondValueIndex) {
        this.firstValueIndex = firstValueIndex;
        this.secondValueIndex = secondValueIndex;
    }

    @Override
    public PairRecord map(Record record) {
        return new PairRecord(record.getCategory(), record.getRecordId(), record.getValueAt(firstValueIndex), record.getValueAt(secondValueIndex));
    }

}
