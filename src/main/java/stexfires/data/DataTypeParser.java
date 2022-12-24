package stexfires.data;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeParser<T> {

    T parse(String source) throws DataTypeParseException;

}
