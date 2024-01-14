package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * @since 0.1
 */
public final class RandomBooleanSupplier implements Supplier<Boolean> {

    private static final int HUNDRED = 100;

    private final RandomGenerator random;
    private final int percent;
    private final @Nullable Boolean constantResult;

    public RandomBooleanSupplier(int percent, RandomGenerator random) {
        Objects.requireNonNull(random);
        this.percent = percent;
        this.random = random;
        if (percent <= 0) {
            constantResult = Boolean.FALSE;
        } else if (percent >= HUNDRED) {
            constantResult = Boolean.TRUE;
        } else {
            constantResult = null;
        }
    }

    public BooleanSupplier asPrimitiveBooleanSupplier() {
        return this::get;
    }

    @Override
    public Boolean get() {
        return (constantResult != null) ? constantResult : (random.nextInt(HUNDRED) < percent);
    }

}
