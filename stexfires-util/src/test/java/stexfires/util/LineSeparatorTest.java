package stexfires.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static stexfires.util.LineSeparator.CR;
import static stexfires.util.LineSeparator.CR_LF;
import static stexfires.util.LineSeparator.LF;

/**
 * Tests for {@link LineSeparator}.
 */
@SuppressWarnings({"HardcodedLineSeparator", "MagicNumber"})
class LineSeparatorTest {

    private static final String STRING_LF = "\n";
    private static final String STRING_CR = "\r";
    private static final String STRING_CR_LF = "\r\n";

    /**
     * Test method for {@link LineSeparator#name()}.
     */
    @Test
    void name() {
        assertEquals("LF", LF.name());
        assertEquals("CR", CR.name());
        assertEquals("CR_LF", CR_LF.name());
    }

    /**
     * Test method for {@link LineSeparator#ordinal()}.
     */
    @Test
    void ordinal() {
        assertEquals(0, LF.ordinal());
        assertEquals(1, CR.ordinal());
        assertEquals(2, CR_LF.ordinal());
    }

    /**
     * Test method for {@link LineSeparator#systemLineSeparator()}.
     */
    @Test
    void systemLineSeparator() {
        assertEquals(System.lineSeparator(), LineSeparator.systemLineSeparator().string());
    }

    /**
     * Test method for {@link LineSeparator#lookup(String)}.
     */
    @Test
    void lookup() {
        assertEquals(Optional.of(LF), LineSeparator.lookup(STRING_LF));
        assertEquals(Optional.of(CR), LineSeparator.lookup(STRING_CR));
        assertEquals(Optional.of(CR_LF), LineSeparator.lookup(STRING_CR_LF));
        assertEquals(Optional.empty(), LineSeparator.lookup(""));
        assertEquals(Optional.empty(), LineSeparator.lookup("  "));
        assertEquals(Optional.empty(), LineSeparator.lookup("unknown"));
        assertEquals(Optional.empty(), LineSeparator.lookup("\t"));
        assertEquals(Optional.empty(), LineSeparator.lookup(null));
    }

    /**
     * Test method for {@link LineSeparator#string()}.
     */
    @Test
    void string() {
        assertEquals(STRING_LF, LF.string());
        assertEquals(STRING_CR, CR.string());
        assertEquals(STRING_CR_LF, CR_LF.string());
    }

    /**
     * Test method for {@link LineSeparator#repeat(int)}.
     */
    @Test
    void repeat() {
        assertEquals("", LF.repeat(0));
        assertEquals("", CR.repeat(0));
        assertEquals("", CR_LF.repeat(0));

        assertEquals(STRING_LF, LF.repeat(1));
        assertEquals(STRING_CR, CR.repeat(1));
        assertEquals(STRING_CR_LF, CR_LF.repeat(1));

        assertEquals(STRING_LF + STRING_LF, LF.repeat(2));
        assertEquals(STRING_CR + STRING_CR, CR.repeat(2));
        assertEquals(STRING_CR_LF + STRING_CR_LF, CR_LF.repeat(2));

        assertEquals(STRING_LF + STRING_LF + STRING_LF, LF.repeat(3));
        assertEquals(STRING_CR + STRING_CR + STRING_CR, CR.repeat(3));
        assertEquals(STRING_CR_LF + STRING_CR_LF + STRING_CR_LF, CR_LF.repeat(3));

        assertEquals(100, LF.repeat(100).length());
        assertEquals(100, CR.repeat(100).length());
        assertEquals(200, CR_LF.repeat(100).length());

        assertThrows(IllegalArgumentException.class, () -> LF.repeat(-1));
        assertThrows(IllegalArgumentException.class, () -> CR.repeat(-1));
        assertThrows(IllegalArgumentException.class, () -> CR_LF.repeat(-1));
    }

    /**
     * Test method for {@link LineSeparator#length()}.
     */
    @Test
    void length() {
        assertEquals(STRING_LF.length(), LF.length());
        assertEquals(STRING_CR.length(), CR.length());
        assertEquals(STRING_CR_LF.length(), CR_LF.length());

        assertEquals(1, LF.length());
        assertEquals(1, CR.length());
        assertEquals(2, CR_LF.length());
    }

