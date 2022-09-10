package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.mapper.RecordMapper;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToKeyValueFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueFieldsRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final String nullKey;

    public ToKeyValueFieldsRecordMapper(int keyIndex, int valueIndex,
                                        @NotNull String nullKey) {
        Objects.requireNonNull(nullKey);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.nullKey = nullKey;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull KeyValueFieldsRecord map(@NotNull T record) {
        return new KeyValueFieldsRecord(record.category(), record.recordId(),
                record.textAtOrElse(keyIndex, nullKey), record.textAt(valueIndex));
    }

}
