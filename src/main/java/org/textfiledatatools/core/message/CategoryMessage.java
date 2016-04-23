package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryMessage implements RecordMessage<Record> {

    private final String other;

    public CategoryMessage() {
        this(null);
    }

    public CategoryMessage(String other) {
        this.other = other;
    }

    @Override
    public String createMessage(Record record) {
        return record.getCategoryOrElse(other);
    }

}
