package stexfires.util.function;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RepeatingPatternBooleanSupplier implements Supplier<Boolean> {

    private static final Boolean DEFAULT_VALUE = Boolean.FALSE;

    private final List<Boolean> pattern;
    private int index;

    public RepeatingPatternBooleanSupplier(List<Boolean> pattern) {
        Objects.requireNonNull(pattern);
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern is empty");
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
    public synchronized @NotNull Boolean get() {
        index++;
        if (index == pattern.size()) {
            index = 0;
        }
        return Objects.requireNonNullElse(pattern.get(index), DEFAULT_VALUE);
    }

}
