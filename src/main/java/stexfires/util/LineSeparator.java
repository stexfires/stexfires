package stexfires.util;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Enum with three common line separators for text files: <code>LF, CR, CR_LF</code>.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public enum LineSeparator {

    /**
     * Line feed: <code>'\n'</code> (0xA)
     */
    LF("\n"),

    /**
     * Carriage return: <code>'\r'</code> (0xD)
     */
    CR("\r"),

    /**
     * CR and LF: <code>'\r\n'</code> (0xD 0xA)
     */
    CR_LF("\r\n");

    private final String separator;

    LineSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Returns the line separator as a <code>String</code>.
     *
     * @return line separator as a <code>String</code>
     */
    public String string() {
        return separator;
    }

    /**
     * Returns the line separator as a <code>Supplier</code>.
     *
     * @return line separator as a <code>Supplier</code>
     */
    public Supplier<String> supplier() {
        return () -> separator;
    }

    /**
     * Returns the line separator as a <code>Stream</code> with a single element.
     *
     * @return line separator as a <code>Stream</code> with a single element
     */
    public Stream<String> stream() {
        return Stream.of(separator);
    }

    /**
     * Returns the line separator as an <code>IntStream</code> with a the chars.
     *
     * @return line separator as an <code>IntStream</code> with a the chars
     */
    public IntStream chars() {
        return separator.chars();
    }

    /**
     * Returns the line separator as a <code>String</code>.
     *
     * @return line separator as a <code>String</code>
     * @see stexfires.util.LineSeparator#string()
     */
    @Override
    public String toString() {
        return separator;
    }

}
