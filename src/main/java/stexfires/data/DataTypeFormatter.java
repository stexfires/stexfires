package stexfires.data;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeFormatter<T> {

    String format(T source) throws DataTypeFormatException;

}
