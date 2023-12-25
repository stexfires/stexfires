package stexfires.util.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class RepeatingPatternBooleanSupplier implements Supplier<Boolean> {

    private final List<Boolean> pattern;
    private int index;

    @SuppressWarnings("ConstantValue")
    public RepeatingPatternBooleanSupplier(List<Boolean> pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern is empty");
        }
        long countNullValues = pattern.stream().filter(Objects::isNull).count();
        if (countNullValues > 0) {
            throw new IllegalArgumentException("pattern contains " + countNullValues + " null values");
        }
        this.pattern = new ArrayList<>(pattern);
        index = -1;
    }

    public static RepeatingPatternBooleanSupplier primitiveBooleans(boolean... pattern) {
        Objects.requireNonNull(pattern);
        List<Boolean> newPattern = new ArrayList<>(pattern.length);
        for (boolean booleanValue : pattern) {
            newPattern.add(booleanValue);
        }
        return new RepeatingPatternBooleanSupplier(newPattern);
    }

    public BooleanSupplier asPrimitiveBooleanSupplier() {
        return this::get;
    }

    @Override
    public synchronized Boolean get() {
        index++;
        if (index == pattern.size()) {
            index = 0;
        }
        return pattern.get(index);
    }

}
