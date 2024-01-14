package stexfires.util.function;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Represents an operation on a single {@code boolean}-valued operand that produces
 * an {@code boolean}-valued result.  This is the primitive type specialization of
 * {@link java.util.function.UnaryOperator} for {@code boolean}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsBoolean(boolean)}.
 *
 * @see java.util.function.UnaryOperator
 * @see stexfires.util.function.BooleanBinaryOperator
 * @see java.util.function.IntUnaryOperator
 * @since 0.1
 */
@SuppressWarnings("MethodNamesDifferingOnlyByCase")
@FunctionalInterface
public interface BooleanUnaryOperator {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static BooleanUnaryOperator identity() {
        return b -> b;
    }

    static BooleanUnaryOperator constant(boolean constant) {
        return b -> constant;
    }

    static BooleanUnaryOperator supplier(BooleanSupplier supplier) {
        Objects.requireNonNull(supplier);
        return b -> supplier.getAsBoolean();
    }

    static BooleanUnaryOperator NOT() {
        return b -> !b;
    }

    static BooleanUnaryOperator AND(BooleanUnaryOperator firstOperator,
                                    BooleanUnaryOperator secondOperator) {
        Objects.requireNonNull(firstOperator);
        Objects.requireNonNull(secondOperator);
        return b -> firstOperator.applyAsBoolean(b) && secondOperator.applyAsBoolean(b);
    }

    static BooleanUnaryOperator OR(BooleanUnaryOperator firstOperator,
                                   BooleanUnaryOperator secondOperator) {
        Objects.requireNonNull(firstOperator);
        Objects.requireNonNull(secondOperator);
        return b -> firstOperator.applyAsBoolean(b) || secondOperator.applyAsBoolean(b);
    }

    boolean applyAsBoolean(boolean operand);

    /**
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     * operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(stexfires.util.function.BooleanUnaryOperator)
     */

    default BooleanUnaryOperator compose(BooleanUnaryOperator before) {
        Objects.requireNonNull(before);
        return b -> applyAsBoolean(before.applyAsBoolean(b));
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     * applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(stexfires.util.function.BooleanUnaryOperator)
     */
    default BooleanUnaryOperator andThen(BooleanUnaryOperator after) {
        Objects.requireNonNull(after);
        return b -> after.applyAsBoolean(applyAsBoolean(b));
    }

    default BooleanUnaryOperator and(BooleanUnaryOperator other) {
        Objects.requireNonNull(other);
        return b -> applyAsBoolean(b) && other.applyAsBoolean(b);
    }

    default BooleanUnaryOperator or(BooleanUnaryOperator other) {
        Objects.requireNonNull(other);
        return b -> applyAsBoolean(b) || other.applyAsBoolean(b);
    }

    default BooleanUnaryOperator negate() {
        return b -> !applyAsBoolean(b);
    }

    default Predicate<Boolean> asPredicate() {
        return this::applyAsBoolean;
    }

    default UnaryOperator<Boolean> asUnaryOperator() {
        return this::applyAsBoolean;
    }

}
