package stexfires.util;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link CharsetCoding}.
 */
final class CharsetCodingTest {

    private static final String DECODER_REPLACEMENT_US_ASCII_REPLACE = "*";
    private static final String DECODER_REPLACEMENT_DEFAULT = "\uFFFD";
    private static final String ENCODER_REPLACEMENT_US_ASCII_REPLACE = "#";
    private static final String ENCODER_REPLACEMENT_DEFAULT = "?";
    private static final String REPLACEMENT_NOT_USED = "-";

    private static final CharsetCoding CHARSET_CODING_US_ASCII_IGNORE =
            new CharsetCoding(StandardCharsets.US_ASCII,
                    CharsetCoding.CodingErrors.IGNORE,
                    REPLACEMENT_NOT_USED,
                    REPLACEMENT_NOT_USED);
    private static final CharsetCoding CHARSET_CODING_US_ASCII_REPLACE =
            new CharsetCoding(StandardCharsets.US_ASCII,
                    CharsetCoding.CodingErrors.REPLACE,
                    DECODER_REPLACEMENT_US_ASCII_REPLACE,
                    ENCODER_REPLACEMENT_US_ASCII_REPLACE);

    /**
     * Test method for {@link CharsetCoding#ignoringErrors(java.nio.charset.Charset)}.
     */
    @Test
    void ignoringErrors_charset() {
        CharsetCoding ignoringErrors = CharsetCoding.ignoringErrors(StandardCharsets.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, ignoringErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.IGNORE, ignoringErrors.codingErrors());
        assertNull(ignoringErrors.decoderReplacement());
        assertNull(ignoringErrors.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#ignoringErrors(stexfires.util.CommonCharsetNames)}.
     */
    @Test
    void ignoringErrors_commonCharsetNames() {
        CharsetCoding ignoringErrors = CharsetCoding.ignoringErrors(CommonCharsetNames.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, ignoringErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.IGNORE, ignoringErrors.codingErrors());
        assertNull(ignoringErrors.decoderReplacement());
        assertNull(ignoringErrors.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#replacingErrors(java.nio.charset.Charset, java.lang.String, java.lang.String)}.
     */
    @Test
    void replacingErrors_charset() {
        CharsetCoding replacingErrors = CharsetCoding.replacingErrors(StandardCharsets.US_ASCII, DECODER_REPLACEMENT_US_ASCII_REPLACE, ENCODER_REPLACEMENT_US_ASCII_REPLACE);
        assertEquals(StandardCharsets.US_ASCII, replacingErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, replacingErrors.codingErrors());
        assertEquals(DECODER_REPLACEMENT_US_ASCII_REPLACE, replacingErrors.decoderReplacement());
        assertEquals(ENCODER_REPLACEMENT_US_ASCII_REPLACE, replacingErrors.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#replacingErrors(stexfires.util.CommonCharsetNames, java.lang.String, java.lang.String)}.
     */
    @Test
    void replacingErrors_commonCharsetNames() {
        CharsetCoding replacingErrors = CharsetCoding.replacingErrors(CommonCharsetNames.US_ASCII, DECODER_REPLACEMENT_US_ASCII_REPLACE, ENCODER_REPLACEMENT_US_ASCII_REPLACE);
        assertEquals(StandardCharsets.US_ASCII, replacingErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, replacingErrors.codingErrors());
        assertEquals(DECODER_REPLACEMENT_US_ASCII_REPLACE, replacingErrors.decoderReplacement());
        assertEquals(ENCODER_REPLACEMENT_US_ASCII_REPLACE, replacingErrors.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#replacingErrorsWithDefaults(java.nio.charset.Charset)}.
     */
    @Test
    void replacingErrorsWithDefaults_charset() {
        CharsetCoding replacingErrorsWithDefaults = CharsetCoding.replacingErrorsWithDefaults(StandardCharsets.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, replacingErrorsWithDefaults.charset());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, replacingErrorsWithDefaults.codingErrors());
        assertNull(replacingErrorsWithDefaults.decoderReplacement());
        assertNull(replacingErrorsWithDefaults.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#replacingErrorsWithDefaults(stexfires.util.CommonCharsetNames)}.
     */
    @Test
    void replacingErrorsWithDefaults_commonCharsetNames() {
        CharsetCoding replacingErrorsWithDefaults = CharsetCoding.replacingErrorsWithDefaults(CommonCharsetNames.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, replacingErrorsWithDefaults.charset());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, replacingErrorsWithDefaults.codingErrors());
        assertNull(replacingErrorsWithDefaults.decoderReplacement());
        assertNull(replacingErrorsWithDefaults.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#reportingErrors(java.nio.charset.Charset)}.
     */
    @Test
    void reportingErrors_charset() {
        CharsetCoding reportingErrors = CharsetCoding.reportingErrors(StandardCharsets.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, reportingErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.REPORT, reportingErrors.codingErrors());
        assertNull(reportingErrors.decoderReplacement());
        assertNull(reportingErrors.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#reportingErrors(stexfires.util.CommonCharsetNames)}.
     */
    @Test
    void reportingErrors_commonCharsetNames() {
        CharsetCoding reportingErrors = CharsetCoding.reportingErrors(CommonCharsetNames.US_ASCII);
        assertEquals(StandardCharsets.US_ASCII, reportingErrors.charset());
        assertEquals(CharsetCoding.CodingErrors.REPORT, reportingErrors.codingErrors());
        assertNull(reportingErrors.decoderReplacement());
        assertNull(reportingErrors.encoderReplacement());
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newDecoder()}.
     */
    @Test
    void newDecoder() {
        CharsetDecoder decoderUsAsciiIgnore = CHARSET_CODING_US_ASCII_IGNORE.newDecoder();
        assertEquals(StandardCharsets.US_ASCII, decoderUsAsciiIgnore.charset());
        assertEquals(CodingErrorAction.IGNORE, decoderUsAsciiIgnore.malformedInputAction());
        assertEquals(CodingErrorAction.IGNORE, decoderUsAsciiIgnore.unmappableCharacterAction());
        assertEquals(DECODER_REPLACEMENT_DEFAULT, decoderUsAsciiIgnore.replacement());

        CharsetDecoder decoderUsAsciiReplace = CHARSET_CODING_US_ASCII_REPLACE.newDecoder();
        assertEquals(StandardCharsets.US_ASCII, decoderUsAsciiReplace.charset());
        assertEquals(CodingErrorAction.REPLACE, decoderUsAsciiReplace.malformedInputAction());
        assertEquals(CodingErrorAction.REPLACE, decoderUsAsciiReplace.unmappableCharacterAction());
        assertEquals(DECODER_REPLACEMENT_US_ASCII_REPLACE, decoderUsAsciiReplace.replacement());

        CharsetDecoder decoderUtf8Ignore = CharsetCoding.UTF_8_IGNORING.newDecoder();
        assertEquals(StandardCharsets.UTF_8, decoderUtf8Ignore.charset());
        assertEquals(CodingErrorAction.IGNORE, decoderUtf8Ignore.malformedInputAction());
        assertEquals(CodingErrorAction.IGNORE, decoderUtf8Ignore.unmappableCharacterAction());
        assertEquals(DECODER_REPLACEMENT_DEFAULT, decoderUtf8Ignore.replacement());

        CharsetDecoder decoderUtf8Replace = CharsetCoding.UTF_8_REPLACING.newDecoder();
        assertEquals(StandardCharsets.UTF_8, decoderUtf8Replace.charset());
        assertEquals(CodingErrorAction.REPLACE, decoderUtf8Replace.malformedInputAction());
        assertEquals(CodingErrorAction.REPLACE, decoderUtf8Replace.unmappableCharacterAction());
        assertEquals(DECODER_REPLACEMENT_DEFAULT, decoderUtf8Replace.replacement());

        CharsetDecoder decoderUtf8Report = CharsetCoding.UTF_8_REPORTING.newDecoder();
        assertEquals(StandardCharsets.UTF_8, decoderUtf8Report.charset());
        assertEquals(CodingErrorAction.REPORT, decoderUtf8Report.malformedInputAction());
        assertEquals(CodingErrorAction.REPORT, decoderUtf8Report.unmappableCharacterAction());
        assertEquals(DECODER_REPLACEMENT_DEFAULT, decoderUtf8Report.replacement());
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newEncoder()}.
     */
    @Test
    void newEncoder() {
        CharsetEncoder encoderUsAsciiIgnore = CHARSET_CODING_US_ASCII_IGNORE.newEncoder();
        assertEquals(StandardCharsets.US_ASCII, encoderUsAsciiIgnore.charset());
        assertEquals(CodingErrorAction.IGNORE, encoderUsAsciiIgnore.malformedInputAction());
        assertEquals(CodingErrorAction.IGNORE, encoderUsAsciiIgnore.unmappableCharacterAction());
        assertArrayEquals(ENCODER_REPLACEMENT_DEFAULT.getBytes(StandardCharsets.US_ASCII), encoderUsAsciiIgnore.replacement());

        CharsetEncoder encoderUsAsciiReplace = CHARSET_CODING_US_ASCII_REPLACE.newEncoder();
        assertEquals(StandardCharsets.US_ASCII, encoderUsAsciiReplace.charset());
        assertEquals(CodingErrorAction.REPLACE, encoderUsAsciiReplace.malformedInputAction());
        assertEquals(CodingErrorAction.REPLACE, encoderUsAsciiReplace.unmappableCharacterAction());
        assertArrayEquals(ENCODER_REPLACEMENT_US_ASCII_REPLACE.getBytes(StandardCharsets.US_ASCII), encoderUsAsciiReplace.replacement());

        CharsetEncoder encoderUtf8Ignore = CharsetCoding.UTF_8_IGNORING.newEncoder();
        assertEquals(StandardCharsets.UTF_8, encoderUtf8Ignore.charset());
        assertEquals(CodingErrorAction.IGNORE, encoderUtf8Ignore.malformedInputAction());
        assertEquals(CodingErrorAction.IGNORE, encoderUtf8Ignore.unmappableCharacterAction());
        assertArrayEquals(ENCODER_REPLACEMENT_DEFAULT.getBytes(StandardCharsets.UTF_8), encoderUtf8Ignore.replacement());

        CharsetEncoder encoderUtf8Replace = CharsetCoding.UTF_8_REPLACING.newEncoder();
        assertEquals(StandardCharsets.UTF_8, encoderUtf8Replace.charset());
        assertEquals(CodingErrorAction.REPLACE, encoderUtf8Replace.malformedInputAction());
        assertEquals(CodingErrorAction.REPLACE, encoderUtf8Replace.unmappableCharacterAction());
        assertArrayEquals(ENCODER_REPLACEMENT_DEFAULT.getBytes(StandardCharsets.UTF_8), encoderUtf8Replace.replacement());

        CharsetEncoder encoderUtf8Report = CharsetCoding.UTF_8_REPORTING.newEncoder();
        assertEquals(StandardCharsets.UTF_8, encoderUtf8Report.charset());
        assertEquals(CodingErrorAction.REPORT, encoderUtf8Report.malformedInputAction());
        assertEquals(CodingErrorAction.REPORT, encoderUtf8Report.unmappableCharacterAction());
        assertArrayEquals(ENCODER_REPLACEMENT_DEFAULT.getBytes(StandardCharsets.UTF_8), encoderUtf8Report.replacement());
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newInputStreamReader(java.io.InputStream)}.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void newInputStreamReader() throws IOException {
        // original text: US-ASCII
        assertEqualsNewInputStreamReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewInputStreamReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewInputStreamReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewInputStreamReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewInputStreamReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_REPORTING);

        // original text: UTF-8
        assertEqualsNewInputStreamReader("abcä", "abc", StandardCharsets.UTF_8, CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewInputStreamReader("abcä", "abc" + DECODER_REPLACEMENT_US_ASCII_REPLACE.repeat(2), StandardCharsets.UTF_8, CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewInputStreamReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewInputStreamReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewInputStreamReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPORTING);

        // original text: ISO-8859-15
        assertEqualsNewInputStreamReader("aä€", "a", CommonCharsetNames.ISO_8859_15.charset(), CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewInputStreamReader("aä€", "a" + DECODER_REPLACEMENT_US_ASCII_REPLACE.repeat(2), CommonCharsetNames.ISO_8859_15.charset(), CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewInputStreamReader("aä€", "a", CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewInputStreamReader("aä€", "a" + DECODER_REPLACEMENT_DEFAULT.repeat(1), CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_REPLACING);
        assertThrows(MalformedInputException.class, () -> assertEqualsNewInputStreamReader("aä€", "aä€", CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_REPORTING));
    }

    @SuppressWarnings("MethodMayBeStatic")
    private void assertEqualsNewInputStreamReader(String sourceText, String expectedText, Charset sourceCharset, CharsetCoding charsetCoding) throws IOException {
        try (var reader = new BufferedReader(charsetCoding.newInputStreamReader(new ByteArrayInputStream(sourceText.getBytes(sourceCharset))))) {
            assertEquals(expectedText, reader.readLine());
        }
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newOutputStreamWriter(java.io.OutputStream)}.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void newOutputStreamWriter() throws IOException {
        // original text: US-ASCII
        assertEqualsNewOutputStreamWriter("abcABD012,.-", "abcABD012,.-", CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewOutputStreamWriter("abcABD012,.-", "abcABD012,.-", CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewOutputStreamWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewOutputStreamWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewOutputStreamWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_REPORTING);

        // original text: UTF-8
        assertEqualsNewOutputStreamWriter("abcäß€", "abc", CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewOutputStreamWriter("abcäß€", "abc" + ENCODER_REPLACEMENT_US_ASCII_REPLACE.repeat(3), CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewOutputStreamWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewOutputStreamWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewOutputStreamWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_REPORTING);

    }

    @SuppressWarnings("MethodMayBeStatic")
    private void assertEqualsNewOutputStreamWriter(String sourceText, String expectedText, CharsetCoding charsetCoding) throws IOException {
        try (var output = new ByteArrayOutputStream(); var writer = new BufferedWriter(charsetCoding.newOutputStreamWriter(output))) {
            writer.write(sourceText);
            writer.flush();
            assertEquals(expectedText, output.toString(charsetCoding.charset()));
        }
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newBufferedReader(java.io.InputStream)}.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void newBufferedReader() throws IOException {
        // original text: US-ASCII
        assertEqualsNewBufferedReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewBufferedReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewBufferedReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewBufferedReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewBufferedReader("abcABD012,.-", "abcABD012,.-", StandardCharsets.US_ASCII, CharsetCoding.UTF_8_REPORTING);

        // original text: UTF-8
        assertEqualsNewBufferedReader("abcä", "abc", StandardCharsets.UTF_8, CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewBufferedReader("abcä", "abc" + DECODER_REPLACEMENT_US_ASCII_REPLACE.repeat(2), StandardCharsets.UTF_8, CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewBufferedReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewBufferedReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewBufferedReader("abcä", "abcä", StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPORTING);

        // original text: ISO-8859-15
        assertEqualsNewBufferedReader("aä€", "a", CommonCharsetNames.ISO_8859_15.charset(), CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewBufferedReader("aä€", "a" + DECODER_REPLACEMENT_US_ASCII_REPLACE.repeat(2), CommonCharsetNames.ISO_8859_15.charset(), CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewBufferedReader("aä€", "a", CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewBufferedReader("aä€", "a" + DECODER_REPLACEMENT_DEFAULT.repeat(1), CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_REPLACING);
        assertThrows(MalformedInputException.class, () -> assertEqualsNewBufferedReader("aä€", "aä€", CommonCharsetNames.ISO_8859_15.charset(), CharsetCoding.UTF_8_REPORTING));

    }

    @SuppressWarnings("MethodMayBeStatic")
    private void assertEqualsNewBufferedReader(String sourceText, String expectedText, Charset sourceCharset, CharsetCoding charsetCoding) throws IOException {
        try (var reader = charsetCoding.newBufferedReader(new ByteArrayInputStream(sourceText.getBytes(sourceCharset)))) {
            assertEquals(expectedText, reader.readLine());
        }
    }

    /**
     * Test method for {@link stexfires.util.CharsetCoding#newBufferedWriter(java.io.OutputStream)}.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void newBufferedWriter() throws IOException {
        // original text: US-ASCII
        assertEqualsNewBufferedWriter("abcABD012,.-", "abcABD012,.-", CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewBufferedWriter("abcABD012,.-", "abcABD012,.-", CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewBufferedWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewBufferedWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewBufferedWriter("abcABD012,.-", "abcABD012,.-", CharsetCoding.UTF_8_REPORTING);

        // original text: UTF-8
        assertEqualsNewBufferedWriter("abcäß€", "abc", CHARSET_CODING_US_ASCII_IGNORE);
        assertEqualsNewBufferedWriter("abcäß€", "abc" + ENCODER_REPLACEMENT_US_ASCII_REPLACE.repeat(3), CHARSET_CODING_US_ASCII_REPLACE);
        assertEqualsNewBufferedWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_IGNORING);
        assertEqualsNewBufferedWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_REPLACING);
        assertEqualsNewBufferedWriter("abcäß€", "abcäß€", CharsetCoding.UTF_8_REPORTING);
    }

    @SuppressWarnings("MethodMayBeStatic")
    private void assertEqualsNewBufferedWriter(String sourceText, String expectedText, CharsetCoding charsetCoding) throws IOException {
        try (var output = new ByteArrayOutputStream(); var writer = charsetCoding.newBufferedWriter(output)) {
            writer.write(sourceText);
            writer.flush();
            assertEquals(expectedText, output.toString(charsetCoding.charset()));
        }
    }

    /**
     * Test method for {@link CharsetCoding#charset()}.
     */
    @Test
    void charset() {
        assertEquals(StandardCharsets.UTF_8, CharsetCoding.UTF_8_IGNORING.charset());
        assertEquals(StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPLACING.charset());
        assertEquals(StandardCharsets.UTF_8, CharsetCoding.UTF_8_REPORTING.charset());
        assertEquals(StandardCharsets.US_ASCII, CHARSET_CODING_US_ASCII_REPLACE.charset());
    }

    /**
     * Test method for {@link CharsetCoding#codingErrors()}.
     */
    @Test
    void codingErrors() {
        assertEquals(CharsetCoding.CodingErrors.IGNORE, CharsetCoding.UTF_8_IGNORING.codingErrors());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, CharsetCoding.UTF_8_REPLACING.codingErrors());
        assertEquals(CharsetCoding.CodingErrors.REPORT, CharsetCoding.UTF_8_REPORTING.codingErrors());
        assertEquals(CharsetCoding.CodingErrors.REPLACE, CHARSET_CODING_US_ASCII_REPLACE.codingErrors());
    }

    /**
     * Test method for {@link CharsetCoding#decoderReplacement()}.
     */
    @Test
    void decoderReplacement() {
        assertNull(CharsetCoding.UTF_8_IGNORING.decoderReplacement());
        assertNull(CharsetCoding.UTF_8_REPLACING.decoderReplacement());
        assertNull(CharsetCoding.UTF_8_REPORTING.decoderReplacement());
        assertEquals(DECODER_REPLACEMENT_US_ASCII_REPLACE, CHARSET_CODING_US_ASCII_REPLACE.decoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding#encoderReplacement()}.
     */
    @Test
    void encoderReplacement() {
        assertNull(CharsetCoding.UTF_8_IGNORING.encoderReplacement());
        assertNull(CharsetCoding.UTF_8_REPLACING.encoderReplacement());
        assertNull(CharsetCoding.UTF_8_REPORTING.encoderReplacement());
        assertEquals(ENCODER_REPLACEMENT_US_ASCII_REPLACE, CHARSET_CODING_US_ASCII_REPLACE.encoderReplacement());
    }

    /**
     * Test method for {@link CharsetCoding.CodingErrors#codingErrorAction()}.
     */
    @Test
    void codingErrorAction() {
        assertEquals(CodingErrorAction.IGNORE, CharsetCoding.CodingErrors.IGNORE.codingErrorAction());
        assertEquals(CodingErrorAction.REPLACE, CharsetCoding.CodingErrors.REPLACE.codingErrorAction());
        assertEquals(CodingErrorAction.REPORT, CharsetCoding.CodingErrors.REPORT.codingErrorAction());
    }

}