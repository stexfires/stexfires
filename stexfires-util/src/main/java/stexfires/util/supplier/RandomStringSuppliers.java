package stexfires.util.supplier;

import java.util.*;
import java.util.function.*;
import java.util.random.*;

/**
 * This class consists of {@code static} utility methods
 * for creating random {@link Supplier}s for {@link java.lang.String} objects.
 *
 * @see java.util.function.Supplier
 * @see java.lang.String
 * @see java.util.random.RandomGenerator
 * @since 0.1
 */
public final class RandomStringSuppliers {

    private RandomStringSuppliers() {
    }

    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          int lowestCodePointBoundary, int highestCodePointBoundary,
                                                          IntPredicate codePointFilter) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePointFilter);
        if ((lowestCodePointBoundary < Character.MIN_VALUE) || (lowestCodePointBoundary > highestCodePointBoundary)) {
            throw new IllegalArgumentException("Illegal boundaries. lowestCodePointBoundary=" + lowestCodePointBoundary + ", highestCodePointBoundary=" + highestCodePointBoundary);
        }
        return () -> random.ints(lowestCodePointBoundary, highestCodePointBoundary + 1)
                           .filter(codePointFilter)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    @SuppressWarnings("ConstantValue")
    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          List<Integer> codePoints) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePoints);
        if (codePoints.isEmpty()) {
            throw new IllegalArgumentException("At least one codePoint must be passed.");
        }
        return () -> random.ints(0, codePoints.size())
                           .mapToObj(codePoints::get)
                           .filter(Objects::nonNull) // for safety
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          int... codePoints) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePoints);
        if (codePoints.length == 0) {
            throw new IllegalArgumentException("At least one codePoint must be passed.");
        }
        return () -> random.ints(0, codePoints.length)
                           .map(i -> codePoints[i])
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          String codePoints) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePoints);
        if (codePoints.isEmpty()) {
            throw new IllegalArgumentException("At least one codePoint must be passed.");
        }
        return () -> random.ints(0, codePoints.length())
                           .map(codePoints::codePointAt)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    @SuppressWarnings("ConstantValue")
    public static Supplier<String> characterConcatenation(RandomGenerator random, IntSupplier length,
                                                          List<Character> characters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(characters);
        if (characters.isEmpty()) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return () -> random.ints(0, characters.size())
                           .mapToObj(characters::get)
                           .filter(Objects::nonNull) // for safety
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                           .toString();
    }

    public static Supplier<String> characterConcatenation(RandomGenerator random, IntSupplier length,
                                                          char... characters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(characters);
        if (characters.length == 0) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return () -> random.ints(0, characters.length)
                           .mapToObj(i -> characters[i])
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                           .toString();
    }

    @SuppressWarnings("ConstantValue")
    public static Supplier<String> stringConcatenation(RandomGenerator random, IntSupplier number,
                                                       List<String> strings) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(number);
        Objects.requireNonNull(strings);
        if (strings.isEmpty()) {
            throw new IllegalArgumentException("At least one string must be passed.");
        }
        return () -> random.ints(0, strings.size())
                           .mapToObj(strings::get)
                           .filter(Objects::nonNull) // for safety
                           .limit(number.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                           .toString();
    }

}
