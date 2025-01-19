package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link stexfires.util.supplier.RepeatingPatternSupplier}.
 */
@SuppressWarnings("MagicNumber")
class RepeatingPatternSupplierTest {

    /**
     * Test method for constructor.
     */
    @Test
    void constructor() {
        RepeatingPatternSupplier<Integer> supplierInteger = new RepeatingPatternSupplier<>(List.of(2, 3, 5, 7, 11));
        for (int i = 0; i < 100; i++) {
            assertEquals(Integer.valueOf(2), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(3), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(5), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(7), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(11), supplierInteger.get(), supplierInteger::toString);
        }

        RepeatingPatternSupplier<Boolean> supplierBoolean = new RepeatingPatternSupplier<>(List.of(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, supplierBoolean.get(), supplierBoolean::toString);
            assertEquals(Boolean.FALSE, supplierBoolean.get(), supplierBoolean::toString);
            assertEquals(Boolean.FALSE, supplierBoolean.get(), supplierBoolean::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.RepeatingPatternSupplier#ofPrimitiveBoolean(boolean...)}.
     */
    @Test
    void ofPrimitiveBoolean() {
        RepeatingPatternSupplier<Boolean> supplierBoolean = RepeatingPatternSupplier.ofPrimitiveBoolean(true, false, false);
        for (int i = 0; i < 100; i++) {
            assertEquals(Boolean.TRUE, supplierBoolean.get(), supplierBoolean::toString);
            assertEquals(Boolean.FALSE, supplierBoolean.get(), supplierBoolean::toString);
            assertEquals(Boolean.FALSE, supplierBoolean.get(), supplierBoolean::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.RepeatingPatternSupplier#ofPrimitiveInt(int...)}.
     */
    @Test
    void ofPrimitiveInt() {
        RepeatingPatternSupplier<Integer> supplierInteger = RepeatingPatternSupplier.ofPrimitiveInt(2, 3, 5, 7, 11);
        for (int i = 0; i < 100; i++) {
            assertEquals(Integer.valueOf(2), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(3), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(5), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(7), supplierInteger.get(), supplierInteger::toString);
            assertEquals(Integer.valueOf(11), supplierInteger.get(), supplierInteger::toString);
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.RepeatingPatternSupplier#ofPrimitiveLong(long...)}.
     */
    @Test
    void ofPrimitiveLong() {
        RepeatingPatternSupplier<Long> supplierLong = RepeatingPatternSupplier.ofPrimitiveLong(2L, 3L, 5L, 7L, 11L);
        for (int i = 0; i < 100; i++) {
            assertEquals(Long.valueOf(2L), supplierLong.get(), supplierLong::toString);
            assertEquals(Long.valueOf(3L), supplierLong.get(), supplierLong::toString);
            assertEquals(Long.valueOf(5L), supplierLong.get(), supplierLong::toString);
            assertEquals(Long.valueOf(7L), supplierLong.get(), supplierLong::toString);
            assertEquals(Long.valueOf(11L), supplierLong.get(), supplierLong::toString);
        }
    }

}