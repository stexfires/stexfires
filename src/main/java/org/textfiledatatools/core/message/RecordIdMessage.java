package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdMessage implements RecordMessage<Record> {

    public static final String DEFAULT_PREFIX = "#";

    private final String prefix;

    public RecordIdMessage() {
        this(null);
    }

    public RecordIdMessage(String prefix) {
        this.prefix = prefix != null ? prefix : DEFAULT_PREFIX;
    }

    @Override
    public String createMessage(Record record) {
        return record.getRecordId() != null ? prefix + String.valueOf(record.getRecordId()) : "";
    }

}
