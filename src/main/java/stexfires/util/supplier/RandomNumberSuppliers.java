package stexfires.util.supplier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RandomNumberSuppliers {

    private RandomNumberSuppliers() {
    }

    public static IntSupplier randomPrimitiveInteger(RandomGenerator random,
                                                     int origin, int bound) {
        Objects.requireNonNull(random);
        return () -> random.nextInt(origin, bound);
    }

    public static Supplier<Integer> randomInteger(RandomGenerator random,
                                                  int origin, int bound) {
        Objects.requireNonNull(random);
        return () -> random.nextInt(origin, bound);
    }

    public static LongSupplier randomPrimitiveLong(RandomGenerator random,
                                                   long origin, long bound) {
        Objects.requireNonNull(random);
        return () -> random.nextLong(origin, bound);
    }

    public static Supplier<Long> randomLong(RandomGenerator random,
                                            long origin, long bound) {
        Objects.requireNonNull(random);
        return () -> random.nextLong(origin, bound);
    }

    public static DoubleSupplier randomPrimitiveDouble(RandomGenerator random,
                                                       double origin, double bound) {
        Objects.requireNonNull(random);
        return () -> random.nextDouble(origin, bound);
    }

    public static Supplier<Double> randomDouble(RandomGenerator random,
                                                double origin, double bound) {
        Objects.requireNonNull(random);
        return () -> random.nextDouble(origin, bound);
    }

    public static Supplier<Float> randomFloat(RandomGenerator random,
                                              float origin, float bound) {
        Objects.requireNonNull(random);
        return () -> random.nextFloat(origin, bound);
    }

    public static Supplier<BigInteger> randomBigInteger(RandomGenerator random,
                                                        long origin, long bound) {
        Objects.requireNonNull(random);
        return () -> BigInteger.valueOf(random.nextLong(origin, bound));
    }

    public static Supplier<BigDecimal> randomBigDecimal(RandomGenerator random,
                                                        double origin, double bound) {
        Objects.requireNonNull(random);
        return () -> BigDecimal.valueOf(random.nextDouble(origin, bound));
    }

    public static DoubleSupplier randomPrimitiveDoubleGaussian(RandomGenerator random,
                                                               double mean, double stddev) {
        Objects.requireNonNull(random);
        return () -> random.nextGaussian(mean, stddev);
    }

    public static Supplier<Double> randomDoubleGaussian(RandomGenerator random,
                                                        double mean, double stddev) {
        Objects.requireNonNull(random);
        return () -> random.nextGaussian(mean, stddev);
    }

    public static IntSupplier primitiveIntegerSelection(RandomGenerator random,
                                                        int... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<Integer> integerSelection(RandomGenerator random,
                                                     List<Integer> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<Integer> integerSelection(RandomGenerator random,
                                                     Integer... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static LongSupplier primitiveLongSelection(RandomGenerator random,
                                                      long... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<Long> longSelection(RandomGenerator random,
                                               List<Long> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<Long> longSelection(RandomGenerator random,
                                               Long... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static DoubleSupplier primitiveDoubleSelection(RandomGenerator random,
                                                          double... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<Double> doubleSelection(RandomGenerator random,
                                                   List<Double> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<Double> doubleSelection(RandomGenerator random,
                                                   Double... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<Float> floatSelection(RandomGenerator random,
                                                 List<Float> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<Float> floatSelection(RandomGenerator random,
                                                 Float... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<BigInteger> bigIntegerSelection(RandomGenerator random,
                                                           List<BigInteger> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<BigInteger> bigIntegerSelection(RandomGenerator random,
                                                           BigInteger... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

    public static Supplier<BigDecimal> bigDecimalSelection(RandomGenerator random,
                                                           List<BigDecimal> sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.isEmpty()) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers.get(random.nextInt(0, sourceNumbers.size()));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Supplier<BigDecimal> bigDecimalSelection(RandomGenerator random,
                                                           BigDecimal... sourceNumbers) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(sourceNumbers);
        if (sourceNumbers.length == 0) {
            throw new IllegalArgumentException("At least one number must be passed.");
        }
        return () -> sourceNumbers[random.nextInt(0, sourceNumbers.length)];
    }

}
