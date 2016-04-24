package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Record;

import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValuesMessage implements RecordMessage<Record> {

    private static final String DEFAULT_DELIMITER = ";";

    private final CharSequence delimiter;

    public ValuesMessage() {
        this(DEFAULT_DELIMITER);
    }

    public ValuesMessage(CharSequence delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String createMessage(Record record) {
        return record.toNewStream().map(Field::getValue).collect(Collectors.joining(delimiter));
    }

}
