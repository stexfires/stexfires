package stexfires.util.supplier;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.BooleanBinaryOperator;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;
import java.util.random.*;

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

    public static <T> Supplier<T> replacingSupplier(Supplier<T> supplier,
                                                    Predicate<T> condition,
                                                    T substituteValue) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(condition);
        Objects.requireNonNull(substituteValue);
        return () -> {
            T value = supplier.get();
            return condition.test(value) ? substituteValue : value;
        };
    }

    public static <T> Supplier<@Nullable T> replacingWithNullSupplier(Supplier<T> supplier,
                                                                      Predicate<T> condition) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(condition);
        return () -> {
            T value = supplier.get();
            return condition.test(value) ? null : value;
        };
    }

    public static <T> Supplier<T> insertingSupplier(Supplier<T> supplier,
                                                    BooleanSupplier conditionSupplier,
                                                    T additionalValue) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(conditionSupplier);
        Objects.requireNonNull(additionalValue);
        return () -> conditionSupplier.getAsBoolean() ? additionalValue : supplier.get();
    }

    public static <T> Supplier<@Nullable T> insertingNullSupplier(Supplier<T> supplier,
                                                                  BooleanSupplier conditionSupplier) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(conditionSupplier);
        return () -> conditionSupplier.getAsBoolean() ? null : supplier.get();
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

    public static <T, C extends Collection<T>> Supplier<C> replenishedCollection(Supplier<C> newCollectionSupplier,
                                                                                 Supplier<T> elementToBeAddedSupplier,
                                                                                 IntSupplier sizeSupplier,
                                                                                 int maxAdditionAttempts) {
        Objects.requireNonNull(newCollectionSupplier);
        Objects.requireNonNull(elementToBeAddedSupplier);
        Objects.requireNonNull(sizeSupplier);
        return () -> {
            C collection = newCollectionSupplier.get();
            int size = sizeSupplier.getAsInt();
            int additionAttempts = 0;
            while ((additionAttempts < maxAdditionAttempts) && (collection.size() < size)) {
                collection.add(elementToBeAddedSupplier.get());
                additionAttempts++;
            }
            return collection;
        };
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static Supplier<byte[]> byteArrayOfIntSupplier(IntSupplier intSupplier,
                                                          IntSupplier lengthSupplier) {
        Objects.requireNonNull(intSupplier);
        Objects.requireNonNull(lengthSupplier);
        return () -> {
            int size = lengthSupplier.getAsInt();
            if (size <= 0) {
                return new byte[0];
            }
            byte[] bytes = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes[i] = (byte) intSupplier.getAsInt();
            }
            return bytes;
        };
    }

    public static Supplier<byte[]> byteArrayOfRandomBytes(RandomGenerator random,
                                                          IntSupplier lengthSupplier) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(lengthSupplier);
        return () -> {
            int size = lengthSupplier.getAsInt();
            if (size <= 0) {
                return new byte[0];
            }
            byte[] bytes = new byte[size];
            random.nextBytes(bytes);
            return bytes;
        };
    }

}
