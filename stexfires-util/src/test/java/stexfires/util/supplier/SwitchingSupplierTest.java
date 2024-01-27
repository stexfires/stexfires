package stexfires.util.supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link stexfires.util.supplier.SwitchingSupplier}.
 */
@SuppressWarnings("MagicNumber")
class SwitchingSupplierTest {

    /**
     * Test method for constructor.
     */
    @Test
    void constructor() {
        SwitchingSupplier<String> switchingStringSupplier = new SwitchingSupplier<>("A", "B", 100, i -> i == 104 || i == 107);
        for (int i = 100; i < 120; i++) {
            if (i <= 103 || i >= 107) {
                assertEquals("A", switchingStringSupplier.get(), switchingStringSupplier::toString);
            } else {
                assertEquals("B", switchingStringSupplier.get(), switchingStringSupplier::toString);
            }
        }

        SwitchingSupplier<Integer> switchingIntegerSupplier = new SwitchingSupplier<>(-1, 1, index -> index % 5 == 0);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
            }
            for (int j = 0; j < 5; j++) {
                assertEquals(-1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.SwitchingSupplier#atIndex(Object, Object, int)}.
     */
    @Test
    void atIndex() {
        SwitchingSupplier<String> switchingStringSupplier = SwitchingSupplier.atIndex("A", "B", 3);
        for (int i = 0; i < 100; i++) {
            if (i < 3) {
                assertEquals("A", switchingStringSupplier.get(), switchingStringSupplier::toString);
            } else {
                assertEquals("B", switchingStringSupplier.get(), switchingStringSupplier::toString);
            }
        }

        SwitchingSupplier<Integer> switchingIntegerSupplier = SwitchingSupplier.atIndex(-1, 1, 3);
        for (int i = 0; i < 100; i++) {
            if (i < 3) {
                assertEquals(-1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
            } else {
                assertEquals(1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
            }
        }
    }

    /**
     * Test method for {@link stexfires.util.supplier.SwitchingSupplier#everyTime(Object, Object)}.
     */
    @Test
    void everyTime() {
        SwitchingSupplier<String> switchingStringSupplier = SwitchingSupplier.everyTime("A", "B");
        for (int i = 0; i < 100; i++) {
            assertEquals("A", switchingStringSupplier.get(), switchingStringSupplier::toString);
            assertEquals("B", switchingStringSupplier.get(), switchingStringSupplier::toString);
        }

        SwitchingSupplier<Integer> switchingIntegerSupplier = SwitchingSupplier.everyTime(-1, 1);
        for (int i = 0; i < 100; i++) {
            assertEquals(-1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
            assertEquals(1, switchingIntegerSupplier.get(), switchingIntegerSupplier::toString);
        }
    }

}