    /**
     * Test method for {@link LineSeparator#charAt(int)}.
     */
    @Test
    void charAt() {
        assertEquals(STRING_LF.charAt(0), LF.charAt(0));
        assertEquals(STRING_CR.charAt(0), CR.charAt(0));
        assertEquals(STRING_CR_LF.charAt(0), CR_LF.charAt(0));
        assertEquals(STRING_CR_LF.charAt(1), CR_LF.charAt(1));

        assertEquals('\n', LF.charAt(0));
        assertEquals('\r', CR.charAt(0));
        assertEquals('\r', CR_LF.charAt(0));
        assertEquals('\n', CR_LF.charAt(1));

        assertThrows(IndexOutOfBoundsException.class, () -> LF.charAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> CR.charAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> CR_LF.charAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> LF.charAt(1));
        assertThrows(IndexOutOfBoundsException.class, () -> CR.charAt(1));
        assertThrows(IndexOutOfBoundsException.class, () -> CR_LF.charAt(2));
    }

    /**
     * Test method for {@link LineSeparator#isEmpty()}.
     */
    @SuppressWarnings("ConstantValue")
    @Test
    void isEmpty() {
        assertEquals(STRING_LF.isEmpty(), LF.isEmpty());
        assertEquals(STRING_CR.isEmpty(), CR.isEmpty());
        assertEquals(STRING_CR_LF.isEmpty(), CR_LF.isEmpty());

        assertFalse(LF.isEmpty());
        assertFalse(CR.isEmpty());
        assertFalse(CR_LF.isEmpty());
    }

