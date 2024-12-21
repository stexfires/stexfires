package stexfires.examples.util;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static stexfires.util.function.ByteArrayFunctions.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber", "UnnecessaryUnicodeEscape"})
public final class ExamplesByteArrayFunction {

    private ExamplesByteArrayFunction() {
    }

    private static void printByteArrayWithInfo(byte @Nullable [] byteArray,
                                               String info) {
        System.out.println(info);
        printByteArray(byteArray);
    }

    private static void printByteArray(byte @Nullable [] byteArray) {
        if (byteArray != null) {
            try {
                System.out.println("  String UTF-8:      \"" + new String(byteArray, StandardCharsets.UTF_8) + "\"");
                System.out.println("  String ISO_8859_1: \"" + new String(byteArray, StandardCharsets.ISO_8859_1) + "\"");
                System.out.println("  String US_ASCII:   \"" + new String(byteArray, StandardCharsets.US_ASCII) + "\"");
                System.out.println("  length:            " + byteArray.length);
                System.out.println("  toString:          " + Arrays.toString(byteArray));
                if (byteArray.length > 0) {
                    System.out.println("  BigInteger:        " + new BigInteger(byteArray));
                }
            } catch (RuntimeException e) {
                System.out.println("  Exception during print: " + e.getMessage());
            }
        } else {
            System.out.println("  Byte array is NULL");
        }
    }

    private static void printPredicate(Predicate<byte @Nullable []> predicate,
                                       String info) {
        System.out.println(info);

        List<byte @Nullable []> byteArrayList = new ArrayList<>();
        byteArrayList.add(null);
        byteArrayList.add(new byte[]{});
        byteArrayList.add(new byte[]{(byte) 0});
        byteArrayList.add(new byte[]{(byte) 65});

        for (byte @Nullable [] byteArray : byteArrayList) {
            System.out.println("  " + predicate.test(byteArray) + "\t" + Arrays.toString(byteArray));
        }
    }

    private static void printStringToByteArrayFunction(Function<@Nullable String, byte @Nullable []> function,
                                                       String info) {
        List<@Nullable String> stringValues = new ArrayList<>();

        stringValues.add(null);
        stringValues.add("");
        stringValues.add("    ");
        stringValues.add("Hello world!");
        stringValues.add("A");
        stringValues.add("\t");
        stringValues.add("Aä²ß");
        stringValues.add("[€] [\uD83D\uDE00] [o\u0308] [A\u030a] [\uD83C\uDDFA\uD83C\uDDF8] [\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66]");
        // Base64
        stringValues.add("QQ==");
        stringValues.add("QeSy3w==");
        // HexFormat
        stringValues.add("20");
        stringValues.add("41");
        stringValues.add("41e4b2df");
        stringValues.add("41-e4-b2-df");
        stringValues.add("48-65-6c-6c-6f-20-77-6f-72-6c-64-21");
        // Number
        stringValues.add("-1");
        stringValues.add("0");
        stringValues.add("9");
        stringValues.add("65");
        stringValues.add("1105507039");
        stringValues.add("22405534230753963835153736737");
        stringValues.add("2253008897699894625889381663835221270622918862688930794077");

        for (@Nullable String stringValue : stringValues) {
            if (stringValue == null) {
                System.out.println(info + " NULL");
            } else {
                System.out.println(info + " \"" + stringValue + "\"" + " [" + stringValue.length() + "]");
            }
            try {
                printByteArray(function.apply(stringValue));
            } catch (RuntimeException e) {
                System.out.println("  message:           " + e.getMessage());
            }
        }
    }

    private static <T> void printByteArrayToObjectFunction(Function<byte @Nullable [], @Nullable T> function,
                                                           String info) {
        System.out.println(info);

        List<byte @Nullable []> byteArrayList = new ArrayList<>();

        byteArrayList.add(null);
        byteArrayList.add(new byte[]{});
        byteArrayList.add(new byte[]{-1});
        byteArrayList.add(new byte[]{0});
        byteArrayList.add(new byte[]{9});
        byteArrayList.add(new byte[]{65});
        byteArrayList.add(new byte[]{65, -28, -78, -33});
        byteArrayList.add(new byte[]{72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33});
        byteArrayList.add(new byte[]{91, -30, -126, -84, 93, 32, 91, -16, -97, -104, -128, 93, 32, 91, 111, -52, -120, 93, 32, 91, 65, -52, -118, 93});

        for (byte @Nullable [] byreArray : byteArrayList) {
            try {
                System.out.println("  " + Arrays.toString(byreArray) + " \"" + function.apply(byreArray) + "\"");
            } catch (RuntimeException e) {
                System.out.println("  " + Arrays.toString(byreArray) + " " + e.getMessage());
            }
        }
    }

    private static void showPredicates() {
        System.out.println("-showPredicates---");

        printPredicate(isNull(), "isNull");
        printPredicate(isNotNull(), "isNotNull");
        printPredicate(isNullOrEmpty(), "isNullOrEmpty");
        printPredicate(isNotNullAndEmpty(), "isNotNullAndEmpty");
        printPredicate(isNotNullAndNotEmpty(), "isNotNullAndNotEmpty");
    }

