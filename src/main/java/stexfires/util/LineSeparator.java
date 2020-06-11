package stexfires.util;

import java.util.Arrays;
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
    public final String string() {
        return separator;
    }

    /**
     * Returns the line separator as a {@code String} repeated {@code count} times.
     *
     * @param count number of times to repeat
     * @return line separator as a {@code String} repeated {@code count} times
     * @throws IllegalArgumentException if the {@code count} is negative.
     */
    public final String string(int count) {
        return separator.repeat(count);
    }

    /**
     * Returns the line separator as a {@code Supplier}.
     *
     * @return line separator as a {@code Supplier}
     * @see java.util.function.Supplier
     */
    public final Supplier<String> supplier() {
        return () -> separator;
    }

    /**
     * Returns the line separator as a {@code Stream} with a single element.
     *
     * @return line separator as a {@code Stream} with a single element
     * @see java.util.stream.Stream#of(Object)
     */
    public final Stream<String> stream() {
        return Stream.of(separator);
    }

    /**
     * Returns the line separator as an {@code IntStream} with the chars.
     *
     * @return line separator as an {@code IntStream} with the chars
     * @see java.lang.String#chars()
     */
    public final IntStream chars() {
        return separator.chars();
    }

    /**
     * Returns the line separator as a {@code String}.
     *
     * @return line separator as a {@code String}
     * @see stexfires.util.LineSeparator#string()
     */
    @Override
    public final String toString() {
        return separator;
    }

    /**
     * Returns the system line separator.
     * Default return value is {@code LF}.
     *
     * @return system line separator
     * @see System#lineSeparator()
     */
    public static LineSeparator systemLineSeparator() {
        return Arrays.stream(LineSeparator.values())
                     .filter(lineSeparator -> lineSeparator.string().equals(System.lineSeparator()))
                     .findFirst()
                     .orElse(LF);
    }

}