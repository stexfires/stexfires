package stexfires.examples.util;

import stexfires.util.CodePoint;
import stexfires.util.Strings;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "SpellCheckingInspection", "UnnecessaryUnicodeEscape"})
public final class ExamplesStrings {

    public static final String SPECIAL_CHARACTERS = "\uD83D\uDE00 üo\u0308A\u030a \uD83C\uDDFA\uD83C\uDDF8 \uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66";

    private ExamplesStrings() {
    }

    private static void showStringsMisc() {
        System.out.println("-showStringsMisc---");

        Integer ten = 10;

        System.out.println(Strings.toNullableString(null));
        System.out.println(Strings.toNullableString("Test"));
        System.out.println(Strings.toNullableString(Boolean.TRUE));
        System.out.println(Strings.toNullableString(BigInteger.TWO));
        System.out.println(Strings.toNullableString(ten));

        System.out.println(Strings.toOptionalString(null));
        System.out.println(Strings.toOptionalString("Test"));
        System.out.println(Strings.toOptionalString(Boolean.TRUE));
        System.out.println(Strings.toOptionalString(BigInteger.TWO));
        System.out.println(Strings.toOptionalString(ten));
    }

    private static void showStringsList() {
        System.out.println("-showStringsList---");

        System.out.println(Strings.list("a"));
        System.out.println(Strings.list("a", "b"));
        System.out.println(Strings.list("a", "b", "c"));
        System.out.println(Strings.list("a", null, "c"));
        System.out.println(Strings.list());
        System.out.println(Strings.list(""));
        System.out.println(Strings.list((String) null));
        System.out.println(Strings.list(null, null));
        System.out.println(Strings.list(null, null, "Test"));
        System.out.println(Strings.list(new String[]{"a"}));
    }

    private static void showStringsListOfNullable() {
        System.out.println("-showStringsListOfNullable---");

        System.out.println(Strings.listOfNullable("a"));
        System.out.println(Strings.listOfNullable(""));
        System.out.println(Strings.listOfNullable(null));
    }

    private static void showStringsStream() {
        System.out.println("-showStringsStream---");

        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream("a", null, "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(null, null, "Test")));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsStreamOfNullable() {
        System.out.println("-showStringsStreamOfNullable---");

        System.out.println(Strings.collect(Strings.streamOfNullable("a")));
        System.out.println(Strings.collect(Strings.streamOfNullable("")));
        System.out.println(Strings.collect(Strings.streamOfNullable(null)));
    }

    private static void showStringsCodePointStream() {
        System.out.println("-showStringsCodePointStream---");

        System.out.println(Strings.codePointStream("a").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStream("Hello").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStream("").map(CodePoint::value).toList());
    }

    private static void showStringsCodePointStreamOfNullable() {
        System.out.println("-showStringsCodePointStreamOfNullable---");

        System.out.println(Strings.codePointStreamOfNullable("a").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable("Hello").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable("").map(CodePoint::value).toList());
        System.out.println(Strings.codePointStreamOfNullable(null).map(CodePoint::value).toList());
    }

    private static void showStringsConcat() {
        System.out.println("-showStringsConcat---");

        System.out.println(Strings.collect(Strings.concat(Stream.empty())));
        System.out.println(Strings.collect(Strings.concat()));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a"), Stream.of("b"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a", "b"), Stream.of("c", "d"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a", "b"), Stream.empty(), Stream.of("c", "d"))));
    }

    private static void showStringsCollect() {
        System.out.println("-showStringsCollect---");

        System.out.println(Strings.collect(Stream.empty()));
        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream("a", null, "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(null, null, "Test")));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsToList() {
        System.out.println("-showStringsToList---");

        System.out.println(Strings.toList(Stream.empty()));
        System.out.println(Strings.toList(Strings.stream("a")));
        System.out.println(Strings.toList(Strings.stream("a", "b")));
        System.out.println(Strings.toList(Strings.stream("a", "b", "c")));
        System.out.println(Strings.toList(Strings.stream("a", null, "c")));
        System.out.println(Strings.toList(Strings.stream()));
        System.out.println(Strings.toList(Strings.stream("")));
        System.out.println(Strings.toList(Strings.stream((String) null)));
        System.out.println(Strings.toList(Strings.stream(null, null)));
        System.out.println(Strings.toList(Strings.stream(null, null, "Test")));
        System.out.println(Strings.toList(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsJoin() {
        System.out.println("-showStringsJoin---");

        System.out.println(Strings.join(Stream.empty()));
        System.out.println(Strings.join(Strings.stream("a")));
        System.out.println(Strings.join(Strings.stream("a", "b")));
        System.out.println(Strings.join(Strings.stream("a", "b", "c")));
        System.out.println(Strings.join(Strings.stream("a", null, "c")));
        System.out.println(Strings.join(Strings.stream()));
        System.out.println(Strings.join(Strings.stream("")));
        System.out.println(Strings.join(Strings.stream((String) null)));
        System.out.println(Strings.join(Strings.stream(null, null)));
        System.out.println(Strings.join(Strings.stream(null, null, "Test")));
        System.out.println(Strings.join(Strings.stream(new String[]{"a"})));

        System.out.println(Strings.join(Strings.stream("a", "b", "c"), ""));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), Strings.DEFAULT_DELIMITER));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), "-"));
    }

    private static void showStringsPrintLine() {
        System.out.println("-showStringsPrintLine---");

        Strings.printLine(Stream.empty(), ", ");
        Strings.printLine(Strings.stream("a", "b", "c"), ", ");
        Strings.printLine(Strings.stream("a", null, "c"), ", ");
    }

    private static void showStringsPrintLines() {
        System.out.println("-showStringsPrintLines---");

        Strings.printLines(Stream.empty());
        Strings.printLines(Strings.stream("a", "b", "c"));
        Strings.printLines(Strings.stream("a", null, "c"));
    }

    private static void showStringsModifyList() {
        System.out.println("-showStringsModifyList---");

        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListRemoveAll(List.of("a", "e")), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListRemoveIf("e"::equalsIgnoreCase), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListReplaceAll("a", "A"), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListReplaceAll(s -> s + s), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListRetainAll(List.of("a", "e")), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListReverse(), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListRotate(2), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListShuffle(), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListSort(String::compareToIgnoreCase), "-")));
        System.out.println(Strings.stream("a", "b", "c", "d", "e", "f", "E", "e").collect(Strings.modifyAndJoinCollector(Strings.modifyListSwap(2, 4), "-")));
    }

    public static void main(String... args) {
        showStringsMisc();
        showStringsList();
        showStringsListOfNullable();
        showStringsStream();
        showStringsStreamOfNullable();
        showStringsCodePointStream();
        showStringsCodePointStreamOfNullable();
        showStringsConcat();
        showStringsCollect();
        showStringsToList();
        showStringsJoin();
        showStringsPrintLine();
        showStringsPrintLines();
        showStringsModifyList();
    }

}
