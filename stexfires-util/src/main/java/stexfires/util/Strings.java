package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods and constants
 * for operating on {@code String} objects.
 *
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

    /**
     * A RegEx pattern for the tab character (code point 9 - CHARACTER TABULATION).
     * <p>
     * Pattern: {@code "\t"}
     *
     * @see java.util.regex.Pattern
     */
    public static final String REGEX_TAB = "\\t";
    /**
     * A RegEx pattern for different whitespace characters ({@code " \t\n\x0B\f\r"}).
     * <p>
     * Pattern: {@code "\s"}
     *
     * @see java.util.regex.Pattern
     */
    public static final String REGEX_WHITESPACE = "\\s";
    /**
     * A RegEx pattern for the backslash character (code point 92 - REVERSE SOLIDUS).
     * <p>
     * Pattern: {@code "\\"}
     *
     * @see java.util.regex.Pattern
     */
    public static final String REGEX_BACKSLASH = "\\\\";

    /**
     * An empty string with a length of zero.
     */
    public static final String EMPTY = "";
    /**
     * A string with a single tab character (code point 9 - CHARACTER TABULATION).
     */
    public static final String TAB = "\t";
    /**
     * A string with a single space character (code point 32 - SPACE).
     */
    public static final String SPACE = " ";
    /**
     * A string with a single backslash character (code point 92 - REVERSE SOLIDUS).
     */
    public static final String BACKSLASH = "\\";

    private Strings() {
    }

    public static @Nullable String toNullableString(@Nullable Object o) {
        return o == null ? null : o.toString();
    }

    public static Optional<String> toOptionalString(@Nullable Object o) {
        return o == null ? Optional.empty() : Optional.of(o.toString());
    }

    @SuppressWarnings("SameReturnValue")
    public static String empty() {
        return EMPTY;
    }

    public static List<String> list(@Nullable String stringValue) {
        return Collections.singletonList(stringValue);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static List<String> list(String... stringValues) {
        return Arrays.stream(stringValues).collect(Collectors.toList());
    }

    public static List<String> listOfNullable(@Nullable String stringValue) {
        return stringValue == null ? Collections.emptyList() : Collections.singletonList(stringValue);
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

    public static Stream<CodePoint> codePointStream(@NotNull String stringValue) {
        Objects.requireNonNull(stringValue);
        return stringValue.codePoints().mapToObj(CodePoint::new);
    }

    public static Stream<CodePoint> codePointStreamOfNullable(@Nullable String stringValue) {
        return stringValue == null ? Stream.empty() : stringValue.codePoints().mapToObj(CodePoint::new);
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

    public static List<String> toList(Stream<String> stream) {
        Objects.requireNonNull(stream);
        return stream.toList();
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

    /**
     * @see java.util.stream.Stream#collect(java.util.stream.Collector)
     * @see java.util.stream.Collectors#collectingAndThen(java.util.stream.Collector, java.util.function.Function)
     * @see java.lang.String#join(CharSequence, Iterable)
     */
    public static Collector<? super String, ?, String> modifyAndJoinCollector(UnaryOperator<List<String>> modifyListOperator,
                                                                              String joinDelimiter) {
        Objects.requireNonNull(modifyListOperator);
        Objects.requireNonNull(joinDelimiter);
        // Use ArrayList because of its mutability.
        return Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new),
                list -> String.join(joinDelimiter, modifyListOperator.apply(list)));
    }

    /**
     * @see java.util.List#removeAll(java.util.Collection)
     */
    public static UnaryOperator<List<String>> modifyListRemoveAll(Collection<String> stringCollection) {
        Objects.requireNonNull(stringCollection);
        return list -> {
            if (list != null && !list.isEmpty()) {
                list.removeAll(stringCollection);
            }
            return list;
        };
    }

    /**
     * @see java.util.List#removeIf(java.util.function.Predicate)
     */
    public static UnaryOperator<List<String>> modifyListRemoveIf(Predicate<? super String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        return list -> {
            if (list != null && !list.isEmpty()) {
                list.removeIf(stringPredicate);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#replaceAll(java.util.List, Object, Object)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static UnaryOperator<List<String>> modifyListReplaceAll(@Nullable String oldVal, @Nullable String newVal) {
        return list -> {
            if (list != null && !list.isEmpty()) {
                Collections.replaceAll(list, oldVal, newVal);
            }
            return list;
        };
    }

    /**
     * @see java.util.List#replaceAll(java.util.function.UnaryOperator)
     */
    public static UnaryOperator<List<String>> modifyListReplaceAll(UnaryOperator<String> stringOperator) {
        Objects.requireNonNull(stringOperator);
        return list -> {
            if (list != null && !list.isEmpty()) {
                list.replaceAll(stringOperator);
            }
            return list;
        };
    }

    /**
     * @see java.util.List#retainAll(java.util.Collection)
     */
    public static UnaryOperator<List<String>> modifyListRetainAll(Collection<String> stringCollection) {
        Objects.requireNonNull(stringCollection);
        return list -> {
            if (list != null && !list.isEmpty()) {
                list.retainAll(stringCollection);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#reverse(java.util.List)
     */
    public static UnaryOperator<List<String>> modifyListReverse() {
        return list -> {
            if (list != null && !list.isEmpty()) {
                Collections.reverse(list);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#rotate(java.util.List, int)
     */
    public static UnaryOperator<List<String>> modifyListRotate(int distance) {
        return list -> {
            if (list != null && !list.isEmpty()) {
                Collections.rotate(list, distance);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#shuffle(java.util.List)
     */
    public static UnaryOperator<List<String>> modifyListShuffle() {
        return list -> {
            if (list != null && !list.isEmpty()) {
                Collections.shuffle(list);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#sort(java.util.List, java.util.Comparator)
     */
    public static UnaryOperator<List<String>> modifyListSort(Comparator<? super String> stringComparator) {
        Objects.requireNonNull(stringComparator);
        return list -> {
            if (list != null && !list.isEmpty()) {
                list.sort(stringComparator);
            }
            return list;
        };
    }

    /**
     * @see java.util.Collections#swap(java.util.List, int, int)
     */
    public static UnaryOperator<List<String>> modifyListSwap(int firstIndex, int secondIndex) {
        return list -> {
            if (list != null && !list.isEmpty()) {
                if (firstIndex != secondIndex
                        && firstIndex >= 0 && secondIndex >= 0
                        && firstIndex < list.size() && secondIndex < list.size()) {
                    Collections.swap(list, firstIndex, secondIndex);
                }
            }
            return list;
        };
    }

}
