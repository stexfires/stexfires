package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdMessage implements RecordMessage<Record> {

    public static final String DEFAULT_PREFIX = "#";
    public static final String DEFAULT_MISSING_RECORD_ID_MESSAGE = "";

    private final String prefix;
    private final String missingRecordIdMessage;

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
    public String createMessage(Record record) {
        return record.getRecordId() != null ? prefix + String.valueOf(record.getRecordId()) : missingRecordIdMessage;
    }

}
