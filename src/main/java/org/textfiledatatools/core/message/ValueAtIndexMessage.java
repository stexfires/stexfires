package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueAtIndexMessage implements RecordMessage<Record> {

    private final int index;

    public ValueAtIndexMessage(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Illegal index! index=" + index);
        }
        this.index = index;
    }

    @Override
    public String createMessage(Record record) {
        return record.getValueAt(index);
    }

}