    @SuppressWarnings("UnnecessaryBoxing")
    private static void showFromFunctions() {
        System.out.println("-showFromFunctions---");

        printStringToByteArrayFunction(fromStringStandard(StandardCharsets.UTF_8), "fromStringStandard UTF_8");
        printStringToByteArrayFunction(fromStringStandard(StandardCharsets.ISO_8859_1), "fromStringStandard ISO_8859_1");
        printStringToByteArrayFunction(fromStringStandard(StandardCharsets.US_ASCII), "fromStringStandard US_ASCII");

        printStringToByteArrayFunction(fromStringIgnoreErrors(StandardCharsets.ISO_8859_1), "fromStringIgnoreErrors ISO_8859_1");
        printStringToByteArrayFunction(fromStringIgnoreErrors(StandardCharsets.US_ASCII), "fromStringIgnoreErrors US_ASCII");
        printStringToByteArrayFunction(fromStringReplaceErrors(StandardCharsets.ISO_8859_1, new byte[]{66}), "fromStringReplaceErrors ISO_8859_1");
        printStringToByteArrayFunction(fromStringReplaceErrors(StandardCharsets.US_ASCII, new byte[]{66}), "fromStringReplaceErrors US_ASCII");
        printStringToByteArrayFunction(fromStringReplaceErrors(StandardCharsets.US_ASCII, "C"), "fromStringReplaceErrors US_ASCII");
        printStringToByteArrayFunction(fromStringAlternativeForError(StandardCharsets.US_ASCII, new byte[]{69, 82, 82, 79, 82}), "fromStringAlternativeForError US_ASCII");

        printStringToByteArrayFunction(fromBase64(Base64.getDecoder()), "fromBase64");
        printStringToByteArrayFunction(fromHex(HexFormat.ofDelimiter("-")), "fromHex ofDelimiter");
        printStringToByteArrayFunction(fromHex(), "fromHex");

        printByteArrayWithInfo(fromPrimitiveInt().apply(65), "fromPrimitiveInt 65");
        printByteArrayWithInfo(fromPrimitiveLong().apply(65L), "fromPrimitiveLong 65");
        printByteArrayWithInfo(fromInteger().apply(Integer.valueOf(65)), "fromInteger 65");
        printByteArrayWithInfo(fromLong().apply(Long.valueOf(65L)), "fromLong 65");
        printByteArrayWithInfo(fromBigInteger().apply(BigInteger.valueOf(65L)), "fromBigInteger 65");
        printByteArrayWithInfo(fromBigInteger().apply(null), "fromBigInteger null");
        printByteArrayWithInfo(fromBigInteger().apply(new BigInteger("1105507039")), "fromBigInteger 1105507039");
    }

    private static void showToFunctions() {
        System.out.println("-showToFunctions---");

        printByteArrayToObjectFunction(toStringRepresentation(), "toStringRepresentation");

        printByteArrayToObjectFunction(toStringStandard(StandardCharsets.UTF_8), "toStringStandard UTF_8");
        printByteArrayToObjectFunction(toStringStandard(StandardCharsets.ISO_8859_1), "toStringStandard ISO_8859_1");
        printByteArrayToObjectFunction(toStringStandard(StandardCharsets.US_ASCII), "toStringStandard US_ASCII");

        printByteArrayToObjectFunction(toStringIgnoreErrors(StandardCharsets.ISO_8859_1), "toStringIgnoreErrors ISO_8859_1");
        printByteArrayToObjectFunction(toStringIgnoreErrors(StandardCharsets.US_ASCII), "toStringIgnoreErrors US_ASCII");
        printByteArrayToObjectFunction(toStringReplaceErrors(StandardCharsets.ISO_8859_1, "*"), "toStringReplaceErrors ISO_8859_1");
        printByteArrayToObjectFunction(toStringReplaceErrors(StandardCharsets.US_ASCII, "*"), "toStringReplaceErrors US_ASCII");
        printByteArrayToObjectFunction(toStringAlternativeForError(StandardCharsets.ISO_8859_1, "Cannot map byte array to String!"), "toStringAlternativeInCaseOfError ISO_8859_1");
        printByteArrayToObjectFunction(toStringAlternativeForError(StandardCharsets.US_ASCII, "Cannot map byte array to String!"), "toStringAlternativeInCaseOfError US_ASCII");

        printByteArrayToObjectFunction(toBase64(Base64.getEncoder()), "toBase64");
        printByteArrayToObjectFunction(toHex(HexFormat.ofDelimiter("-")), "toHex ofDelimiter");
        printByteArrayToObjectFunction(toHex(), "toHex");

        printByteArrayToObjectFunction(toInteger(), "toInteger");
        printByteArrayToObjectFunction(toLong(), "toLong");
        printByteArrayToObjectFunction(toBigInteger(), "toBigInteger");
    }

    private static void showUnaryOperators() {
        System.out.println("-showUnaryOperators---");

        byte[] byteArrayOriginal = new byte[]{72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32, 72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33, 32};

        printByteArrayWithInfo(byteArrayOriginal, "original");
        printByteArrayWithInfo(encodeBase64(Base64.getEncoder()).apply(byteArrayOriginal), "encodeBase64");
        printByteArrayWithInfo(decodeBase64(Base64.getDecoder()).apply(encodeBase64(Base64.getEncoder()).apply(byteArrayOriginal)), "encodeBase64 and decodeBase64");
        printByteArrayWithInfo(compressGZIP().apply(byteArrayOriginal), "compressGZIP");
        printByteArrayWithInfo(decompressGZIP().apply(compressGZIP().apply(byteArrayOriginal)), "compressGZIP and decompressGZIP");
        printByteArrayWithInfo(encodeBase64(Base64.getEncoder()).apply(compressGZIP().apply(byteArrayOriginal)), "compressGZIP and encodeBase64");
        printByteArrayWithInfo(decompressGZIP().apply(decodeBase64(Base64.getDecoder()).apply(encodeBase64(Base64.getEncoder()).apply(compressGZIP().apply(byteArrayOriginal)))), "compressGZIP and encodeBase64 and decodeBase64 and decompressGZIP");
    }

    public static void main(String... args) {
        showPredicates();
        showFromFunctions();
        showToFunctions();
        showUnaryOperators();
    }

}
