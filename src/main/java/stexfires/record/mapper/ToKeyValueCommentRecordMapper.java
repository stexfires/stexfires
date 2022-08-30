package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueCommentRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToKeyValueCommentRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueCommentRecord> {

    private final int keyIndex;
    private final int valueIndex;
    private final int commentIndex;
    private final String nullKeyValue;

    public ToKeyValueCommentRecordMapper(int keyIndex, int valueIndex, int commentIndex, String nullKeyValue) {
        Objects.requireNonNull(nullKeyValue);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.commentIndex = commentIndex;
        this.nullKeyValue = nullKeyValue;
    }

    @Override
    public final @NotNull KeyValueCommentRecord map(@NotNull T record) {
        return new KeyValueCommentRecord(record.category(), record.recordId(),
                record.valueAtOrElse(keyIndex, nullKeyValue), record.valueAt(valueIndex), record.valueAt(commentIndex));
    }

}
