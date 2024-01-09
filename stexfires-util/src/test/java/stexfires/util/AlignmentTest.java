package stexfires.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static stexfires.util.Alignment.CENTER;
import static stexfires.util.Alignment.END;
import static stexfires.util.Alignment.START;

/**
 * Tests for {@link Alignment}.
 */
final class AlignmentTest {

    /**
     * Test method for {@link Alignment#name()}.
     */
    @Test
    void name() {
        assertEquals("START", START.name());
        assertEquals("CENTER", CENTER.name());
        assertEquals("END", END.name());

    }

    /**
     * Test method for {@link Alignment#ordinal()}.
     */
    @Test
    void ordinal() {
        assertEquals(0, START.ordinal());
        assertEquals(1, CENTER.ordinal());
        assertEquals(2, END.ordinal());
    }

    /**
     * Test method for {@link Alignment#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("START", START.toString());
        assertEquals("CENTER", CENTER.toString());
        assertEquals("END", END.toString());
    }

    /**
     * Test method for {@link Alignment#shiftToStart()}.
     */
    @Test
    void shiftToStart() {
        assertEquals(START, START.shiftToStart());
        assertEquals(START, CENTER.shiftToStart());
        assertEquals(CENTER, END.shiftToStart());
    }

    /**
     * Test method for {@link Alignment#shiftToEnd()}.
     */
    @Test
    void shiftToEnd() {
        assertEquals(CENTER, START.shiftToEnd());
        assertEquals(END, CENTER.shiftToEnd());
        assertEquals(END, END.shiftToEnd());
    }

    /**
     * Test method for {@link Alignment#mirror()}.
     */
    @Test
    void mirror() {
        assertEquals(END, START.mirror());
        assertEquals(CENTER, CENTER.mirror());
        assertEquals(START, END.mirror());
    }

}