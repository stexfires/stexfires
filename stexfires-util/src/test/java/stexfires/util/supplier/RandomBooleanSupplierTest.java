package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link stexfires.util.supplier.RandomBooleanSupplier}.
 */
@SuppressWarnings("MagicNumber")
class RandomBooleanSupplierTest {

    /**
     * Test method for constructor with constant results.
     */
    @Test
    void constructorConstantResult() {
        RandomBooleanSupplier randomBooleanSupplier0 = new RandomBooleanSupplier(0, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplier0.get(), randomBooleanSupplier0::toString);
        }

        RandomBooleanSupplier randomBooleanSupplier100 = new RandomBooleanSupplier(100, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier100.get(), randomBooleanSupplier100::toString);
        }

        RandomBooleanSupplier randomBooleanSupplier200 = new RandomBooleanSupplier(200, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, randomBooleanSupplier200.get(), randomBooleanSupplier200::toString);
        }

        RandomBooleanSupplier randomBooleanSupplierNeg = new RandomBooleanSupplier(-5, new Random(5L));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.FALSE, randomBooleanSupplierNeg.get(), randomBooleanSupplierNeg::toString);
        }
    }

    /**
     * Test method for constructor with different results.
     */
    @Test
    void constructorDifferentResult() {
        RandomBooleanSupplier randomBooleanSupplier50 = new RandomBooleanSupplier(50, new Random(5L));
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier50.get(), randomBooleanSupplier50::toString);

        RandomBooleanSupplier randomBooleanSupplier10 = new RandomBooleanSupplier(10, new Random(5L));
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.TRUE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
        assertEquals(Boolean.FALSE, randomBooleanSupplier10.get(), randomBooleanSupplier10::toString);
    }

    /**
     * Test method for {@link stexfires.util.supplier.RandomBooleanSupplier#asPrimitiveBooleanSupplier()}.
     */
    @Test
    void asPrimitiveBooleanSupplier() {
        BooleanSupplier randomBooleanSupplier0 = new RandomBooleanSupplier(0, new Random(5L)).asPrimitiveBooleanSupplier();
        for (int i = 0; i < 100; i++) {
            assertFalse(randomBooleanSupplier0.getAsBoolean(), randomBooleanSupplier0::toString);
        }

        BooleanSupplier randomBooleanSupplier100 = new RandomBooleanSupplier(100, new Random(5L)).asPrimitiveBooleanSupplier();
        for (int i = 0; i < 100; i++) {
            assertTrue(randomBooleanSupplier100.getAsBoolean(), randomBooleanSupplier100::toString);
        }
    }

}