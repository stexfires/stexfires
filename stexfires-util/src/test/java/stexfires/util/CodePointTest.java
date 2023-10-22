package stexfires.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link CodePoint}.
 */
@SuppressWarnings({"MagicNumber", "SpellCheckingInspection"})
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

        assertEquals(Character.MIN_HIGH_SURROGATE, CodePoint.MIN_HIGH_SURROGATE);
        assertEquals(0xD800, CodePoint.MIN_HIGH_SURROGATE);
        assertEquals(Character.MAX_HIGH_SURROGATE, CodePoint.MAX_HIGH_SURROGATE);
        assertEquals(0xDBFF, CodePoint.MAX_HIGH_SURROGATE);
        assertEquals(Character.MIN_LOW_SURROGATE, CodePoint.MIN_LOW_SURROGATE);
        assertEquals(0xDC00, CodePoint.MIN_LOW_SURROGATE);
        assertEquals(Character.MAX_LOW_SURROGATE, CodePoint.MAX_LOW_SURROGATE);
        assertEquals(0xDFFF, CodePoint.MAX_LOW_SURROGATE);
        assertEquals(Character.MIN_VALUE, CodePoint.MIN_BMP_CODE_POINT);
        assertEquals(0x00, CodePoint.MIN_BMP_CODE_POINT);
        assertEquals(Character.MAX_VALUE, CodePoint.MAX_BMP_CODE_POINT);
        assertEquals(0xFFFF, CodePoint.MAX_BMP_CODE_POINT);
        assertEquals(Character.MIN_SUPPLEMENTARY_CODE_POINT, CodePoint.MIN_SUPPLEMENTARY_CODE_POINT);
        assertEquals(0x010000, CodePoint.MIN_SUPPLEMENTARY_CODE_POINT);
        assertEquals(Character.MAX_CODE_POINT, CodePoint.MAX_SUPPLEMENTARY_CODE_POINT);
        assertEquals(0x10FFFF, CodePoint.MAX_SUPPLEMENTARY_CODE_POINT);
    }

    /**
     * Test method for {@link CodePoint#CodePoint(int)} and {@link CodePoint#value()}.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    @Test
    void constructorAndValue() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(codePoint, new CodePoint(codePoint).value());
        }

        // illeagl code points
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(CodePoint.MIN_VALUE - 1));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(CodePoint.MAX_VALUE + 1));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new CodePoint(Integer.MAX_VALUE));
    }

    /**
     * Test method for {@link CodePoint#valueAsChar()}.
     */
    @Test
    void valueAsChar() {
        // all characters
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            assertEquals(ch, new CodePoint(ch).valueAsChar());
            assertEquals(ch, CodePoint.ofChar(ch).valueAsChar());
        }
        assertEquals(Character.MAX_VALUE, new CodePoint(Character.MAX_VALUE).valueAsChar());
        assertEquals(Character.MAX_VALUE, CodePoint.ofChar(Character.MAX_VALUE).valueAsChar());

        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            CodePoint cp = new CodePoint(codePoint);
            if (cp.isBmpCodePoint() && codePoint <= Character.MAX_VALUE) {
                assertEquals((char) codePoint, cp.valueAsChar());
            } else {
                assertThrows(ArithmeticException.class, cp::valueAsChar);
            }
        }
    }

    /**
     * Test method for {@link CodePoint#valueAsOptionalCharacter()}.
     */
    @Test
    void valueAsOptionalCharacter() {
        // all characters
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            assertEquals(Optional.of(ch), new CodePoint(ch).valueAsOptionalCharacter());
            assertEquals(Optional.of(ch), CodePoint.ofChar(ch).valueAsOptionalCharacter());
        }
        assertEquals(Optional.of(Character.MAX_VALUE), new CodePoint(Character.MAX_VALUE).valueAsOptionalCharacter());
        assertEquals(Optional.of(Character.MAX_VALUE), CodePoint.ofChar(Character.MAX_VALUE).valueAsOptionalCharacter());

        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            CodePoint cp = new CodePoint(codePoint);
            if (cp.isBmpCodePoint() && codePoint <= Character.MAX_VALUE) {
                assertEquals(Optional.of((char) codePoint), cp.valueAsOptionalCharacter());
            } else {
                assertEquals(Optional.empty(), cp.valueAsOptionalCharacter());
            }
        }
    }

    /**
     * Test method for {@link CodePoint#ofName(String)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void ofName() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            if (Character.isDefined(codePoint)) {
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint)));
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint).toUpperCase()));
                assertEquals(new CodePoint(codePoint), CodePoint.ofName(Character.getName(codePoint).toLowerCase()));
            }
        }

        // illegal names
        assertThrows(NullPointerException.class, () -> CodePoint.ofName(null));
        assertThrows(IllegalArgumentException.class, () -> CodePoint.ofName(""));
        assertThrows(IllegalArgumentException.class, () -> CodePoint.ofName("test"));
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
     * Test method for {@link CodePoint#findFirst(CodePoint, CodePoint, java.util.function.Predicate)}.
     */
    @Test
    void findFirst() {
        // startCodePoint < endCodePoint
        assertEquals(CodePoint.MIN_VALUE, CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> true).orElseThrow().value());
        assertTrue(CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> false).isEmpty());

        assertEquals(new CodePoint(2), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> cp.value() == 2).orElseThrow());
        assertEquals(CodePoint.ofChar('\t'), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isWhitespace).orElseThrow());
        assertEquals(CodePoint.ofChar(' '), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isSpaceChar).orElseThrow());
        assertEquals(CodePoint.ofChar('0'), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isDigit).orElseThrow());
        assertEquals(CodePoint.ofChar('A'), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isLetter).orElseThrow());

        assertEquals(CodePoint.ofChar('A'), CodePoint.findFirst(CodePoint.ofChar('0'), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isLetter).orElseThrow());
        assertEquals(CodePoint.ofChar('A'), CodePoint.findFirst(CodePoint.ofChar('A'), new CodePoint(CodePoint.MAX_VALUE), CodePoint::isLetter).orElseThrow());

        assertEquals(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK), CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> cp.unicodeBlock().isEmpty()).orElseThrow());

        // startCodePoint > endCodePoint
        assertEquals(CodePoint.MAX_VALUE, CodePoint.findFirst(new CodePoint(CodePoint.MAX_VALUE), new CodePoint(CodePoint.MIN_VALUE), cp -> true).orElseThrow().value());
        assertTrue(CodePoint.findFirst(new CodePoint(CodePoint.MAX_VALUE), new CodePoint(CodePoint.MIN_VALUE), cp -> false).isEmpty());

        assertEquals(new CodePoint(205743), CodePoint.findFirst(new CodePoint(CodePoint.MAX_VALUE), new CodePoint(CodePoint.MIN_VALUE), CodePoint::isLetter).orElseThrow());

        // startCodePoint == endCodePoint
        assertEquals(CodePoint.MIN_VALUE, CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MIN_VALUE), cp -> true).orElseThrow().value());
        assertTrue(CodePoint.findFirst(new CodePoint(CodePoint.MIN_VALUE), new CodePoint(CodePoint.MIN_VALUE), cp -> false).isEmpty());
        assertEquals(CodePoint.MAX_VALUE, CodePoint.findFirst(new CodePoint(CodePoint.MAX_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> true).orElseThrow().value());
        assertTrue(CodePoint.findFirst(new CodePoint(CodePoint.MAX_VALUE), new CodePoint(CodePoint.MAX_VALUE), cp -> false).isEmpty());
        assertEquals(CodePoint.ofChar('A'), CodePoint.findFirst(CodePoint.ofChar('A'), CodePoint.ofChar('A'), cp -> true).orElseThrow());
        assertTrue(CodePoint.findFirst(CodePoint.ofChar('A'), CodePoint.ofChar('A'), cp -> false).isEmpty());
        assertEquals(CodePoint.ofChar('A'), CodePoint.findFirst(CodePoint.ofChar('A'), CodePoint.ofChar('A'), CodePoint::isLetter).orElseThrow());
    }

    /**
     * Test method for {@link CodePoint#compareTo(CodePoint)}.
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    void compareTo() {
        assertEquals(0, new CodePoint(0).compareTo(new CodePoint(0)));
        assertTrue(new CodePoint(0).compareTo(new CodePoint(1)) < 0);
        assertTrue(new CodePoint(1).compareTo(new CodePoint(0)) > 0);

        assertEquals(0, new CodePoint(48).compareTo(new CodePoint(48)));
        assertTrue(new CodePoint(48).compareTo(new CodePoint(64)) < 0);
        assertTrue(new CodePoint(64).compareTo(new CodePoint(48)) > 0);

        assertThrows(NullPointerException.class, () -> new CodePoint(0).compareTo(null));
    }

    /**
     * Test method for {@link CodePoint#next()}.
     */
    @Test
    void next() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint < CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(codePoint + 1, new CodePoint(codePoint).next().orElseThrow().value());
        }
    }

    /**
     * Test method for {@link CodePoint#previous()}.
     */
    @Test
    void previous() {
        for (int codePoint = CodePoint.MIN_VALUE + 1; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(codePoint - 1, new CodePoint(codePoint).previous().orElseThrow().value());
        }
    }

    /**
     * Test method for {@link CodePoint#hasNext()}.
     */
    @Test
    void hasNext() {
        for (int codePoint = CodePoint.MIN_VALUE; codePoint < CodePoint.MAX_VALUE; codePoint++) {
            assertTrue(new CodePoint(codePoint).hasNext());
        }
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).hasNext());
    }

    /**
     * Test method for {@link CodePoint#hasPrevious()}.
     */
    @Test
    void hasPrevious() {
        for (int codePoint = CodePoint.MIN_VALUE + 1; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertTrue(new CodePoint(codePoint).hasPrevious());
        }
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).hasPrevious());
    }

    /**
     * Test method for {@link CodePoint#name()}.
     */
    @Test
    void name() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            Optional<String> name = new CodePoint(codePoint).name();
            assertNotNull(name);
            if (name.isPresent()) {
                assertEquals(Character.getName(codePoint), name.get());
            } else {
                assertNull(Character.getName(codePoint));
            }
        }

        assertEquals("NULL", new CodePoint(CodePoint.MIN_ASCII_VALUE).name().orElse(null));
        assertEquals("DELETE", new CodePoint(CodePoint.MAX_ASCII_VALUE).name().orElse(null));
        assertEquals("NULL", new CodePoint(CodePoint.MIN_VALUE).name().orElse(null));
        assertTrue(new CodePoint(CodePoint.MAX_VALUE).name().isEmpty());

        assertEquals("SPACE", new CodePoint(32).name().orElse(null));
        assertEquals("EXCLAMATION MARK", new CodePoint(33).name().orElse(null));
        assertEquals("DOLLAR SIGN", new CodePoint(36).name().orElse(null));
        assertEquals("RIGHT PARENTHESIS", new CodePoint(41).name().orElse(null));
        assertEquals("PLUS SIGN", new CodePoint(43).name().orElse(null));

        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals("LATIN CAPITAL LETTER " + (char) codePoint, new CodePoint(codePoint).name().orElse(null));
        }
        assertEquals("LEFT SQUARE BRACKET", new CodePoint(91).name().orElse(null));
        assertEquals("CIRCUMFLEX ACCENT", new CodePoint(94).name().orElse(null));
        assertEquals("LOW LINE", new CodePoint(95).name().orElse(null));
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals("LATIN SMALL LETTER " + (char) (codePoint - 97 + 65), new CodePoint(codePoint).name().orElse(null));
        }
        assertTrue(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).name().isEmpty());

        assertEquals("HIGH SURROGATES D800", new CodePoint(CodePoint.MIN_HIGH_SURROGATE).name().orElse(null));
        assertEquals("HIGH PRIVATE USE SURROGATES DBFF", new CodePoint(CodePoint.MAX_HIGH_SURROGATE).name().orElse(null));
        assertEquals("LOW SURROGATES DC00", new CodePoint(CodePoint.MIN_LOW_SURROGATE).name().orElse(null));
        assertEquals("LOW SURROGATES DFFF", new CodePoint(CodePoint.MAX_LOW_SURROGATE).name().orElse(null));
        assertEquals("NULL", new CodePoint(CodePoint.MIN_BMP_CODE_POINT).name().orElse(null));
        assertTrue(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).name().isEmpty());
        assertEquals("LINEAR B SYLLABLE B008 A", new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).name().orElse(null));
        assertTrue(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).name().isEmpty());
    }

    /**
     * Test method for {@link CodePoint#string()}.
     */
    @SuppressWarnings("HardcodedLineSeparator")
    @Test
    void string() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            String characterString = new CodePoint(codePoint).string();
            assertNotNull(characterString);
            assertEquals(Character.charCount(codePoint), characterString.length());
            assertEquals(Character.toString(codePoint), characterString);
        }

        // individual code points
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
    }

    /**
     * Test method for {@link CodePoint#isPrintable()}.
     */
    @Test
    void isPrintable() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            int characterType = Character.getType(codePoint);
            if (characterType != Character.UNASSIGNED && characterType != Character.CONTROL && characterType != Character.SURROGATE && characterType != Character.PRIVATE_USE) {
                assertTrue(new CodePoint(codePoint).isPrintable());
            } else {
                assertFalse(new CodePoint(codePoint).isPrintable());
            }
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isPrintable());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isPrintable());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isPrintable());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isPrintable());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isPrintable());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isPrintable());

        // individual code points
        assertFalse(new CodePoint(10).isPrintable());
        assertTrue(new CodePoint(32).isPrintable());
        assertTrue(new CodePoint(65).isPrintable());
        assertTrue(new CodePoint(97).isPrintable());
        assertTrue(new CodePoint(1924).isPrintable());
        assertTrue(new CodePoint(2793).isPrintable());
        assertTrue(new CodePoint(12359).isPrintable());
        assertTrue(new CodePoint(129501).isPrintable());
        assertTrue(new CodePoint(127820).isPrintable());
        assertTrue(new CodePoint(128147).isPrintable());

        assertFalse(new CodePoint(888).isPrintable());
        assertFalse(new CodePoint(42975).isPrintable());
        assertFalse(new CodePoint(55317).isPrintable());
    }

    /**
     * Test method for {@link CodePoint#printableString()}.
     */
    @Test
    void printableString() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            CodePoint cp = new CodePoint(codePoint);
            Optional<String> printableString = cp.printableString();
            assertNotNull(printableString);
            if (printableString.isPresent()) {
                assertTrue(cp.isPrintable());
                assertEquals(Character.charCount(codePoint), printableString.get().length());
                assertEquals(Character.toString(codePoint), printableString.get());
            } else {
                assertFalse(cp.isPrintable());
            }
        }

        assertEquals(" ", new CodePoint(32).printableString().orElse(null));
        assertEquals("!", new CodePoint(33).printableString().orElse(null));
        assertEquals("A", new CodePoint(65).printableString().orElse(null));
        assertEquals("0", new CodePoint(48).printableString().orElse(null));
        assertEquals("a", new CodePoint(97).printableString().orElse(null));
        assertEquals("€", new CodePoint(8364).printableString().orElse(null));
        assertEquals("𐀀", new CodePoint(65536).printableString().orElse(null));
        assertEquals("🀀", new CodePoint(126976).printableString().orElse(null));
        assertEquals("🏿", new CodePoint(127999).printableString().orElse(null));

        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MAX_VALUE).printableString().isEmpty());
        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertTrue(new CodePoint(codePoint).printableString().isEmpty());
        }
        assertTrue(new CodePoint(888).printableString().isEmpty());
        assertTrue(new CodePoint(42975).printableString().isEmpty());
        assertTrue(new CodePoint(55317).printableString().isEmpty());
        assertTrue(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MIN_LOW_SURROGATE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MAX_LOW_SURROGATE).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).printableString().isEmpty());
        assertTrue(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).printableString().isEmpty());
        assertEquals("\uD800\uDC00", new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).printableString().orElse(null));
        assertTrue(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).printableString().isEmpty());
    }

    /**
     * Test method for {@link CodePoint#hexString()}.
     */
    @Test
    void hexString() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).hexString());
            assertEquals(Integer.toHexString(codePoint), new CodePoint(codePoint).hexString());
        }

        // constant code points
        assertEquals("0", new CodePoint(CodePoint.MIN_ASCII_VALUE).hexString());
        assertEquals("7f", new CodePoint(CodePoint.MAX_ASCII_VALUE).hexString());
        assertEquals("0", new CodePoint(CodePoint.MIN_VALUE).hexString());
        assertEquals("10ffff", new CodePoint(CodePoint.MAX_VALUE).hexString());

        assertEquals("d800", new CodePoint(CodePoint.MIN_HIGH_SURROGATE).hexString());
        assertEquals("dbff", new CodePoint(CodePoint.MAX_HIGH_SURROGATE).hexString());
        assertEquals("dc00", new CodePoint(CodePoint.MIN_LOW_SURROGATE).hexString());
        assertEquals("dfff", new CodePoint(CodePoint.MAX_LOW_SURROGATE).hexString());
        assertEquals("0", new CodePoint(CodePoint.MIN_BMP_CODE_POINT).hexString());
        assertEquals("ffff", new CodePoint(CodePoint.MAX_BMP_CODE_POINT).hexString());
        assertEquals("10000", new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).hexString());
        assertEquals("10ffff", new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).hexString());

        // individual code points
        assertEquals("20", new CodePoint(32).hexString());
        assertEquals("21", new CodePoint(33).hexString());
        assertEquals("24", new CodePoint(36).hexString());
        assertEquals("29", new CodePoint(41).hexString());
        assertEquals("2b", new CodePoint(43).hexString());
    }

    /**
     * Test method for {@link CodePoint#unicodeEscapes()}.
     */
    @Test
    void unicodeEscapes() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            var cp = new CodePoint(codePoint);
            assertNotNull(cp.unicodeEscapes());
            if (Character.isBmpCodePoint(codePoint)) {
                assertEquals(6, cp.unicodeEscapes().length());
            } else {
                assertEquals(12, cp.unicodeEscapes().length());
            }
        }

        // constant code points
        assertEquals("\\u0000", new CodePoint(CodePoint.MIN_ASCII_VALUE).unicodeEscapes());
        assertEquals("\\u007f", new CodePoint(CodePoint.MAX_ASCII_VALUE).unicodeEscapes());
        assertEquals("\\u0000", new CodePoint(CodePoint.MIN_VALUE).unicodeEscapes());
        assertEquals("\\udbff\\udfff", new CodePoint(CodePoint.MAX_VALUE).unicodeEscapes());

        assertEquals("\\ud800", new CodePoint(CodePoint.MIN_HIGH_SURROGATE).unicodeEscapes());
        assertEquals("\\udbff", new CodePoint(CodePoint.MAX_HIGH_SURROGATE).unicodeEscapes());
        assertEquals("\\udc00", new CodePoint(CodePoint.MIN_LOW_SURROGATE).unicodeEscapes());
        assertEquals("\\udfff", new CodePoint(CodePoint.MAX_LOW_SURROGATE).unicodeEscapes());
        assertEquals("\\u0000", new CodePoint(CodePoint.MIN_BMP_CODE_POINT).unicodeEscapes());
        assertEquals("\\uffff", new CodePoint(CodePoint.MAX_BMP_CODE_POINT).unicodeEscapes());
        assertEquals("\\ud800\\udc00", new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).unicodeEscapes());
        assertEquals("\\udbff\\udfff", new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).unicodeEscapes());

        // individual code points
        assertEquals("\\u0020", new CodePoint(32).unicodeEscapes());
        assertEquals("\\u0021", new CodePoint(33).unicodeEscapes());
        assertEquals("\\u0024", new CodePoint(36).unicodeEscapes());
        assertEquals("\\u0029", new CodePoint(41).unicodeEscapes());
        assertEquals("\\u002b", new CodePoint(43).unicodeEscapes());
        assertEquals("\\u0041", new CodePoint(65).unicodeEscapes());

        assertEquals("\\u0061", new CodePoint(97).unicodeEscapes());
        assertEquals("\\u20ac", new CodePoint(8364).unicodeEscapes());
        assertEquals("\\ud800\\udc00", new CodePoint(65536).unicodeEscapes());
        assertEquals("\\ud83c\\udc00", new CodePoint(126976).unicodeEscapes());
        assertEquals("\\ud83c\\udfff", new CodePoint(127999).unicodeEscapes());
    }

    /**
     * Test method for {@link CodePoint#isAlphabetic()}.
     */
    @Test
    void isAlphabetic() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isAlphabetic(codePoint), new CodePoint(codePoint).isAlphabetic());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isAlphabetic());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isAlphabetic());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isAlphabetic());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isAlphabetic());

        // individual code points
        assertFalse(new CodePoint(32).isAlphabetic());
        assertTrue(new CodePoint(65).isAlphabetic());
    }

    /**
     * Test method for {@link CodePoint#isASCII()}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void isASCII() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            if (codePoint >= CodePoint.MIN_ASCII_VALUE && codePoint <= CodePoint.MAX_ASCII_VALUE) {
                assertTrue(new CodePoint(codePoint).isASCII());
            } else {
                assertFalse(new CodePoint(codePoint).isASCII());
            }
        }
    }

    /**
     * Test method for {@link CodePoint#isBetween(int, int)}.
     */
    @Test
    void isBetween() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertTrue(new CodePoint(codePoint).isBetween(CodePoint.MIN_VALUE, CodePoint.MAX_VALUE));
            assertFalse(new CodePoint(codePoint).isBetween(CodePoint.MAX_VALUE, CodePoint.MIN_VALUE));
        }

        // individual code points
        assertFalse(new CodePoint(32).isBetween(31, 31));
        assertTrue(new CodePoint(32).isBetween(31, 32));
        assertTrue(new CodePoint(32).isBetween(31, 33));

        assertFalse(new CodePoint(32).isBetween(32, 31));
        assertTrue(new CodePoint(32).isBetween(32, 32));
        assertTrue(new CodePoint(32).isBetween(32, 33));

        assertFalse(new CodePoint(32).isBetween(33, 31));
        assertFalse(new CodePoint(32).isBetween(33, 32));
        assertFalse(new CodePoint(32).isBetween(33, 33));
    }

    /**
     * Test method for {@link CodePoint#isBmpCodePoint()}.
     */
    @Test
    void isBmpCodePoint() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isBmpCodePoint(codePoint), new CodePoint(codePoint).isBmpCodePoint());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isBmpCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isBmpCodePoint());
        assertTrue(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isBmpCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isBmpCodePoint());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isBmpCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isBmpCodePoint());

        // individual code points
        assertTrue(new CodePoint(32).isBmpCodePoint());
        assertTrue(new CodePoint(65).isBmpCodePoint());
        assertFalse(new CodePoint(129501).isBmpCodePoint());
        assertFalse(new CodePoint(127820).isBmpCodePoint());
        assertFalse(new CodePoint(128147).isBmpCodePoint());
    }

    /**
     * Test method for {@link CodePoint#isDefined()}.
     */
    @Test
    void isDefined() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isDefined(codePoint), new CodePoint(codePoint).isDefined());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isDefined());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isDefined());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isDefined());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isDefined());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isDefined());
        assertTrue(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isDefined());
        assertTrue(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isDefined());
        assertTrue(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isDefined());
        assertTrue(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isDefined());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isDefined());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isDefined());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isDefined());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isDefined());

        // individual code points
        assertTrue(new CodePoint(32).isDefined());
        assertFalse(new CodePoint(888).isDefined());
    }

    /**
     * Test method for {@link CodePoint#isDigit()}.
     */
    @Test
    void isDigit() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isDigit(codePoint), new CodePoint(codePoint).isDigit());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isDigit());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isDigit());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isDigit());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isDigit());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isDigit());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isDigit());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isDigit());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isDigit());

        // individual code points
        assertFalse(new CodePoint(32).isDigit());
        assertFalse(new CodePoint(65).isDigit());
        assertTrue(new CodePoint(48).isDigit());
        assertTrue(new CodePoint(2793).isDigit());
    }

    /**
     * Test method for {@link CodePoint#isIdentifierIgnorable()}.
     */
    @Test
    void isIdentifierIgnorable() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isIdentifierIgnorable(codePoint), new CodePoint(codePoint).isIdentifierIgnorable());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isIdentifierIgnorable());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isIdentifierIgnorable());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isIdentifierIgnorable());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isIdentifierIgnorable());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isIdentifierIgnorable());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isIdentifierIgnorable());

        // individual code points
        assertTrue(new CodePoint(1).isIdentifierIgnorable());
        assertFalse(new CodePoint(32).isIdentifierIgnorable());
        assertFalse(new CodePoint(48).isIdentifierIgnorable());
        assertFalse(new CodePoint(65).isIdentifierIgnorable());
        assertTrue(new CodePoint(8296).isIdentifierIgnorable());
        assertTrue(new CodePoint(113825).isIdentifierIgnorable());
    }

    /**
     * Test method for {@link CodePoint#isIdeographic()}.
     */
    @Test
    void isIdeographic() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isIdeographic(codePoint), new CodePoint(codePoint).isIdeographic());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isIdeographic());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isIdeographic());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isIdeographic());

        // individual code points
        assertFalse(new CodePoint(32).isIdeographic());
        assertFalse(new CodePoint(65).isIdeographic());
        assertTrue(new CodePoint(12294).isIdeographic());
        assertTrue(new CodePoint(12295).isIdeographic());
        assertTrue(new CodePoint(12321).isIdeographic());
    }

    /**
     * Test method for {@link CodePoint#isISOControl()}.
     */
    @Test
    void isISOControl() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isISOControl(codePoint), new CodePoint(codePoint).isISOControl());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isISOControl());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isISOControl());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isISOControl());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isISOControl());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isISOControl());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isISOControl());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isISOControl());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isISOControl());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isISOControl());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isISOControl());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isISOControl());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isISOControl());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isISOControl());

        // individual code points
        assertTrue(new CodePoint(0).isISOControl());
        assertTrue(new CodePoint(1).isISOControl());
        assertFalse(new CodePoint(32).isISOControl());
        for (int codePoint = 0x00; codePoint <= 0x1F; codePoint++) {
            assertTrue(new CodePoint(codePoint).isISOControl());
        }
        for (int codePoint = 0x7F; codePoint <= 0x9F; codePoint++) {
            assertTrue(new CodePoint(codePoint).isISOControl());
        }
    }

    /**
     * Test method for {@link CodePoint#isJavaIdentifierPart()}.
     */
    @Test
    void isJavaIdentifierPart() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isJavaIdentifierPart(codePoint), new CodePoint(codePoint).isJavaIdentifierPart());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isJavaIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isJavaIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isJavaIdentifierPart());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isJavaIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isJavaIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isJavaIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isJavaIdentifierPart());

        // individual code points
        assertFalse(new CodePoint(32).isJavaIdentifierPart());
        assertTrue(new CodePoint(48).isJavaIdentifierPart());
        assertFalse(new CodePoint(59).isJavaIdentifierPart());
        assertTrue(new CodePoint(65).isJavaIdentifierPart());
        assertTrue(new CodePoint(95).isJavaIdentifierPart());
        assertTrue(new CodePoint(97).isJavaIdentifierPart());
        assertTrue(new CodePoint(113825).isJavaIdentifierPart());
    }

    /**
     * Test method for {@link CodePoint#isLetter()}.
     */
    @Test
    void isLetter() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isLetter(codePoint), new CodePoint(codePoint).isLetter());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isLetter());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isLetter());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isLetter());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isLetter());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isLetter());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isLetter());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isLetter());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isLetter());

        // individual code points
        assertFalse(new CodePoint(32).isLetter());
        assertTrue(new CodePoint(65).isLetter());
        assertTrue(new CodePoint(97).isLetter());
        assertTrue(new CodePoint(1924).isLetter());
        assertTrue(new CodePoint(12359).isLetter());
    }

    /**
     * Test method for {@link CodePoint#isLetterOrDigit()}.
     */
    @Test
    void isLetterOrDigit() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isLetterOrDigit(codePoint), new CodePoint(codePoint).isLetterOrDigit());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isLetterOrDigit());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isLetterOrDigit());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isLetterOrDigit());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isLetterOrDigit());

        // individual code points
        assertFalse(new CodePoint(32).isLetterOrDigit());
        assertTrue(new CodePoint(48).isLetterOrDigit());
        assertTrue(new CodePoint(65).isLetterOrDigit());
        assertTrue(new CodePoint(97).isLetterOrDigit());
        assertTrue(new CodePoint(1924).isLetterOrDigit());
        assertTrue(new CodePoint(2793).isLetterOrDigit());
        assertTrue(new CodePoint(12359).isLetterOrDigit());
    }

    /**
     * Test method for {@link CodePoint#isLowerCase()}.
     */
    @Test
    void isLowerCase() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isLowerCase(codePoint), new CodePoint(codePoint).isLowerCase());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isLowerCase());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isLowerCase());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isLowerCase());

        // individual code points
        assertFalse(new CodePoint(32).isLowerCase());
        assertFalse(new CodePoint(65).isLowerCase());
        assertTrue(new CodePoint(97).isLowerCase());
        assertFalse(new CodePoint(1924).isLowerCase());
        assertFalse(new CodePoint(12359).isLowerCase());
    }

    /**
     * Test method for {@link CodePoint#isMirrored()}.
     */
    @Test
    void isMirrored() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isMirrored(codePoint), new CodePoint(codePoint).isMirrored());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isMirrored());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isMirrored());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isMirrored());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isMirrored());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isMirrored());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isMirrored());

        // individual code points
        assertFalse(new CodePoint(32).isMirrored());
        assertTrue(new CodePoint(62).isMirrored());
        assertFalse(new CodePoint(65).isMirrored());
        assertTrue(new CodePoint(91).isMirrored());
        assertFalse(new CodePoint(97).isMirrored());
        assertTrue(new CodePoint(10706).isMirrored());
    }

    /**
     * Test method for {@link CodePoint#isSpaceChar()}.
     */
    @Test
    void isSpaceChar() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isSpaceChar(codePoint), new CodePoint(codePoint).isSpaceChar());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isSpaceChar());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isSpaceChar());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isSpaceChar());

        // individual code points
        assertTrue(new CodePoint(32).isSpaceChar());
        assertFalse(new CodePoint(65).isSpaceChar());
        assertTrue(new CodePoint(160).isSpaceChar());
        assertTrue(new CodePoint(5760).isSpaceChar());
        assertTrue(new CodePoint(8192).isSpaceChar());
    }

    /**
     * Test method for {@link CodePoint#isSupplementaryCodePoint()}.
     */
    @Test
    void isSupplementaryCodePoint() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isSupplementaryCodePoint(codePoint), new CodePoint(codePoint).isSupplementaryCodePoint());
        }
        // supplementary code points
        for (int codePoint = CodePoint.MIN_SUPPLEMENTARY_CODE_POINT; codePoint <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT; codePoint++) {
            assertTrue(new CodePoint(codePoint).isSupplementaryCodePoint());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isSupplementaryCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_VALUE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isSupplementaryCodePoint());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isSupplementaryCodePoint());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isSupplementaryCodePoint());
        assertTrue(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isSupplementaryCodePoint());

        // individual code points
        assertFalse(new CodePoint(32).isSupplementaryCodePoint());
        assertFalse(new CodePoint(65).isSupplementaryCodePoint());
        assertFalse(new CodePoint(97).isSupplementaryCodePoint());
        assertFalse(new CodePoint(1924).isSupplementaryCodePoint());
        assertFalse(new CodePoint(12359).isSupplementaryCodePoint());
        assertTrue(new CodePoint(129501).isSupplementaryCodePoint());
        assertTrue(new CodePoint(127820).isSupplementaryCodePoint());
        assertTrue(new CodePoint(128147).isSupplementaryCodePoint());
    }

    /**
     * Test method for {@link CodePoint#isTitleCase()}.
     */
    @Test
    void isTitleCase() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isTitleCase(codePoint), new CodePoint(codePoint).isTitleCase());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isTitleCase());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isTitleCase());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isTitleCase());

        // individual code points
        assertFalse(new CodePoint(32).isTitleCase());
        assertFalse(new CodePoint(65).isTitleCase());
        assertTrue(new CodePoint(453).isTitleCase());
        assertTrue(new CodePoint(8072).isTitleCase());
    }

    /**
     * Test method for {@link CodePoint#isUnicodeIdentifierPart()}.
     */
    @Test
    void isUnicodeIdentifierPart() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isUnicodeIdentifierPart(codePoint), new CodePoint(codePoint).isUnicodeIdentifierPart());
        }

        // constant code points
        assertTrue(new CodePoint(CodePoint.MIN_ASCII_VALUE).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MAX_ASCII_VALUE).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_VALUE).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isUnicodeIdentifierPart());

        // individual code points
        assertFalse(new CodePoint(32).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(48).isUnicodeIdentifierPart());
        assertFalse(new CodePoint(59).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(65).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(95).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(97).isUnicodeIdentifierPart());
        assertTrue(new CodePoint(113825).isUnicodeIdentifierPart());
    }

    /**
     * Test method for {@link CodePoint#isUnicodeIdentifierStart()}.
     */
    @Test
    void isUnicodeIdentifierStart() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isUnicodeIdentifierStart(codePoint), new CodePoint(codePoint).isUnicodeIdentifierStart());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isUnicodeIdentifierStart());
        assertTrue(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isUnicodeIdentifierStart());

        // individual code points
        assertFalse(new CodePoint(32).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(48).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(59).isUnicodeIdentifierStart());
        assertTrue(new CodePoint(65).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(95).isUnicodeIdentifierStart());
        assertTrue(new CodePoint(97).isUnicodeIdentifierStart());
        assertFalse(new CodePoint(113825).isUnicodeIdentifierStart());
    }

    /**
     * Test method for {@link CodePoint#isUpperCase()}.
     */
    @Test
    void isUpperCase() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isUpperCase(codePoint), new CodePoint(codePoint).isUpperCase());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isUpperCase());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isUpperCase());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isUpperCase());

        // individual code points
        assertFalse(new CodePoint(32).isUpperCase());
        assertTrue(new CodePoint(65).isUpperCase());
        assertFalse(new CodePoint(97).isUpperCase());
        assertFalse(new CodePoint(1924).isUpperCase());
        assertFalse(new CodePoint(12359).isUpperCase());
    }

    /**
     * Test method for {@link CodePoint#isWhitespace()}.
     */
    @Test
    void isWhitespace() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.isWhitespace(codePoint), new CodePoint(codePoint).isWhitespace());
        }

        // constant code points
        assertFalse(new CodePoint(CodePoint.MIN_ASCII_VALUE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_ASCII_VALUE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MIN_VALUE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_VALUE).isWhitespace());
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MIN_HIGH_SURROGATE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_HIGH_SURROGATE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MIN_LOW_SURROGATE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_LOW_SURROGATE).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MIN_BMP_CODE_POINT).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_BMP_CODE_POINT).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).isWhitespace());
        assertFalse(new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).isWhitespace());

        // individual code points
        assertTrue(new CodePoint(32).isWhitespace());
        assertFalse(new CodePoint(65).isWhitespace());
        assertFalse(new CodePoint(160).isWhitespace());
        assertTrue(new CodePoint(5760).isWhitespace());
        assertTrue(new CodePoint(8192).isWhitespace());
    }

    /**
     * Test method for {@link CodePoint#charCount()}.
     */
    @Test
    void charCount() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertEquals(Character.charCount(codePoint), new CodePoint(codePoint).charCount());
            if (Character.isBmpCodePoint(codePoint)) {
                assertEquals(1, new CodePoint(codePoint).charCount());
            } else {
                assertEquals(2, new CodePoint(codePoint).charCount());
            }
            if (Character.isSupplementaryCodePoint(codePoint)) {
                assertEquals(2, new CodePoint(codePoint).charCount());
            } else {
                assertEquals(1, new CodePoint(codePoint).charCount());
            }
        }

        // ascii code points
        for (int codePoint = CodePoint.MIN_ASCII_VALUE; codePoint <= CodePoint.MAX_ASCII_VALUE; codePoint++) {
            assertEquals(1, new CodePoint(codePoint).charCount());
        }

        // bmp code points
        for (int codePoint = CodePoint.MIN_BMP_CODE_POINT; codePoint <= CodePoint.MAX_BMP_CODE_POINT; codePoint++) {
            assertEquals(1, new CodePoint(codePoint).charCount());
        }

        // supplementary code points
        for (int codePoint = CodePoint.MIN_SUPPLEMENTARY_CODE_POINT; codePoint <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT; codePoint++) {
            assertEquals(2, new CodePoint(codePoint).charCount());
        }

        // individual code points
        assertEquals(2, new CodePoint(129501).charCount());
        assertEquals(2, new CodePoint(127820).charCount());
        assertEquals(2, new CodePoint(128147).charCount());
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
     * Test method for {@link CodePoint#lowSurrogate()}.
     */
    @Test
    void lowSurrogate() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).lowSurrogate());
            if (Character.isSupplementaryCodePoint(codePoint)) {
                assertEquals(Character.lowSurrogate(codePoint), new CodePoint(codePoint).lowSurrogate().orElseThrow());
            } else {
                assertTrue(new CodePoint(codePoint).lowSurrogate().isEmpty());
            }
        }

        // supplementary code points
        for (int codePoint = CodePoint.MIN_SUPPLEMENTARY_CODE_POINT; codePoint <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT; codePoint++) {
            assertTrue(new CodePoint(codePoint).lowSurrogate().isPresent());
        }
    }

    /**
     * Test method for {@link CodePoint#highSurrogate()}.
     */
    @Test
    void highSurrogate() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).highSurrogate());
            if (Character.isSupplementaryCodePoint(codePoint)) {
                assertEquals(Character.highSurrogate(codePoint), new CodePoint(codePoint).highSurrogate().orElseThrow());
            } else {
                assertTrue(new CodePoint(codePoint).highSurrogate().isEmpty());
            }
        }

        // supplementary code points
        for (int codePoint = CodePoint.MIN_SUPPLEMENTARY_CODE_POINT; codePoint <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT; codePoint++) {
            assertTrue(new CodePoint(codePoint).highSurrogate().isPresent());
        }
    }

    /**
     * Test method for {@link CodePoint#type()}.
     */
    @Test
    void type() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).type());
            assertEquals(Character.getType(codePoint), new CodePoint(codePoint).type().intConstant());
        }

        assertEquals(CodePoint.Type.CONTROL, new CodePoint(CodePoint.MIN_ASCII_VALUE).type());
        assertEquals(CodePoint.Type.CONTROL, new CodePoint(CodePoint.MAX_ASCII_VALUE).type());
        assertEquals(CodePoint.Type.CONTROL, new CodePoint(CodePoint.MIN_VALUE).type());
        assertEquals(CodePoint.Type.UNASSIGNED, new CodePoint(CodePoint.MAX_VALUE).type());

        for (int codePoint = 0; codePoint < 32; codePoint++) {
            assertEquals(CodePoint.Type.CONTROL, new CodePoint(codePoint).type());
        }
        assertEquals(CodePoint.Type.SPACE_SEPARATOR, new CodePoint(32).type());
        assertEquals(CodePoint.Type.OTHER_PUNCTUATION, new CodePoint(33).type());
        assertEquals(CodePoint.Type.CURRENCY_SYMBOL, new CodePoint(36).type());
        assertEquals(CodePoint.Type.END_PUNCTUATION, new CodePoint(41).type());
        assertEquals(CodePoint.Type.MATH_SYMBOL, new CodePoint(43).type());

        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals(CodePoint.Type.DECIMAL_DIGIT_NUMBER, new CodePoint(codePoint).type());
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals(CodePoint.Type.UPPERCASE_LETTER, new CodePoint(codePoint).type());
        }
        assertEquals(CodePoint.Type.START_PUNCTUATION, new CodePoint(91).type());
        assertEquals(CodePoint.Type.MODIFIER_SYMBOL, new CodePoint(94).type());
        assertEquals(CodePoint.Type.CONNECTOR_PUNCTUATION, new CodePoint(95).type());
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals(CodePoint.Type.LOWERCASE_LETTER, new CodePoint(codePoint).type());
        }
        assertEquals(CodePoint.Type.UNASSIGNED, new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).type());

        assertEquals(CodePoint.Type.SURROGATE, new CodePoint(CodePoint.MIN_HIGH_SURROGATE).type());
        assertEquals(CodePoint.Type.SURROGATE, new CodePoint(CodePoint.MAX_HIGH_SURROGATE).type());
        assertEquals(CodePoint.Type.SURROGATE, new CodePoint(CodePoint.MIN_LOW_SURROGATE).type());
        assertEquals(CodePoint.Type.SURROGATE, new CodePoint(CodePoint.MAX_LOW_SURROGATE).type());
        assertEquals(CodePoint.Type.CONTROL, new CodePoint(CodePoint.MIN_BMP_CODE_POINT).type());
        assertEquals(CodePoint.Type.UNASSIGNED, new CodePoint(CodePoint.MAX_BMP_CODE_POINT).type());
        assertEquals(CodePoint.Type.OTHER_LETTER, new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).type());
        assertEquals(CodePoint.Type.UNASSIGNED, new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).type());
    }

    /**
     * Test method for {@link CodePoint#directionality()}.
     */
    @Test
    void directionality() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).directionality());
            assertEquals(Character.getDirectionality(codePoint), new CodePoint(codePoint).directionality().byteConstant());
        }

        assertEquals(CodePoint.Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL, new CodePoint(CodePoint.MIN_ASCII_VALUE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL, new CodePoint(CodePoint.MAX_ASCII_VALUE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL, new CodePoint(CodePoint.MIN_VALUE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_UNDEFINED, new CodePoint(CodePoint.MAX_VALUE).directionality());

        assertEquals(CodePoint.Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL, new CodePoint(0).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_SEGMENT_SEPARATOR, new CodePoint(9).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_PARAGRAPH_SEPARATOR, new CodePoint(10).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_WHITESPACE, new CodePoint(12).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_OTHER_NEUTRALS, new CodePoint(33).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR, new CodePoint(35).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR, new CodePoint(44).directionality());

        for (int codePoint = 48; codePoint < 58; codePoint++) {
            assertEquals(CodePoint.Directionality.DIRECTIONALITY_EUROPEAN_NUMBER, new CodePoint(codePoint).directionality());
        }
        for (int codePoint = 65; codePoint < 91; codePoint++) {
            assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(codePoint).directionality());
        }
        for (int codePoint = 97; codePoint < 123; codePoint++) {
            assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(codePoint).directionality());
        }
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_UNDEFINED, new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).directionality());

        assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(CodePoint.MIN_HIGH_SURROGATE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(CodePoint.MAX_HIGH_SURROGATE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(CodePoint.MIN_LOW_SURROGATE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(CodePoint.MAX_LOW_SURROGATE).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_BOUNDARY_NEUTRAL, new CodePoint(CodePoint.MIN_BMP_CODE_POINT).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_UNDEFINED, new CodePoint(CodePoint.MAX_BMP_CODE_POINT).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_LEFT_TO_RIGHT, new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).directionality());
        assertEquals(CodePoint.Directionality.DIRECTIONALITY_UNDEFINED, new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).directionality());
    }

    /**
     * Test method for {@link CodePoint#unicodeBlock()}.
     */
    @Test
    void unicodeBlock() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).unicodeBlock());
            assertEquals(Character.UnicodeBlock.of(codePoint), new CodePoint(codePoint).unicodeBlock().orElse(null));
        }
        for (int codePoint = CodePoint.MIN_VALUE; codePoint < FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK; codePoint++) {
            assertTrue(new CodePoint(codePoint).unicodeBlock().isPresent());
        }
        assertFalse(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlock().isPresent());
        assertNull(Character.UnicodeBlock.of(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK));

        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MIN_ASCII_VALUE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MAX_ASCII_VALUE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MIN_VALUE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("SUPPLEMENTARY_PRIVATE_USE_AREA_B", new CodePoint(CodePoint.MAX_VALUE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        for (int codePoint = 0; codePoint < 128; codePoint++) {
            assertEquals("BASIC_LATIN", new CodePoint(codePoint).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        }
        for (int codePoint = 128; codePoint < 256; codePoint++) {
            assertEquals("LATIN_1_SUPPLEMENT", new CodePoint(codePoint).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        }
        assertTrue(new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeBlock().isEmpty());

        assertEquals("HIGH_SURROGATES", new CodePoint(CodePoint.MIN_HIGH_SURROGATE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("HIGH_PRIVATE_USE_SURROGATES", new CodePoint(CodePoint.MAX_HIGH_SURROGATE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("LOW_SURROGATES", new CodePoint(CodePoint.MIN_LOW_SURROGATE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("LOW_SURROGATES", new CodePoint(CodePoint.MAX_LOW_SURROGATE).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("BASIC_LATIN", new CodePoint(CodePoint.MIN_BMP_CODE_POINT).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("SPECIALS", new CodePoint(CodePoint.MAX_BMP_CODE_POINT).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("LINEAR_B_SYLLABARY", new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
        assertEquals("SUPPLEMENTARY_PRIVATE_USE_AREA_B", new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).unicodeBlock().map(Character.UnicodeBlock::toString).orElse(""));
    }

    /**
     * Test method for {@link CodePoint#unicodeScript()}.
     */
    @Test
    void unicodeScript() {
        // all code points
        for (int codePoint = CodePoint.MIN_VALUE; codePoint <= CodePoint.MAX_VALUE; codePoint++) {
            assertNotNull(new CodePoint(codePoint).unicodeScript());
            assertEquals(Character.UnicodeScript.of(codePoint), new CodePoint(codePoint).unicodeScript());
        }

        // constant code points
        assertEquals(Character.UnicodeScript.COMMON, new CodePoint(CodePoint.MIN_ASCII_VALUE).unicodeScript());
        assertEquals(Character.UnicodeScript.COMMON, new CodePoint(CodePoint.MAX_ASCII_VALUE).unicodeScript());
        assertEquals(Character.UnicodeScript.COMMON, new CodePoint(CodePoint.MIN_VALUE).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MAX_VALUE).unicodeScript());

        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(FIRST_CODE_POINT_WITHOUT_UNICODE_BLOCK).unicodeScript());

        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MIN_HIGH_SURROGATE).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MAX_HIGH_SURROGATE).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MIN_LOW_SURROGATE).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MAX_LOW_SURROGATE).unicodeScript());
        assertEquals(Character.UnicodeScript.COMMON, new CodePoint(CodePoint.MIN_BMP_CODE_POINT).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MAX_BMP_CODE_POINT).unicodeScript());
        assertEquals(Character.UnicodeScript.LINEAR_B, new CodePoint(CodePoint.MIN_SUPPLEMENTARY_CODE_POINT).unicodeScript());
        assertEquals(Character.UnicodeScript.UNKNOWN, new CodePoint(CodePoint.MAX_SUPPLEMENTARY_CODE_POINT).unicodeScript());
    }

}