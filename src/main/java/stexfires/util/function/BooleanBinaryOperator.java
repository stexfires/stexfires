package stexfires.util.function;

import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * Represents an operation upon two {@code boolean}-valued operands and producing an
 * {@code boolean}-valued result.   This is the primitive type specialization of
 * {@link java.util.function.BinaryOperator} for {@code boolean}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsBoolean(boolean, boolean)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.BinaryOperator
 * @see stexfires.util.function.BooleanUnaryOperator
 * @see java.util.function.IntBinaryOperator
 * @since 0.1
 */
@FunctionalInterface
public interface BooleanBinaryOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    boolean applyAsBoolean(boolean left, boolean right);

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default BooleanBinaryOperator andThen(BooleanUnaryOperator after) {
        Objects.requireNonNull(after);
        return (left, right) -> after.applyAsBoolean(applyAsBoolean(left, right));
    }

    default BinaryOperator<Boolean> asBinaryOperator() {
        return this::applyAsBoolean;
    }

    default BooleanUnaryOperator asBooleanUnaryOperator(boolean right) {
        return (left) -> applyAsBoolean(left, right);
    }

    static BooleanBinaryOperator AND() {
        return (left, right) -> left && right;
    }

    static BooleanBinaryOperator NAND() {
        return (left, right) -> !(left && right);
    }

    static BooleanBinaryOperator OR() {
        return (left, right) -> left || right;
    }

    static BooleanBinaryOperator NOR() {
        return (left, right) -> !(left || right);
    }

    static BooleanBinaryOperator XOR() {
        return (left, right) -> left != right;
    }

    static BooleanBinaryOperator XNOR() {
        return (left, right) -> left == right;
    }

}
