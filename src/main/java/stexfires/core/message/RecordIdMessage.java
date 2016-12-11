package stexfires.core.message;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdMessage<T extends Record> implements RecordMessage<T> {

    public static final String DEFAULT_PREFIX = "#";
    public static final String DEFAULT_MISSING_RECORD_ID_MESSAGE = "";

    protected final String prefix;
    protected final String missingRecordIdMessage;

    public RecordIdMessage() {
        this(DEFAULT_PREFIX, DEFAULT_MISSING_RECORD_ID_MESSAGE);
    }

    public RecordIdMessage(String prefix, String missingRecordIdMessage) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(missingRecordIdMessage);
        this.prefix = prefix;
        this.missingRecordIdMessage = missingRecordIdMessage;
    }

    @Override
    public String createMessage(T record) {
        return record.hasRecordId() ? prefix + String.valueOf(record.getRecordId()) : missingRecordIdMessage;
    }

}
