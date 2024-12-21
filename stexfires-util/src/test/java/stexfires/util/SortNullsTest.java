package stexfires.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static stexfires.util.SortNulls.*;

/**
 * Tests for {@link SortNulls}.
 */
final class SortNullsTest {

    /**
     * Test method for {@link SortNulls#name()}.
     */
    @Test
    void name() {
        assertEquals("FIRST", FIRST.name());
        assertEquals("LAST", LAST.name());
    }

    /**
     * Test method for {@link SortNulls#ordinal()}.
     */
    @Test
    void ordinal() {
        assertEquals(0, FIRST.ordinal());
        assertEquals(1, LAST.ordinal());
    }

    /**
     * Test method for {@link SortNulls#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("FIRST", FIRST.toString());
        assertEquals("LAST", LAST.toString());
    }

    /**
     * Test method for {@link SortNulls#wrap(java.util.Comparator)}.
     */
    @SuppressWarnings({"EqualsWithItself", "DataFlowIssue"})
    @Test
    void wrap() {
        assertEquals(0, FIRST.wrap(String::compareTo).compare(null, null));
        assertEquals(0, FIRST.wrap(String::compareTo).compare("a", "a"));
        assertTrue(FIRST.wrap(String::compareTo).compare(null, "a") < 0);
        assertTrue(FIRST.wrap(String::compareTo).compare("a", null) > 0);
        assertTrue(FIRST.wrap(String::compareTo).compare("a", "b") < 0);
        assertThrows(NullPointerException.class, () -> FIRST.wrap(null));

        assertEquals(0, LAST.wrap(String::compareTo).compare(null, null));
        assertEquals(0, LAST.wrap(String::compareTo).compare("a", "a"));
        assertTrue(LAST.wrap(String::compareTo).compare(null, "a") > 0);
        assertTrue(LAST.wrap(String::compareTo).compare("a", null) < 0);
        assertTrue(LAST.wrap(String::compareTo).compare("a", "b") < 0);
        assertThrows(NullPointerException.class, () -> LAST.wrap(null));
    }

    /**
     * Test method for {@link SortNulls#reverse()}.
     */
    @Test
    void reverse() {
        assertEquals(LAST, FIRST.reverse());
        assertEquals(FIRST, LAST.reverse());
    }

}