package stexfires.record.producer;

import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * @since 0.1
 */
public class ProducerException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProducerException(@Nullable String message) {
        super(message);
    }

    public ProducerException(@Nullable Throwable cause) {
        super(cause);
    }

    public ProducerException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

}
