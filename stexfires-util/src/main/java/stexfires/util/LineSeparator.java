package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Enum with three common line separators for text files: {@code LF, CR, CR_LF}.
 * <p>
 * The methods {@code string()} and {@code toString()} return the line separator as a {@code String} with one or two characters.
 * All methods of the {@code CharSequence} interface are based on this {@code String} representation.
 *
 * @since 0.1
 */
@SuppressWarnings("HardcodedLineSeparator")
public enum LineSeparator implements CharSequence {

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

    private final @NotNull String separator;

    /**
     * Constructs a {@code LineSeparator} with the specified line separator string.
     *
     * @param separator line separator as a {@code String} with one or two characters
     * @see LineSeparator#string()
     */
    LineSeparator(@NotNull String separator) {
        this.separator = separator;
    }

    /**
     * Returns the system line separator as a {@code LineSeparator}.
     * Default return value is {@code LF}.
     *
     * @return system line separator
     * @see System#lineSeparator()
     */
    @SuppressWarnings("DuplicateBranchesInSwitch")
    public static @NotNull LineSeparator systemLineSeparator() {
        return switch (System.lineSeparator()) {
            case "\n" -> LF;
            case "\r" -> CR;
            case "\r\n" -> CR_LF;
            default -> LF;
        };
    }

    /**
     * Returns the {@code LineSeparator} matching the passed {@code String}.
     *
     * @param lookupString Optional {@code String} for lookup. Can be {@code null}.
     * @return {@code LineSeparator} as {@code Optional} matching the passed {@code String}
     */
    public static @NotNull Optional<LineSeparator> lookup(@Nullable String lookupString) {
        return switch (lookupString) {
            case "\n" -> Optional.of(LF);
            case "\r" -> Optional.of(CR);
            case "\r\n" -> Optional.of(CR_LF);
            case null, default -> Optional.empty();
        };
    }

    /**
     * Returns the {@code LineSeparator} as a {@code String} with one or two characters.
     *
     * @return {@code LineSeparator} as a {@code String} with one or two characters
     */
    public final @NotNull String string() {
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
    public final @NotNull String repeat(int count) {
        return separator.repeat(count);
    }

    /**
     * Returns the {@code String} length.
     *
     * @return {@code String} length, always 1 or 2
     * @see String#length()
     * @see LineSeparator#string()
     */
    @Override
    public final int length() {
        return switch (this) {
            case LF, CR -> 1;
            case CR_LF -> 2;
        };
    }

    /**
     * Returns the {@code char} value at the specified index.
     *
     * @param index the index of the {@code char} value to be returned
     * @return the specified {@code char} value
     * @throws IndexOutOfBoundsException if the {@code index} argument is negative or not less than
     *                                   {@code length()}
     * @see String#charAt(int)
     * @see LineSeparator#string()
     */
    @Override
    public final char charAt(int index) {
        return separator.charAt(index);
    }

    /**
     * Returns always {@code false}.
     *
     * @return always {@code false}
     * @see String#isEmpty()
     * @see LineSeparator#string()
     */
    @Override
    public final boolean isEmpty() {
        return false;
    }

    /**
     * Returns a {@code CharSequence} that is a subsequence of this sequence.
     * The subsequence starts with the {@code char} value at the specified index and
     * ends with the {@code char} value at index {@code end - 1}.  The length
     * (in {@code char}s) of the
     * returned sequence is {@code end - start}, so if {@code start == end}
     * then an empty sequence is returned.
     *
     * @param start the start index, inclusive
     * @param end   the end index, exclusive
     * @return the specified subsequence
     * @throws IndexOutOfBoundsException if {@code start} or {@code end} are negative,
     *                                   if {@code end} is greater than {@code length()},
     *                                   or if {@code start} is greater than {@code end}
     * @see String#subSequence(int, int)
     * @see LineSeparator#string()
     */
    @Override
    public final @NotNull CharSequence subSequence(int start, int end) {
        return separator.subSequence(start, end);
    }

    /**
     * Returns the {@code LineSeparator} as an infinite {@code Supplier<String>}.
     *
     * @return {@code LineSeparator} as an infinite {@code Supplier<String>}
     * @see java.util.function.Supplier
     * @see LineSeparator#string()
     */
    public final @NotNull Supplier<String> supplier() {
        return () -> separator;
    }

    /**
     * Returns the {@code LineSeparator} as a sequential {@code Stream<String>} with a single element.
     *
     * @return {@code LineSeparator} as a sequential {@code Stream<String>} with a single element
     * @see java.util.stream.Stream#of(Object)
     * @see LineSeparator#string()
     */
    public final @NotNull Stream<String> stream() {
        return Stream.of(separator);
    }

    /**
     * Returns the {@code LineSeparator} as an {@code IntStream} with the char values of the {@code String}.
     *
     * @return {@code LineSeparator} as an {@code IntStream} with the char values of the {@code String}
     * @see String#chars()
     * @see LineSeparator#string()
     */
    @SuppressWarnings("MagicNumber")
    @Override
    public final @NotNull IntStream chars() {
        return switch (this) {
            case LF -> IntStream.of(10);
            case CR -> IntStream.of(13);
            case CR_LF -> IntStream.of(13, 10);
        };
    }

    /**
     * Returns the {@code LineSeparator} as an {@code IntStream} with the code points of the {@code String}.
     *
     * @return {@code LineSeparator} as an {@code IntStream} with the code points of the {@code String}
     * @see String#codePoints()
     * @see LineSeparator#string()
     */
    @SuppressWarnings("MagicNumber")
    @Override
    public final @NotNull IntStream codePoints() {
        return switch (this) {
            case LF -> IntStream.of(10);
            case CR -> IntStream.of(13);
            case CR_LF -> IntStream.of(13, 10);
        };
    }

    /**
     * Returns the {@code LineSeparator} as a {@code byte[]}.
     *
     * @param charset The {@code Charset} to be used to encode the String
     * @return {@code LineSeparator} as a {@code byte[]}
     * @see String#getBytes(Charset)
     * @see LineSeparator#string()
     */
    public final byte[] bytes(@NotNull Charset charset) {
        Objects.requireNonNull(charset);
        return separator.getBytes(charset);
    }

    /**
     * Returns the {@code LineSeparator} as a regular expression.
     * <p>
     * {@code LF} is {@code \n}.
     * <br>
     * {@code CR} is {@code \r}.
     * <br>
     * {@code CR_LF} is {@code \r\n}.
     *
     * @return {@code LineSeparator} as a regular expression
     * @see java.util.regex.Pattern
     */
    public final @NotNull String regex() {
        return switch (this) {
            case LF -> "\\n";
            case CR -> "\\r";
            case CR_LF -> "\\r\\n";
        };
    }

    /**
     * Returns the {@code LineSeparator} as an escaped Java string.
     * <p>
     * {@code LF} is {@code \n}.
     * <br>
     * {@code CR} is {@code \r}.
     * <br>
     * {@code CR_LF} is {@code \r\n}.
     *
     * @return {@code LineSeparator} as an escaped Java string
     */
    public final @NotNull String escapedJavaString() {
        return switch (this) {
            case LF -> "\\n";
            case CR -> "\\r";
            case CR_LF -> "\\r\\n";
        };
    }

    /**
     * Returns the {@code LineSeparator} as a {@code String} with one or two characters.
     *
     * @return {@code LineSeparator} as a {@code String} with one or two characters
     * @see LineSeparator#string()
     */
    @Override
    public final @NotNull String toString() {
        return separator;
    }

}