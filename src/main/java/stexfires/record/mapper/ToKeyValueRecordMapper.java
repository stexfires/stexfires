package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToKeyValueRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final String nullKeyValue;

    public ToKeyValueRecordMapper(int keyIndex, int valueIndex, String nullKeyValue) {
        Objects.requireNonNull(nullKeyValue);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.nullKeyValue = nullKeyValue;
    }

    @Override
    public final @NotNull KeyValueRecord map(@NotNull T record) {
        return new KeyValueRecord(record.category(), record.recordId(),
                record.valueAtOrElse(keyIndex, nullKeyValue), record.valueAt(valueIndex));
    }

}