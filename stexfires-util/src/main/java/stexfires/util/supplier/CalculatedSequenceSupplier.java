package stexfires.util.supplier;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * This class consists of {@code static} utility methods
 * for creating different {@code Supplier}s, which calculate a sequence of values.
 * <p>
 * It uses internally a {@code long} index, which is incremented by {@code 1} for each call of {@link #get()}.
 * <p>
 * The {@link #get()} method calculates the next value of the sequence. It can throw an {@link ArithmeticException}.
 * <p>
 * Examples:
 * <pre>
 * CalculatedSequenceSupplier.ofValues(BigInteger.ZERO, BigInteger.ONE, BigInteger::add); // Fibonacci numbers: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
 * CalculatedSequenceSupplier.ofLongIndexAndValue(BigInteger.ONE, (index, value) -> value.multiply(BigInteger.valueOf(index))); // Factorials: 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, ...
 * </pre>
 *
 * @see java.util.function.Supplier
 * @since 0.1
 */
public final class CalculatedSequenceSupplier<T> implements Supplier<T> {

    public static final long DEFAULT_START_INDEX = 0;

    private final @Nullable BiFunction<Long, T, T> indexAndValueFunction;
    private final @Nullable BinaryOperator<T> valuesOperator;
    private final long startIndex;
    private T currentValue;
    private @Nullable T helperValue;
    private long index;

    private CalculatedSequenceSupplier(T firstValue,
                                       @Nullable T helperValue,
                                       long startIndex,
                                       @Nullable BiFunction<Long, T, T> indexAndValueFunction,
                                       @Nullable BinaryOperator<T> valuesOperator) {
        Objects.requireNonNull(firstValue);
        currentValue = firstValue;
        this.helperValue = helperValue;
        this.startIndex = startIndex;
        index = startIndex;
        this.indexAndValueFunction = indexAndValueFunction;
        this.valuesOperator = valuesOperator;
    }

    public static <T> CalculatedSequenceSupplier<T> ofIntIndexAndValue(T firstValue,
                                                                       BiFunction<Integer, T, T> indexAndValueFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexAndValueFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, DEFAULT_START_INDEX,
                (index, value) -> indexAndValueFunction.apply(Math.toIntExact(index), value), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofIntIndexAndValue(T firstValue,
                                                                       int startIndex,
                                                                       BiFunction<Integer, T, T> indexAndValueFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexAndValueFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, startIndex,
                (index, value) -> indexAndValueFunction.apply(Math.toIntExact(index), value), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofLongIndexAndValue(T firstValue,
                                                                        BiFunction<Long, T, T> indexAndValueFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexAndValueFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, DEFAULT_START_INDEX,
                indexAndValueFunction, null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofLongIndexAndValue(T firstValue,
                                                                        long startIndex,
                                                                        BiFunction<Long, T, T> indexAndValueFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexAndValueFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, startIndex,
                indexAndValueFunction, null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofIntIndex(T firstValue,
                                                               IntFunction<T> indexFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, DEFAULT_START_INDEX,
                (index, value) -> indexFunction.apply(Math.toIntExact(index)), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofIntIndex(T firstValue,
                                                               int startIndex,
                                                               IntFunction<T> indexFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, startIndex,
                (index, value) -> indexFunction.apply(Math.toIntExact(index)), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofLongIndex(T firstValue,
                                                                LongFunction<T> indexFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, DEFAULT_START_INDEX,
                (index, value) -> indexFunction.apply(index), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofLongIndex(T firstValue,
                                                                long startIndex,
                                                                LongFunction<T> indexFunction) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(indexFunction);
        return new CalculatedSequenceSupplier<>(firstValue, null, startIndex,
                (index, value) -> indexFunction.apply(index), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofValue(T firstValue,
                                                            UnaryOperator<T> valueOperator) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(valueOperator);
        return new CalculatedSequenceSupplier<>(firstValue, null, Long.MIN_VALUE,
                (index, value) -> valueOperator.apply(value), null);
    }

    public static <T> CalculatedSequenceSupplier<T> ofValues(T firstValue,
                                                             T secondValue,
                                                             BinaryOperator<T> valuesOperator) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(secondValue);
        Objects.requireNonNull(valuesOperator);
        return new CalculatedSequenceSupplier<>(firstValue, secondValue, Long.MIN_VALUE,
                null, valuesOperator);
    }

    public static <T> CalculatedSequenceSupplier<T> ofAlternatingValues(T firstValue,
                                                                        T secondValue) {
        Objects.requireNonNull(firstValue);
        Objects.requireNonNull(secondValue);
        return new CalculatedSequenceSupplier<>(firstValue, secondValue, Long.MIN_VALUE,
                null, (v0, v1) -> v0);
    }

    @SuppressWarnings("ConstantValue")
    @Override
    public synchronized T get() throws ArithmeticException {
        if ((index > startIndex) && (currentValue != null)) {
            if (indexAndValueFunction != null) {
                currentValue = indexAndValueFunction.apply(index, currentValue);
            } else if ((valuesOperator != null) && (helperValue != null)) {
                T beforeValue = currentValue;
                if (index == (startIndex + 1)) {
                    currentValue = helperValue;
                } else {
                    currentValue = valuesOperator.apply(helperValue, currentValue);
                }
                helperValue = beforeValue;
            }
        }
        index = Math.incrementExact(index);
        return currentValue;
    }

    @Override
    public String toString() {
        return "CalculatedSequenceSupplier{" +
                "startIndex=" + startIndex +
                ", currentValue=" + currentValue +
                ", helperValue=" + helperValue +
                ", index=" + index +
                '}';
    }

}
