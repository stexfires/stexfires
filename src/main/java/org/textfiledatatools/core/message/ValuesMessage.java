package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.Records;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValuesMessage implements RecordMessage<Record> {

    private final CharSequence delimiter;

    public ValuesMessage() {
        this(Records.DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public ValuesMessage(CharSequence delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String createMessage(Record record) {
        return Records.joinFieldValues(record, delimiter);
    }

}
