package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.record.KeyValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToKeyValueMapper implements RecordMapper<Record, KeyValueRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final String nullKeyValue;

    public ToKeyValueMapper(int keyIndex, int valueIndex, String nullKeyValue) {
        Objects.requireNonNull(nullKeyValue);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.nullKeyValue = nullKeyValue;
    }

    @Override
    public KeyValueRecord map(Record record) {
        return new KeyValueRecord(record.getCategory(), record.getRecordId(),
                record.getValueAtOrElse(keyIndex, nullKeyValue), record.getValueAt(valueIndex));
    }

}
