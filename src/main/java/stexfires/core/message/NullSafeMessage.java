package stexfires.core.message;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullSafeMessage<T extends Record> implements RecordMessage<T> {

    public static final String DEFAULT_RECORD_IS_NULL_MESSAGE = "ERROR: Record is null!";

    private final RecordMessage<? super T> recordMessage;
    private final String recordIsNullMessage;

    public NullSafeMessage(RecordMessage<? super T> recordMessage) {
        this(recordMessage, DEFAULT_RECORD_IS_NULL_MESSAGE);
    }

    public NullSafeMessage(RecordMessage<? super T> recordMessage, String recordIsNullMessage) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(recordIsNullMessage);
        this.recordMessage = recordMessage;
        this.recordIsNullMessage = recordIsNullMessage;
    }

    @Override
    public final String createMessage(T record) {
        return record == null ? recordIsNullMessage : recordMessage.createMessage(record);
    }

}
