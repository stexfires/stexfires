package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    /**
     * @see String#lines()
     */
    public static Stream<String> splitTextByLines(@Nullable String text) {
        return (text == null || text.isEmpty()) ? Stream.empty() :
                text.lines();
    }

    /**
     * @see String#lines()
     */
    public static Function<String, Stream<String>> splitTextByLinesFunction() {
        return Strings::splitTextByLines;
    }

    /**
     * @see String#chars()
     */
    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static Stream<String> splitTextByChars(@Nullable String text) {
        return (text == null || text.isEmpty()) ? Stream.empty() :
                text.chars().mapToObj((int c) -> Character.toString((char) c));
    }

    /**
     * @see String#chars()
     */
    public static Function<String, Stream<String>> splitTextByCharsFunction() {
        return Strings::splitTextByChars;
    }

    /**
     * @see String#codePoints()
     */
    public static Stream<String> splitTextByCodePoints(@Nullable String text) {
        return (text == null || text.isEmpty()) ? Stream.empty() :
                text.codePoints().mapToObj(Character::toString);
    }

    /**
     * @see String#codePoints()
     */
    public static Function<String, Stream<String>> splitTextByCodePointsFunction() {
        return Strings::splitTextByCodePoints;
    }

    /**
     * @see String#split(String, int)
     */
    public static Stream<String> splitTextByRegex(@Nullable String text, String regex, int limit) {
        Objects.requireNonNull(regex);
        return (text == null || text.isEmpty()) ? Stream.empty() :
                Arrays.stream(text.split(regex, limit));
    }

    /**
     * @see String#split(String, int)
     */
    public static Function<String, Stream<String>> splitTextByRegexFunction(String regex, int limit) {
        Objects.requireNonNull(regex);
        return s -> Strings.splitTextByRegex(s, regex, limit);
    }

    /**
     * @see String#substring(int, int)
     */
    public static Stream<String> splitTextByLength(@Nullable String text, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length <= 0");
        }
        return (text == null || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitTextByLengthIterator.lengthSpliterator(text, length), false);
    }

    /**
     * @see String#substring(int, int)
     */
    public static Function<String, Stream<String>> splitTextByLengthFunction(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length <= 0");
        }
        return s -> Strings.splitTextByLength(s, length);
    }

    /**
     * @see java.text.BreakIterator#getSentenceInstance(java.util.Locale)
     */
    public static Stream<String> splitTextBySentenceBreaks(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return (text == null || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitTextByBreakIterator.sentenceSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getSentenceInstance(java.util.Locale)
     */
    public static Function<String, Stream<String>> splitTextBySentenceBreaksFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> Strings.splitTextBySentenceBreaks(s, locale);
    }

    /**
     * @see java.text.BreakIterator#getWordInstance(java.util.Locale)
     */
    public static Stream<String> splitTextByWordBreaks(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return (text == null || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitTextByBreakIterator.wordSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getWordInstance(java.util.Locale)
     */
    public static Function<String, Stream<String>> splitTextByWordBreaksFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> Strings.splitTextByWordBreaks(s, locale);
    }

    /**
     * @see java.text.BreakIterator#getLineInstance(java.util.Locale)
     */
    public static Stream<String> splitTextByLineBreaks(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return (text == null || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitTextByBreakIterator.lineSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getLineInstance(java.util.Locale)
     */
    public static Function<String, Stream<String>> splitTextByLineBreaksFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> Strings.splitTextByLineBreaks(s, locale);
    }

    /**
     * @see java.text.BreakIterator#getCharacterInstance(java.util.Locale)
     */
    public static Stream<String> splitTextByCharacterBreaks(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return (text == null || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitTextByBreakIterator.characterSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getCharacterInstance(java.util.Locale)
     */
    public static Function<String, Stream<String>> splitTextByCharacterBreaksFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> Strings.splitTextByCharacterBreaks(s, locale);
    }

    private static final class SplitTextByLengthIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.SIZED | Spliterator.SUBSIZED
                | Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;
        private final String text;
        private final int length;
        private final int maxIndex;
        private int currentIndex;

        private SplitTextByLengthIterator(String text, int length) {
            Objects.requireNonNull(text);
            if (length <= 0) {
                throw new IllegalArgumentException("length <= 0");
            }
            this.text = text;
            this.length = length;

            // init indexes
            currentIndex = 0;
            maxIndex = text.length();
        }

        private static Spliterator<String> lengthSpliterator(String text, int length) {
            var iterator = new SplitTextByLengthIterator(text, length);
            return Spliterators.spliterator(iterator, iterator.size(), SPLITERATOR_CHARACTERISTICS);
        }

        private int size() {
            // Calculate the total number of text blocks and round up the last one.
            return (maxIndex + length - 1) / length;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return currentIndex < maxIndex;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws java.util.NoSuchElementException if the iteration has no more elements
         */
        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("currentIndex=" + currentIndex + ", maxIndex=" + maxIndex);
            }

            // determine the current indexes
            int beginIndex = currentIndex;
            int endIndex = Math.min(beginIndex + length, maxIndex);

            // move the index to the next position
            currentIndex += length;

            return text.substring(beginIndex, endIndex);
        }

    }

    private static final class SplitTextByBreakIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;
        private final String text;
        private final BreakIterator breakIterator;
        private int currentBeginIndex;
        private int currentEndIndex;

        private SplitTextByBreakIterator(String text, BreakIterator breakIterator) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(breakIterator);
            this.text = text;
            this.breakIterator = breakIterator;

            // determine the first indexes
            currentBeginIndex = breakIterator.first();
            currentEndIndex = breakIterator.next();
        }

        private static Spliterator<String> sentenceSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
            breakIterator.setText(text);

            var iterator = new SplitTextByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> wordSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getWordInstance(locale);
            breakIterator.setText(text);

            var iterator = new SplitTextByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> lineSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getLineInstance(locale);
            breakIterator.setText(text);

            var iterator = new SplitTextByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> characterSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getCharacterInstance(locale);
            breakIterator.setText(text);

            var iterator = new SplitTextByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return currentEndIndex != BreakIterator.DONE;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("currentBeginIndex=" + currentBeginIndex + ", currentEndIndex=" + currentEndIndex);
            }

            // save the current indexes
            int beginIndex = currentBeginIndex;
            int endIndex = currentEndIndex;

            // determine the next indexes
            currentBeginIndex = currentEndIndex;
            currentEndIndex = breakIterator.next();

            return text.substring(beginIndex, endIndex);
        }

    }

}
