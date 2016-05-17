package org.textfiledatatools.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on strings and string streams.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class Strings {

    public static final String DEFAULT_DELIMITER = ", ";
    public static final String EMPTY = "";

    private Strings() {
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

    public static List<String> collect(Stream<String> stream) {
        return stream.collect(Collectors.toList());
    }

    public static String join(Stream<String> stream) {
        return join(stream, DEFAULT_DELIMITER);
    }

    public static String join(Stream<String> stream, CharSequence delimiter) {
        return stream.collect(Collectors.joining(delimiter));
    }

    public static void printLine(Stream<String> stream, CharSequence delimiter) {
        System.out.println(join(stream, delimiter));
    }

    public static void printLines(Stream<String> stream) {
        stream.forEachOrdered(System.out::println);
    }

}
