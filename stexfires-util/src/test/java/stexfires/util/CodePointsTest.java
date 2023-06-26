package stexfires.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link CodePoints}.
 */
@SuppressWarnings("MagicNumber")
class CodePointsTest {

    /**
     * Test method for {@link CodePoints#typeAsString(int, String)}.
     */
    @Test
    void typeAsString() {
        assertEquals("UNASSIGNED", CodePoints.typeAsString(-1, ""));
        assertEquals("UNASSIGNED", CodePoints.typeAsString(Integer.MIN_VALUE, ""));
        assertEquals("UNASSIGNED", CodePoints.typeAsString(Integer.MAX_VALUE, ""));

        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertEquals("CONTROL", CodePoints.typeAsString(codePoint, ""));
        }
        assertEquals("SPACE_SEPARATOR", CodePoints.typeAsString(32, ""));
        assertEquals("OTHER_PUNCTUATION", CodePoints.typeAsString(33, ""));
        assertEquals("CURRENCY_SYMBOL", CodePoints.typeAsString(36, ""));
        assertEquals("END_PUNCTUATION", CodePoints.typeAsString(41, ""));
        assertEquals("MATH_SYMBOL", CodePoints.typeAsString(43, ""));
        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals("DECIMAL_DIGIT_NUMBER", CodePoints.typeAsString(codePoint, ""));
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("UPPERCASE_LETTER", CodePoints.typeAsString(codePoint, ""));
        }
        assertEquals("START_PUNCTUATION", CodePoints.typeAsString(91, ""));
        assertEquals("MODIFIER_SYMBOL", CodePoints.typeAsString(94, ""));
        assertEquals("CONNECTOR_PUNCTUATION", CodePoints.typeAsString(95, ""));
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("LOWERCASE_LETTER", CodePoints.typeAsString(codePoint, ""));
        }
    }

    /**
     * Test method for {@link CodePoints#directionalityAsString(int, String)}.
     */
    @Test
    void directionalityAsString() {
        assertEquals("DIRECTIONALITY_UNDEFINED", CodePoints.directionalityAsString(-1, ""));
        assertEquals("DIRECTIONALITY_UNDEFINED", CodePoints.directionalityAsString(Integer.MIN_VALUE, ""));
        assertEquals("DIRECTIONALITY_UNDEFINED", CodePoints.directionalityAsString(Integer.MAX_VALUE, ""));

        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", CodePoints.directionalityAsString(0, ""));
        assertEquals("DIRECTIONALITY_SEGMENT_SEPARATOR", CodePoints.directionalityAsString(9, ""));
        assertEquals("DIRECTIONALITY_PARAGRAPH_SEPARATOR", CodePoints.directionalityAsString(10, ""));
        assertEquals("DIRECTIONALITY_WHITESPACE", CodePoints.directionalityAsString(12, ""));
        assertEquals("DIRECTIONALITY_OTHER_NEUTRALS", CodePoints.directionalityAsString(33, ""));
        assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR", CodePoints.directionalityAsString(35, ""));
        assertEquals("DIRECTIONALITY_COMMON_NUMBER_SEPARATOR", CodePoints.directionalityAsString(44, ""));
        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER", CodePoints.directionalityAsString(codePoint, ""));
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", CodePoints.directionalityAsString(codePoint, ""));
        }
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", CodePoints.directionalityAsString(codePoint, ""));
        }
    }

    /**
     * Test method for {@link CodePoints#unicodeBlockAsString(int, String)}.
     */
    @Test
    void unicodeBlockAsString() {
        assertEquals("", CodePoints.unicodeBlockAsString(-1, ""));
        assertEquals("", CodePoints.unicodeBlockAsString(Integer.MIN_VALUE, ""));
        assertEquals("", CodePoints.unicodeBlockAsString(Integer.MAX_VALUE, ""));

        for (int codePoint = 0; codePoint < 128; codePoint++) {
            assertEquals("BASIC_LATIN", CodePoints.unicodeBlockAsString(codePoint, ""));
        }
        for (int codePoint = 128; codePoint < 256; codePoint++) {
            assertEquals("LATIN_1_SUPPLEMENT", CodePoints.unicodeBlockAsString(codePoint, ""));
        }
    }

    /**
     * Test method for {@link CodePoints#toPrintableString(int, String)}.
     */
    @Test
    void toPrintableString() {
        assertEquals("", CodePoints.toPrintableString(-1, ""));
        assertEquals("", CodePoints.toPrintableString(Integer.MIN_VALUE, ""));
        assertEquals("", CodePoints.toPrintableString(Integer.MAX_VALUE, ""));

        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertEquals("", CodePoints.toPrintableString(codePoint, ""));
        }
        assertEquals(" ", CodePoints.toPrintableString(32, ""));
        assertEquals("A", CodePoints.toPrintableString(65, ""));
        assertEquals("", CodePoints.toPrintableString(888, ""));
        assertEquals("", CodePoints.toPrintableString(42975, ""));
        assertEquals("", CodePoints.toPrintableString(55317, ""));
    }

}