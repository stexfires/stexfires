package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link stexfires.util.supplier.SequenceSupplier}.
 */
@SuppressWarnings("MagicNumber")
class SequenceSupplierTest {

    /**
     * Test method for {@link stexfires.util.supplier.SequenceSupplier#asString(long)}.
     */
    @Test
    void asString() {
        Supplier<String> supplier0 = SequenceSupplier.asString(0L);
        for (long longValue = 0L; longValue < 100L; longValue++) {
            assertEquals(String.valueOf(longValue), supplier0.get());
        }

        Supplier<String> supplier1000 = SequenceSupplier.asString(1_000L);
        for (long longValue = 1_000L; longValue < 2_000L; longValue++) {
            assertEquals(String.valueOf(longValue), supplier1000.get());
        }

        Supplier<String> supplierNeg = SequenceSupplier.asString(-10L);
        for (long longValue = -10L; longValue < 90L; longValue++) {
            assertEquals(String.valueOf(longValue), supplierNeg.get());
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.SequenceSupplier#asLong(long)}.
     */
    @Test
    void asLong() {
        Supplier<Long> supplier0 = SequenceSupplier.asLong(0L);
        for (long longValue = 0L; longValue < 100L; longValue++) {
            assertEquals(Long.valueOf(longValue), supplier0.get());
        }

        Supplier<Long> supplier1000 = SequenceSupplier.asLong(1_000L);
        for (long longValue = 1_000L; longValue < 2_000L; longValue++) {
            assertEquals(Long.valueOf(longValue), supplier1000.get());
        }

        Supplier<Long> supplierNeg = SequenceSupplier.asLong(-10L);
        for (long longValue = -10L; longValue < 90L; longValue++) {
            assertEquals(Long.valueOf(longValue), supplierNeg.get());
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.SequenceSupplier#asPrimitiveLong(long)}.
     */
    @Test
    void asPrimitiveLong() {
        LongSupplier supplier0 = SequenceSupplier.asPrimitiveLong(0L);
        for (long longValue = 0L; longValue < 100L; longValue++) {
            assertEquals(longValue, supplier0.getAsLong());
        }

        LongSupplier supplier1000 = SequenceSupplier.asPrimitiveLong(1_000L);
        for (long longValue = 1_000L; longValue < 2_000L; longValue++) {
            assertEquals(longValue, supplier1000.getAsLong());
        }

        LongSupplier supplierNeg = SequenceSupplier.asPrimitiveLong(-10L);
        for (long longValue = -10L; longValue < 90L; longValue++) {
            assertEquals(longValue, supplierNeg.getAsLong());
        }
    }

}