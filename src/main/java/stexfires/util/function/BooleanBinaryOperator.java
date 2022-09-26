package stexfires.util.function;

import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;

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
     * @param first  the first operand
     * @param second the second operand
     * @return the operator result
     */
    boolean applyAsBoolean(boolean first, boolean second);

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
        return (first, second) -> after.applyAsBoolean(applyAsBoolean(first, second));
    }

    default BinaryOperator<Boolean> asBinaryOperator() {
        return this::applyAsBoolean;
    }

    default BooleanUnaryOperator asBooleanUnaryOperator(boolean second) {
        return (first) -> applyAsBoolean(first, second);
    }

    static BooleanBinaryOperator constant(boolean constant) {
        return (first, second) -> constant;
    }

    static BooleanBinaryOperator supplier(BooleanSupplier supplier) {
        Objects.requireNonNull(supplier);
        return (first, second) -> supplier.getAsBoolean();
    }

    static BooleanBinaryOperator first() {
        return (first, second) -> first;
    }

    static BooleanBinaryOperator second() {
        return (first, second) -> second;
    }

    static BooleanBinaryOperator equals() {
        return (first, second) -> first == second;
    }

    static BooleanBinaryOperator AND() {
        return (first, second) -> first && second;
    }

    static BooleanBinaryOperator NAND() {
        return (first, second) -> !(first && second);
    }

    static BooleanBinaryOperator OR() {
        return (first, second) -> first || second;
    }

    static BooleanBinaryOperator NOR() {
        return (first, second) -> !(first || second);
    }

    static BooleanBinaryOperator XOR() {
        return (first, second) -> first != second;
    }

    static BooleanBinaryOperator XNOR() {
        return (first, second) -> first == second;
    }

}
