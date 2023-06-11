package stexfires.examples.data;

import stexfires.data.BooleanDataTypeFormatter;
import stexfires.data.BooleanDataTypeParser;
import stexfires.data.ConvertingDataTypeFormatter;
import stexfires.data.ConvertingDataTypeParser;
import stexfires.data.DataTypeConverterException;
import stexfires.data.DataTypeFormatter;
import stexfires.data.DataTypeParser;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.util.function.ByteArrayFunctions;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static stexfires.data.DataTypeConverters.URI_TO_URL;
import static stexfires.data.DataTypeConverters.URL_TO_URI;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection", "MagicNumber", "CallToPrintStackTrace"})
public final class ExamplesMiscDataType {

    private ExamplesMiscDataType() {
    }

    private static void testParseBoolean(String source, BooleanDataTypeParser parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseLocale(String source, GenericDataTypeParser<Locale> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + localeAsString(parser.parse(source)));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatBoolean(Boolean source, BooleanDataTypeFormatter formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatLocale(Locale source, GenericDataTypeFormatter<Locale> formatter) {
        try {
            System.out.println("Format: " + localeAsString(source) + ". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: " + localeAsString(source) + ". Error: " + e.getMessage());
        }
    }

    private static String localeAsString(Locale locale) {
        if (locale == null) {
            return "<NULL>";
        }
        return "\"" + locale.toLanguageTag() + "\" (" + locale + " - " + locale.hashCode() + ")";
    }

    private static void testParseCharset(String source, GenericDataTypeParser<Charset> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatCharset(Charset source, GenericDataTypeFormatter<Charset> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    private static void testParseClass(String source, GenericDataTypeParser<Class> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    private static void testFormatClass(Class source, GenericDataTypeFormatter<Class> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testParseByteArray(String source, GenericDataTypeParser<byte[]> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + Arrays.toString(parser.parse(source)));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static void testFormatByteArray(byte[] source, GenericDataTypeFormatter<byte[]> formatter) {
        try {
            System.out.println("Format: \"" + Arrays.toString(source) + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + Arrays.toString(source) + "\". Error: " + e.getMessage());
        }
    }

    private static <T> void testParse(String source, DataTypeParser<T> parser) {
        try {
            System.out.println("Parse: \"" + source + "\". Result: " + parser.parse(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    private static <T> void testFormat(T source, DataTypeFormatter<T> formatter) {
        try {
            System.out.println("Format: \"" + source + "\". Result: " + formatter.format(source));
        } catch (DataTypeConverterException e) {
            System.out.println("Format: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {

        System.out.println("---BooleanDataTypeFormatter");
        testFormatBoolean(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", null));
        testFormatBoolean(null, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormatBoolean(Boolean.TRUE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));
        testFormatBoolean(Boolean.FALSE, new BooleanDataTypeFormatter(() -> "TRUE", () -> "FALSE", () -> "NULL"));

        System.out.println("---BooleanDataTypeParser");
        Set<String> trueValues = Set.of("true", "TRUE");
        Set<String> falseValues = Set.of("false", "FALSE");
        testParseBoolean(null, new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean(null, new BooleanDataTypeParser(trueValues, falseValues, () -> Boolean.TRUE, null));
        testParseBoolean("", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("", new BooleanDataTypeParser(trueValues, falseValues, null, () -> Boolean.TRUE));
        testParseBoolean("true", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("TRUE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("false", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("FALSE", new BooleanDataTypeParser(trueValues, falseValues, null, null));
        testParseBoolean("test", new BooleanDataTypeParser(trueValues, falseValues, null, null));

        System.out.println("---GenericDataTypeFormatter Locale");
        testFormatLocale(null, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(null, GenericDataTypeFormatter.forLocale(null));
        testFormatLocale(null, GenericDataTypeFormatter.forLocaleWithSupplier(() -> Locale.getDefault().toLanguageTag()));
        testFormatLocale(null, GenericDataTypeFormatter.forLocale(Locale.getDefault().toLanguageTag()));
        testFormatLocale(Locale.getDefault(), GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.GERMAN, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.GERMANY, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.ENGLISH, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.US, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.JAPAN, GenericDataTypeFormatter.forLocaleWithSupplier(null));
        testFormatLocale(Locale.forLanguageTag("und"), GenericDataTypeFormatter.forLocaleWithSupplier(null));

        System.out.println("---GenericDataTypeParser Locale");
        testParseLocale(null, GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale(null, GenericDataTypeParser.forLocale(null));
        testParseLocale(null, GenericDataTypeParser.forLocaleWithSuppliers(Locale::getDefault, null));
        testParseLocale("", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("", GenericDataTypeParser.forLocale(null));
        testParseLocale("", GenericDataTypeParser.forLocaleWithSuppliers(null, Locale::getDefault));
        testParseLocale("de", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("de_DE", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("de-DE", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("de-POSIX-x-URP-lvariant-Abc-Def", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("ja-JP", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("ja-JP-x-lvariant-JP", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("ja-JP-u-ca-japanese-x-lvariant-JP", GenericDataTypeParser.forLocaleWithSuppliers(null, null));
        testParseLocale("und", GenericDataTypeParser.forLocaleWithSuppliers(null, null));

        System.out.println("---GenericDataTypeFormatter Charset");
        testFormatCharset(null, GenericDataTypeFormatter.forCharsetWithSupplier(null));
        testFormatCharset(null, GenericDataTypeFormatter.forCharset(null));
        testFormatCharset(null, GenericDataTypeFormatter.forCharsetWithSupplier(StandardCharsets.ISO_8859_1::name));
        testFormatCharset(null, GenericDataTypeFormatter.forCharset(StandardCharsets.ISO_8859_1.name()));
        testFormatCharset(StandardCharsets.ISO_8859_1, GenericDataTypeFormatter.forCharsetWithSupplier(null));

        System.out.println("---GenericDataTypeParser Charset");
        testParseCharset(null, GenericDataTypeParser.forCharsetWithSuppliers(null, null));
        testParseCharset(null, GenericDataTypeParser.forCharset(null));
        testParseCharset(null, GenericDataTypeParser.forCharsetWithSuppliers(() -> StandardCharsets.ISO_8859_1, null));
        testParseCharset(null, GenericDataTypeParser.forCharset(StandardCharsets.ISO_8859_1));
        testParseCharset("", GenericDataTypeParser.forCharsetWithSuppliers(null, null));
        testParseCharset("", GenericDataTypeParser.forCharset(null));
        testParseCharset("", GenericDataTypeParser.forCharsetWithSuppliers(null, () -> StandardCharsets.ISO_8859_1));
        testParseCharset("", GenericDataTypeParser.forCharset(StandardCharsets.ISO_8859_1));
        testParseCharset("ISO-8859-1", GenericDataTypeParser.forCharset(null));
        testParseCharset("test", GenericDataTypeParser.forCharset(null));

        System.out.println("---GenericDataTypeFormatter Class");
        testFormatClass(null, GenericDataTypeFormatter.forClassWithSupplier(null));
        testFormatClass(null, GenericDataTypeFormatter.forClass(null));
        testFormatClass(null, GenericDataTypeFormatter.forClassWithSupplier(Object.class::getName));
        testFormatClass(null, GenericDataTypeFormatter.forClass(Object.class.getName()));
        testFormatClass(Object.class, GenericDataTypeFormatter.forClassWithSupplier(null));
        testFormatClass(Integer.class, GenericDataTypeFormatter.forClassWithSupplier(null));
        testFormatClass(String.class, GenericDataTypeFormatter.forClassWithSupplier(null));
        testFormatClass(ArrayList.class, GenericDataTypeFormatter.forClassWithSupplier(null));

        System.out.println("---GenericDataTypeParser Class");
        testParseClass(null, GenericDataTypeParser.forClassWithSuppliers(null, null));
        testParseClass(null, GenericDataTypeParser.forClass(null));
        testParseClass(null, GenericDataTypeParser.forClassWithSuppliers(() -> Object.class, null));
        testParseClass(null, GenericDataTypeParser.forClass(Object.class));
        testParseClass("", GenericDataTypeParser.forClassWithSuppliers(null, null));
        testParseClass("", GenericDataTypeParser.forClass(null));
        testParseClass("", GenericDataTypeParser.forClassWithSuppliers(null, () -> Object.class));
        testParseClass("", GenericDataTypeParser.forClass(Object.class));
        testParseClass("java.lang.Object", GenericDataTypeParser.forClass(null));
        testParseClass("java.lang.Integer", GenericDataTypeParser.forClass(null));
        testParseClass("java.lang.String", GenericDataTypeParser.forClass(null));
        testParseClass("java.util.ArrayList", GenericDataTypeParser.forClass(null));
        testParseClass("test", GenericDataTypeParser.forClass(null));

        System.out.println("---GenericDataTypeFormatter byte[]");
        testFormatByteArray(null, GenericDataTypeFormatter.forByteArrayWithSupplier(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(null, GenericDataTypeFormatter.forByteArray(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(null, GenericDataTypeFormatter.forByteArrayWithSupplier(ByteArrayFunctions.toHex(), () -> "<NULL>"));
        testFormatByteArray(new byte[]{66, 67}, GenericDataTypeFormatter.forByteArray(ByteArrayFunctions.toHex(), null));
        testFormatByteArray(new byte[]{72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33}, GenericDataTypeFormatter.forByteArray(ByteArrayFunctions.toStringStandard(StandardCharsets.US_ASCII), null));
        testFormatByteArray(new byte[]{66, 67}, GenericDataTypeFormatter.forByteArray(ByteArrayFunctions.toBase64(Base64.getEncoder()), null));

        System.out.println("---GenericDataTypeParser byte[]");
        testParseByteArray(null, GenericDataTypeParser.forByteArrayWithSuppliers(ByteArrayFunctions.fromHex(), null, null));
        testParseByteArray(null, GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), null));
        testParseByteArray(null, GenericDataTypeParser.forByteArrayWithSuppliers(ByteArrayFunctions.fromHex(), () -> new byte[]{}, null));
        testParseByteArray(null, GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("", GenericDataTypeParser.forByteArrayWithSuppliers(ByteArrayFunctions.fromHex(), null, null));
        testParseByteArray("", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), null));
        testParseByteArray("", GenericDataTypeParser.forByteArrayWithSuppliers(ByteArrayFunctions.fromHex(), null, () -> new byte[]{}));
        testParseByteArray("", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("4243", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("4243Z", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromHex(), new byte[]{}));
        testParseByteArray("Hello world!", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromStringStandard(StandardCharsets.US_ASCII), new byte[]{}));
        testParseByteArray("QkM=", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromBase64(Base64.getDecoder()), new byte[]{}));
        testParseByteArray("--", GenericDataTypeParser.forByteArray(ByteArrayFunctions.fromBase64(Base64.getDecoder()), new byte[]{}));

        System.out.println("---GenericDataTypeFormatter Character");
        testFormat('A', GenericDataTypeFormatter.forCharacterWithSupplier(null));
        testFormat('€', GenericDataTypeFormatter.forCharacterWithSupplier(null));

        System.out.println("---GenericDataTypeParser Character");
        testParse("A", GenericDataTypeParser.forCharacter(null));
        testParse("€", GenericDataTypeParser.forCharacter(null));
        testParse("test", GenericDataTypeParser.forCharacter(null));

        System.out.println("---GenericDataTypeFormatter Currency");
        testFormat(Currency.getInstance("EUR"), GenericDataTypeFormatter.forCurrencyWithSupplier(null));
        testFormat(Currency.getInstance(Locale.JAPAN), GenericDataTypeFormatter.forCurrencyWithSupplier(null));

        System.out.println("---GenericDataTypeParser Currency");
        testParse("EUR", GenericDataTypeParser.forCurrency(null));
        testParse("JPY", GenericDataTypeParser.forCurrency(null));
        testParse("test", GenericDataTypeParser.forCurrency(null));

        System.out.println("---GenericDataTypeFormatter UUID");
        testFormat(UUID.randomUUID(), GenericDataTypeFormatter.forUuidWithSupplier(null));
        testFormat(UUID.fromString("0c9ce18d-7d41-4015-bfb4-22fca6689ab7"), GenericDataTypeFormatter.forUuidWithSupplier(null));

        System.out.println("---GenericDataTypeParser UUID");
        testParse("0c9ce18d-7d41-4015-bfb4-22fca6689ab7", GenericDataTypeParser.forUuid(null));
        testParse("test", GenericDataTypeParser.forUuid(null));

        System.out.println("---GenericDataTypeFormatter URI");
        try {
            testFormat(new URI("https://abcd.efgh.ijkl/"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f"), GenericDataTypeFormatter.forUriWithSupplier(true, null));

            testFormat(new URI("http://www.example.com:1080/docs/resource1.html#chapter1"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("mailto:java-net@www.example.com"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("news:comp.lang.java"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("urn:isbn:096139210x"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("http://example.com/languages/java/"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("sample/a/index.html#28"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("../../demo/b/index.html"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
            testFormat(new URI("file:///~/calendar"), GenericDataTypeFormatter.forUriWithSupplier(true, null));
        } catch (URISyntaxException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("---GenericDataTypeParser URI");
        testParse("https://abcd.efgh.ijkl/", GenericDataTypeParser.forUri(true, null));
        testParse("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f", GenericDataTypeParser.forUri(true, null));
        testParse("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f", GenericDataTypeParser.forUri(true, null));

        testParse("http://www.example.com:1080/docs/resource1.html#chapter1", GenericDataTypeParser.forUri(true, null));
        testParse("mailto:java-net@www.example.com", GenericDataTypeParser.forUri(true, null));
        testParse("news:comp.lang.java", GenericDataTypeParser.forUri(true, null));
        testParse("urn:isbn:096139210x", GenericDataTypeParser.forUri(true, null));
        testParse("http://example.com/languages/java/", GenericDataTypeParser.forUri(true, null));
        testParse("sample/a/index.html#28", GenericDataTypeParser.forUri(true, null));
        testParse("../../demo/b/index.html", GenericDataTypeParser.forUri(true, null));
        testParse("file:///~/calendar", GenericDataTypeParser.forUri(true, null));

        testParse("   ", GenericDataTypeParser.forUri(true, null));
        testParse("ä:ä//ä:ä?ä   ", GenericDataTypeParser.forUri(true, null));

        System.out.println("---ConvertingDataTypeFormatter formatterConverterUrlToUri");
        try {
            DataTypeFormatter<URL> dataTypeFormatter = new ConvertingDataTypeFormatter<>(
                    URL_TO_URI,
                    GenericDataTypeFormatter.forUri(true, null),
                    null,
                    null);

            testFormat(new URI("https://abcd.efgh.ijkl/").toURL(), dataTypeFormatter);
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f").toURL(), dataTypeFormatter);
            testFormat(new URI("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f").toURL(), dataTypeFormatter);

            testFormat(new URI("http://www.example.com:1080/docs/resource1.html#chapter1").toURL(), dataTypeFormatter);
            testFormat(new URI("mailto:java-net@www.example.com").toURL(), dataTypeFormatter);
            testFormat(new URI("http://example.com/languages/java/").toURL(), dataTypeFormatter);
            testFormat(new URI("file:///~/calendar").toURL(), dataTypeFormatter);
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("---ConvertingDataTypeParser parserConverterUriToUrl");
        DataTypeParser<URL> urlDataTypeParser = new ConvertingDataTypeParser<>(
                null,
                GenericDataTypeParser.forUri(true, null),
                URI_TO_URL,
                null,
                null);
        testParse("https://abcd.efgh.ijkl/", urlDataTypeParser);
        testParse("https://abcd.efgh.ijkl/test?a=b&ä=äß€&d=e%20f", urlDataTypeParser);
        testParse("https://abcd.efgh.ijkl/test?a=b&%C3%A4=%C3%A4%C3%9F%E2%82%AC&d=e%20f", urlDataTypeParser);

        testParse("http://www.example.com:1080/docs/resource1.html#chapter1", urlDataTypeParser);
        testParse("mailto:java-net@www.example.com", urlDataTypeParser);
        testParse("news:comp.lang.java", urlDataTypeParser);
        testParse("urn:isbn:096139210x", urlDataTypeParser);
        testParse("http://example.com/languages/java/", urlDataTypeParser);
        testParse("sample/a/index.html#28", urlDataTypeParser);
        testParse("../../demo/b/index.html", urlDataTypeParser);
        testParse("file:///~/calendar", urlDataTypeParser);

        testParse("   ", urlDataTypeParser);
        testParse("ä:ä//ä:ä?ä   ", urlDataTypeParser);

        System.out.println("---GenericDataTypeFormatter Path");
        testFormat(Path.of("."), GenericDataTypeFormatter.forPathWithSupplier(null));
        testFormat(Path.of("C:\\folder\\file.txt"), GenericDataTypeFormatter.forPathWithSupplier(null));
        testFormat(Path.of("../../folder/"), GenericDataTypeFormatter.forPathWithSupplier(null));

        System.out.println("---GenericDataTypeParser Path");
        testParse(".", GenericDataTypeParser.forPath(null));
        testParse("C:\\folder\\file.txt", GenericDataTypeParser.forPath(null));
        testParse("../../folder/", GenericDataTypeParser.forPath(null));
        testParse(" ", GenericDataTypeParser.forPath(null));

        try {
            System.out.println("---GenericDataTypeFormatter InetAddress");
            testFormat(InetAddress.getLocalHost(), GenericDataTypeFormatter.forInetAddressHostAddress(null));
            testFormat(InetAddress.getLocalHost(), GenericDataTypeFormatter.forInetAddressHostName(null));
            testFormat(InetAddress.getLoopbackAddress(), GenericDataTypeFormatter.forInetAddressHostAddress(null));
            testFormat(InetAddress.getLoopbackAddress(), GenericDataTypeFormatter.forInetAddressHostName(null));
            testFormat(InetAddress.getByName("127.0.0.1"), GenericDataTypeFormatter.forInetAddressHostAddress(null));
            testFormat(InetAddress.getByName("127.0.0.1"), GenericDataTypeFormatter.forInetAddressHostName(null));
            testFormat(InetAddress.getByName("137.254.56.25"), GenericDataTypeFormatter.forInetAddressHostAddress(null));
            testFormat(InetAddress.getByName("137.254.56.25"), GenericDataTypeFormatter.forInetAddressHostName(null));
            testFormat(InetAddress.getByName("java.net"), GenericDataTypeFormatter.forInetAddressHostAddress(null));
            testFormat(InetAddress.getByName("java.net"), GenericDataTypeFormatter.forInetAddressHostName(null));

            System.out.println("---GenericDataTypeParser InetAddress");
            testParse("127.0.0.1", GenericDataTypeParser.forInetAddress(null));
            testParse("localhost", GenericDataTypeParser.forInetAddress(null));
            testParse("137.254.56.25", GenericDataTypeParser.forInetAddress(null));
            testParse("java.net", GenericDataTypeParser.forInetAddress(null));
            testParse(".", GenericDataTypeParser.forInetAddress(null));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
