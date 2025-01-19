package stexfires.record.mapper.impl;

import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.NotNullRecordMessage;
import stexfires.record.message.RecordMessage;

import java.util.*;

/**
 * @since 0.1
 */
public final class ToKeyValueCommentFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueCommentFieldsRecord> {

    private final NotNullRecordMessage<? super T> keyMessage;
    private final RecordMessage<? super T> valueMessage;
    private final RecordMessage<? super T> commentMessage;

    public ToKeyValueCommentFieldsRecordMapper(NotNullRecordMessage<? super T> keyMessage,
                                               RecordMessage<? super T> valueMessage,
                                               RecordMessage<? super T> commentMessage) {
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        Objects.requireNonNull(commentMessage);
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
        this.commentMessage = commentMessage;
    }

    @Override
    public KeyValueCommentFieldsRecord map(T record) {
        return new KeyValueCommentFieldsRecord(record.category(), record.recordId(),
                keyMessage.createMessage(record),
                valueMessage.createMessage(record),
                commentMessage.createMessage(record));
    }

}
