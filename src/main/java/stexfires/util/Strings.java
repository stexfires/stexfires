package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on {@code String} objects.
 *
 * @author Mathias Kalb
 * @see java.lang.String
 * @see java.util.stream.Stream
 * @see java.util.List
 * @since 0.1
 */
public final class Strings {

    /**
     * A default delimiter for joining strings: {@code ", "}
     */
    public static final String DEFAULT_DELIMITER = ", ";

    private static final String EMPTY = "";

    private Strings() {
    }

    public static @Nullable String asString(@Nullable Object o) {
        return o == null ? null : o.toString();
    }

    public static Optional<String> asOptionalString(@Nullable Object o) {
        return o == null ? Optional.empty() : Optional.of(o.toString());
    }

    public static String empty() {
        return EMPTY;
    }

    public static List<String> list(@Nullable String stringValue) {
        return Collections.singletonList(stringValue);
    }

    public static List<String> listOfNullable(@Nullable String stringValue) {
        return stringValue == null ? Collections.emptyList() : Collections.singletonList(stringValue);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static List<String> list(String... stringValues) {
        return Arrays.stream(stringValues).collect(Collectors.toList());
    }

    public static Stream<String> stream(@Nullable String stringValue) {
        return Stream.of(stringValue);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Stream<String> stream(String... stringValues) {
        return Stream.of(stringValues);
    }

    public static Stream<String> streamOfNullable(@Nullable String stringValue) {
        return Stream.ofNullable(stringValue);
    }

    public static Stream<String> concat(Stream<String> firstStream, Stream<String> secondStream) {
        Objects.requireNonNull(firstStream);
        Objects.requireNonNull(secondStream);
        return Stream.concat(firstStream, secondStream);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static Stream<String> concat(Stream<String>... streams) {
        Objects.requireNonNull(streams);
        return Stream.of(streams).flatMap(Function.identity());
    }

    public static List<String> collect(Stream<String> stream) {
        Objects.requireNonNull(stream);
        return stream.collect(Collectors.toList());
    }

    public static String join(Stream<String> stream) {
        Objects.requireNonNull(stream);
        return join(stream, DEFAULT_DELIMITER);
    }

    public static String join(Stream<String> stream, CharSequence delimiter) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(delimiter);
        return stream.collect(Collectors.joining(delimiter));
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLine(Stream<String> stream, CharSequence delimiter) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(delimiter);
        System.out.println(join(stream, delimiter));
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void printLines(Stream<String> stream) {
        Objects.requireNonNull(stream);
        stream.forEachOrdered(System.out::println);
    }

}
