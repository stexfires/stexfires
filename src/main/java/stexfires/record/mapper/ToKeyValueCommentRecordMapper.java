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
    private final String nullKey;

    public ToKeyValueCommentRecordMapper(int keyIndex, int valueIndex, int commentIndex, String nullKey) {
        Objects.requireNonNull(nullKey);
        this.keyIndex = keyIndex;
        this.valueIndex = valueIndex;
        this.commentIndex = commentIndex;
        this.nullKey = nullKey;
    }

    @Override
    public final @NotNull KeyValueCommentRecord map(@NotNull T record) {
        return new KeyValueCommentRecord(record.category(), record.recordId(),
                record.textAtOrElse(keyIndex, nullKey), record.textAt(valueIndex), record.textAt(commentIndex));
    }

}
