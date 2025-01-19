package stexfires.util.function;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.util.*;
import java.util.function.*;

/**
 * @see java.lang.Number
 * @see java.lang.Math
 * @see java.util.function.Function
 * @see java.util.function.UnaryOperator
 * @since 0.1
 */
public final class NumberUnaryOperators {

    private NumberUnaryOperators() {
    }

    public static final class PrimitiveIntUnaryOperators {

        private PrimitiveIntUnaryOperators() {
        }

        public static IntUnaryOperator of(IntFunction<Integer> function) {
            Objects.requireNonNull(function);
            return function::apply;
        }

        public static IntUnaryOperator concat(IntUnaryOperator first, IntUnaryOperator second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            return (s) -> second.applyAsInt(first.applyAsInt(s));
        }

        public static IntUnaryOperator concat(IntUnaryOperator first, IntUnaryOperator second, IntUnaryOperator third) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            Objects.requireNonNull(third);
            return (s) -> third.applyAsInt(second.applyAsInt(first.applyAsInt(s)));
        }

        public static IntUnaryOperator conditionalOperator(IntPredicate condition, IntUnaryOperator trueOperator, IntUnaryOperator falseOperator) {
            Objects.requireNonNull(condition);
            Objects.requireNonNull(trueOperator);
            Objects.requireNonNull(falseOperator);
            return (s) -> condition.test(s) ? trueOperator.applyAsInt(s) : falseOperator.applyAsInt(s);
        }

        public static IntUnaryOperator identity() {
            return n ->
                    n;
        }

        public static IntUnaryOperator constant(int constant) {
            return n ->
                    constant;
        }

        public static IntUnaryOperator incrementExact() {
            return Math::incrementExact;
        }

        public static IntUnaryOperator decrementExact() {
            return Math::decrementExact;
        }

        public static IntUnaryOperator toZero() {
            return n ->
                    0;
        }

        public static IntUnaryOperator absExact() {
            return Math::absExact;
        }

        public static IntUnaryOperator negateExact() {
            return Math::negateExact;
        }

        public static IntUnaryOperator squareExact() {
            return n ->
                    Math.multiplyExact(n, n);
        }

        public static IntUnaryOperator digitSum() {
            return n ->
                    Integer.toString(n).codePoints().filter(Character::isDigit).map(Character::getNumericValue).sum();
        }

        public static IntUnaryOperator addExact(int secondValue) {
            return n ->
                    Math.addExact(n, secondValue);
        }

        public static IntUnaryOperator subtractExact(int secondValue) {
            return n ->
                    Math.subtractExact(n, secondValue);
        }

        public static IntUnaryOperator multiplyExact(int secondValue) {
            return n ->
                    Math.multiplyExact(n, secondValue);
        }

        public static IntUnaryOperator divideExact(int divisor) {
            return n ->
                    Math.divideExact(n, divisor);
        }

        public static IntUnaryOperator min(int secondValue) {
            return n ->
                    Math.min(n, secondValue);
        }

        public static IntUnaryOperator max(int secondValue) {
            return n ->
                    Math.max(n, secondValue);
        }

        public static IntUnaryOperator floorDivExact(int divisor) {
            return n ->
                    Math.floorDivExact(n, divisor);
        }

        public static IntUnaryOperator floorMod(int divisor) {
            return n ->
                    Math.floorMod(n, divisor);
        }

        public static IntUnaryOperator ceilDivExact(int divisor) {
            return n ->
                    Math.ceilDivExact(n, divisor);
        }

        public static IntUnaryOperator ceilMod(int divisor) {
            return n ->
                    Math.ceilMod(n, divisor);
        }

