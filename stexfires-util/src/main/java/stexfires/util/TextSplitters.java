package stexfires.util;

import org.jspecify.annotations.Nullable;

import java.text.BreakIterator;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

/**
 * This class consists of {@code static} utility methods
 * for splitting text into a stream of strings.
 *
 * @see java.lang.String
 * @see java.util.regex.Pattern
 * @see java.text.BreakIterator
 * @since 0.1
 */
public final class TextSplitters {

    private TextSplitters() {
    }

    /**
     * Split text by lines.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#lines()
     */
    public static Stream<String> splitByLines(@Nullable String text) {
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                text.lines();
    }

    /**
     * A function that splits text by lines.
     *
     * @see String#lines()
     * @see TextSplitters#splitByLines(String)
     */
    public static Function<@Nullable String, Stream<String>> splitByLinesFunction() {
        return TextSplitters::splitByLines;
    }

    /**
     * Split text by chars.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#chars()
     */
    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static Stream<String> splitByChars(@Nullable String text) {
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                text.chars().mapToObj((int c) -> Character.toString((char) c));
    }

    /**
     * A function that splits text by chars.
     *
     * @see String#chars()
     * @see TextSplitters#splitByChars(String)
     */
    public static Function<@Nullable String, Stream<String>> splitByCharsFunction() {
        return TextSplitters::splitByChars;
    }

    /**
     * Split text by code points.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#codePoints()
     */
    public static Stream<String> splitByCodePoints(@Nullable String text) {
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                text.codePoints().mapToObj(Character::toString);
    }

    /**
     * A function that splits text by code points.
     *
     * @see String#codePoints()
     * @see TextSplitters#splitByCodePoints(String)
     */
    public static Function<@Nullable String, Stream<String>> splitByCodePointsFunction() {
        return TextSplitters::splitByCodePoints;
    }

    /**
     * Split text by a pattern.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     */
    public static Stream<String> splitByPattern(@Nullable String text, Pattern pattern) {
        Objects.requireNonNull(pattern);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                pattern.splitAsStream(text);
    }

    /**
     * A function that splits text by a pattern.
     *
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     * @see TextSplitters#splitByPattern(String, Pattern)
     */
    public static Function<@Nullable String, Stream<String>> splitByPatternFunction(Pattern pattern) {
        Objects.requireNonNull(pattern);
        return s -> TextSplitters.splitByPattern(s, pattern);
    }

    /**
     * Split text by a regex.
     * It compiles the regex to a pattern.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.util.regex.Pattern#compile(String)
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     * @see TextSplitters#splitByPattern(String, Pattern)
     */
    public static Stream<String> splitByRegex(@Nullable String text, String regex) throws PatternSyntaxException {
        Objects.requireNonNull(regex);
        Pattern pattern = Pattern.compile(regex);
        return splitByPattern(text, pattern);
    }

    /**
     * A function that splits text by a regex.
     * It compiles the regex to a pattern.
     *
     * @see java.util.regex.Pattern#compile(String)
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     * @see TextSplitters#splitByPattern(String, Pattern)
     */
    public static Function<@Nullable String, Stream<String>> splitRegexFunction(String regex) throws PatternSyntaxException {
        Objects.requireNonNull(regex);
        Pattern pattern = Pattern.compile(regex);
        return s -> TextSplitters.splitByPattern(s, pattern);
    }

    /**
     * Split text by a separator.
     * It quotes the separator to a regex and compiles it to a pattern.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.util.regex.Pattern#quote(String)
     * @see java.util.regex.Pattern#compile(String)
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     * @see TextSplitters#splitByPattern(String, Pattern)
     */
    public static Stream<String> splitBySeparator(@Nullable String text, String separator) throws PatternSyntaxException {
        Objects.requireNonNull(separator);
        Pattern pattern = Pattern.compile(Pattern.quote(separator));
        return splitByPattern(text, pattern);
    }

    /**
     * A function that splits text by a separator.
     * It quotes the separator to a regex and compiles it to a pattern.
     *
     * @see java.util.regex.Pattern#quote(String)
     * @see java.util.regex.Pattern#compile(String)
     * @see java.util.regex.Pattern#splitAsStream(CharSequence)
     * @see TextSplitters#splitByPattern(String, Pattern)
     */
    public static Function<@Nullable String, Stream<String>> splitBySeparatorFunction(String separator) throws PatternSyntaxException {
        Objects.requireNonNull(separator);
        Pattern pattern = Pattern.compile(Pattern.quote(separator));
        return s -> TextSplitters.splitByPattern(s, pattern);
    }

    /**
     * Split text by a regex.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#split(String, int)
     */
    public static Stream<String> splitByRegex(@Nullable String text, String regex, int limit) {
        Objects.requireNonNull(regex);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                Arrays.stream(text.split(regex, limit));
    }

    /**
     * A function that splits text by a regex.
     *
     * @see String#split(String, int)
     * @see TextSplitters#splitByRegex(String, String, int)
     */
    public static Function<@Nullable String, Stream<String>> splitByRegexFunction(String regex, int limit) {
        Objects.requireNonNull(regex);
        return s -> TextSplitters.splitByRegex(s, regex, limit);
    }

    /**
     * Split text by a regex with delimiters.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#splitWithDelimiters(String, int)
     */
    public static Stream<String> splitByRegexWithDelimiters(@Nullable String text, String regex, int limit) {
        Objects.requireNonNull(regex);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                Arrays.stream(text.splitWithDelimiters(regex, limit));
    }

