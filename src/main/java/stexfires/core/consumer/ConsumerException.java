package stexfires.core.consumer;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConsumerException extends Exception {

    public static final String DEFAULT_MESSAGE = "Could not consume this record:";
    public static final String MESSAGE_RECORD_SEPARATOR = " ";

    private static final long serialVersionUID = 1L;

    public ConsumerException(@Nullable String message, @Nullable Record record) {
        super(createMessage(message, record));
    }

    public ConsumerException(@Nullable Record record, @Nullable Throwable cause) {
        super(createMessage(null, record), cause);
    }

    public ConsumerException(@Nullable String message, @Nullable Record record, @Nullable Throwable cause) {
        super(createMessage(message, record), cause);
    }

    protected static String createMessage(@Nullable String message, @Nullable Record record) {
        return Objects.requireNonNullElse(message, DEFAULT_MESSAGE) + MESSAGE_RECORD_SEPARATOR + record;
    }

}
