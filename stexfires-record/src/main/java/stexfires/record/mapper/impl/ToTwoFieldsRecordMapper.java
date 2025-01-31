package stexfires.record.mapper.impl;

import stexfires.record.TextRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.RecordMessage;

import java.util.*;

/**
 * @since 0.1
 */
public final class ToTwoFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, TwoFieldsRecord> {

    private final RecordMessage<? super T> firstTextMessage;
    private final RecordMessage<? super T> secondTextMessage;

    public ToTwoFieldsRecordMapper(RecordMessage<? super T> firstTextMessage,
                                   RecordMessage<? super T> secondTextMessage) {
        Objects.requireNonNull(firstTextMessage);
        Objects.requireNonNull(secondTextMessage);
        this.firstTextMessage = firstTextMessage;
        this.secondTextMessage = secondTextMessage;
    }

    @Override
    public TwoFieldsRecord map(T record) {
        return new TwoFieldsRecord(record.category(), record.recordId(),
                firstTextMessage.createMessage(record),
                secondTextMessage.createMessage(record));
    }

}
