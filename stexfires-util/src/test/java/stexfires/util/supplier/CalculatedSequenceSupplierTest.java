package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link stexfires.util.supplier.CalculatedSequenceSupplier}.
 */
@SuppressWarnings("MagicNumber")
class CalculatedSequenceSupplierTest {

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofIntIndexAndValue(Object, java.util.function.BiFunction)}.
     */
    @Test
    void ofIntIndexAndValue() {
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofIntIndexAndValue(0, Integer::sum);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
            assertEquals(sum, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofIntIndexAndValue(Object, int, java.util.function.BiFunction)}.
     */
    @Test
    void ofIntIndexAndValueWithStartIndex() {
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofIntIndexAndValue(10, 10, Integer::sum);
        int sum = 0;
        for (int i = 10; i < 100; i++) {
            sum += i;
            assertEquals(sum, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofLongIndexAndValue(Object, java.util.function.BiFunction)}.
     */
    @Test
    void ofLongIndexAndValue() {
        CalculatedSequenceSupplier<Long> supplier = CalculatedSequenceSupplier.ofLongIndexAndValue(0L, Long::sum);
        long sum = 0;
        for (long i = 0; i < 100; i++) {
            sum += i;
            assertEquals(sum, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofLongIndexAndValue(Object, long, java.util.function.BiFunction)}.
     */
    @Test
    void ofLongIndexAndValueWithStartIndex() {
        CalculatedSequenceSupplier<Long> supplier = CalculatedSequenceSupplier.ofLongIndexAndValue(10L, 10L, Long::sum);
        long sum = 0;
        for (long i = 10; i < 100; i++) {
            sum += i;
            assertEquals(sum, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofIntIndex(Object, java.util.function.IntFunction)}.
     */
    @Test
    void ofIntIndex() {
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofIntIndex(0, i -> i);
        for (int i = 0; i < 100; i++) {
            assertEquals(i, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofIntIndex(Object, int, java.util.function.IntFunction)}.
     */
    @Test
    void ofIntIndexWithStartIndex() {
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofIntIndex(10, 10, i -> i);
        for (int i = 10; i < 100; i++) {
            assertEquals(i, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofLongIndex(Object, java.util.function.LongFunction)}.
     */
    @Test
    void ofLongIndex() {
        CalculatedSequenceSupplier<Long> supplier = CalculatedSequenceSupplier.ofLongIndex(0L, i -> i);
        for (long i = 0; i < 100; i++) {
            assertEquals(i, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofLongIndex(Object, long, java.util.function.LongFunction)}.
     */
    @Test
    void ofLongIndexWithStartIndex() {
        CalculatedSequenceSupplier<Long> supplier = CalculatedSequenceSupplier.ofLongIndex(10L, 10L, i -> i);
        for (long i = 10; i < 100; i++) {
            assertEquals(i, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofValue(Object, java.util.function.UnaryOperator)}.
     */
    @Test
    void ofValue() {
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofValue(2, value -> value * 2);
        int product = 1;
        for (int i = 0; i < 10; i++) {
            product *= 2;
            assertEquals(product, supplier.get(), supplier::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofValues(Object, Object, java.util.function.BinaryOperator)}.
     */
    @Test
    void ofValues() {
        int v0 = 1;
        int v1 = 2;
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofValues(v0, v1, Integer::sum);
        assertEquals(v0, supplier.get(), supplier::toString);
        assertEquals(v1, supplier.get(), supplier::toString);
        for (int i = 0; i < 10; i++) {
            int sum = v0 + v1;
            assertEquals(sum, supplier.get(), supplier::toString);
            v0 = v1;
            v1 = sum;
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.CalculatedSequenceSupplier#ofAlternatingValues(Object, Object)}.
     */
    @Test
    void ofAlternatingValues() {
        int v0 = 1;
        int v1 = 2;
        CalculatedSequenceSupplier<Integer> supplier = CalculatedSequenceSupplier.ofAlternatingValues(v0, v1);
        assertEquals(v0, supplier.get(), supplier::toString);
        assertEquals(v1, supplier.get(), supplier::toString);
        for (int i = 0; i < 10; i++) {
            assertEquals(v0, supplier.get(), supplier::toString);
            assertEquals(v1, supplier.get(), supplier::toString);
        }
    }

}