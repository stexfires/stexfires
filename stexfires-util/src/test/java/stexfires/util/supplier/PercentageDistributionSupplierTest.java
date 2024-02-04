package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link stexfires.util.supplier.PercentageDistributionSupplier}.
 */
@SuppressWarnings("MagicNumber")
class PercentageDistributionSupplierTest {

    /**
     * Test method for constructor with constant results.
     */
    @Test
    void constructorConstantResult() {
        PercentageDistributionSupplier<Boolean> randomBooleanSupplier0 = new PercentageDistributionSupplier<>(0, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplier0.get(), randomBooleanSupplier0::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier100 = new PercentageDistributionSupplier<>(100, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier100.get(), randomBooleanSupplier100::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier200 = new PercentageDistributionSupplier<>(200, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier200.get(), randomBooleanSupplier200::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplierNeg = new PercentageDistributionSupplier<>(-200, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplierNeg.get(), randomBooleanSupplierNeg::toString);
        }
    }

    /**
     * Test method for constructor with different results.
     */
    @Test
    void constructorDifferentResult() {
        PercentageDistributionSupplier<Boolean> randomBooleanSupplier50 = new PercentageDistributionSupplier<>(50, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier10 = new PercentageDistributionSupplier<>(10, Boolean.TRUE, Boolean.FALSE, new Random(5L));
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
    }

    /**
     * Test method for {@link stexfires.util.supplier.PercentageDistributionSupplier#asPrimitiveBooleanSupplier(int, boolean, boolean, java.util.random.RandomGenerator)}.
     */
    @Test
    void asPrimitiveBooleanSupplier() {
        BooleanSupplier randomBooleanSupplier0 = PercentageDistributionSupplier.asPrimitiveBooleanSupplier(0, true, false, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertFalse(randomBooleanSupplier0.getAsBoolean(), randomBooleanSupplier0::toString);
        }

        BooleanSupplier randomBooleanSupplier100 = PercentageDistributionSupplier.asPrimitiveBooleanSupplier(100, true, false, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertTrue(randomBooleanSupplier100.getAsBoolean(), randomBooleanSupplier100::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.PercentageDistributionSupplier#asPrimitiveIntSupplier(int, int, int, java.util.random.RandomGenerator)}.
     */
    @Test
    void asPrimitiveIntSupplier() {
        IntSupplier randomIntSupplier0 = PercentageDistributionSupplier.asPrimitiveIntSupplier(0, 1, 0, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(0, randomIntSupplier0.getAsInt(), randomIntSupplier0::toString);
        }

        IntSupplier randomIntSupplier100 = PercentageDistributionSupplier.asPrimitiveIntSupplier(100, 1, 0, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(1, randomIntSupplier100.getAsInt(), randomIntSupplier100::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.PercentageDistributionSupplier#asPrimitiveLongSupplier(int, long, long, java.util.random.RandomGenerator)}.
     */
    @Test
    void asPrimitiveLongSupplier() {
        LongSupplier randomLongSupplier0 = PercentageDistributionSupplier.asPrimitiveLongSupplier(0, 1L, 0L, new Random(5L));
        for (long i = 0; i < 100; i++) {
            assertEquals(0L, randomLongSupplier0.getAsLong(), randomLongSupplier0::toString);
        }

        LongSupplier randomLongSupplier100 = PercentageDistributionSupplier.asPrimitiveLongSupplier(100, 1L, 0L, new Random(5L));
        for (long i = 0; i < 100; i++) {
            assertEquals(1L, randomLongSupplier100.getAsLong(), randomLongSupplier100::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.PercentageDistributionSupplier#asPrimitiveDoubleSupplier(int, double, double, java.util.random.RandomGenerator)}.
     */
    @Test
    void asPrimitiveDoubleSupplier() {
        DoubleSupplier randomDoubleSupplier0 = PercentageDistributionSupplier.asPrimitiveDoubleSupplier(0, 1.0d, 0.0d, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(0.0d, randomDoubleSupplier0.getAsDouble(), randomDoubleSupplier0::toString);
        }

        DoubleSupplier randomDoubleSupplier100 = PercentageDistributionSupplier.asPrimitiveDoubleSupplier(100, 1.0d, 0.0d, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(1.0d, randomDoubleSupplier100.getAsDouble(), randomDoubleSupplier100::toString);
        }
    }

}