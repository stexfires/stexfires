package stexfires.examples.util;

import stexfires.util.Strings;
import stexfires.util.TextSplitters;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "HardcodedLineSeparator", "SpellCheckingInspection"})
public final class ExamplesTextSplitters {

    private static final String EXAMPLE_TEXT = "Hello world! This is a sentence. Is this also a sentence? The price is $12.35. He said: \"Hello world\".\nSpecial characters: " + ExamplesStrings.SPECIAL_CHARACTERS + ".";

    private ExamplesTextSplitters() {
    }

    private static void printExample(Stream<String> stream) {
        System.out.println(stream.toList());
    }

    private static void printExample(Function<String, Stream<String>> function) {
        Function<String, String> mapFunction = s -> "(" + s + ")";

        System.out.println(Stream.of(EXAMPLE_TEXT).flatMap(function).map(mapFunction).collect(Collectors.joining()));
    }

    private static void showSplit() {
        System.out.println("-showSplit---");

        printExample(TextSplitters.splitByLines("splitByLines:\nFirst line\nSecond Line\r\nThird line"));
        printExample(TextSplitters.splitByChars("splitByChars:      Abc 12.34€ " + ExamplesStrings.SPECIAL_CHARACTERS));
        printExample(TextSplitters.splitByCodePoints("splitByCodePoints: Abc 12.34€ " + ExamplesStrings.SPECIAL_CHARACTERS));

        printExample(TextSplitters.splitByPattern("splitByPattern  .Abc012.def..GHI.", Pattern.compile(Pattern.quote("."))));
        printExample(TextSplitters.splitByRegex("splitByRegex    .Abc012.def..GHI.", Pattern.quote(".")));
        printExample(TextSplitters.splitBySeparator("splitBySeparator.Abc012.def..GHI.", "."));

        printExample(TextSplitters.splitByRegex("splitByRegex AbcNeg-def--GHI-", "-", -1));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc0-def--GHI-", "-", 0));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc1-def--GHI-", "-", 1));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc2-def--GHI-", "-", 2));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc3-def--GHI-", "-", 3));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc4-def--GHI-", "-", 4));
        printExample(TextSplitters.splitByRegex("splitByRegex Abc5-def--GHI-", "-", 5));

        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters AbcNeg-def--GHI-", "-", -1));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc0-def--GHI-", "-", 0));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc1-def--GHI-", "-", 1));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc2-def--GHI-", "-", 2));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc3-def--GHI-", "-", 3));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc4-def--GHI-", "-", 4));
        printExample(TextSplitters.splitByRegexWithDelimiters("splitByRegexWithDelimiters Abc5-def--GHI-", "-", 5));

        printExample(TextSplitters.splitByLength("splitByLength  abcdefghi", 3));
        printExample(TextSplitters.splitByLength("splitByLength  abcdefghij", 3));
        printExample(TextSplitters.splitByLength("splitByLength  abcdefghijk", 3));
    }

    private static void showSplitFunction() {
        System.out.println("-showSplitFunction---");

        printExample(TextSplitters.splitByLinesFunction());
        printExample(TextSplitters.splitByCharsFunction());
        printExample(TextSplitters.splitByCodePointsFunction());

        printExample(TextSplitters.splitByPatternFunction(Pattern.compile(Strings.REGEX_WHITESPACE)));
        printExample(TextSplitters.splitRegexFunction(Strings.REGEX_WHITESPACE));
        printExample(TextSplitters.splitBySeparatorFunction(Strings.SPACE));

        printExample(TextSplitters.splitByRegexFunction(Strings.REGEX_WHITESPACE, 0));
        printExample(TextSplitters.splitByRegexWithDelimitersFunction(Strings.REGEX_WHITESPACE, 0));
        printExample(TextSplitters.splitByLengthFunction(3));
    }

    private static void showBreak() {
        System.out.println("-showBreak---");

        printExample(TextSplitters.breakBySentence(EXAMPLE_TEXT, Locale.US));
        printExample(TextSplitters.breakByWord(EXAMPLE_TEXT, Locale.US));
        printExample(TextSplitters.breakByLine(EXAMPLE_TEXT, Locale.US));
        printExample(TextSplitters.breakByCharacter(EXAMPLE_TEXT, Locale.US));
    }

    private static void showBreakFunction() {
        System.out.println("-showBreakFunction---");

        printExample(TextSplitters.breakBySentenceFunction(Locale.US));
        printExample(TextSplitters.breakByWordFunction(Locale.US));
        printExample(TextSplitters.breakByLineFunction(Locale.US));
        printExample(TextSplitters.breakByCharacterFunction(Locale.US));
    }

    public static void main(String... args) {
        showSplit();
        showSplitFunction();
        showBreak();
        showBreakFunction();
    }

}
