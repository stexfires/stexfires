package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Fields;
import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValuesMessage implements RecordMessage<Record> {

    private final CharSequence delimiter;

    public ValuesMessage() {
        this(Fields.DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public ValuesMessage(CharSequence delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String createMessage(Record record) {
        return Fields.joinValues(record, delimiter);
    }

}
