package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.util.StringComparisonType;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MessageFilter<T extends Record> implements RecordFilter<T> {

    protected final RecordMessage<? super T> recordMessage;
    protected final StringComparisonType stringComparisonType;
    protected final String compareMessage;

    public MessageFilter(RecordMessage<? super T> recordMessage, StringComparisonType stringComparisonType, String compareMessage) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareMessage);
        this.recordMessage = recordMessage;
        this.stringComparisonType = stringComparisonType;
        this.compareMessage = compareMessage;
    }

    @Override
    public boolean isValid(T record) {
        return StringComparisonType.compare(recordMessage.createMessage(record), stringComparisonType, compareMessage);
    }

}

