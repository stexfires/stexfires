package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.mapper.RecordMapper;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToKeyValueCommentFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueCommentFieldsRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final int commentIndex;
    private final String nullKey;

    public ToKeyValueCommentFieldsRecordMapper(int keyIndex, int valueIndex, int commentIndex,
                                               @NotNull String nullKey) {
        Objects.requireNonNull(nullKey);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.commentIndex = commentIndex;
        this.nullKey = nullKey;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull KeyValueCommentFieldsRecord map(@NotNull T record) {
        return new KeyValueCommentFieldsRecord(record.category(), record.recordId(),
                record.textAtOrElse(keyIndex, nullKey), record.textAt(valueIndex), record.textAt(commentIndex));
    }

}
