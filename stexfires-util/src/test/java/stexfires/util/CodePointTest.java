package stexfires.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link CodePoint}.
 */
@SuppressWarnings("MagicNumber")
class CodePointTest {

    static final int FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK = 12256;

    /**
     * Test method for constants.
     */
    @Test
    void constants() {
        assertEquals(0, CodePoint.MIN_ASCII_VALUE);
        assertEquals(0x00, CodePoint.MIN_ASCII_VALUE);
        assertEquals(127, CodePoint.MAX_ASCII_VALUE);
        assertEquals(0x7F, CodePoint.MAX_ASCII_VALUE);

        assertEquals(Character.MIN_CODE_POINT, CodePoint.MIN_VALUE);
        assertEquals(0, CodePoint.MIN_VALUE);
        assertEquals(0x00, CodePoint.MIN_VALUE);

        assertEquals(Character.MAX_CODE_POINT, CodePoint.MAX_VALUE);
        assertEquals(1114111, CodePoint.MAX_VALUE);
        assertEquals(0x10FFFF, CodePoint.MAX_VALUE);
    }

    /**
     * Test method for {@link CodePoint#CodePoint(int)}.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    @Test
    void constructor() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(codePoint, new CodePoint(codePoint).value());
        }
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(CodePoint.MIN_VALUE - 1));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(CodePoint.MAX_VALUE + 1));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(Integer.MAX_VALUE));
    }

    /**
     * Test method for {@link CodePoint#ofName(String)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void ofName() {
        assertThrows(NullPointerException.class, () -> CodePoint.ofName(null));
        assertThrows(IllegalArgumentException.class, () -> CodePoint.ofName(""));
        assertThrows(IllegalArgumentException.class, () -> CodePoint.ofName("test"));
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            if (Character.isDefined(codePoint)) {
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint)));
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint).toUpperCase()));
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint).toLowerCase()));
            }
        }
    }

    /**
     * Test method for {@link CodePoint#ofChar(char)}.
     */
    @Test
    void ofChar() {
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            assertEquals(ch, CodePoint.ofChar(ch).value());
        }
        assertEquals(Character.MAX_VALUE, CodePoint.ofChar(Character.MAX_VALUE).value());
    }

    /**
     * Test method for {@link CodePoint#name()}.
     */
    @Test
    void name() {
        assertEquals("NULL", new CodePoint(CodePoint.MIN_ASCII_VALUE).name());
        assertEquals("DELETE", new CodePoint(CodePoint.MAX_ASCII_VALUE).name());
        assertEquals("NULL", new CodePoint(CodePoint.MIN_VALUE).name());
        assertNull(new CodePoint(CodePoint.MAX_VALUE).name());

        assertEquals("SPACE", new CodePoint(32).name());
        assertEquals("EXCLAMATION MARK", new CodePoint(33).name());
        assertEquals("DOLLAR SIGN", new CodePoint(36).name());
        assertEquals("RIGHT PARENTHESIS", new CodePoint(41).name());
        assertEquals("PLUS SIGN", new CodePoint(43).name());

        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("LATIN CAPITAL LETTER " + (char) codePoint, new CodePoint(codePoint).name());
        }
        assertEquals("LEFT SQUARE BRACKET", new CodePoint(91).name());
        assertEquals("CIRCUMFLEX ACCENT", new CodePoint(94).name());
        assertEquals("LOW LINE", new CodePoint(95).name());
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("LATIN SMALL LETTER " + (char) (codePoint - 97 + 65), new CodePoint(codePoint).name());
        }
        assertNull(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).name());
    }

    /**
     * Test method for {@link CodePoint#string()}.
     */
    @SuppressWarnings("HardcodedLineSeparator")
    @Test
    void string() {
        assertEquals("\t", new CodePoint(9).string());
        assertEquals("\n", new CodePoint(10).string());
        assertEquals("\r", new CodePoint(13).string());
        assertEquals(" ", new CodePoint(32).string());
        assertEquals("!", new CodePoint(33).string());
        assertEquals("A", new CodePoint(65).string());
        assertEquals("0", new CodePoint(48).string());
        assertEquals("a", new CodePoint(97).string());
        assertEquals("€", new CodePoint(8364).string());
        assertEquals("𐀀", new CodePoint(65536).string());
        assertEquals("🀀", new CodePoint(126976).string());
        assertEquals("🏿", new CodePoint(127999).string());

        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            String characterString = new CodePoint(codePoint).string();
            assertNotNull(characterString);
            assertEquals(Character.charCount(codePoint), characterString.length());
            assertEquals(Character.toString(codePoint), characterString);
        }
    }

    /**
     * Test method for {@link CodePoint#printableString(String)}.
     */
    @Test
    void printableString() {
        assertEquals(" ", new CodePoint(32).printableString("not printable"));
        assertEquals("!", new CodePoint(33).printableString("not printable"));
        assertEquals("A", new CodePoint(65).printableString("not printable"));
        assertEquals("0", new CodePoint(48).printableString("not printable"));
        assertEquals("a", new CodePoint(97).printableString("not printable"));
        assertEquals("€", new CodePoint(8364).printableString("not printable"));
        assertEquals("𐀀", new CodePoint(65536).printableString("not printable"));
        assertEquals("🀀", new CodePoint(126976).printableString("not printable"));
        assertEquals("🏿", new CodePoint(127999).printableString("not printable"));

        assertEquals("not printable", new CodePoint(CodePoint.MIN_ASCII_VALUE).printableString("not printable"));
        assertEquals("not printable", new CodePoint(CodePoint.MAX_ASCII_VALUE).printableString("not printable"));
        assertEquals("not printable", new CodePoint(CodePoint.MIN_VALUE).printableString("not printable"));
        assertEquals("not printable", new CodePoint(CodePoint.MAX_VALUE).printableString("not printable"));
        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertEquals("not printable", new CodePoint(codePoint).printableString("not printable"));
        }
        assertEquals("not printable", new CodePoint(888).printableString("not printable"));
        assertEquals("not printable", new CodePoint(42975).printableString("not printable"));
        assertEquals("not printable", new CodePoint(55317).printableString("not printable"));
        assertEquals("not printable", new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).printableString("not printable"));

        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            String printableString = new CodePoint(codePoint).printableString("not printable");
            if (!"not printable".equals(printableString)) {
                assertNotNull(printableString);
                assertEquals(Character.charCount(codePoint), printableString.length());
                assertEquals(Character.toString(codePoint), printableString);
            }
        }
    }

    /**
     * Test method for {@link CodePoint#hexString()}.
     */
    @Test
    void hexString() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MIN_VALUE; codePoint++) {
            assertEquals(Integer.toHexString(codePoint), new CodePoint(codePoint).hexString());
        }

        assertEquals("0", new CodePoint(CodePoint.MIN_ASCII_VALUE).hexString());
        assertEquals("7f", new CodePoint(CodePoint.MAX_ASCII_VALUE).hexString());
        assertEquals("0", new CodePoint(CodePoint.MIN_VALUE).hexString());
        assertEquals("10ffff", new CodePoint(CodePoint.MAX_VALUE).hexString());

        assertEquals("20", new CodePoint(32).hexString());
        assertEquals("21", new CodePoint(33).hexString());
        assertEquals("24", new CodePoint(36).hexString());
        assertEquals("29", new CodePoint(41).hexString());
        assertEquals("2b", new CodePoint(43).hexString());
    }

    /**
     * Test method for {@link CodePoint#isASCII()}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void isASCII() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            if (codePoint >= CodePoint.MIN_ASCII_VALUE && codePoint <= CodePoint.MAX_ASCII_VALUE) {
                assertTrue(new CodePoint(codePoint).isASCII());
            } else {
                assertFalse(new CodePoint(codePoint).isASCII());
            }
        }
    }

    /**
     * Test method for {@link CodePoint#charCount()}.
     */
    @Test
    void charCount() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.charCount(codePoint), new CodePoint(codePoint).charCount());
        }
        for (int codePoint = CodePoint.MIN_ASCII_VALUE; codePoint <= CodePoint.MAX_ASCII_VALUE; codePoint++) {
            assertEquals(1, new CodePoint(codePoint).charCount());
        }
        assertEquals(2, new CodePoint(129501).charCount());
    }

    /**
     * Test method for {@link CodePoint#decimalDigit()}.
     */
    @Test
    void decimalDigit() {
        // ASCII DIGITs
        assertEquals(0, new CodePoint('0').decimalDigit().orElse(-1));
        assertEquals(1, new CodePoint('1').decimalDigit().orElse(-1));
        assertEquals(2, new CodePoint('2').decimalDigit().orElse(-1));
        assertEquals(3, new CodePoint('3').decimalDigit().orElse(-1));
        assertEquals(4, new CodePoint('4').decimalDigit().orElse(-1));
        assertEquals(5, new CodePoint('5').decimalDigit().orElse(-1));
        assertEquals(6, new CodePoint('6').decimalDigit().orElse(-1));
        assertEquals(7, new CodePoint('7').decimalDigit().orElse(-1));
        assertEquals(8, new CodePoint('8').decimalDigit().orElse(-1));
        assertEquals(9, new CodePoint('9').decimalDigit().orElse(-1));

        //ARABIC-INDIC DIGITs
        assertEquals(0, new CodePoint(1632).decimalDigit().orElse(-1));
        assertEquals(1, new CodePoint(1633).decimalDigit().orElse(-1));
        assertEquals(2, new CodePoint(1634).decimalDigit().orElse(-1));
        assertEquals(3, new CodePoint(1635).decimalDigit().orElse(-1));
        assertEquals(4, new CodePoint(1636).decimalDigit().orElse(-1));
        assertEquals(5, new CodePoint(1637).decimalDigit().orElse(-1));
        assertEquals(6, new CodePoint(1638).decimalDigit().orElse(-1));
        assertEquals(7, new CodePoint(1639).decimalDigit().orElse(-1));
        assertEquals(8, new CodePoint(1640).decimalDigit().orElse(-1));
        assertEquals(9, new CodePoint(1641).decimalDigit().orElse(-1));

        for (int codePoint = CodePoint.MIN_VALUE; codePoint < 48; codePoint++) {
            assertFalse(new CodePoint(codePoint).decimalDigit().isPresent());
        }
        for (int codePoint = 58; codePoint < 1632; codePoint++) {
            assertFalse(new CodePoint(codePoint).decimalDigit().isPresent());
        }
    }

    /**
     * Test method for {@link CodePoint#numericValue()}.
     */
    @SuppressWarnings("CharUsedInArithmeticContext")
    @Test
    void numericValue() {
        assertEquals(0, new CodePoint('0').numericValue().orElse(-1));
        assertEquals(1, new CodePoint('1').numericValue().orElse(-1));
        assertEquals(2, new CodePoint('2').numericValue().orElse(-1));
        assertEquals(3, new CodePoint('3').numericValue().orElse(-1));
        assertEquals(4, new CodePoint('4').numericValue().orElse(-1));
        assertEquals(5, new CodePoint('5').numericValue().orElse(-1));
        assertEquals(6, new CodePoint('6').numericValue().orElse(-1));
        assertEquals(7, new CodePoint('7').numericValue().orElse(-1));
        assertEquals(8, new CodePoint('8').numericValue().orElse(-1));
        assertEquals(9, new CodePoint('9').numericValue().orElse(-1));

        for (int codePoint = CodePoint.MIN_VALUE; codePoint < 48; codePoint++) {
            assertFalse(new CodePoint(codePoint).numericValue().isPresent());
        }
        for (int codePoint = 'A'; codePoint <= 'Z'; codePoint++) {
            assertEquals(codePoint - 'A' + 10, new CodePoint(codePoint).numericValue().orElse(-1));
        }
        for (int codePoint = 'a'; codePoint <= 'z'; codePoint++) {
            assertEquals(codePoint - 'a' + 10, new CodePoint(codePoint).numericValue().orElse(-1));
        }
        for (int codePoint = 'z' + 1; codePoint <= CodePoint.MAX_ASCII_VALUE; codePoint++) {
            assertFalse(new CodePoint(codePoint).numericValue().isPresent());
        }

        // ROMAN NUMERAL
        assertEquals(2, new CodePoint(8545).numericValue().orElse(-1));
        assertEquals(3, new CodePoint(8562).numericValue().orElse(-1));
        assertEquals(50, new CodePoint(8556).numericValue().orElse(-1));

        // Large Numbers
        assertEquals(1000000, new CodePoint(93022).numericValue().orElse(-1));
        assertEquals(10000000, new CodePoint(126113).numericValue().orElse(-1));
        assertEquals(20000000, new CodePoint(126114).numericValue().orElse(-1));
        assertEquals(100000000, new CodePoint(93023).numericValue().orElse(-1));
    }

    /**
     * Test method for {@link CodePoint#type()}.
     */
    @Test
    void type() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.getType(codePoint), new CodePoint(codePoint).type().intConstant());
        }
    }

    /**
     * Test method for {@link CodePoint#typeAsInt()}.
     */
    @Test
    void typeAsInt() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.getType(codePoint), new CodePoint(codePoint).typeAsInt());
        }
    }

    /**
     * Test method for {@link CodePoint#typeAsString()}.
     */
    @Test
    void typeAsString() {
        assertEquals("CONTROL", new CodePoint(CodePoint.MIN_ASCII_VALUE).typeAsString());
        assertEquals("CONTROL", new CodePoint(CodePoint.MAX_ASCII_VALUE).typeAsString());
        assertEquals("CONTROL", new CodePoint(CodePoint.MIN_VALUE).typeAsString());
        assertEquals("UNASSIGNED", new CodePoint(CodePoint.MAX_VALUE).typeAsString());

        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertEquals("CONTROL", new CodePoint(codePoint).typeAsString());
        }
        assertEquals("SPACE_SEPARATOR", new CodePoint(32).typeAsString());
        assertEquals("OTHER_PUNCTUATION", new CodePoint(33).typeAsString());
        assertEquals("CURRENCY_SYMBOL", new CodePoint(36).typeAsString());
        assertEquals("END_PUNCTUATION", new CodePoint(41).typeAsString());
        assertEquals("MATH_SYMBOL", new CodePoint(43).typeAsString());

        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals("DECIMAL_DIGIT_NUMBER", new CodePoint(codePoint).typeAsString());
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("UPPERCASE_LETTER", new CodePoint(codePoint).typeAsString());
        }
        assertEquals("START_PUNCTUATION", new CodePoint(91).typeAsString());
        assertEquals("MODIFIER_SYMBOL", new CodePoint(94).typeAsString());
        assertEquals("CONNECTOR_PUNCTUATION", new CodePoint(95).typeAsString());
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("LOWERCASE_LETTER", new CodePoint(codePoint).typeAsString());
        }
        assertEquals("UNASSIGNED", new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).typeAsString());
    }

    /**
     * Test method for {@link CodePoint#directionality()}.
     */
    @Test
    void directionality() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.getDirectionality(codePoint), new CodePoint(codePoint).directionality().byteConstant());
        }
    }

    /**
     * Test method for {@link CodePoint#directionalityAsByte()}.
     */
    @Test
    void directionalityAsByte() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.getDirectionality(codePoint), new CodePoint(codePoint).directionalityAsByte());
        }
    }

    /**
     * Test method for {@link CodePoint#directionalityAsString()}.
     */
    @Test
    void directionalityAsString() {
        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", new CodePoint(CodePoint.MIN_ASCII_VALUE).directionalityAsString());
        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", new CodePoint(CodePoint.MAX_ASCII_VALUE).directionalityAsString());
        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", new CodePoint(CodePoint.MIN_VALUE).directionalityAsString());
        assertEquals("DIRECTIONALITY_UNDEFINED", new CodePoint(CodePoint.MAX_VALUE).directionalityAsString());

        assertEquals("DIRECTIONALITY_BOUNDARY_NEUTRAL", new CodePoint(0).directionalityAsString());
        assertEquals("DIRECTIONALITY_SEGMENT_SEPARATOR", new CodePoint(9).directionalityAsString());
        assertEquals("DIRECTIONALITY_PARAGRAPH_SEPARATOR", new CodePoint(10).directionalityAsString());
        assertEquals("DIRECTIONALITY_WHITESPACE", new CodePoint(12).directionalityAsString());
        assertEquals("DIRECTIONALITY_OTHER_NEUTRALS", new CodePoint(33).directionalityAsString());
        assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR", new CodePoint(35).directionalityAsString());
        assertEquals("DIRECTIONALITY_COMMON_NUMBER_SEPARATOR", new CodePoint(44).directionalityAsString());

        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals("DIRECTIONALITY_EUROPEAN_NUMBER", new CodePoint(codePoint).directionalityAsString());
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", new CodePoint(codePoint).directionalityAsString());
        }
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("DIRECTIONALITY_LEFT_TO_RIGHT", new CodePoint(codePoint).directionalityAsString());
        }
        assertEquals("DIRECTIONALITY_UNDEFINED", new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).directionalityAsString());
    }

    /**
     * Test method for {@link CodePoint#unicodeBlock()}.
     */
    @Test
    void unicodeBlock() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.UnicodeBlock.of(codePoint), new CodePoint(codePoint).unicodeBlock());
        }
        for (int codePoint = CodePoint.MIN_VALUE; codePoint < FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK; codePoint++) {
            assertNotNull(new CodePoint(codePoint).unicodeBlock());
        }
        assertNull(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlock());
        assertNull(Character.UnicodeBlock.of(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK));
    }

    /**
     * Test method for {@link CodePoint#unicodeBlockAsString(String)}.
     */
    @Test
    void unicodeBlockAsString() {
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MIN_ASCII_VALUE).unicodeBlockAsString(""));
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MAX_ASCII_VALUE).unicodeBlockAsString(""));
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MIN_VALUE).unicodeBlockAsString(""));
        assertEquals("SUPPLEMENTARY_PRIVATE_USE_AREA_B", new CodePoint(CodePoint.MAX_VALUE).unicodeBlockAsString(""));
        for (int codePoint = 0; codePoint < 128; codePoint++) {
            assertEquals("BASIC_LATIN", new CodePoint(codePoint).unicodeBlockAsString(""));
        }
        for (int codePoint = 128; codePoint < 256; codePoint++) {
            assertEquals("LATIN_1_SUPPLEMENT", new CodePoint(codePoint).unicodeBlockAsString(""));
        }
        for (int codePoint = CodePoint.MIN_VALUE; codePoint < FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK; codePoint++) {
            assertNotNull(new CodePoint(codePoint).unicodeBlockAsString(null), "codePoint=" + codePoint);
        }

        assertEquals("", new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlockAsString(""));
        assertEquals("unknown", new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlockAsString("unknown"));
        assertNull(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlockAsString(null));
    }

}