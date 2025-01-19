package stexfires.examples.util;

import stexfires.util.CodePoint;
import stexfires.util.Strings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection", "UnnecessaryUnicodeEscape"})
public final class ExamplesStrings {

    public static final String SPECIAL_CHARACTERS = "\uD83D\uDE00 üo\u0308A\u030a \uD83C\uDDFA\uD83C\uDDF8 \uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66";

    private ExamplesStrings() {
    }

    @SuppressWarnings("ConstantValue")
    private static void showToNullableString() {
        System.out.println("-showToNullableString---");

        Integer integerTwo = 2;
        Integer integerNull = null;

        System.out.println(Strings.toNullableString(null));
        System.out.println(Strings.toNullableString(Strings.EMPTY));
        System.out.println(Strings.toNullableString(Strings.SPACE));
        System.out.println(Strings.toNullableString("Test"));
        System.out.println(Strings.toNullableString(Boolean.TRUE));
        System.out.println(Strings.toNullableString(BigInteger.TWO));
        System.out.println(Strings.toNullableString(BigDecimal.TWO));
        System.out.println(Strings.toNullableString(integerTwo));
        System.out.println(Strings.toNullableString(integerNull));
        System.out.println(Strings.toNullableString(SPECIAL_CHARACTERS));
    }

    @SuppressWarnings("ConstantValue")
    private static void showToOptionalString() {
        System.out.println("-showToOptionalString---");

        Integer integerTwo = 2;
        Integer integerNull = null;

        System.out.println(Strings.toOptionalString(null));
        System.out.println(Strings.toOptionalString(Strings.EMPTY));
        System.out.println(Strings.toOptionalString(Strings.SPACE));
        System.out.println(Strings.toOptionalString("Test"));
        System.out.println(Strings.toOptionalString(Boolean.TRUE));
        System.out.println(Strings.toOptionalString(BigInteger.TWO));
        System.out.println(Strings.toOptionalString(BigDecimal.TWO));
        System.out.println(Strings.toOptionalString(integerTwo));
        System.out.println(Strings.toOptionalString(integerNull));
        System.out.println(Strings.toOptionalString(SPECIAL_CHARACTERS));
    }

    private static void showListOfNotNull() {
        System.out.println("-showListOfNotNull---");

        System.out.println(Strings.listOfNotNull("a"));
        System.out.println(Strings.listOfNotNull(Strings.EMPTY));
        System.out.println(Strings.listOfNotNull(Strings.SPACE));
    }

    private static void showListOfNullable() {
        System.out.println("-showListOfNullable---");

        System.out.println(Strings.listOfNullable("a"));
        System.out.println(Strings.listOfNullable(Strings.EMPTY));
        System.out.println(Strings.listOfNullable(Strings.SPACE));
        System.out.println(Strings.listOfNullable(null));
    }

    private static void showList() {
        System.out.println("-showList---");

        System.out.println(Strings.list("a"));
        System.out.println(Strings.list("a", "b"));
        System.out.println(Strings.list("a", "b", "c"));
        System.out.println(Strings.list("a", null, "c"));
        System.out.println(Strings.list());
        System.out.println(Strings.list(Strings.EMPTY));
        System.out.println(Strings.list(Strings.SPACE));
        System.out.println(Strings.list((String) null));
        System.out.println(Strings.list(null, null));
        System.out.println(Strings.list(null, null, "Test"));
        System.out.println(Strings.list("a"));
    }

    private static void showStreamOfNotNull() {
        System.out.println("-showStreamOfNotNull---");

        System.out.println(Strings.streamOfNotNull("a").toList());
        System.out.println(Strings.streamOfNotNull(Strings.EMPTY).toList());
        System.out.println(Strings.streamOfNotNull(Strings.SPACE).toList());
    }

    private static void showStreamOfNullable() {
        System.out.println("-showStreamOfNullable---");

        System.out.println(Strings.streamOfNullable("a").toList());
        System.out.println(Strings.streamOfNullable(Strings.EMPTY).toList());
        System.out.println(Strings.streamOfNullable(Strings.SPACE).toList());
        System.out.println(Strings.streamOfNullable(null).toList());
    }

    private static void showStream() {
        System.out.println("-showStream---");

        System.out.println(Strings.stream("a").toList());
        System.out.println(Strings.stream("a", "b").toList());
        System.out.println(Strings.stream("a", "b", "c").toList());
        System.out.println(Strings.stream("a", null, "c").toList());
        System.out.println(Strings.stream().toList());
        System.out.println(Strings.stream(Strings.EMPTY).toList());
        System.out.println(Strings.stream(Strings.SPACE).toList());
        System.out.println(Strings.stream((String) null).toList());
        System.out.println(Strings.stream(null, null).toList());
        System.out.println(Strings.stream(null, null, "Test").toList());
        System.out.println(Strings.stream(new String[]{"a"}).toList());
    }

    private static void showCodePointStreamOfNotNull() {
        System.out.println("-showCodePointStreamOfNotNull---");

        System.out.println(Strings.codePointStreamOfNotNull("a").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNotNull("Hello").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNotNull(Strings.EMPTY).map(CodePoint::value).toList());
    }

