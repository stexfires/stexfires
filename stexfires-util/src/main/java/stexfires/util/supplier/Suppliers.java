package stexfires.util.supplier;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.BooleanBinaryOperator;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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

    public static <T> Supplier<T> constantOfNotNull(T constant) {
        Objects.requireNonNull(constant);
        return () -> constant;
    }

    public static <T> Supplier<@Nullable T> constantOfNullable(@Nullable T constant) {
        return () -> constant;
    }

    public static <T> Supplier<@Nullable T> constantNull() {
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

    public static BooleanSupplier combinePrimitiveBoolean(BooleanSupplier first, BooleanSupplier second, BooleanBinaryOperator combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.applyAsBoolean(first.getAsBoolean(), second.getAsBoolean());
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

    public static <T> Supplier<T> randomListSelection(RandomGenerator random,
                                                      List<T> values) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(values);
        if (values.isEmpty()) {
            throw new IllegalArgumentException("At least one value must be passed.");
        }
        int bound = values.size();
        if (bound == 1) {
            return constantOfNotNull(values.getFirst());
        } else {
            return () -> values.get(random.nextInt(0, bound));
        }
    }

    @SafeVarargs
    public static <T> Supplier<T> randomSelection(RandomGenerator random,
                                                  T... values) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(values);
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value must be passed.");
        }
        int bound = values.length;
        if (bound == 1) {
            return constantOfNotNull(values[0]);
        } else {
            return () -> values[random.nextInt(0, bound)];
        }
    }

    public static <T> Supplier<T> intSupplierListSelection(IntSupplier indexSupplier,
                                                           List<T> values) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(values);
        if (values.isEmpty()) {
            throw new IllegalArgumentException("At least one value must be passed.");
        }
        int bound = values.size();
        if (bound == 1) {
            return constantOfNotNull(values.getFirst());
        } else {
            return () -> values.get(indexSupplier.getAsInt() % bound);
        }
    }

    @SafeVarargs
    public static <T> Supplier<T> intSupplierSelection(IntSupplier indexSupplier,
                                                       T... values) {
        Objects.requireNonNull(indexSupplier);
        Objects.requireNonNull(values);
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one value must be passed.");
        }
        int bound = values.length;
        if (bound == 1) {
            return constantOfNotNull(values[0]);
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

    public static BooleanSupplier randomPrimitiveBoolean(RandomGenerator random) {
        Objects.requireNonNull(random);
        return random::nextBoolean;
    }

    public static Supplier<UUID> randomUUID() {
        return UUID::randomUUID;
    }

    public static Supplier<String> randomUUIDAsString() {
        return () -> UUID.randomUUID().toString();
    }

    public static Supplier<String> localTimeNowAsString() {
        return () -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static Supplier<String> threadNameAsString() {
        return () -> Thread.currentThread().getName();
    }

    public static Supplier<String> stringCutting(IntSupplier beginIndex, IntSupplier length,
                                                 String completeString) {
        Objects.requireNonNull(beginIndex);
        Objects.requireNonNull(length);
        Objects.requireNonNull(completeString);
        if (completeString.isEmpty()) {
            throw new IllegalArgumentException("The string must not be empty.");
        }
        return () -> {
            int begin = beginIndex.getAsInt();
            if (begin > completeString.length()) {
                begin = completeString.length();
            }
            int end = begin + length.getAsInt();
            if (end > completeString.length()) {
                end = completeString.length();
            }
            return completeString.substring(begin, end);
        };
    }

}
