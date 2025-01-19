package stexfires.examples.javatest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MethodCanBeVariableArityMethod", "MagicNumber", "UnnecessaryUnicodeEscape"})
public final class GZIPTest {

    private GZIPTest() {
    }

    public static byte[] compressGZIP(byte[] uncompressedData) {
        Objects.requireNonNull(uncompressedData);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
            gzipStream.write(uncompressedData);
        } catch (IOException e) {
            // Should not normally occur.
            throw new UncheckedIOException("Error occurred while compressing data.", e);
        }
        // 'toByteArray' should be called after closing GZIPOutputStream
        return byteStream.toByteArray();
    }

    public static byte[] decompressGZIP(byte[] compressedData) {
        Objects.requireNonNull(compressedData);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipStream = new GZIPInputStream(byteStream)) {
            return gzipStream.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Error occurred while decompressing data.", e);
        }
    }

    private static void testGZIP(String inputString) {
        System.out.println("---testGZIP--");
        System.out.println("inputString: " + inputString);

        byte[] originalData = inputString.getBytes(StandardCharsets.UTF_8);
        byte[] compressedData = compressGZIP(originalData);
        byte[] uncompressedData = decompressGZIP(compressedData);
        String ouputString = new String(uncompressedData, StandardCharsets.UTF_8);

        System.out.println("Strings          equals:   " + inputString.equals(ouputString));
        System.out.println("Arrays           equals:   " + Arrays.equals(originalData, uncompressedData));
        System.out.println("originalData     length:   " + originalData.length);
        System.out.println("originalData     toString: " + Arrays.toString(originalData));
        System.out.println("compressedData   length:   " + compressedData.length);
        System.out.println("compressedData   toString: " + Arrays.toString(compressedData));
        System.out.println("uncompressedData length:   " + uncompressedData.length);
        System.out.println("uncompressedData toString: " + Arrays.toString(uncompressedData));
        System.out.println("rate:                      " + ((double) compressedData.length / originalData.length));
    }

    private static void testGZIPSpecial() {
        System.out.println("---testGZIPSpecial--");

        System.out.println(Arrays.toString(compressGZIP(new byte[0])));
        System.out.println(Arrays.toString(compressGZIP(new byte[1])));
        System.out.println(Arrays.toString(compressGZIP(new byte[100])));
        System.out.println(Arrays.toString(compressGZIP(new byte[10000])));
        System.out.println(Arrays.toString(compressGZIP(new byte[]{-1, -1, -1})));
        System.out.println(Arrays.toString(compressGZIP(new byte[]{-128, -128, -128})));
        System.out.println(Arrays.toString(compressGZIP(new byte[]{127, 127, 127})));

        System.out.println(Arrays.toString(decompressGZIP(new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, -1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0})));

        // throws ZipException
        System.out.println(Arrays.toString(decompressGZIP(new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, -1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0})));
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String... args) {
        testGZIP("");
        testGZIP("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        testGZIP("ä ² ß € \uD83D\uDE00 o\u0308 A\u030a");
        testGZIP("Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! ");
        testGZIPSpecial();
    }

}
