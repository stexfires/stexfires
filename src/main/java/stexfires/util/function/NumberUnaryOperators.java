package stexfires.util.function;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
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
                    Math.max(min, Math.min(max, n));
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
                    Math.max(min, Math.min(max, n));
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
            return n ->
                    n;
        }

        public static UnaryOperator<BigInteger> constant(BigInteger constant) {
            return n ->
                    constant;
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> increment() {
            return n -> n == null ? n :
                    n.add(BigInteger.ONE);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> decrement() {
            return n -> n == null ? n :
                    n.subtract(BigInteger.ONE);
        }

        public static UnaryOperator<BigInteger> toZero() {
            return n -> BigInteger.ZERO;
        }

        @SuppressWarnings("ReturnOfNull")
        public static UnaryOperator<BigInteger> toNull() {
            return n -> null;
        }

        public static UnaryOperator<BigInteger> nullToZero() {
            return n -> n == null ? BigInteger.ZERO :
                    n;
        }

        public static UnaryOperator<BigInteger> nullToConstant(BigInteger constant) {
            return n -> n == null ? constant :
                    n;
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> abs() {
            return n -> n == null ? n :
                    n.abs();
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> negate() {
            return n -> n == null ? n :
                    n.negate();
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> square() {
            return n -> n == null ? n :
                    n.multiply(n);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> sqrt() {
            return n -> n == null ? n :
                    n.sqrt();
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> digitSum() {
            return n -> n == null ? n :
                    BigInteger.valueOf(n.toString().codePoints().filter(Character::isDigit).map(Character::getNumericValue).sum());
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> add(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.add(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> subtract(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.subtract(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> multiply(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.multiply(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> divide(BigInteger divisor) {
            Objects.requireNonNull(divisor);
            return n -> n == null ? n :
                    n.divide(divisor);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> mod(BigInteger modulus) {
            Objects.requireNonNull(modulus);
            return n -> n == null ? n :
                    n.mod(modulus);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> modInverse(BigInteger modulus) {
            Objects.requireNonNull(modulus);
            return n -> n == null ? n :
                    n.modInverse(modulus);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> remainder(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.remainder(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> gcd(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.gcd(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> min(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.min(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> max(BigInteger secondValue) {
            Objects.requireNonNull(secondValue);
            return n -> n == null ? n :
                    n.max(secondValue);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> modPow(BigInteger exponent, BigInteger modulus) {
            Objects.requireNonNull(exponent);
            Objects.requireNonNull(modulus);
            return n -> n == null ? n :
                    n.modPow(exponent, modulus);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> pow(int exponent) {
            return n -> n == null ? n :
                    n.pow(exponent);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> shiftLeft(int distance) {
            return n -> n == null ? n :
                    n.shiftLeft(distance);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> shiftRight(int distance) {
            return n -> n == null ? n :
                    n.shiftRight(distance);
        }

        @SuppressWarnings("ConstantConditions")
        public static UnaryOperator<BigInteger> clamp(BigInteger min, BigInteger max) {
            return n -> n == null ? n :
                    min.max(max.min(n));
        }

    }

}
