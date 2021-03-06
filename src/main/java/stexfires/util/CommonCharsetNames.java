package stexfires.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Enum with common charset names.
 * The method {@code charset()} returns a charset ({@code java.nio.charset.Charset}) object.
 * <p>
 * Example: {@code Charset c = CommonCharsetNames.WINDOWS_1252.charset();}
 * <p>
 * Source: <a href="https://docs.oracle.com/en/java/javase/11/intl/supported-encodings.html">https://docs.oracle.com/en/java/javase/11/intl/supported-encodings.html</a>
 *
 * @author Mathias Kalb
 * @see java.nio.charset.StandardCharsets
 * @see java.nio.charset.Charset
 * @since 0.1
 */
public enum CommonCharsetNames {

    /**
     * IBM00858 or Cp858 : Variant of Cp850 with Euro character
     */
    IBM00858("IBM00858"),
    /**
     * IBM437 or Cp437 : MS-DOS United States, Australia, New Zealand, South Africa
     */
    IBM437("IBM437"),
    /**
     * IBM775 or Cp775 : PC Baltic
     */
    IBM775("IBM775"),
    /**
     * IBM850 or Cp850 : MS-DOS Latin-1
     */
    IBM850("IBM850"),
    /**
     * IBM852 or Cp852 : MS-DOS Latin-2
     */
    IBM852("IBM852"),
    /**
     * IBM855 or Cp855 : IBM Cyrillic
     */
    IBM855("IBM855"),
    /**
     * IBM857 or Cp857 : IBM Turkish
     */
    IBM857("IBM857"),
    /**
     * IBM862 or Cp862 : PC Hebrew
     */
    IBM862("IBM862"),
    /**
     * IBM866 or Cp866 : MS-DOS Russian
     */
    IBM866("IBM866"),
    /**
     * ISO-8859-1 or ISO8859_1 : ISO-8859-1, Latin Alphabet No. 1
     * <p>
     * Aliases : iso-ir-100, csISOLatin1, latin1, l1, IBM819, CP819
     */
    ISO_8859_1("ISO-8859-1"),
    /**
     * ISO-8859-2 or ISO8859_2 : Latin Alphabet No. 2
     */
    ISO_8859_2("ISO-8859-2"),
    /**
     * ISO-8859-4 or ISO8859_4 : Latin Alphabet No. 4
     */
    ISO_8859_4("ISO-8859-4"),
    /**
     * ISO-8859-5 or ISO8859_5 : Latin/Cyrillic Alphabet
     */
    ISO_8859_5("ISO-8859-5"),
    /**
     * ISO-8859-7 or ISO8859_7 : Latin/Greek Alphabet (ISO-8859-7:2003)
     */
    ISO_8859_7("ISO-8859-7"),
    /**
     * ISO-8859-9 or ISO8859_9 : Latin Alphabet No. 5
     */
    ISO_8859_9("ISO-8859-9"),
    /**
     * ISO-8859-13 or ISO8859_13 : Latin Alphabet No. 7
     */
    ISO_8859_13("ISO-8859-13"),
    /**
     * ISO-8859-15 or ISO8859_15 : Latin Alphabet No. 9
     */
    ISO_8859_15("ISO-8859-15"),
    /**
     * KOI8-R or KOI8_R : KOI8-R, Russian
     */
    KOI8_R("KOI8-R"),
    /**
     * KOI8-U or KOI8_U : KOI8-U, Ukrainian
     */
    KOI8_U("KOI8-U"),
    /**
     * US-ASCII or ASCII : American Standard Code for Information Interchange
     */
    US_ASCII("US-ASCII"),
    /**
     * UTF-8 or UTF8 : Eight-bit Unicode (or UCS) Transformation Format
     */
    UTF_8("UTF-8"),
    /**
     * UTF-16 or UTF-16 : Sixteen-bit Unicode (or UCS) Transformation Format, byte order identified by an optional byte-order mark
     */
    UTF_16("UTF-16"),
    /**
     * UTF-16BE or UnicodeBigUnmarked : Sixteen-bit Unicode (or UCS) Transformation Format, big-endian byte order
     */
    UTF_16BE("UTF-16BE"),
    /**
     * UTF-16LE or UnicodeLittleUnmarked : Sixteen-bit Unicode (or UCS) Transformation Format, little-endian byte order
     */
    UTF_16LE("UTF-16LE"),
    /**
     * UTF-32 or UTF_32 : 32-bit Unicode (or UCS) Transformation Format, byte order identified by an optional byte-order mark
     */
    UTF_32("UTF-32"),
    /**
     * UTF-32BE or UTF_32BE : 32-bit Unicode (or UCS) Transformation Format, big-endian byte order
     */
    UTF_32BE("UTF-32BE"),
    /**
     * UTF-32LE or UTF_32LE : 32-bit Unicode (or UCS) Transformation Format, little-endian byte order
     */
    UTF_32LE("UTF-32LE"),
    /**
     * x-UTF-32BE-BOM or UTF_32BE_BOM : 32-bit Unicode (or UCS) Transformation Format, big-endian byte order, with byte-order mark
     */
    X_UTF_32BE_BOM("x-UTF-32BE-BOM"),
    /**
     * x-UTF-32LE-BOM or UTF_32LE_BOM : 32-bit Unicode (or UCS) Transformation Format, little-endian byte order, with byte-order mark
     */
    X_UTF_32LE_BOM("x-UTF-32LE-BOM"),
    /**
     * windows-1250 or Cp1250 : Windows Eastern European
     */
    WINDOWS_1250("windows-1250"),
    /**
     * windows-1251 or Cp1251 : Windows Cyrillic
     */
    WINDOWS_1251("windows-1251"),
    /**
     * windows-1252 or Cp1252 : Windows Latin-1
     */
    WINDOWS_1252("windows-1252"),
    /**
     * windows-1253 or Cp1253 : Windows Greek
     */
    WINDOWS_1253("windows-1253"),
    /**
     * windows-1254 or Cp1254 : Windows Turkish
     */
    WINDOWS_1254("windows-1254"),
    /**
     * windows-1257 or Cp1257 : Windows Baltic
     */
    WINDOWS_1257("windows-1257"),
    /**
     * x-IBM737 or Cp737 : PC Greek
     */
    X_IBM737("x-IBM737"),
    /**
     * x-IBM874 or Cp874 : IBM Thai
     */
    X_IBM874("x-IBM874"),
    /**
     * x-UTF-16LE-BOM or UnicodeLittle : Sixteen-bit Unicode (or UCS) Transformation Format, little-endian byte order, with byte-order mark
     */
    X_UTF_16LE_BOM("x-UTF-16LE-BOM");

    private final String canonicalName;

    CommonCharsetNames(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    /**
     * Returns the canonical name.
     *
     * @return the canonical name
     */
    public final String getCanonicalName() {
        return canonicalName;
    }

    /**
     * Returns a charset object.
     *
     * @return a charset object
     * @throws UnsupportedCharsetException If no support for the named charset is available
     *                                     in this instance of the Java virtual machine
     * @see java.nio.charset.StandardCharsets
     * @see java.nio.charset.Charset#forName(String)
     */
    public final Charset charset() throws UnsupportedCharsetException {
        return switch (this) {
            case US_ASCII -> StandardCharsets.US_ASCII;
            case ISO_8859_1 -> StandardCharsets.ISO_8859_1;
            case UTF_8 -> StandardCharsets.UTF_8;
            case UTF_16BE -> StandardCharsets.UTF_16BE;
            case UTF_16LE -> StandardCharsets.UTF_16LE;
            case UTF_16 -> StandardCharsets.UTF_16;
            default -> Charset.forName(canonicalName);
        };
    }

}
