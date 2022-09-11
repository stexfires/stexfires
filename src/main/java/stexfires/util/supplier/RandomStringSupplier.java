package stexfires.util.supplier;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.function.LongSupplier;
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

    public static RandomStringSupplier characterConcatenation(RandomGenerator random,
                                                              int lowestCharacterBoundary, int highestCharacterBoundary,
                                                              IntPredicate characterFilter, LongSupplier lengthSupplier) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(characterFilter);
        Objects.requireNonNull(lengthSupplier);
        if (lowestCharacterBoundary < Character.MIN_VALUE || lowestCharacterBoundary > highestCharacterBoundary || highestCharacterBoundary > Character.MAX_VALUE) {
            throw new IllegalArgumentException("Illegal character boundaries. lowestCharacterBoundary=" + lowestCharacterBoundary + ", highestCharacterBoundary=" + highestCharacterBoundary);
        }
        return new RandomStringSupplier(() -> random.ints(lowestCharacterBoundary, highestCharacterBoundary + 1)
                                                    .filter(characterFilter)
                                                    .limit(lengthSupplier.getAsLong())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    public static RandomStringSupplier characterConcatenation(RandomGenerator random,
                                                              String sourceCharacters,
                                                              LongSupplier lengthSupplier) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceCharacters);
        Objects.requireNonNull(lengthSupplier);
        if (sourceCharacters.isEmpty()) {
            throw new IllegalArgumentException("At least one character must be passed.");
        }
        return new RandomStringSupplier(() -> random.ints(0, sourceCharacters.length())
                                                    .map(sourceCharacters::charAt)
                                                    .limit(lengthSupplier.getAsLong())
                                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                                    .toString());
    }

    @Override
    public String get() {
        return stringSupplier.get();
    }

}
