package stexfires.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static stexfires.util.LineSeparator.CR;
import static stexfires.util.LineSeparator.CR_LF;
import static stexfires.util.LineSeparator.LF;

@SuppressWarnings("HardcodedLineSeparator")
class LineSeparatorTest {

    private static final String STRING_LF = "\n";
    private static final String STRING_CR = "\r";
    private static final String STRING_CR_LF = "\r\n";

    @Test
    void name() {
        assertEquals("LF", LF.name());
        assertEquals("CR", CR.name());
        assertEquals("CR_LF", CR_LF.name());
    }

    @Test
    void ordinal() {
        assertEquals(0, LF.ordinal());
        assertEquals(1, CR.ordinal());
        assertEquals(2, CR_LF.ordinal());
    }

    @Test
    void string() {
        assertEquals(STRING_LF, LF.string());
        assertEquals(STRING_CR, CR.string());
        assertEquals(STRING_CR_LF, CR_LF.string());
    }

    @Test
    void string_count() {
        assertEquals("", LF.string(0));
        assertEquals("", CR.string(0));
        assertEquals("", CR_LF.string(0));

        assertEquals(STRING_LF, LF.string(1));
        assertEquals(STRING_CR, CR.string(1));
        assertEquals(STRING_CR_LF, CR_LF.string(1));

        assertEquals(STRING_LF + STRING_LF, LF.string(2));
        assertEquals(STRING_CR + STRING_CR, CR.string(2));
        assertEquals(STRING_CR_LF + STRING_CR_LF, CR_LF.string(2));

        assertEquals(STRING_LF + STRING_LF + STRING_LF, LF.string(3));
        assertEquals(STRING_CR + STRING_CR + STRING_CR, CR.string(3));
        assertEquals(STRING_CR_LF + STRING_CR_LF + STRING_CR_LF, CR_LF.string(3));

        assertThrows(IllegalArgumentException.class, () -> LF.string(-1));
        assertThrows(IllegalArgumentException.class, () -> CR.string(-1));
        assertThrows(IllegalArgumentException.class, () -> CR_LF.string(-1));
    }

    @Test
    void length() {
        assertEquals(1, LF.length());
        assertEquals(1, CR.length());
        assertEquals(2, CR_LF.length());
    }

    @Test
    void supplier() {
        Supplier<String> supplierLF = LF.supplier();
        assertEquals(STRING_LF, supplierLF.get());
        assertEquals(STRING_LF, supplierLF.get());
        assertEquals(STRING_LF, supplierLF.get());

        Supplier<String> supplierCR = CR.supplier();
        assertEquals(STRING_CR, supplierCR.get());
        assertEquals(STRING_CR, supplierCR.get());
        assertEquals(STRING_CR, supplierCR.get());

        Supplier<String> supplierCRLF = CR_LF.supplier();
        assertEquals(STRING_CR_LF, supplierCRLF.get());
        assertEquals(STRING_CR_LF, supplierCRLF.get());
        assertEquals(STRING_CR_LF, supplierCRLF.get());
    }

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
    }

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
    }

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
    }

    @Test
    void testToString() {
        assertEquals(STRING_LF, LF.toString());
        assertEquals(STRING_CR, CR.toString());
        assertEquals(STRING_CR_LF, CR_LF.toString());
    }

    @Test
    void systemLineSeparator() {
        assertEquals(System.lineSeparator(), LineSeparator.systemLineSeparator().string());
    }

}