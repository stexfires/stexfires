package stexfires.record.mapper.impl;

import stexfires.record.TextRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.NotNullRecordMessage;
import stexfires.record.message.RecordMessage;

import java.util.Objects;

/**
 * @since 0.1
 */
public final class ToKeyValueFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, KeyValueFieldsRecord> {

    private final NotNullRecordMessage<? super T> keyMessage;
    private final RecordMessage<? super T> valueMessage;

    public ToKeyValueFieldsRecordMapper(NotNullRecordMessage<? super T> keyMessage,
                                        RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
    }

    @Override
    public KeyValueFieldsRecord map(T record) {
        return new KeyValueFieldsRecord(record.category(), record.recordId(),
                keyMessage.createMessage(record),
                valueMessage.createMessage(record));
    }

}
