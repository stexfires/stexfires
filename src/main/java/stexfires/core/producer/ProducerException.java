package stexfires.core.producer;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ProducerException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProducerException() {
        super();
    }

    public ProducerException(String message) {
        super(message);
    }

    public ProducerException(Throwable cause) {
        super(cause);
    }

    public ProducerException(String message, Throwable cause) {
        super(message, cause);
    }

}