    private static void showCodePointStreamOfNullable() {
        System.out.println("-showCodePointStreamOfNullable---");

        System.out.println(Strings.codePointStreamOfNullable("a").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable("Hello").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable(Strings.EMPTY).map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable(null).map(CodePoint::value).toList());
    }

    private static void showConcatTwoStreams() {
        System.out.println("-showConcatTwoStreams---");

        System.out.println(Strings.concatTwoStreams(Stream.of("a"), Stream.of("b")).toList());
        System.out.println(Strings.concatTwoStreams(Stream.of("a", "b"), Stream.of("c", "d")).toList());
        System.out.println(Strings.concatTwoStreams(Strings.streamOfNullable(null), Strings.stream("a", null, "b")).toList());
    }

    private static void showConcatManyStreams() {
        System.out.println("-showConcatManyStreams---");

        System.out.println(Strings.concatManyStreams(Stream.empty()).toList());
        System.out.println(Strings.concatManyStreams().toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a")).toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a"), Stream.of("b")).toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a", "b"), Stream.of("c", "d")).toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a", "b"), Stream.empty(), Stream.of("c", "d")).toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a", "b"), Stream.of("c", "d"), Stream.of("e")).toList());
        System.out.println(Strings.concatManyStreams(Stream.of("a", null), Stream.of("b", "c"), Strings.streamOfNotNull("d"), Strings.streamOfNullable(null), Strings.stream("e", null, "f")).toList());
    }

    private static void showJoin() {
        System.out.println("-showJoin---");

        System.out.println(Strings.join(Stream.empty()));
        System.out.println(Strings.join(Strings.stream("a")));
        System.out.println(Strings.join(Strings.stream("a", "b")));
        System.out.println(Strings.join(Strings.stream("a", "b", "c")));
        System.out.println(Strings.join(Strings.stream("a", null, "c")));
        System.out.println(Strings.join(Strings.stream()));
        System.out.println(Strings.join(Strings.stream(Strings.EMPTY)));
        System.out.println(Strings.join(Strings.stream(Strings.SPACE)));
        System.out.println(Strings.join(Strings.stream((String) null)));
        System.out.println(Strings.join(Strings.stream(null, null)));
        System.out.println(Strings.join(Strings.stream(null, null, "Test")));
        System.out.println(Strings.join(Strings.stream("a")));

        System.out.println(Strings.join(Strings.stream("a", "b", "c"), ""));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), Strings.DEFAULT_DELIMITER));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), "-"));
        System.out.println(Strings.join(Strings.stream("a", null, "c"), "-"));
    }

    private static void showPrintLine() {
        System.out.println("-showPrintLine---");

        Strings.printLine(Stream.empty(), ", ");
        Strings.printLine(Strings.stream("a", "b", "c"), ", ");
        Strings.printLine(Strings.stream("a", null, "c"), ", ");
    }

    private static void showPrintLines() {
        System.out.println("-showPrintLines---");

        Strings.printLines(Stream.empty());
        Strings.printLines(Strings.stream("a", "b", "c"));
        Strings.printLines(Strings.stream("a", null, "c"));
    }

    private static void printModify(String methodName, UnaryOperator<List<String>> modifyListOperator) {
        System.out.println(methodName + ": " + Strings.stream("a", "b", "c", "d", "e", "f", "E", "e")
                                                      .collect(Strings.modifyAndJoinCollector(modifyListOperator, "-")));
    }

    private static void showModifyList() {
        System.out.println("-showModifyList---");

        printModify("modifyListRemoveAll  ", Strings.modifyListRemoveAll(List.of("a", "e")));
        printModify("modifyListRemoveIf   ", Strings.modifyListRemoveIf("e"::equalsIgnoreCase));
        printModify("modifyListReplaceAll ", Strings.modifyListReplaceAll("a", "A"));
        printModify("modifyListReplaceAll ", Strings.modifyListReplaceAll(s -> s + s));
        printModify("modifyListRetainAll  ", Strings.modifyListRetainAll(List.of("a", "e")));
        printModify("modifyListReverse    ", Strings.modifyListReverse());
        printModify("modifyListRotate     ", Strings.modifyListRotate(2));
        printModify("modifyListShuffle    ", Strings.modifyListShuffle());
        printModify("modifyListSort       ", Strings.modifyListSort(String::compareToIgnoreCase));
        printModify("modifyListSwap       ", Strings.modifyListSwap(2, 4));
    }

    public static void main(String... args) {
        showToNullableString();
        showToOptionalString();
        showListOfNotNull();
        showListOfNullable();
        showList();
        showStreamOfNotNull();
        showStreamOfNullable();
        showStream();
        showCodePointStreamOfNotNull();
        showCodePointStreamOfNullable();
        showConcatTwoStreams();
        showConcatManyStreams();
        showJoin();
        showPrintLine();
        showPrintLines();
        showModifyList();
    }

}
