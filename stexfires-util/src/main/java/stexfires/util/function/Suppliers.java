package stexfires.util.function;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.LongBinaryOperator;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.random.RandomGenerator;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@code Supplier}s.
 *
 * @see java.util.function.Supplier
 * @see java.util.function.BooleanSupplier
 * @see java.util.function.IntSupplier
 * @see java.util.function.LongSupplier
 * @see java.util.function.DoubleSupplier
 * @since 0.1
 */
public final class Suppliers {

    private Suppliers() {
    }

    public static <T> Supplier<T> constant(T constant) {
        return () -> constant;
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T> Supplier<T> constantNull() {
        return () -> null;
    }

    public static BooleanSupplier constantPrimitiveBoolean(boolean constant) {
        return () -> constant;
    }

    public static IntSupplier constantPrimitiveInt(int constant) {
        return () -> constant;
    }

    public static LongSupplier constantPrimitiveLong(long constant) {
        return () -> constant;
    }

    public static DoubleSupplier constantPrimitiveDouble(double constant) {
        return () -> constant;
    }

    public static <T> Supplier<T> combine(Supplier<T> first, Supplier<T> second, BinaryOperator<T> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.get(), second.get());
    }

    public static BooleanSupplier combinePrimitiveBoolean(BooleanSupplier first, BooleanSupplier second, BinaryOperator<Boolean> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.getAsBoolean(), second.getAsBoolean());
    }

    public static IntSupplier combinePrimitiveInt(IntSupplier first, IntSupplier second, IntBinaryOperator combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.applyAsInt(first.getAsInt(), second.getAsInt());
    }

    public static LongSupplier combinePrimitiveLong(LongSupplier first, LongSupplier second, LongBinaryOperator combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.applyAsLong(first.getAsLong(), second.getAsLong());
    }

    public static DoubleSupplier combinePrimitiveDouble(DoubleSupplier first, DoubleSupplier second, DoubleBinaryOperator combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.applyAsDouble(first.getAsDouble(), second.getAsDouble());
    }

    public static <T, R> Supplier<R> mapTo(Supplier<T> supplier, Function<T, R> function) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        return () -> function.apply(supplier.get());
    }

    public static <T> BooleanSupplier mapToPrimitiveBoolean(Supplier<T> supplier, Predicate<T> function) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        return () -> function.test(supplier.get());
    }

    public static <T> IntSupplier mapToPrimitiveInt(Supplier<T> supplier, ToIntFunction<T> function) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        return () -> function.applyAsInt(supplier.get());
    }

    public static <T> LongSupplier mapToPrimitiveLong(Supplier<T> supplier, ToLongFunction<T> function) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        return () -> function.applyAsLong(supplier.get());
    }

    public static <T> DoubleSupplier mapToPrimitiveDouble(Supplier<T> supplier, ToDoubleFunction<T> function) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        return () -> function.applyAsDouble(supplier.get());
    }

    public static <T> Supplier<T> randomSelection(RandomGenerator random,
                                                  List<T> values) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(values);
        int bound = values.size();
        if (bound == 0) {
            return constantNull();
        } else if (bound == 1) {
            return constant(values.get(0));
        } else {
            return () -> values.get(random.nextInt(0, bound));
        }
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> Supplier<T> randomSelection(RandomGenerator random,
                                                  T[] values) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(values);
        int bound = values.length;
        if (bound == 0) {
            return constantNull();
        } else if (bound == 1) {
            return constant(values[0]);
        } else {
            return () -> values[random.nextInt(0, bound)];
        }
    }

    public static <T> Supplier<T> intSupplierSelection(IntSupplier indexSupplier,
                                                       List<T> values) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(values);
        int bound = values.size();
        if (bound == 0) {
            return constantNull();
        } else if (bound == 1) {
            return constant(values.get(0));
        } else {
            return () -> values.get(indexSupplier.getAsInt() % bound);
        }
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> Supplier<T> intSupplierSelection(IntSupplier indexSupplier,
                                                       T[] values) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(values);
        int bound = values.length;
        if (bound == 0) {
            return constantNull();
        } else if (bound == 1) {
            return constant(values[0]);
        } else {
            return () -> values[indexSupplier.getAsInt() % bound];
        }
    }

    public static <T> Supplier<T> conditional(BooleanSupplier conditionSupplier,
                                              Supplier<T> trueSupplier, Supplier<T> falseSupplier) {
        Objects.requireNonNull(conditionSupplier);
        Objects.requireNonNull(trueSupplier);
        Objects.requireNonNull(falseSupplier);
        return () -> conditionSupplier.getAsBoolean() ? trueSupplier.get() : falseSupplier.get();
    }

    public static Supplier<String> localTimeAsString() {
        return () -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static Supplier<String> threadNameAsString() {
        return () -> Thread.currentThread().getName();
    }

    public static Supplier<String> sequenceAsString(long initialValue) {
        return new SequenceSupplier(initialValue).asStringSupplier();
    }

    public static Supplier<Long> sequenceAsLong(long initialValue) {
        return new SequenceSupplier(initialValue).asLongSupplier();
    }

    public static LongSupplier sequenceAsPrimitiveLong(long initialValue) {
        return new SequenceSupplier(initialValue).asPrimitiveLongSupplier();
    }

    private static final class SequenceSupplier {

        private final AtomicLong atomicLong;

        private SequenceSupplier(long initialValue) {
            atomicLong = new AtomicLong(initialValue);
        }

        private Supplier<String> asStringSupplier() {
            return () -> String.valueOf(atomicLong.getAndIncrement());
        }

        private Supplier<Long> asLongSupplier() {
            return atomicLong::getAndIncrement;
        }

        private LongSupplier asPrimitiveLongSupplier() {
            return atomicLong::getAndIncrement;
        }

    }

}