        public static IntUnaryOperator clamp(int min, int max) {
            return n ->
                    Math.clamp(n, min, max);
        }

    }

    public static final class PrimitiveLongUnaryOperators {

        private PrimitiveLongUnaryOperators() {
        }

        public static LongUnaryOperator of(LongFunction<Long> function) {
            Objects.requireNonNull(function);
            return function::apply;
        }

        public static LongUnaryOperator concat(LongUnaryOperator first, LongUnaryOperator second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            return (s) -> second.applyAsLong(first.applyAsLong(s));
        }

        public static LongUnaryOperator concat(LongUnaryOperator first, LongUnaryOperator second, LongUnaryOperator third) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            Objects.requireNonNull(third);
            return (s) -> third.applyAsLong(second.applyAsLong(first.applyAsLong(s)));
        }

        public static LongUnaryOperator conditionalOperator(LongPredicate condition, LongUnaryOperator trueOperator, LongUnaryOperator falseOperator) {
            Objects.requireNonNull(condition);
            Objects.requireNonNull(trueOperator);
            Objects.requireNonNull(falseOperator);
            return (s) -> condition.test(s) ? trueOperator.applyAsLong(s) : falseOperator.applyAsLong(s);
        }

        public static LongUnaryOperator identity() {
            return n ->
                    n;
        }

        public static LongUnaryOperator constant(long constant) {
            return n ->
                    constant;
        }

        public static LongUnaryOperator incrementExact() {
            return Math::incrementExact;
        }

        public static LongUnaryOperator decrementExact() {
            return Math::decrementExact;
        }

        public static LongUnaryOperator toZero() {
            return n ->
                    0L;
        }

        public static LongUnaryOperator absExact() {
            return Math::absExact;
        }

        public static LongUnaryOperator negateExact() {
            return Math::negateExact;
        }

        public static LongUnaryOperator squareExact() {
            return n ->
                    Math.multiplyExact(n, n);
        }

        public static LongUnaryOperator digitSum() {
            return n ->
                    Long.toString(n).codePoints().filter(Character::isDigit).map(Character::getNumericValue).sum();
        }

        public static LongUnaryOperator addExact(long secondValue) {
            return n ->
                    Math.addExact(n, secondValue);
        }

        public static LongUnaryOperator subtractExact(long secondValue) {
            return n ->
                    Math.subtractExact(n, secondValue);
        }

        public static LongUnaryOperator multiplyExact(long secondValue) {
            return n ->
                    Math.multiplyExact(n, secondValue);
        }

        public static LongUnaryOperator divideExact(long divisor) {
            return n ->
                    Math.divideExact(n, divisor);
        }

        public static LongUnaryOperator min(long secondValue) {
            return n ->
                    Math.min(n, secondValue);
        }

        public static LongUnaryOperator max(long secondValue) {
            return n ->
                    Math.max(n, secondValue);
        }

        public static LongUnaryOperator floorDivExact(long divisor) {
            return n ->
                    Math.floorDivExact(n, divisor);
        }

        public static LongUnaryOperator floorMod(long divisor) {
            return n ->
                    Math.floorMod(n, divisor);
        }

        public static LongUnaryOperator ceilDivExact(long divisor) {
            return n ->
                    Math.ceilDivExact(n, divisor);
        }

        public static LongUnaryOperator ceilMod(long divisor) {
            return n ->
                    Math.ceilMod(n, divisor);
        }

        public static LongUnaryOperator multiplyHigh(long secondValue) {
            return n ->
                    Math.multiplyHigh(n, secondValue);
        }

        public static LongUnaryOperator unsignedMultiplyHigh(long secondValue) {
            return n ->
                    Math.unsignedMultiplyHigh(n, secondValue);
        }

        public static LongUnaryOperator clamp(long min, long max) {
            return n ->
                    Math.clamp(n, min, max);
        }

    }

    public static final class BigIntegerUnaryOperators {

        private BigIntegerUnaryOperators() {
        }

        public static UnaryOperator<BigInteger> of(Function<BigInteger, BigInteger> function) {
            Objects.requireNonNull(function);
            return function::apply;
        }

        public static UnaryOperator<BigInteger> concat(UnaryOperator<BigInteger> first, UnaryOperator<BigInteger> second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            return (s) -> second.apply(first.apply(s));
        }

        public static UnaryOperator<BigInteger> concat(UnaryOperator<BigInteger> first, UnaryOperator<BigInteger> second, UnaryOperator<BigInteger> third) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            Objects.requireNonNull(third);
            return (s) -> third.apply(second.apply(first.apply(s)));
        }

        public static UnaryOperator<BigInteger> conditionalOperator(Predicate<BigInteger> condition, UnaryOperator<BigInteger> trueOperator, UnaryOperator<BigInteger> falseOperator) {
            Objects.requireNonNull(condition);
            Objects.requireNonNull(trueOperator);
            Objects.requireNonNull(falseOperator);
            return (s) -> condition.test(s) ? trueOperator.apply(s) : falseOperator.apply(s);
        }

        public static UnaryOperator<BigInteger> identity() {
            return n -> n;
        }

        public static UnaryOperator<BigInteger> constant(BigInteger constant) {
            return n -> constant;
        }

        public static UnaryOperator<BigInteger> increment() {
            return n -> n.add(BigInteger.ONE);
        }

        public static UnaryOperator<BigInteger> decrement() {
            return n -> n.subtract(BigInteger.ONE);
        }

        public static UnaryOperator<BigInteger> toZero() {
            return n -> BigInteger.ZERO;
        }

        public static UnaryOperator<@Nullable BigInteger> toNull() {
            return n -> null;
        }

        public static UnaryOperator<@Nullable BigInteger> nullToZero() {
            return n -> (n == null) ? BigInteger.ZERO : n;
        }

        public static UnaryOperator<@Nullable BigInteger> nullToConstant(BigInteger constant) {
            Objects.requireNonNull(constant);
            return n -> (n == null) ? constant : n;
        }

        public static UnaryOperator<BigInteger> abs() {
            return BigInteger::abs;
        }

        public static UnaryOperator<BigInteger> negate() {
            return BigInteger::negate;
        }

        public static UnaryOperator<BigInteger> square() {
            return n -> n.multiply(n);
        }

        public static UnaryOperator<BigInteger> sqrt() {
            return BigInteger::sqrt;
        }

        public static UnaryOperator<BigInteger> digitSum() {
            return n -> BigInteger.valueOf(n.toString().codePoints().filter(Character::isDigit).map(Character::getNumericValue).sum());
        }

        public static UnaryOperator<BigInteger> add(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.add(secondValue);
        }

        public static UnaryOperator<BigInteger> subtract(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.subtract(secondValue);
        }

        public static UnaryOperator<BigInteger> multiply(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.multiply(secondValue);
        }

        public static UnaryOperator<BigInteger> divide(BigInteger divisor) {
            Objects.requireNonNull(divisor);
            return n -> n.divide(divisor);
        }

        public static UnaryOperator<BigInteger> mod(BigInteger modulus) {
            Objects.requireNonNull(modulus);
            return n -> n.mod(modulus);
        }

        public static UnaryOperator<BigInteger> modInverse(BigInteger modulus) {
            Objects.requireNonNull(modulus);
            return n -> n.modInverse(modulus);
        }

        public static UnaryOperator<BigInteger> remainder(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.remainder(secondValue);
        }

        public static UnaryOperator<BigInteger> gcd(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.gcd(secondValue);
        }

        public static UnaryOperator<BigInteger> min(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.min(secondValue);
        }

        public static UnaryOperator<BigInteger> max(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n.max(secondValue);
        }

        public static UnaryOperator<BigInteger> modPow(BigInteger exponent, BigInteger modulus) {
            Objects.requireNonNull(exponent);
            Objects.requireNonNull(modulus);
            return n -> n.modPow(exponent, modulus);
        }

        public static UnaryOperator<BigInteger> pow(int exponent) {
            return n -> n.pow(exponent);
        }

        public static UnaryOperator<BigInteger> shiftLeft(int distance) {
            return n -> n.shiftLeft(distance);
        }

        public static UnaryOperator<BigInteger> shiftRight(int distance) {
            return n -> n.shiftRight(distance);
        }

        public static UnaryOperator<BigInteger> clamp(BigInteger min, BigInteger max) {
            return n -> min.max(max.min(n));
        }

    }

}
