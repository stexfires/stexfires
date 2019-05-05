package stexfires.util.supplier;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RandomBooleanSupplier implements Supplier<Boolean> {

    private static final int HUNDRED = 100;

    private final Random random;
    private final int percent;
    private final Boolean constantResult;

    public RandomBooleanSupplier(int percent) {
        this(percent, new Random());
    }

    public RandomBooleanSupplier(int percent, long seed) {
        this(percent, new Random(seed));
    }

    public RandomBooleanSupplier(int percent, Random random) {
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
    public @NotNull Boolean get() {
        return (constantResult != null) ? constantResult : (random.nextInt(HUNDRED) < percent);
    }

}
