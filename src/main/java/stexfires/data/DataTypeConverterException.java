package stexfires.data;

import java.io.Serial;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DataTypeConverterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DataTypeConverterException(Type type) {
        super(buildMessage(type, null, null));
    }

    public DataTypeConverterException(Type type, String message) {
        super(buildMessage(type, message, null));
    }

    public DataTypeConverterException(Type type, Throwable cause) {
        super(buildMessage(type, null, cause), cause);
    }

    public DataTypeConverterException(Type type, String message, Throwable cause) {
        super(buildMessage(type, message, cause), cause);
    }

    protected static String buildMessage(Type type, String message, Throwable cause) {
        if (message == null && cause == null) {
            return switch (type) {
                case null -> "The data type cannot be converted.";
                case Converter -> "The data type cannot be converted to another data type.";
                case Formatter -> "The data type cannot be formatted as text.";
                case Parser -> "The text cannot be parsed into the datatype.";
            };
        } else if (message == null) {
            return switch (type) {
                case null -> "The data type cannot be converted because the following error occurred. ";
                case Converter ->
                        "The data type cannot be converted to another data type because the following error occurred. ";
                case Formatter -> "The data type cannot be formatted as text because the following error occurred. ";
                case Parser -> "The text cannot be parsed into the datatype because the following error occurred. ";
            } + cause.getClass();
        } else {
            return switch (type) {
                case null -> "The data type cannot be converted due to the following reason. ";
                case Converter ->
                        "The data type cannot be converted to another data type due to the following reason. ";
                case Formatter -> "The data type cannot be formatted as text due to the following reason. ";
                case Parser -> "The text cannot be parsed into the datatype due to the following reason. ";
            } + message;
        }
    }

    public enum Type {
        Converter, Formatter, Parser
    }

}
