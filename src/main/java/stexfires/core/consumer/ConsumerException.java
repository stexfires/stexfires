package stexfires.core.consumer;

import stexfires.core.Record;

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
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append(message);
        } else {
            builder.append(DEFAULT_MESSAGE);
        }
        builder.append(MESSAGE_SEPARATOR);
        builder.append(record);
        return builder.toString();
    }

}
