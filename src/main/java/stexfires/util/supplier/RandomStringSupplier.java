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
public final class RandomStringSupplier implements Supplier<String> {

    private final Supplier<String> stringSupplier;

    private RandomStringSupplier(Supplier<String> stringSupplier) {
        this.stringSupplier = stringSupplier;
    }

    public static RandomStringSupplier uuid() {
        return new RandomStringSupplier(() -> UUID.randomUUID().toString());
    }

    public static RandomStringSupplier stringSelection(RandomGenerator random,
                                                       List<String> sourceStrings) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceStrings);
        if (sourceStrings.isEmpty()) {
            throw new IllegalArgumentException("At least one string must be passed.");
        }
        return new RandomStringSupplier(() -> sourceStrings.get(random.nextInt(0, sourceStrings.size())));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static RandomStringSupplier stringSelection(RandomGenerator random,
                                                       String... sourceStrings) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceStrings);
        if (sourceStrings.length == 0) {
            throw new IllegalArgumentException("At least one string must be passed.");
        }
        return new RandomStringSupplier(() -> sourceStrings[random.nextInt(0, sourceStrings.length)]);
    }

    public static RandomStringSupplier characterConcatenation(RandomGenerator random, IntSupplier length,
                                                              int lowestCharacterBoundary, int highestCharacterBoundary,
                                                              IntPredicate characterFilter) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(characterFilter);
        if (lowestCharacterBoundary < Character.MIN_VALUE || lowestCharacterBoundary > highestCharacterBoundary || highestCharacterBoundary > Character.MAX_VALUE) {
            throw new IllegalArgumentException("Illegal character boundaries. lowestCharacterBoundary=" + lowestCharacterBoundary + ", highestCharacterBoundary=" + highestCharacterBoundary);
        }
        return new RandomStringSupplier(() -> random.ints(lowestCharacterBoundary, highestCharacterBoundary + 1)
                                                    .filter(characterFilter)
                                                    .limit(length.getAsInt())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    public static RandomStringSupplier characterConcatenation(RandomGenerator random, IntSupplier length,
                                                              String sourceCharacters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(sourceCharacters);
        if (sourceCharacters.isEmpty()) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return new RandomStringSupplier(() -> random.ints(0, sourceCharacters.length())
                                                    .map(sourceCharacters::charAt)
                                                    .limit(length.getAsInt())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    public static RandomStringSupplier characterConcatenation(RandomGenerator random, IntSupplier length,
                                                              List<Character> sourceCharacters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(sourceCharacters);
        if (sourceCharacters.isEmpty()) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }

        return new RandomStringSupplier(() -> random.ints(0, sourceCharacters.size())
                                                    .map(i -> (int) sourceCharacters.get(i))
                                                    .limit(length.getAsInt())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static RandomStringSupplier characterConcatenation(RandomGenerator random, IntSupplier length,
                                                              Character... sourceCharacters) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(length);
        Objects.requireNonNull(sourceCharacters);
        if (sourceCharacters.length == 0) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return new RandomStringSupplier(() -> random.ints(0, sourceCharacters.length)
                                                    .map(i -> (int) sourceCharacters[i])
                                                    .limit(length.getAsInt())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    public static RandomStringSupplier stringCutting(IntSupplier beginIndex, IntSupplier length,
                                                     String sourceString) {
        Objects.requireNonNull(beginIndex);
        Objects.requireNonNull(length);
        Objects.requireNonNull(sourceString);
        if (sourceString.isEmpty()) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return new RandomStringSupplier(() -> {
            int begin = beginIndex.getAsInt();
            if (begin > sourceString.length()) {
                begin = sourceString.length();
            }
            int end = begin + length.getAsInt();
            if (end > sourceString.length()) {
                end = sourceString.length();
            }
            return sourceString.substring(begin, end);
        });
    }

    @Override
    public String get() {
        return stringSupplier.get();
    }

}
