package stexfires.util.function;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @see java.lang.Number
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 * @see java.util.function.LongPredicate
 * @see java.util.function.DoublePredicate
 * @since 0.1
 */
public final class NumberPredicates {

    /*
    NEGATIVE,
    POSITIVE,
    ZERO,
    NOT_ZERO,
    ODD,
    EVEN,
    MAX_VALUE,
    MIN_VALUE;

    EQUAL_TO,
    NOT_EQUAL_TO,
    LESS_THAN,
    LESS_THAN_OR_EQUAL_TO,
    GREATER_THAN_OR_EQUAL_TO,
    GREATER_THAN,
    MULTIPLE_OF,
    SAME_SIGN,
    SAME_ABSOLUTE_VALUE;
     */

    private NumberPredicates() {
    }

    public static final class PrimitiveIntPredicates {

        private PrimitiveIntPredicates() {
        }

        public static IntPredicate zero() {
            return n ->
                    n == 0;
        }

        public static IntPredicate lessThan(int compareNumber) {
            return n ->
                    n < compareNumber;
        }

    }

    public static final class PrimitiveLongPredicates {

        private PrimitiveLongPredicates() {
        }

        public static LongPredicate zero() {
            return n ->
                    n == 0L;
        }

        public static LongPredicate lessThan(long compareNumber) {
            return n ->
                    n < compareNumber;
        }

    }

    public static final class BigIntegerPredicates {

        private BigIntegerPredicates() {
        }

        public static Predicate<BigInteger> zero() {
            return n -> n != null &&
                    n.signum() == 0;
        }

        public static Predicate<BigInteger> lessThan(BigInteger compareNumber) {
            Objects.requireNonNull(compareNumber);
            return n -> n != null &&
                    n.compareTo(compareNumber) < 0;
        }
    }

}
