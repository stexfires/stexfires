package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Record;
import stexfires.core.record.KeyValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToKeyValueMapper<T extends Record> implements RecordMapper<T, KeyValueRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final String nullKeyValue;

    public ToKeyValueMapper(int keyIndex, int valueIndex, String nullKeyValue) {
        Objects.requireNonNull(nullKeyValue);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.nullKeyValue = nullKeyValue;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public final @NotNull KeyValueRecord map(@NotNull T record) {
        return new KeyValueRecord(record.getCategory(), record.getRecordId(),
                record.getValueAtOrElse(keyIndex, nullKeyValue), record.getValueAt(valueIndex));
    }

}
