package stexfires.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on <code>String</code> objects.
 *
 * @author Mathias Kalb
 * @see java.lang.String
 * @see java.util.stream.Stream
 * @see java.util.List
 * @since 0.1
 */
public final class Strings {

    /**
     * A default delimiter for joining strings: <code>", "</code>
     */
    public static final String DEFAULT_DELIMITER = ", ";

    private static final String EMPTY = "";

    private Strings() {
    }

    public static String asString(Object o) {
        return o == null ? null : o.toString();
    }

    public static String empty() {
        return EMPTY;
    }

    public static List<String> list(String string) {
        return Collections.singletonList(string);
    }

    public static List<String> list(String... strings) {
        return Arrays.stream(strings).collect(Collectors.toList());
    }

    public static Stream<String> stream(String string) {
        return Stream.of(string);
    }

    public static Stream<String> stream(String... strings) {
        return Stream.of(strings);
    }

    public static Stream<String> streamOfNullable(String string) {
        return string == null ? Stream.empty() : Stream.of(string);
    }

    public static Stream<String> concat(Stream<String> firstStream, Stream<String> secondStream) {
        return Stream.concat(firstStream, secondStream);
    }

    @SafeVarargs
    public static Stream<String> concat(Stream<String>... streams) {
        return Stream.of(streams).flatMap(Function.identity());
    }

    public static List<String> collect(Stream<String> stream) {
        return stream.collect(Collectors.toList());
    }

    public static String join(Stream<String> stream) {
        return join(stream, DEFAULT_DELIMITER);
    }

    public static String join(Stream<String> stream, CharSequence delimiter) {
        return stream.collect(Collectors.joining(delimiter));
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLine(Stream<String> stream, CharSequence delimiter) {
        System.out.println(join(stream, delimiter));
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLines(Stream<String> stream) {
        stream.forEachOrdered(System.out::println);
    }

}
