package stexfires.util.supplier;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RandomStringSupplier {

    private RandomStringSupplier() {
    }

    public static Supplier<String> uuid() {
        return () -> UUID.randomUUID().toString();
    }

    public static Supplier<String> stringSelection(RandomGenerator random,
                                                   List<String> sourceStrings) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceStrings);
        if (sourceStrings.isEmpty()) {
            throw new IllegalArgumentException("At least one string must be passed.");
        }
        return () -> sourceStrings.get(random.nextInt(0, sourceStrings.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<String> stringSelection(RandomGenerator random,
                                                   String... sourceStrings) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceStrings);
        if (sourceStrings.length == 0) {
            throw new IllegalArgumentException("At least one string must be passed.");
        }
        return () -> sourceStrings[random.nextInt(0, sourceStrings.length)];
    }

    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          int lowestCodePointBoundary, int highestCodePointBoundary,
                                                          IntPredicate codePointFilter) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePointFilter);
        if (lowestCodePointBoundary < Character.MIN_VALUE || lowestCodePointBoundary > highestCodePointBoundary) {
            throw new IllegalArgumentException("Illegal boundaries. lowestCodePointBoundary=" + lowestCodePointBoundary + ", highestCodePointBoundary=" + highestCodePointBoundary);
        }
        return () -> random.ints(lowestCodePointBoundary, highestCodePointBoundary + 1)
                           .filter(codePointFilter)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

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
                           .filter(Objects::nonNull)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<String> codePointConcatenation(RandomGenerator random, IntSupplier length,
                                                          Integer... codePoints) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(codePoints);
        if (codePoints.length == 0) {
            throw new IllegalArgumentException("At least one codePoint must be passed.");
        }
        return () -> random.ints(0, codePoints.length)
                           .mapToObj(i -> codePoints[i])
                           .filter(Objects::nonNull)
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
                           .filter(Objects::nonNull)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                           .toString();
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<String> characterConcatenation(RandomGenerator random, IntSupplier length,
                                                          Character... characters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(characters);
        if (characters.length == 0) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return () -> random.ints(0, characters.length)
                           .mapToObj(i -> characters[i])
                           .filter(Objects::nonNull)
                           .limit(length.getAsInt())
                           .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                           .toString();
    }

    public static Supplier<String> stringCutting(IntSupplier beginIndex, IntSupplier length,
                                                 String completeString) {
        Objects.requireNonNull(beginIndex);
        Objects.requireNonNull(length);
        Objects.requireNonNull(completeString);
        if (completeString.isEmpty()) {
            throw new IllegalArgumentException("The string must not be empty.");
        }
        return () -> {
            int begin = beginIndex.getAsInt();
            if (begin > completeString.length()) {
                begin = completeString.length();
            }
            int end = begin + length.getAsInt();
            if (end > completeString.length()) {
                end = completeString.length();
            }
            return completeString.substring(begin, end);
        };
    }

}
