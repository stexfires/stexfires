package stexfires.record.consumer;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

import java.io.Serial;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConsumerException extends Exception {

    public static final String DEFAULT_MESSAGE = "Could not consume this record:";
    public static final String MESSAGE_RECORD_SEPARATOR = " ";

    @Serial
    private static final long serialVersionUID = 1L;

    public ConsumerException(@Nullable String message, @Nullable TextRecord record) {
        super(createMessage(message, record));
    }

    public ConsumerException(@Nullable TextRecord record, @Nullable Throwable cause) {
        super(createMessage(null, record), cause);
    }

    public ConsumerException(@Nullable String message, @Nullable TextRecord record, @Nullable Throwable cause) {
        super(createMessage(message, record), cause);
    }

    protected static String createMessage(@Nullable String message, @Nullable TextRecord record) {
        return Objects.requireNonNullElse(message, DEFAULT_MESSAGE) + MESSAGE_RECORD_SEPARATOR + record;
    }

}
