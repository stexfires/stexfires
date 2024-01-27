package stexfires.util.supplier;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * A RandomBooleanSupplier supplies endless random {@link java.lang.Boolean} values.
 * <p>
 * The {@link RandomBooleanSupplier#get()} method is not {@code synchronized},
 * so a suitable {@link java.util.random.RandomGenerator} must be used in a multithreading context.
 *
 * @see java.util.function.Supplier
 * @see java.util.function.BooleanSupplier
 * @see java.lang.Boolean
 * @see java.util.random.RandomGenerator
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

    @Override
    public String toString() {
        return "RandomBooleanSupplier{" +
                "percent=" + percent +
                ", constantResult=" + constantResult +
                '}';
    }

}
