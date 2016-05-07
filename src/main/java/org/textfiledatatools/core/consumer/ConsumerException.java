package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConsumerException extends Exception {

    protected static final String DEFAULT_MESSAGE = "Could not consume this record:";
    protected static final String MESSAGE_SEPARATOR = " ";
    private static final long serialVersionUID = 1L;

    public ConsumerException(String message, Record record) {
        super(createMessage(message, record));
    }

    public ConsumerException(Record record, Throwable cause) {
        super(createMessage(null, record), cause);
    }

    public ConsumerException(String message, Record record, Throwable cause) {
        super(createMessage(message, record), cause);
    }

    protected static String createMessage(String message, Record record) {
        StringBuilder b = new StringBuilder();
        if (message != null) {
            b.append(message);
        } else {
            b.append(DEFAULT_MESSAGE);
        }
        b.append(MESSAGE_SEPARATOR);
        b.append(record);
        return b.toString();
    }

}
