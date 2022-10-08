package stexfires.util.function;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BooleanSupplier;
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
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 * @see java.util.function.LongPredicate
 * @since 0.1
 */
public final class NumberPredicates {

    private NumberPredicates() {
    }

    public static final class PrimitiveIntPredicates {

        private PrimitiveIntPredicates() {
        }

        public static IntPredicate applyOperatorAndTest(IntUnaryOperator operator,
                                                        IntPredicate predicate) {
            Objects.requireNonNull(operator);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(operator.applyAsInt(n));
        }

        public static <T> IntPredicate applyFunctionAndTest(IntFunction<T> function,
                                                            Predicate<? super T> predicate) {
            Objects.requireNonNull(function);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(function.apply(n));
        }

        public static IntPredicate concatAnd(IntPredicate firstPredicate,
                                             IntPredicate secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) && secondPredicate.test(n);
        }

        public static IntPredicate concatOr(IntPredicate firstPredicate,
                                            IntPredicate secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) || secondPredicate.test(n);
        }

        public static IntPredicate constantTrue() {
            return n -> true;
        }

        public static IntPredicate constantFalse() {
            return n -> false;
        }

        public static IntPredicate constant(boolean constant) {
            return n -> constant;
        }

        public static IntPredicate supplier(BooleanSupplier booleanSupplier) {
            Objects.requireNonNull(booleanSupplier);
            return s -> booleanSupplier.getAsBoolean();
        }

        public static IntPredicate negative() {
            return n ->
                    n < 0;
        }

        public static IntPredicate positive() {
            return n ->
                    n > 0;
        }

        public static IntPredicate zero() {
            return n ->
                    n == 0;
        }

        public static IntPredicate notZero() {
            return n ->
                    n != 0;
        }

        public static IntPredicate odd() {
            return n ->
                    (n & 1) == 1;
        }

        public static IntPredicate even() {
            return n ->
                    (n & 1) == 0;
        }

        public static IntPredicate maxValue() {
            return n ->
                    n == Integer.MAX_VALUE;
        }

        public static IntPredicate minValue() {
            return n ->
                    n == Integer.MIN_VALUE;
        }

        public static IntPredicate equalTo(int compareNumber) {
            return n ->
                    n == compareNumber;
        }

        public static IntPredicate notEqualTo(int compareNumber) {
            return n ->
                    n != compareNumber;
        }

        public static IntPredicate lessThan(int compareNumber) {
            return n ->
                    n < compareNumber;
        }

        public static IntPredicate lessThanOrEqualTo(int compareNumber) {
            return n ->
                    n <= compareNumber;
        }

        public static IntPredicate greaterThanOrEqualTo(int compareNumber) {
            return n ->
                    n >= compareNumber;
        }

        public static IntPredicate greaterThan(int compareNumber) {
            return n ->
                    n > compareNumber;
        }

        public static IntPredicate multipleOf(int compareNumber) {
            return n ->
                    (compareNumber != 0) && (n % compareNumber == 0);
        }

        public static IntPredicate sameSign(int compareNumber) {
            return n ->
                    (n > 0 && compareNumber > 0) || (n == 0 && compareNumber == 0) || (n < 0 && compareNumber < 0);
        }

        public static IntPredicate sameAbsoluteValue(int compareNumber) {
            return n ->
                    Math.abs(n) == Math.abs(compareNumber);
        }

        public static IntPredicate between(int lowNumber, int highNumber) {
            return n ->
                    n >= lowNumber && n <= highNumber;
        }

        public static IntPredicate containedIn(Collection<Integer> numbers) {
            Objects.requireNonNull(numbers);
            return numbers::contains;
        }

    }

    public static final class PrimitiveLongPredicates {

        private PrimitiveLongPredicates() {
        }

        public static LongPredicate applyOperatorAndTest(LongUnaryOperator operator,
                                                         LongPredicate predicate) {
            Objects.requireNonNull(operator);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(operator.applyAsLong(n));
        }

        public static <T> LongPredicate applyFunctionAndTest(LongFunction<T> function,
                                                             Predicate<? super T> predicate) {
            Objects.requireNonNull(function);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(function.apply(n));
        }

        public static LongPredicate concatAnd(LongPredicate firstPredicate,
                                              LongPredicate secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) && secondPredicate.test(n);
        }

        public static LongPredicate concatOr(LongPredicate firstPredicate,
                                             LongPredicate secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) || secondPredicate.test(n);
        }

        public static LongPredicate constantTrue() {
            return n -> true;
        }

        public static LongPredicate constantFalse() {
            return n -> false;
        }

        public static LongPredicate constant(boolean constant) {
            return n -> constant;
        }

        public static LongPredicate supplier(BooleanSupplier booleanSupplier) {
            Objects.requireNonNull(booleanSupplier);
            return s -> booleanSupplier.getAsBoolean();
        }

        public static LongPredicate negative() {
            return n ->
                    n < 0L;
        }

        public static LongPredicate positive() {
            return n ->
                    n > 0L;
        }

        public static LongPredicate zero() {
            return n ->
                    n == 0L;
        }

        public static LongPredicate notZero() {
            return n ->
                    n != 0L;
        }

        public static LongPredicate odd() {
            return n ->
                    (n & 1L) == 1L;
        }

        public static LongPredicate even() {
            return n ->
                    (n & 1L) == 0L;
        }

        public static LongPredicate maxValue() {
            return n ->
                    n == Long.MAX_VALUE;
        }

        public static LongPredicate minValue() {
            return n ->
                    n == Long.MIN_VALUE;
        }

        public static LongPredicate equalTo(long compareNumber) {
            return n ->
                    n == compareNumber;
        }

        public static LongPredicate notEqualTo(long compareNumber) {
            return n ->
                    n != compareNumber;
        }

        public static LongPredicate lessThan(long compareNumber) {
            return n ->
                    n < compareNumber;
        }

        public static LongPredicate lessThanOrEqualTo(long compareNumber) {
            return n ->
                    n <= compareNumber;
        }

        public static LongPredicate greaterThanOrEqualTo(long compareNumber) {
            return n ->
                    n >= compareNumber;
        }

        public static LongPredicate greaterThan(long compareNumber) {
            return n ->
                    n > compareNumber;
        }

        public static LongPredicate multipleOf(long compareNumber) {
            return n ->
                    (compareNumber != 0L) && (n % compareNumber == 0L);
        }

        public static LongPredicate sameSign(long compareNumber) {
            return n ->
                    (n > 0L && compareNumber > 0L) || (n == 0L && compareNumber == 0L) || (n < 0L && compareNumber < 0L);
        }

        public static LongPredicate sameAbsoluteValue(long compareNumber) {
            return n ->
                    Math.abs(n) == Math.abs(compareNumber);
        }

        public static LongPredicate between(long lowNumber, long highNumber) {
            return n ->
                    n >= lowNumber && n <= highNumber;
        }

        public static LongPredicate containedIn(Collection<Long> numbers) {
            Objects.requireNonNull(numbers);
            return numbers::contains;
        }

    }

    public static final class BigIntegerPredicates {

        private BigIntegerPredicates() {
        }

        public static Predicate<BigInteger> applyOperatorAndTest(UnaryOperator<BigInteger> operator,
                                                                 Predicate<BigInteger> predicate) {
            Objects.requireNonNull(operator);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(operator.apply(n));
        }

        public static <T> Predicate<BigInteger> applyFunctionAndTest(Function<BigInteger, T> function,
                                                                     Predicate<? super T> predicate) {
            Objects.requireNonNull(function);
            Objects.requireNonNull(predicate);
            return n -> predicate.test(function.apply(n));
        }

        public static Predicate<BigInteger> concatAnd(Predicate<BigInteger> firstPredicate,
                                                      Predicate<BigInteger> secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) && secondPredicate.test(n);
        }

        public static Predicate<BigInteger> concatOr(Predicate<BigInteger> firstPredicate,
                                                     Predicate<BigInteger> secondPredicate) {
            Objects.requireNonNull(firstPredicate);
            Objects.requireNonNull(secondPredicate);
            return n -> firstPredicate.test(n) || secondPredicate.test(n);
        }

        public static Predicate<BigInteger> isNullOr(Predicate<BigInteger> predicate) {
            Objects.requireNonNull(predicate);
            return n -> n == null || predicate.test(n);
        }

        public static Predicate<BigInteger> isNotNullAnd(Predicate<BigInteger> predicate) {
            Objects.requireNonNull(predicate);
            return n -> n != null && predicate.test(n);
        }

        @SuppressWarnings("Convert2MethodRef")
        public static Predicate<BigInteger> isNull() {
            return n -> n == null;
        }

        @SuppressWarnings("Convert2MethodRef")
        public static Predicate<BigInteger> isNotNull() {
            return n -> n != null;
        }

        public static Predicate<String> constantTrue() {
            return n -> true;
        }

        public static Predicate<String> constantFalse() {
            return n -> false;
        }

        public static Predicate<BigInteger> constant(boolean constant) {
            return n -> constant;
        }

        public static Predicate<BigInteger> supplier(BooleanSupplier booleanSupplier) {
            Objects.requireNonNull(booleanSupplier);
            return s -> booleanSupplier.getAsBoolean();
        }

        public static Predicate<BigInteger> negative() {
            return n -> n != null &&
                    n.signum() == -1;
        }

        public static Predicate<BigInteger> positive() {
            return n -> n != null &&
                    n.signum() == 1;
        }

        public static Predicate<BigInteger> zero() {
            return n -> n != null &&
                    n.signum() == 0;
        }

        public static Predicate<BigInteger> notZero() {
            return n -> n != null &&
                    n.signum() != 0;
        }

        public static Predicate<BigInteger> odd() {
            return n -> n != null &&
                    n.and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0;
        }

        public static Predicate<BigInteger> even() {
            return n -> n != null &&
                    n.and(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0;
        }

        public static Predicate<BigInteger> equalTo(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) == 0;
        }

        public static Predicate<BigInteger> notEqualTo(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) != 0;
        }

        public static Predicate<BigInteger> lessThan(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) < 0;
        }

        public static Predicate<BigInteger> lessThanOrEqualTo(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) <= 0;
        }

        public static Predicate<BigInteger> greaterThanOrEqualTo(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) >= 0;
        }

        public static Predicate<BigInteger> greaterThan(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) > 0;
        }

        public static Predicate<BigInteger> multipleOf(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    (compareNumber.signum() != 0) && (n.remainder(compareNumber).compareTo(BigInteger.ZERO) == 0);
        }

        public static Predicate<BigInteger> sameSign(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.signum() == compareNumber.signum();
        }

        public static Predicate<BigInteger> sameAbsoluteValue(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.abs().compareTo(compareNumber.abs()) == 0;
        }

        public static Predicate<BigInteger> between(BigInteger lowNumber, BigInteger highNumber) {
            Objects.requireNonNull(lowNumber);
            Objects.requireNonNull(highNumber);
            return n -> n != null &&
                    n.compareTo(lowNumber) >= 0 && n.compareTo(highNumber) <= 0;
        }

        public static Predicate<BigInteger> containedIn(Collection<BigInteger> numbers) {
            Objects.requireNonNull(numbers);
            return s -> s != null && numbers.contains(s);
        }

    }

}
