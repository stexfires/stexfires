package stexfires.core.producer;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ProducerException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProducerException() {
        super();
    }

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
