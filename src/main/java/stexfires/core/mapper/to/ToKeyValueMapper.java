package stexfires.core.mapper.to;

import stexfires.core.Record;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.record.KeyValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToKeyValueMapper implements RecordMapper<Record, KeyValueRecord> {

    protected final int keyIndex;
    protected final int valueIndex;
    protected final String nullKeyValue;

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
