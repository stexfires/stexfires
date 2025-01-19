package stexfires.util;

import org.jspecify.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * Enum with common charset names.
 * The method {@code charset()} returns a charset ({@code java.nio.charset.Charset}) object.
 * <p>
 * Example: {@code Charset c = CommonCharsetNames.WINDOWS_1252.charset();}
 * <p>
 * Source: <a href="https://docs.oracle.com/en/java/javase/21/intl/supported-encodings.html#GUID-E20951E6-C420-4D2F-A6BE-1470B4D55B3B">supported-encodings</a>
 * <p>
 * The ordinal of the enum values can change in future versions.
 *
 * @see java.nio.charset.StandardCharsets
 * @see java.nio.charset.Charset
 * @since 0.1
 */
@SuppressWarnings({"SpellCheckingInspection"})
public enum CommonCharsetNames {

    /**
     * CESU-8 or CESU8
     * <p>Description: Unicode CESU-8
     * <p>Aliases: CESU8 csCESU-8
     */
    CESU_8("CESU-8"),
    /**
     * GB18030 or GB18030
     * <p>Description: Simplified Chinese, PRC standard
     * <p>Aliases: gb18030-2022 or gb18030-2000 if the system property and value jdk.charset.GB18030=2000 are specified
     */
    GB18030("GB18030"),
    /**
     * IBM00858 or Cp858
     * <p>Description: Variant of Cp850 with Euro character
     * <p>Aliases: cp858 ccsid00858 cp00858 858 PC-Multilingual-850+euro
     */
    IBM00858("IBM00858"),
    /**
     * IBM437 or Cp437
     * <p>Description: MS-DOS United States, Australia, New Zealand, South Africa
     * <p>Aliases: cp437 ibm437 ibm-437 437 cspc8codepage437 windows-437
     */
    IBM437("IBM437"),
    /**
     * IBM775 or Cp775
     * <p>Description: PC Baltic
     * <p>Aliases: cp775 ibm775 ibm-775 775
     */
    IBM775("IBM775"),
    /**
     * IBM850 or Cp850
     * <p>Description: MS-DOS Latin-1
     * <p>Aliases: cp850 ibm-850 ibm850 850 cspc850multilingual
     */
    IBM850("IBM850"),
    /**
     * IBM852 or Cp852
     * <p>Description: MS-DOS Latin-2
     * <p>Aliases: cp852 ibm852 ibm-852 852 csPCp852
     */
    IBM852("IBM852"),
    /**
     * IBM855 or Cp855
     * <p>Description: IBM Cyrillic
     * <p>Aliases: cp855 ibm-855 ibm855 855 cspcp855
     */
    IBM855("IBM855"),
    /**
     * IBM857 or Cp857
     * <p>Description: IBM Turkish
     * <p>Aliases: cp857 ibm857 ibm-857 857 csIBM857
     */
    IBM857("IBM857"),
    /**
     * IBM862 or Cp862
     * <p>Description: PC Hebrew
     * <p>Aliases: cp862 ibm862 ibm-862 862 csIBM862 cspc862latinhebrew
     */
    IBM862("IBM862"),
    /**
     * IBM866 or Cp866
     * <p>Description: MS-DOS Russian
     * <p>Aliases: cp866 ibm866 ibm-866 866 csIBM866
     */
    IBM866("IBM866"),
    /**
     * ISO-8859-1 or ISO8859_1
     * <p>Description: ISO-8859-1, Latin Alphabet No. 1
     * <p>Aliases: iso-ir-100 ISO_8859-1 latin1 l1 IBM819 cp819 csISOLatin1 819 IBM-819 ISO8859_1 ISO_8859-1:1987 ISO_8859_1 8859_1 ISO8859-1
     */
    ISO_8859_1("ISO-8859-1"),
    /**
     * ISO-8859-13 or ISO8859_13
     * <p>Description: Latin Alphabet No. 7
     * <p>Aliases: iso8859_13 8859_13 iso_8859-13 ISO8859-13
     */
    ISO_8859_13("ISO-8859-13"),
    /**
     * ISO-8859-15 or ISO8859_15
     * <p>Description: Latin Alphabet No. 9
     * <p>Aliases: ISO_8859-15 Latin-9 csISO885915 8859_15 ISO-8859-15 ISO8859_15 ISO8859-15 IBM923 IBM-923 cp923 923 LATIN0 LATIN9 L9 csISOlatin0 csISOlatin9 ISO8859_15_FDIS
     */
    ISO_8859_15("ISO-8859-15"),
    /**
     * ISO-8859-16 or ISO8859_16
     * <p>Description: Latin Alphabet No. 10 or South-Eastern European
     * <p>Aliases: iso-ir-226 ISO_8859-16:2001 ISO_8859-16 latin10 l10 csISO885916
     */
    ISO_8859_16("ISO-8859-16"),
    /**
     * ISO-8859-2 or ISO8859_2
     * <p>Description: Latin Alphabet No. 2
     * <p>Aliases: iso8859_2 8859_2 iso-ir-101 ISO_8859-2 ISO_8859-2:1987 ISO8859-2 latin2 l2 ibm912 ibm-912 cp912 912 csISOLatin2
     */
    ISO_8859_2("ISO-8859-2"),
    /**
     * ISO-8859-4 or ISO8859_4
     * <p>Description: Latin Alphabet No. 4
     * <p>Aliases: iso8859_4 iso8859-4 8859_4 iso-ir-110 ISO_8859-4 ISO_8859-4:1988 latin4 l4 ibm914 ibm-914 cp914 914 csISOLatin4
     */
    ISO_8859_4("ISO-8859-4"),
    /**
     * ISO-8859-5 or ISO8859_5
     * <p>Description: Latin/Cyrillic Alphabet
     * <p>Aliases: iso8859_5 8859_5 iso-ir-144 ISO_8859-5 ISO_8859-5:1988 ISO8859-5 cyrillic ibm915 ibm-915 cp915 915 csISOLatinCyrillic
     */
    ISO_8859_5("ISO-8859-5"),
    /**
     * ISO-8859-7 or ISO8859_7
     * <p>Description: Latin/Greek Alphabet (ISO-8859-7:2003)
     * <p>Aliases: iso8859_7 8859_7 iso-ir-126 ISO_8859-7 ISO_8859-7:1987 ELOT_928 ECMA-118 greek greek8 csISOLatinGreek sun_eu_greek ibm813 ibm-813 813 cp813 iso8859-7
     */
    ISO_8859_7("ISO-8859-7"),
    /**
     * ISO-8859-9 or ISO8859_9
     * <p>Description: Latin Alphabet No. 5
     * <p>Aliases: iso8859_9 8859_9 iso-ir-148 ISO_8859-9 ISO_8859-9:1989 ISO8859-9 latin5 l5 ibm920 ibm-920 920 cp920 csISOLatin5
     */
    ISO_8859_9("ISO-8859-9"),
    /**
     * KOI8-R or KOI8_R
     * <p>Description: KOI8-R, Russian
     * <p>Aliases: koi8_r koi8 cskoi8r
     */
    KOI8_R("KOI8-R"),
    /**
     * KOI8-U or KOI8_U
     * <p>Description: KOI8-U, Ukrainian
     * <p>Aliases: koi8_u
     */
    KOI8_U("KOI8-U"),
    /**
     * US-ASCII or ASCII
     * <p>Description: American Standard Code for Information Interchange
     * <p>Aliases: iso-ir-6 ANSI_X3.4-1986 ISO_646.irv:1991 ASCII ISO646-US us IBM367 cp367 csASCII default 646 iso_646.irv:1983 ANSI_X3.4-1968 ascii7
     */
    US_ASCII("US-ASCII"),
    /**
     * UTF-16 or UTF-16
     * <p>Description: Sixteen-bit Unicode (or UCS) Transformation Format, byte order identified by an optional byte-order mark
     * <p>Aliases: UTF_16 utf16 unicode UnicodeBig
     */
    UTF_16("UTF-16"),
    /**
     * UTF-16BE or UnicodeBigUnmarked
     * <p>Description: Sixteen-bit Unicode (or UCS) Transformation Format, big-endian byte order
     * <p>Aliases: UTF_16BE ISO-10646-UCS-2 X-UTF-16BE UnicodeBigUnmarked
     */
    UTF_16BE("UTF-16BE"),
    /**
     * UTF-16LE or UnicodeLittleUnmarked
     * <p>Description: Sixteen-bit Unicode (or UCS) Transformation Format, little-endian byte order
     * <p>Aliases: UTF_16LE X-UTF-16LE UnicodeLittleUnmarked
     */
    UTF_16LE("UTF-16LE"),
    /**
     * UTF-32 or UTF-32
     * <p>Description: 32-bit Unicode (or UCS) Transformation Format, byte order identified by an optional byte-order mark
     * <p>Aliases: UTF_32 UTF32
     */
    UTF_32("UTF-32"),
    /**
     * UTF-32BE or UTF-32BE
     * <p>Description: 32-bit Unicode (or UCS) Transformation Format, big-endian byte order
     * <p>Aliases: UTF_32BE X-UTF-32BE
     */
    UTF_32BE("UTF-32BE"),
    /**
     * UTF-32LE or UTF-32LE
     * <p>Description: 32-bit Unicode (or UCS) Transformation Format, little-endian byte order
     * <p>Aliases: UTF_32LE X-UTF-32LE
     */
    UTF_32LE("UTF-32LE"),
    /**
     * UTF-8 or UTF8
     * <p>Description: Eight-bit Unicode (or UCS) Transformation Format
     * <p>Aliases: UTF8 unicode-1-1-utf-8
     */
    UTF_8("UTF-8"),
    /**
     * windows-1250 or Cp1250
     * <p>Description: Windows Eastern European
     * <p>Aliases: cp1250 cp5346
     */
    WINDOWS_1250("windows-1250"),
    /**
     * windows-1251 or Cp1251
     * <p>Description: Windows Cyrillic
     * <p>Aliases: cp1251 cp5347 ansi-1251
     */
    WINDOWS_1251("windows-1251"),
    /**
     * windows-1252 or Cp1252
     * <p>Description: Windows Latin-1
     * <p>Aliases: cp1252 cp5348 ibm-1252 ibm1252
     */
    WINDOWS_1252("windows-1252"),
    /**
     * windows-1253 or Cp1253
     * <p>Description: Windows Greek
     * <p>Aliases: cp1253 cp5349
     */
    WINDOWS_1253("windows-1253"),
    /**
     * windows-1254 or Cp1254
     * <p>Description: Windows Turkish
     * <p>Aliases: cp1254 cp5350
     */
    WINDOWS_1254("windows-1254"),
    /**
     * windows-1257 or Cp1257
     * <p>Description: Windows Baltic
     * <p>Aliases: cp1257 cp5353
     */
    WINDOWS_1257("windows-1257"),
    /**
     * x-IBM737 or Cp737
     * <p>Description: PC Greek
     * <p>Aliases: cp737 ibm737 ibm-737 737
     */
    X_IBM737("x-IBM737"),
    /**
     * x-IBM874 or Cp874
     * <p>Description: IBM Thai
     * <p>Aliases: cp874 ibm874 ibm-874 874
     */
    X_IBM874("x-IBM874"),
    /**
     * x-UTF-16LE-BOM or UnicodeLittle
     * <p>Description: Sixteen-bit Unicode (or UCS) Transformation Format, little-endian byte order, with byte-order mark
     * <p>Aliases: UnicodeLittle
     */
    X_UTF_16LE_BOM("x-UTF-16LE-BOM"),
    /**
     * X-UTF-32BE-BOM or X-UTF-32BE-BOM
     * <p>Description: 32-bit Unicode (or UCS) Transformation Format, big-endian byte order, with byte-order mark
     * <p>Aliases: UTF_32BE_BOM UTF-32BE-BOM
     */
    X_UTF_32BE_BOM("X-UTF-32BE-BOM"),
    /**
     * X-UTF-32LE-BOM or X-UTF-32LE-BOM
     * <p>Description: 32-bit Unicode (or UCS) Transformation Format, little-endian byte order, with byte-order mark
     * <p>Aliases: UTF_32LE_BOM UTF-32LE-BOM
     */
    X_UTF_32LE_BOM("X-UTF-32LE-BOM");

