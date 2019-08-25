package stexfires.util;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Enum with three common line separators for text files: {@code LF, CR, CR_LF}.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("HardcodedLineSeparator")
public enum LineSeparator {

    /**
     * Line feed: {@code '\n'} (0xA)
     */
    LF("\n"),

    /**
     * Carriage return: {@code '\r'} (0xD)
     */
    CR("\r"),

    /**
     * CR and LF: {@code '\r\n'} (0xD 0xA)
     */
    CR_LF("\r\n");

    private final String separator;

    LineSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Returns the line separator as a {@code String}.
     *
     * @return line separator as a {@code String}
     */
    public String string() {
        return separator;
    }

    /**
     * Returns the line separator as a {@code Supplier}.
     *
     * @return line separator as a {@code Supplier}
     * @see java.util.function.Supplier
     */
    public Supplier<String> supplier() {
        return () -> separator;
    }

    /**
     * Returns the line separator as a {@code Stream} with a single element.
     *
     * @return line separator as a {@code Stream} with a single element
     * @see java.util.stream.Stream#of(Object)
     */
    public Stream<String> stream() {
        return Stream.of(separator);
    }

    /**
     * Returns the line separator as an {@code IntStream} with the chars.
     *
     * @return line separator as an {@code IntStream} with the chars
     * @see java.lang.String#chars()
     */
    public IntStream chars() {
        return separator.chars();
    }

    /**
     * Returns the line separator as a {@code String}.
     *
     * @return line separator as a {@code String}
     * @see stexfires.util.LineSeparator#string()
     */
    @Override
    public String toString() {
        return separator;
    }

}
