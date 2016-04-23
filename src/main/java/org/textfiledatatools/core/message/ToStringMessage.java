package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToStringMessage implements RecordMessage<Record> {

    @Override
    public String createMessage(Record record) {
        return record.toString();
    }

}
