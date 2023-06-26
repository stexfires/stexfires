package stexfires.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link BooleanDataTypeFormatter}.
 */
class BooleanDataTypeFormatterTest {

    private static final String STRING_TRUE = "TRUE";
    private static final String STRING_FALSE = "FALSE";
    private static final String STRING_NULL = "NULL";

    private final BooleanDataTypeFormatter formatter = new BooleanDataTypeFormatter(() -> STRING_TRUE, () -> STRING_FALSE, () -> STRING_NULL);

    /**
     * Test method for {@link BooleanDataTypeFormatter#format(Boolean)}.
     */
    @Test
    void format() {
        assertEquals(STRING_TRUE, formatter.format(Boolean.TRUE));
        assertEquals(STRING_FALSE, formatter.format(Boolean.FALSE));
        assertEquals(STRING_NULL, formatter.format(null));
    }

}