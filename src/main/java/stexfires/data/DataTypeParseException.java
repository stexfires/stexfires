package stexfires.data;

import java.io.Serial;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DataTypeParseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DataTypeParseException(String message) {
        super(message);
    }

}
