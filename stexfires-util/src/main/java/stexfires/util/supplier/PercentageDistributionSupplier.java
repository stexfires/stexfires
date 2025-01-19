package stexfires.util.supplier;

import java.util.*;
import java.util.function.*;
import java.util.random.*;

/**
 * A PercentageDistributionSupplier supplies endless random values with a given percentage share of a value.
 * There are two values. The first value has a given percentage share of the total number of values.
 * The other value has the remaining percentage share.
 * <p>
 * There are four static methods to create primitive suppliers for boolean, int, long and double.
 * <p>
 * The {@link PercentageDistributionSupplier#get()} method is not {@code synchronized},
 * so a suitable {@link java.util.random.RandomGenerator} must be used in a multithreading context.
 *
 * @see java.util.function.Supplier
 * @see java.util.random.RandomGenerator
 * @since 0.1
 */
public final class PercentageDistributionSupplier<T> implements Supplier<T> {

    private static final int HUNDRED = 100;

    private final RandomGenerator random;
    private final int percentageShareOfValue;
    private final T value;
    private final T otherValue;

    public PercentageDistributionSupplier(RandomGenerator random,
                                          int percentageShareOfValue,
                                          T value,
                                          T otherValue) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(value);
        Objects.requireNonNull(otherValue);
        this.random = random;
        this.percentageShareOfValue = percentageShareOfValue;
        this.value = value;
        this.otherValue = otherValue;
    }

    public static BooleanSupplier asPrimitiveBooleanSupplier(RandomGenerator random,
                                                             int percentageShareOfValue,
                                                             boolean value,
                                                             boolean otherValue) {
        return new PercentageDistributionSupplier<>(random, percentageShareOfValue, value, otherValue)::get;
    }

    public static IntSupplier asPrimitiveIntSupplier(RandomGenerator random,
                                                     int percentageShareOfValue,
                                                     int value,
                                                     int otherValue) {
        return new PercentageDistributionSupplier<>(random, percentageShareOfValue, value, otherValue)::get;
    }

    public static LongSupplier asPrimitiveLongSupplier(RandomGenerator random,
                                                       int percentageShareOfValue,
                                                       long value,
                                                       long otherValue) {
        return new PercentageDistributionSupplier<>(random, percentageShareOfValue, value, otherValue)::get;
    }

    public static DoubleSupplier asPrimitiveDoubleSupplier(RandomGenerator random,
                                                           int percentageShareOfValue,
                                                           double value,
                                                           double otherValue) {
        return new PercentageDistributionSupplier<>(random, percentageShareOfValue, value, otherValue)::get;
    }

    @Override
    public T get() {
        return (random.nextInt(HUNDRED) < percentageShareOfValue) ? value : otherValue;
    }

    @Override
    public String toString() {
        return "PercentageDistributionSupplier{" +
                "percentageShareOfValue=" + percentageShareOfValue +
                ", value=" + value +
                ", otherValue=" + otherValue +
                '}';
    }

}
