package stexfires.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RepeatingPatternBooleanSupplier implements Supplier<Boolean> {

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
        List<Boolean> nb = new ArrayList<>(pattern.length);
        for (boolean b : pattern) {
            nb.add(b);
        }
        return new RepeatingPatternBooleanSupplier(nb);
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
