package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    public static final String REGEX_TAB = "\\t";
    public static final String REGEX_WHITESPACE = "\\s";
    public static final String REGEX_BACKSLASH = "\\\\";

    public static final String EMPTY = "";

    public static final int MIN_ASCII_CODE_POINT = 0;
    public static final int MAX_ASCII_CODE_POINT = 127;

    private Strings() {
    }

    public static @Nullable String asString(@Nullable Object o) {
        return o == null ? null : o.toString();
    }

    public static Optional<String> asOptionalString(@Nullable Object o) {
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
     * @see String#lines() ()
     */
    public static Stream<String> splitTextByLines(String text) {
        Objects.requireNonNull(text);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return text.lines();
    }

    /**
     * @see String#chars()
     */
    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static Stream<String> splitTextByChars(String text) {
        Objects.requireNonNull(text);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return text.chars().mapToObj((int c) -> Character.toString((char) c));
    }

    /**
     * @see String#codePoints()
     */
    public static Stream<String> splitTextByCodePoints(String text) {
        Objects.requireNonNull(text);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return text.codePoints().mapToObj(Character::toString);
    }

    /**
     * @see String#split(String, int)
     */
    public static Stream<String> splitTextByRegex(String text, String regex, int limit) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(regex);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return Arrays.stream(text.split(regex, limit));
    }

    /**
     * @see String#substring(int, int)
     */
    public static Stream<String> splitTextByLength(String text, int length) {
        Objects.requireNonNull(text);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return StreamSupport.stream(SplitTextByLengthIterator.lengthSpliterator(text, length), false);
    }

    /**
     * @see java.text.BreakIterator#getSentenceInstance(java.util.Locale)
     */
    public static Stream<String> breakTextBySentence(String text, Locale locale) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(locale);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return StreamSupport.stream(BreakTextIterator.sentenceSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getWordInstance(java.util.Locale)
     */
    public static Stream<String> breakTextByWord(String text, Locale locale) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(locale);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return StreamSupport.stream(BreakTextIterator.wordSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getLineInstance(java.util.Locale)
     */
    public static Stream<String> breakTextByLine(String text, Locale locale) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(locale);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return StreamSupport.stream(BreakTextIterator.lineSpliterator(text, locale), false);
    }

    /**
     * @see java.text.BreakIterator#getCharacterInstance(java.util.Locale)
     */
    public static Stream<String> breakTextByCharacter(String text, Locale locale) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(locale);
        if (text.isEmpty()) {
            return Stream.empty();
        }
        return StreamSupport.stream(BreakTextIterator.characterSpliterator(text, locale), false);
    }

    private static final class SplitTextByLengthIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.SIZED | Spliterator.SUBSIZED
                | Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;

        private static Spliterator<String> lengthSpliterator(String text, int length) {
            var iterator = new SplitTextByLengthIterator(text, length);
            return Spliterators.spliterator(iterator, iterator.size(), SPLITERATOR_CHARACTERISTICS);
        }

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

    private static final class BreakTextIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;

        private static Spliterator<String> sentenceSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakTextIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> wordSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getWordInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakTextIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> lineSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getLineInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakTextIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private static Spliterator<String> characterSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getCharacterInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakTextIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        private final String text;
        private final BreakIterator breakIterator;
        private int currentBeginIndex;
        private int currentEndIndex;

        private BreakTextIterator(String text, BreakIterator breakIterator) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(breakIterator);
            this.text = text;
            this.breakIterator = breakIterator;

            // determine the first indexes
            currentBeginIndex = breakIterator.first();
            currentEndIndex = breakIterator.next();
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
