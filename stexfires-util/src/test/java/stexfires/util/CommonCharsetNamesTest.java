package stexfires.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static stexfires.util.CommonCharsetNames.ISO_8859_1;
import static stexfires.util.CommonCharsetNames.ISO_8859_15;
import static stexfires.util.CommonCharsetNames.US_ASCII;
import static stexfires.util.CommonCharsetNames.UTF_16;
import static stexfires.util.CommonCharsetNames.UTF_16BE;
import static stexfires.util.CommonCharsetNames.UTF_16LE;
import static stexfires.util.CommonCharsetNames.UTF_8;
import static stexfires.util.CommonCharsetNames.WINDOWS_1252;

/**
 * Tests for {@link CommonCharsetNames}.
 */
class CommonCharsetNamesTest {

    /**
     * Test method for {@link CommonCharsetNames#ofStandardCharset(Charset)}.
     */
    @Test
    void ofStandardCharset() {
        assertEquals(US_ASCII, CommonCharsetNames.ofStandardCharset(StandardCharsets.US_ASCII));
        assertEquals(ISO_8859_1, CommonCharsetNames.ofStandardCharset(StandardCharsets.ISO_8859_1));
        assertEquals(UTF_8, CommonCharsetNames.ofStandardCharset(StandardCharsets.UTF_8));
        assertEquals(UTF_16BE, CommonCharsetNames.ofStandardCharset(StandardCharsets.UTF_16BE));
        assertEquals(UTF_16LE, CommonCharsetNames.ofStandardCharset(StandardCharsets.UTF_16LE));
        assertEquals(UTF_16, CommonCharsetNames.ofStandardCharset(StandardCharsets.UTF_16));

        assertThrows(IllegalArgumentException.class, () -> CommonCharsetNames.ofStandardCharset(Charset.forName("ISO-8859-15")));
        assertThrows(IllegalArgumentException.class, () -> CommonCharsetNames.ofStandardCharset(Charset.forName("windows-1252")));

        assertThrows(NullPointerException.class, () -> CommonCharsetNames.ofStandardCharset(null));
    }

    /**
     * Test method for {@link CommonCharsetNames#name()}.
     */
    @Test
    void name() {
        assertEquals("US_ASCII", US_ASCII.name());
        assertEquals("ISO_8859_1", ISO_8859_1.name());
        assertEquals("UTF_8", UTF_8.name());
        assertEquals("UTF_16BE", UTF_16BE.name());
        assertEquals("UTF_16LE", UTF_16LE.name());
        assertEquals("UTF_16", UTF_16.name());

        assertEquals("ISO_8859_15", ISO_8859_15.name());
        assertEquals("WINDOWS_1252", WINDOWS_1252.name());
    }

    /**
     * Test method for {@link CommonCharsetNames#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("US_ASCII", US_ASCII.toString());
        assertEquals("ISO_8859_1", ISO_8859_1.toString());
        assertEquals("UTF_8", UTF_8.toString());
        assertEquals("UTF_16BE", UTF_16BE.toString());
        assertEquals("UTF_16LE", UTF_16LE.toString());
        assertEquals("UTF_16", UTF_16.toString());

        assertEquals("ISO_8859_15", ISO_8859_15.toString());
        assertEquals("WINDOWS_1252", WINDOWS_1252.toString());
    }

    /**
     * Test method for {@link CommonCharsetNames#canonicalName()}.
     */
    @Test
    void canonicalName() {
        assertEquals("US-ASCII", US_ASCII.canonicalName());
        assertEquals("ISO-8859-1", ISO_8859_1.canonicalName());
        assertEquals("UTF-8", UTF_8.canonicalName());
        assertEquals("UTF-16BE", UTF_16BE.canonicalName());
        assertEquals("UTF-16LE", UTF_16LE.canonicalName());
        assertEquals("UTF-16", UTF_16.canonicalName());

        assertEquals("ISO-8859-15", ISO_8859_15.canonicalName());
        assertEquals("windows-1252", WINDOWS_1252.canonicalName());
    }

    /**
     * Test method for {@link CommonCharsetNames#charset()}.
     */
    @Test
    void charset() {
        assertEquals(StandardCharsets.US_ASCII, US_ASCII.charset());
        assertEquals(StandardCharsets.ISO_8859_1, ISO_8859_1.charset());
        assertEquals(StandardCharsets.UTF_8, UTF_8.charset());
        assertEquals(StandardCharsets.UTF_16BE, UTF_16BE.charset());
        assertEquals(StandardCharsets.UTF_16LE, UTF_16LE.charset());
        assertEquals(StandardCharsets.UTF_16, UTF_16.charset());

        assertEquals(Charset.forName("ISO-8859-15"), ISO_8859_15.charset());
        assertEquals(Charset.forName("windows-1252"), WINDOWS_1252.charset());
    }

}