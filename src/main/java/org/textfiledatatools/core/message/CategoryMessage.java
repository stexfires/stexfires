package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryMessage implements RecordMessage<Record> {

    private final String nullCategoryValue;

    public CategoryMessage() {
        this(null);
    }

    public CategoryMessage(String nullCategoryValue) {
        this.nullCategoryValue = nullCategoryValue;
    }

    @Override
    public String createMessage(Record record) {
        return record.getCategoryOrElse(nullCategoryValue);
    }

}
