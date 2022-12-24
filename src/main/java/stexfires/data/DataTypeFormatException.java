package stexfires.data;

import java.io.Serial;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DataTypeFormatException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DataTypeFormatException(String message) {
        super(message);
    }

}
