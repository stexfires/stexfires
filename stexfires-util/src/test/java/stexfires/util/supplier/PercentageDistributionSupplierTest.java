package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

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
        PercentageDistributionSupplier<Boolean> randomBooleanSupplier0 = new PercentageDistributionSupplier<>(new Random(5L), 0, Boolean.TRUE, Boolean.FALSE);
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplier0.get(), randomBooleanSupplier0::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier100 = new PercentageDistributionSupplier<>(new Random(5L), 100, Boolean.TRUE, Boolean.FALSE);
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier100.get(), randomBooleanSupplier100::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier200 = new PercentageDistributionSupplier<>(new Random(5L), 200, Boolean.TRUE, Boolean.FALSE);
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier200.get(), randomBooleanSupplier200::toString);
        }

        PercentageDistributionSupplier<Boolean> randomBooleanSupplierNeg = new PercentageDistributionSupplier<>(new Random(5L), -200, Boolean.TRUE, Boolean.FALSE);
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplierNeg.get(), randomBooleanSupplierNeg::toString);
        }
    }

    /**
     * Test method for constructor with different results.
     */
    @Test
    void constructorDifferentResult() {
        PercentageDistributionSupplier<Boolean> randomBooleanSupplier50 = new PercentageDistributionSupplier<>(new Random(5L), 50, Boolean.TRUE, Boolean.FALSE);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);

        PercentageDistributionSupplier<Boolean> randomBooleanSupplier10 = new PercentageDistributionSupplier<>(new Random(5L), 10, Boolean.TRUE, Boolean.FALSE);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
    }

    /**
     * Test method for {@link PercentageDistributionSupplier#asPrimitiveBooleanSupplier(java.util.random.RandomGenerator, int, boolean, boolean)}.
     */
    @Test
    void asPrimitiveBooleanSupplier() {
        BooleanSupplier randomBooleanSupplier0 = PercentageDistributionSupplier.asPrimitiveBooleanSupplier(new Random(5L), 0, true, false);
        for (int i = 0; i < 100; i++) {
            assertFalse(randomBooleanSupplier0.getAsBoolean(), randomBooleanSupplier0::toString);
        }

        BooleanSupplier randomBooleanSupplier100 = PercentageDistributionSupplier.asPrimitiveBooleanSupplier(new Random(5L), 100, true, false);
        for (int i = 0; i < 100; i++) {
            assertTrue(randomBooleanSupplier100.getAsBoolean(), randomBooleanSupplier100::toString);
        }
    }

    /**
     * Test method for {@link PercentageDistributionSupplier#asPrimitiveIntSupplier(java.util.random.RandomGenerator, int, int, int)}.
     */
    @Test
    void asPrimitiveIntSupplier() {
        IntSupplier randomIntSupplier0 = PercentageDistributionSupplier.asPrimitiveIntSupplier(new Random(5L), 0, 1, 0);
        for (int i = 0; i < 100; i++) {
            assertEquals(0, randomIntSupplier0.getAsInt(), randomIntSupplier0::toString);
        }

        IntSupplier randomIntSupplier100 = PercentageDistributionSupplier.asPrimitiveIntSupplier(new Random(5L), 100, 1, 0);
        for (int i = 0; i < 100; i++) {
            assertEquals(1, randomIntSupplier100.getAsInt(), randomIntSupplier100::toString);
        }
    }

    /**
     * Test method for {@link PercentageDistributionSupplier#asPrimitiveLongSupplier(java.util.random.RandomGenerator, int, long, long)}.
     */
    @Test
    void asPrimitiveLongSupplier() {
        LongSupplier randomLongSupplier0 = PercentageDistributionSupplier.asPrimitiveLongSupplier(new Random(5L), 0, 1L, 0L);
        for (long i = 0L; i < 100L; i++) {
            assertEquals(0L, randomLongSupplier0.getAsLong(), randomLongSupplier0::toString);
        }

        LongSupplier randomLongSupplier100 = PercentageDistributionSupplier.asPrimitiveLongSupplier(new Random(5L), 100, 1L, 0L);
        for (long i = 0L; i < 100L; i++) {
            assertEquals(1L, randomLongSupplier100.getAsLong(), randomLongSupplier100::toString);
        }
    }

    /**
     * Test method for {@link PercentageDistributionSupplier#asPrimitiveDoubleSupplier(java.util.random.RandomGenerator, int, double, double)}.
     */
    @Test
    void asPrimitiveDoubleSupplier() {
        DoubleSupplier randomDoubleSupplier0 = PercentageDistributionSupplier.asPrimitiveDoubleSupplier(new Random(5L), 0, 1.0d, 0.0d);
        for (int i = 0; i < 100; i++) {
            assertEquals(0.0d, randomDoubleSupplier0.getAsDouble(), randomDoubleSupplier0::toString);
        }

        DoubleSupplier randomDoubleSupplier100 = PercentageDistributionSupplier.asPrimitiveDoubleSupplier(new Random(5L), 100, 1.0d, 0.0d);
        for (int i = 0; i < 100; i++) {
            assertEquals(1.0d, randomDoubleSupplier100.getAsDouble(), randomDoubleSupplier100::toString);
        }
    }

}