package stexfires.util.function;

import java.math.BigInteger;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.LongBinaryOperator;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * This class consists of {@code static} utility methods for operating on {@code Supplier}s.
 *
 * @author Mathias Kalb
 * @see java.util.function.Supplier
 * @see java.util.function.IntSupplier
 * @see java.util.function.LongSupplier
 * @see java.util.function.BooleanSupplier
 * @since 0.1
 */
public final class Suppliers {

    private Suppliers() {
    }

    public static Supplier<String> combineString(Supplier<String> first, Supplier<String> second, BinaryOperator<String> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.get(), second.get());
    }

    public static Supplier<Boolean> combineBoolean(Supplier<Boolean> first, Supplier<Boolean> second, BinaryOperator<Boolean> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.get(), second.get());
    }

    public static Supplier<Integer> combineInteger(Supplier<Integer> first, Supplier<Integer> second, BinaryOperator<Integer> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.get(), second.get());
    }

    public static Supplier<Long> combineLong(Supplier<Long> first, Supplier<Long> second, BinaryOperator<Long> combiner) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(combiner);
        return () -> combiner.apply(first.get(), second.get());
    }

    public static Supplier<BigInteger> combineBigInteger(Supplier<BigInteger> first, Supplier<BigInteger> second, BinaryOperator<BigInteger> combiner) {
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

    public static IntSupplier combinePrimitiveInteger(IntSupplier first, IntSupplier second, IntBinaryOperator combiner) {
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

    public static Supplier<String> stringSupplierLocalTime() {
        return () -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static Supplier<String> stringSupplierThreadName() {
        return () -> Thread.currentThread().getName();
    }

}