    private final String canonicalName;

    /**
     * Constructs a {@code CommonCharsetNames} with the specified canonical name.
     *
     * @param canonicalName canonical name
     * @see CommonCharsetNames#canonicalName()
     */
    CommonCharsetNames(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    /**
     * A static method to get the {@code CommonCharsetNames} from the {@code StandardCharsets} constants.
     *
     * @param standardCharset one of the six known constants from {@code StandardCharsets}. Must not be {@code null}.
     * @throws java.lang.IllegalArgumentException if the {@code Charset} is not one of the six known constants from {@code StandardCharsets}.
     * @see java.nio.charset.StandardCharsets
     */
    public static CommonCharsetNames ofStandardCharset(Charset standardCharset) {
        Objects.requireNonNull(standardCharset);
        if (StandardCharsets.US_ASCII.equals(standardCharset)) {
            return US_ASCII;
        } else if (StandardCharsets.ISO_8859_1.equals(standardCharset)) {
            return ISO_8859_1;
        } else if (StandardCharsets.UTF_8.equals(standardCharset)) {
            return UTF_8;
        } else if (StandardCharsets.UTF_16BE.equals(standardCharset)) {
            return UTF_16BE;
        } else if (StandardCharsets.UTF_16LE.equals(standardCharset)) {
            return UTF_16LE;
        } else if (StandardCharsets.UTF_16.equals(standardCharset)) {
            return UTF_16;
        }
        throw new IllegalArgumentException("Charset is not a standard charset! " + standardCharset);
    }

    /**
     * Returns the {@code CommonCharsetNames} matching the passed canonical name.
     *
     * @param canonicalName Optional canonical name for the lookup. Can be {@code null}.
     * @return {@code CommonCharsetNames} as {@code Optional} matching the passed canonical name.
     * @see CommonCharsetNames#canonicalName()
     */
    public static Optional<CommonCharsetNames> lookup(@Nullable String canonicalName) {
        return Arrays.stream(CommonCharsetNames.values())
                     .filter(commonCharsetNames -> commonCharsetNames.canonicalName().equals(canonicalName))
                     .findFirst();
    }

    /**
     * Returns the canonical name of the {@code CommonCharsetNames} which can be used for the {@code java.nio} API.
     *
     * @return the canonical name
     */
    public final String canonicalName() {
        return canonicalName;
    }

    /**
     * Returns a {@code Charset} object for the {@code CommonCharsetNames}.
     *
     * @return a {@code Charset} object
     * @throws UnsupportedCharsetException If no support for the named charset is available
     *                                     in this instance of the Java virtual machine
     * @see java.nio.charset.Charset
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