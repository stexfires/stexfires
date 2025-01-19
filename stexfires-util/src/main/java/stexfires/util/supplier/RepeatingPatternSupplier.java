package stexfires.util.supplier;

import java.util.*;
import java.util.function.*;

/**
 * A RepeatingPatternSupplier supplies endless values from a given pattern.
 * <p>
 * The {@link RepeatingPatternSupplier#get()} method is {@code synchronized}.
 *
 * @see java.util.function.Supplier
 * @see java.util.Collection
 * @since 0.1
 */
public final class RepeatingPatternSupplier<T> implements Supplier<T> {

    private final List<T> pattern;
    private int currentIndex;

    @SuppressWarnings("ConstantValue")
    public RepeatingPatternSupplier(Collection<T> pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern is empty");
        }
        long countNullValues = pattern.stream().filter(Objects::isNull).count();
        if (countNullValues > 0) {
            throw new IllegalArgumentException("pattern contains " + countNullValues + " null values");
        }
        this.pattern = List.copyOf(pattern);
        currentIndex = -1;
    }

    public static RepeatingPatternSupplier<Boolean> ofPrimitiveBoolean(boolean... pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.length == 0) {
            throw new IllegalArgumentException("pattern is empty");
        }
        List<Boolean> objectPattern = new ArrayList<>(pattern.length);
        for (boolean primitiveValue : pattern) {
            objectPattern.add(primitiveValue);
        }
        return new RepeatingPatternSupplier<>(objectPattern);
    }

    @SuppressWarnings("Convert2streamapi")
    public static RepeatingPatternSupplier<Integer> ofPrimitiveInt(int... pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.length == 0) {
            throw new IllegalArgumentException("pattern is empty");
        }
        List<Integer> objectPattern = new ArrayList<>(pattern.length);
        for (int primitiveValue : pattern) {
            objectPattern.add(primitiveValue);
        }
        return new RepeatingPatternSupplier<>(objectPattern);
    }

    @SuppressWarnings("Convert2streamapi")
    public static RepeatingPatternSupplier<Long> ofPrimitiveLong(long... pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.length == 0) {
            throw new IllegalArgumentException("pattern is empty");
        }
        List<Long> objectPattern = new ArrayList<>(pattern.length);
        for (long primitiveValue : pattern) {
            objectPattern.add(primitiveValue);
        }
        return new RepeatingPatternSupplier<>(objectPattern);
    }

    @Override
    public synchronized T get() {
        currentIndex++;
        // Reset currentIndex at end of pattern
        if (currentIndex == pattern.size()) {
            currentIndex = 0;
        }
        return pattern.get(currentIndex);
    }

    @Override
    public String toString() {
        return "RepeatingPatternSupplier{" +
                "currentIndex=" + currentIndex +
                '}';
    }

}