    /**
     * Test method for {@link LineSeparator#subSequence(int, int)}.
     */
    @Test
    void subSequence() {
        assertEquals(STRING_LF.subSequence(0, 1), LF.subSequence(0, 1));
        assertEquals(STRING_CR.subSequence(0, 1), CR.subSequence(0, 1));
        assertEquals(STRING_CR_LF.subSequence(0, 1), CR_LF.subSequence(0, 1));
        assertEquals(STRING_CR_LF.subSequence(0, 2), CR_LF.subSequence(0, 2));
        assertEquals(STRING_CR_LF.subSequence(1, 2), CR_LF.subSequence(1, 2));

        assertEquals("\n", LF.subSequence(0, 1));
        assertEquals("\r", CR.subSequence(0, 1));
        assertEquals("\r", CR_LF.subSequence(0, 1));
        assertEquals("\r\n", CR_LF.subSequence(0, 2));
        assertEquals("\n", CR_LF.subSequence(1, 2));

        assertThrows(IndexOutOfBoundsException.class, () -> LF.subSequence(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> CR.subSequence(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> CR_LF.subSequence(-1, 0));

        assertThrows(IndexOutOfBoundsException.class, () -> LF.subSequence(1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> CR.subSequence(1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> CR_LF.subSequence(1, 0));

        assertThrows(IndexOutOfBoundsException.class, () -> LF.subSequence(1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> CR.subSequence(1, 2));
    }

    /**
     * Test method for {@link LineSeparator#supplier()}.
     */
    @Test
    void supplier() {
        Supplier<String> supplierLF = LF.supplier();
        assertEquals(STRING_LF, supplierLF.get());
        assertEquals(STRING_LF, supplierLF.get());
        assertEquals(STRING_LF, supplierLF.get());
        assertNotSame(supplierLF, LF.supplier());

        Supplier<String> supplierCR = CR.supplier();
        assertEquals(STRING_CR, supplierCR.get());
        assertEquals(STRING_CR, supplierCR.get());
        assertEquals(STRING_CR, supplierCR.get());
        assertNotSame(supplierCR, CR.supplier());

        Supplier<String> supplierCRLF = CR_LF.supplier();
        assertEquals(STRING_CR_LF, supplierCRLF.get());
        assertEquals(STRING_CR_LF, supplierCRLF.get());
        assertEquals(STRING_CR_LF, supplierCRLF.get());
        assertNotSame(supplierCRLF, CR_LF.supplier());
    }

    /**
     * Test method for {@link LineSeparator#stream()}.
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void stream() {
        assertEquals(1, LF.stream().count());
        assertEquals(1, CR.stream().count());
        assertEquals(1, CR_LF.stream().count());

        assertEquals(Optional.of(STRING_LF), LF.stream().findFirst());
        assertEquals(Optional.of(STRING_CR), CR.stream().findFirst());
        assertEquals(Optional.of(STRING_CR_LF), CR_LF.stream().findFirst());

        assertFalse(LF.stream().isParallel());
        assertFalse(CR.stream().isParallel());
        assertFalse(CR_LF.stream().isParallel());

        assertNotSame(LF.stream(), LF.stream());
        assertNotSame(CR.stream(), CR.stream());
        assertNotSame(CR_LF.stream(), CR_LF.stream());
    }

    /**
     * Test method for {@link LineSeparator#chars()}.
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void chars() {
        assertEquals(1, LF.chars().count());
        assertEquals(1, CR.chars().count());
        assertEquals(2, CR_LF.chars().count());

        assertEquals(OptionalInt.of(STRING_LF.charAt(0)), LF.chars().findFirst());
        assertEquals(OptionalInt.of(STRING_CR.charAt(0)), CR.chars().findFirst());
        assertEquals(OptionalInt.of(STRING_CR_LF.charAt(0)), CR_LF.chars().findFirst());
        assertEquals(OptionalInt.of(STRING_CR_LF.charAt(1)), CR_LF.chars().skip(1).findFirst());

        assertFalse(LF.chars().isParallel());
        assertFalse(CR.chars().isParallel());
        assertFalse(CR_LF.chars().isParallel());

        assertNotSame(LF.chars(), LF.chars());
        assertNotSame(CR.chars(), CR.chars());
        assertNotSame(CR_LF.chars(), CR_LF.chars());
    }

    /**
     * Test method for {@link LineSeparator#codePoints()}.
     */
    @SuppressWarnings("EqualsWithItself")
    @Test
    void codePoints() {
        assertEquals(1, LF.codePoints().count());
        assertEquals(1, CR.codePoints().count());
        assertEquals(2, CR_LF.codePoints().count());

        assertEquals(OptionalInt.of(STRING_LF.charAt(0)), LF.codePoints().findFirst());
        assertEquals(OptionalInt.of(STRING_CR.charAt(0)), CR.codePoints().findFirst());
        assertEquals(OptionalInt.of(STRING_CR_LF.charAt(0)), CR_LF.codePoints().findFirst());
        assertEquals(OptionalInt.of(STRING_CR_LF.charAt(1)), CR_LF.codePoints().skip(1).findFirst());

        assertFalse(LF.codePoints().isParallel());
        assertFalse(CR.codePoints().isParallel());
        assertFalse(CR_LF.codePoints().isParallel());

        assertNotSame(LF.codePoints(), LF.codePoints());
        assertNotSame(CR.codePoints(), CR.codePoints());
        assertNotSame(CR_LF.codePoints(), CR_LF.codePoints());
    }

    /**
     * Test method for {@link LineSeparator#bytes(java.nio.charset.Charset)}.
     */
    @SuppressWarnings("MagicNumber")
    @Test
    void bytes() {
        assertArrayEquals(STRING_LF.getBytes(StandardCharsets.US_ASCII), LF.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(STRING_CR.getBytes(StandardCharsets.US_ASCII), CR.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(STRING_CR_LF.getBytes(StandardCharsets.US_ASCII), CR_LF.bytes(StandardCharsets.US_ASCII));

        assertArrayEquals(new byte[]{0x0A}, LF.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(new byte[]{0x0D}, CR.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(new byte[]{0x0D, 0x0A}, CR_LF.bytes(StandardCharsets.US_ASCII));

        assertArrayEquals(new byte[]{0x0A}, LF.bytes(StandardCharsets.UTF_8));
        assertArrayEquals(new byte[]{0x0D}, CR.bytes(StandardCharsets.UTF_8));
        assertArrayEquals(new byte[]{0x0D, 0x0A}, CR_LF.bytes(StandardCharsets.UTF_8));

        assertNotSame(LF.bytes(StandardCharsets.US_ASCII), LF.bytes(StandardCharsets.US_ASCII));
        assertNotSame(CR.bytes(StandardCharsets.US_ASCII), CR.bytes(StandardCharsets.US_ASCII));
        assertNotSame(CR_LF.bytes(StandardCharsets.US_ASCII), CR_LF.bytes(StandardCharsets.US_ASCII));

        assertArrayEquals(LF.bytes(StandardCharsets.US_ASCII), LF.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(CR.bytes(StandardCharsets.US_ASCII), CR.bytes(StandardCharsets.US_ASCII));
        assertArrayEquals(CR_LF.bytes(StandardCharsets.US_ASCII), CR_LF.bytes(StandardCharsets.US_ASCII));
    }

    /**
     * Test method for {@link LineSeparator#regex()}.
     */
    @Test
    void regex() {
        assertEquals("\\n", LF.regex());
        assertEquals("\\r", CR.regex());
        assertEquals("\\r\\n", CR_LF.regex());
    }

    /**
     * Test method for {@link LineSeparator#escapedJavaString()}.
     */
    @Test
    void escapedJavaString() {
        assertEquals("\\n", LF.escapedJavaString());
        assertEquals("\\r", CR.escapedJavaString());
        assertEquals("\\r\\n", CR_LF.escapedJavaString());
    }

    /**
     * Test method for {@link LineSeparator#toString()}.
     */
    @Test
    void testToString() {
        assertEquals(STRING_LF, LF.toString());
        assertEquals(STRING_CR, CR.toString());
        assertEquals(STRING_CR_LF, CR_LF.toString());
    }

}