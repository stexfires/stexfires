package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.*;

/**
 * @see Number
 * @see java.util.function.Function
 * @since 0.1
 */
public final class NumberFunctions {

    private NumberFunctions() {
    }

    public static <T extends Number, R> Function<@Nullable T, R> constantOfNotNull(R constant) {
        Objects.requireNonNull(constant);
        return n -> constant;
    }

    public static <T extends Number, R> Function<@Nullable T, @Nullable R> constantOfNullable(@Nullable R constant) {
        return n -> constant;
    }

    public static <T extends Number, R> Function<@Nullable T, @Nullable R> constantNull() {
        return n -> null;
    }

    public static <T extends Number, R> Function<@Nullable T, R> supplier(Supplier<R> supplier) {
        Objects.requireNonNull(supplier);
        return n -> supplier.get();
    }

    public static <T extends Number, R> Function<@Nullable T, R> nullHandler(R nullValue,
                                                                             Function<T, R> nonNullFunction) {
        Objects.requireNonNull(nullValue);
        Objects.requireNonNull(nonNullFunction);
        return n -> (n == null) ? nullValue : nonNullFunction.apply(n);
    }

    public static <T extends Number, R> Function<T, R> conditional(Predicate<T> condition,
                                                                   Function<T, R> trueFunction,
                                                                   Function<T, R> falseFunction) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueFunction);
        Objects.requireNonNull(falseFunction);
        return n -> condition.test(n) ? trueFunction.apply(n) : falseFunction.apply(n);
    }

    public static <T extends Number> Function<@Nullable T, @Nullable T> identityOfNullable() {
        return n -> n;
    }

    public static <T extends Number> UnaryOperator<@Nullable T> asUnaryOperatorForNullable(Function<@Nullable T, @Nullable T> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    public static <T extends Number> UnaryOperator<T> asUnaryOperatorForNotNull(Function<T, T> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    public static IntUnaryOperator asIntUnaryOperatorForNotNull(Function<Integer, Integer> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}