    /**
     * A function that splits text by a regex with delimiters.
     *
     * @see String#splitWithDelimiters(String, int)
     * @see TextSplitters#splitByRegexWithDelimiters(String, String, int)
     */
    public static Function<@Nullable String, Stream<String>> splitByRegexWithDelimitersFunction(String regex, int limit) {
        Objects.requireNonNull(regex);
        return s -> TextSplitters.splitByRegexWithDelimiters(s, regex, limit);
    }

    /**
     * Split text by length.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see String#substring(int, int)
     */
    public static Stream<String> splitByLength(@Nullable String text, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length <= 0");
        }
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(SplitByLengthIterator.lengthSpliterator(text, length), false);
    }

    /**
     * A function that splits text by length.
     *
     * @see String#substring(int, int)
     * @see TextSplitters#splitByLength(String, int)
     */
    public static Function<@Nullable String, Stream<String>> splitByLengthFunction(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length <= 0");
        }
        return s -> TextSplitters.splitByLength(s, length);
    }

    /**
     * Break text by sentence.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.text.BreakIterator#getSentenceInstance(java.util.Locale)
     */
    public static Stream<String> breakBySentence(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(BreakByBreakIterator.sentenceSpliterator(text, locale), false);
    }

    /**
     * A function that breaks text by sentence.
     *
     * @see java.text.BreakIterator#getSentenceInstance(java.util.Locale)
     * @see TextSplitters#breakBySentence(String, Locale)
     */
    public static Function<@Nullable String, Stream<String>> breakBySentenceFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> TextSplitters.breakBySentence(s, locale);
    }

    /**
     * Break text by word.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.text.BreakIterator#getWordInstance(java.util.Locale)
     */
    public static Stream<String> breakByWord(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(BreakByBreakIterator.wordSpliterator(text, locale), false);
    }

    /**
     * A function that breaks text by word.
     *
     * @see java.text.BreakIterator#getWordInstance(java.util.Locale)
     * @see TextSplitters#breakByWord(String, Locale)
     */
    public static Function<@Nullable String, Stream<String>> breakByWordFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> TextSplitters.breakByWord(s, locale);
    }

    /**
     * Break text by line.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.text.BreakIterator#getLineInstance(java.util.Locale)
     */
    public static Stream<String> breakByLine(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(BreakByBreakIterator.lineSpliterator(text, locale), false);
    }

    /**
     * A function that breaks text by line.
     *
     * @see java.text.BreakIterator#getLineInstance(java.util.Locale)
     * @see TextSplitters#breakByLine(String, Locale)
     */
    public static Function<@Nullable String, Stream<String>> breakByLineFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> TextSplitters.breakByLine(s, locale);
    }

    /**
     * Break text by character.
     * <p>
     * If the text is {@code null} or empty, an empty stream is returned.
     *
     * @see java.text.BreakIterator#getCharacterInstance(java.util.Locale)
     */
    public static Stream<String> breakByCharacter(@Nullable String text, Locale locale) {
        Objects.requireNonNull(locale);
        return ((text == null) || text.isEmpty()) ? Stream.empty() :
                StreamSupport.stream(BreakByBreakIterator.characterSpliterator(text, locale), false);
    }

    /**
     * A function that breaks text by character.
     *
     * @see java.text.BreakIterator#getCharacterInstance(java.util.Locale)
     * @see TextSplitters#breakByCharacter(String, Locale)
     */
    public static Function<@Nullable String, Stream<String>> breakByCharacterFunction(Locale locale) {
        Objects.requireNonNull(locale);
        return s -> TextSplitters.breakByCharacter(s, locale);
    }

    private static final class SplitByLengthIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.SIZED | Spliterator.SUBSIZED
                | Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;
        private final String text;
        private final int length;
        private final int maxIndex;
        private int currentIndex;

        private SplitByLengthIterator(String text, int length) {
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

        @SuppressWarnings("MagicConstant")
        private static Spliterator<String> lengthSpliterator(String text, int length) {
            var iterator = new SplitByLengthIterator(text, length);
            return Spliterators.spliterator(iterator, iterator.size(), SPLITERATOR_CHARACTERISTICS);
        }

        private int size() {
            // Calculate the total number of text blocks and round up the last one.
            return ((maxIndex + length) - 1) / length;
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

    private static final class BreakByBreakIterator implements Iterator<String> {

        private static final int SPLITERATOR_CHARACTERISTICS = Spliterator.NONNULL | Spliterator.ORDERED | Spliterator.IMMUTABLE;
        private final String text;
        private final BreakIterator breakIterator;
        private int currentBeginIndex;
        private int currentEndIndex;

        private BreakByBreakIterator(String text, BreakIterator breakIterator) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(breakIterator);
            this.text = text;
            this.breakIterator = breakIterator;

            // determine the first indexes
            currentBeginIndex = breakIterator.first();
            currentEndIndex = breakIterator.next();
        }

        @SuppressWarnings("MagicConstant")
        private static Spliterator<String> sentenceSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        @SuppressWarnings("MagicConstant")
        private static Spliterator<String> wordSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getWordInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        @SuppressWarnings("MagicConstant")
        private static Spliterator<String> lineSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getLineInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakByBreakIterator(text, breakIterator);
            return Spliterators.spliteratorUnknownSize(iterator, SPLITERATOR_CHARACTERISTICS);
        }

        @SuppressWarnings("MagicConstant")
        private static Spliterator<String> characterSpliterator(String text, Locale locale) {
            Objects.requireNonNull(text);
            Objects.requireNonNull(locale);

            BreakIterator breakIterator = BreakIterator.getCharacterInstance(locale);
            breakIterator.setText(text);

            var iterator = new BreakByBreakIterator(text, breakIterator);
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
