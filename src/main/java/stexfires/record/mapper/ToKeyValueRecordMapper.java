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
    private final String nullKey;

    public ToKeyValueRecordMapper(int keyIndex, int valueIndex, String nullKey) {
        Objects.requireNonNull(nullKey);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.nullKey = nullKey;
    }

    @Override
    public final @NotNull KeyValueRecord map(@NotNull T record) {
        return new KeyValueRecord(record.category(), record.recordId(),
                record.textAtOrElse(keyIndex, nullKey), record.textAt(valueIndex));
    }

}
