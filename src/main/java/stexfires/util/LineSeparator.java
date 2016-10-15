package stexfires.util;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum LineSeparator {

    /**
     * Line feed: '\n'
     */
    LF("\n"),

    /**
     * Carriage return: '\r'
     */
    CR("\r"),

    /**
     * CR and LF: '\r\n'
     */
    CR_LF("\r\n");

    private final String separator;

    LineSeparator(String separator) {
        this.separator = separator;
    }

    public String string() {
        return separator;
    }

    public Supplier<String> supplier() {
        return () -> separator;
    }

    public Stream<String> stream() {
        return Stream.of(separator);
    }

    public IntStream chars() {
        return separator.chars();
    }

    @Override
    public String toString() {
        return separator;
    }

}
