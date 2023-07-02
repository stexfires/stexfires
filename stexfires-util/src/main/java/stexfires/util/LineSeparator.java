package stexfires.util;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Enum with three common line separators for text files: {@code LF, CR, CR_LF}.
 *
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

    /**
     * Constructs a {@code LineSeparator} with the specified line separator string.
     *
     * @param separator line separator as a {@code String} with one or two characters
     * @see LineSeparator#string()
     */
    LineSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Returns the system line separator as a {@code LineSeparator}.
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

    /**
     * Returns the {@code LineSeparator} as a {@code String} with one or two characters.
     *
     * @return {@code LineSeparator} as a {@code String} with one or two characters
     */
    public final String string() {
        return separator;
    }

    /**
     * Returns the {@code LineSeparator} as a {@code String} repeated {@code count} times.
     *
     * @param count number of times to repeat
     * @return {@code LineSeparator} as a {@code String} repeated {@code count} times
     * @throws IllegalArgumentException if the {@code count} is negative.
     * @see String#repeat(int)
     * @see LineSeparator#string()
     */
    public final String repeat(int count) {
        return separator.repeat(count);
    }

    /**
     * Returns the {@code String} length.
     *
     * @return {@code String} length
     * @see String#length()
     * @see LineSeparator#string()
     */
    public final int length() {
        return separator.length();
    }

    /**
     * Returns the {@code LineSeparator} as an infinite {@code Supplier<String>}.
     *
     * @return {@code LineSeparator} as an infinite {@code Supplier<String>}
     * @see java.util.function.Supplier
     * @see LineSeparator#string()
     */
    public final Supplier<String> supplier() {
        return () -> separator;
    }

    /**
     * Returns the {@code LineSeparator} as a sequential {@code Stream<String>} with a single element.
     *
     * @return {@code LineSeparator} as a sequential {@code Stream<String>} with a single element
     * @see java.util.stream.Stream#of(Object)
     * @see LineSeparator#string()
     */
    public final Stream<String> stream() {
        return Stream.of(separator);
    }

    /**
     * Returns the {@code LineSeparator} as an {@code IntStream} with the char values of the {@code String}.
     *
     * @return {@code LineSeparator} as an {@code IntStream} with the char values of the {@code String}
     * @see String#chars()
     * @see LineSeparator#string()
     */
    public final IntStream chars() {
        return separator.chars();
    }

    /**
     * Returns the {@code LineSeparator} as an {@code IntStream} with the code points of the {@code String}.
     *
     * @return {@code LineSeparator} as an {@code IntStream} with the code points of the {@code String}
     * @see String#codePoints()
     * @see LineSeparator#string()
     */
    public final IntStream codePoints() {
        return separator.codePoints();
    }

    /**
     * Returns the {@code LineSeparator} as a {@code String} with one or two characters.
     *
     * @return {@code LineSeparator} as a {@code String} with one or two characters
     * @see LineSeparator#string()
     */
    @Override
    public final String toString() {
        return separator;
    }

}