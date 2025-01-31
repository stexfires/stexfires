package stexfires.record.mapper.impl;

import stexfires.record.TextRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.RecordMessage;

import java.util.*;

/**
 * @since 0.1
 */
public final class ToValueFieldRecordMapper<T extends TextRecord> implements RecordMapper<T, ValueFieldRecord> {

    private final RecordMessage<? super T> valueMessage;

    public ToValueFieldRecordMapper(RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(valueMessage);
        this.valueMessage = valueMessage;
    }

    @Override
    public ValueFieldRecord map(T record) {
        return new ValueFieldRecord(record.category(), record.recordId(),
                valueMessage.createMessage(record));
    }

}
