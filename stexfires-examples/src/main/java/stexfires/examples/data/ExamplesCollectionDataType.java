package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.CollectionDataTypeFormatter;
import stexfires.data.CollectionDataTypeParser;
import stexfires.data.DataTypeConverterException;
import stexfires.data.GenericDataTypeFormatter;
import stexfires.data.GenericDataTypeParser;
import stexfires.data.StringDataTypeFormatter;
import stexfires.data.StringDataTypeParser;
import stexfires.util.StringComparators;
import stexfires.util.TextSplitters;
import stexfires.util.supplier.Suppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber", "UnnecessaryUnicodeEscape", "ReuseOfLocalVariable", "ConstantValue", "HardcodedLineSeparator", "SpellCheckingInspection"})
public final class ExamplesCollectionDataType {

    private ExamplesCollectionDataType() {
    }

    private static <T extends @Nullable Object, C extends @Nullable Collection<@Nullable T>> void testFormat(@Nullable C source, CollectionDataTypeFormatter<T, C> formatter) {
        try {
            String formatResult = formatter.format(source);
            if (source == null) {
                System.out.println("Format: NULL. Result: " + formatResult);
            } else if (source.isEmpty()) {
                System.out.println("Format: EMPTY. Result: " + formatResult);
            } else {
                System.out.println("Format: " + source.size() + " " + source + ". Result: " + formatResult);
            }
        } catch (DataTypeConverterException e) {
            System.out.println("Format: " + source + ". Error: " + e.getMessage());
        }
    }

    private static <T extends @Nullable Object, C extends @Nullable Collection<@Nullable T>> void testParse(@Nullable String source, CollectionDataTypeParser<T, C> parser) {
        try {
            C parseResult = parser.parse(source);
            if (parseResult == null) {
                System.out.println("Parse: \"" + source + "\". Result: NULL");
            } else if (parseResult.isEmpty()) {
                System.out.println("Parse: \"" + source + "\". Result: EMPTY: " + parseResult.getClass().getSimpleName());
            } else {
                System.out.println("Parse: \"" + source + "\". Result: " + parseResult.size() + " " + parseResult);
            }
        } catch (DataTypeConverterException e) {
            System.out.println("Parse: \"" + source + "\". Error: " + e.getMessage());
        }
    }

    public static void main(String... args) {
        System.out.println("---CollectionDataTypeFormatter withDelimiter stringDelimitedList");
        List<@Nullable String> stringDelimitedList = null;
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList = new ArrayList<>();
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList.add("a");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList.add("b");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList.add("c");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList.add("");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        stringDelimitedList.add(null);
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", StringDataTypeFormatter.identity(), null));
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", new StringDataTypeFormatter(null, null), null));
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", new StringDataTypeFormatter(null, Suppliers.constantOfNotNull("<null>")), null));
        stringDelimitedList.add(", ");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(", ", "[", "]", new StringDataTypeFormatter(null, Suppliers.constantOfNotNull("<null>")), null));
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter("|", null, null, new StringDataTypeFormatter(null, Suppliers.constantOfNotNull("<null>")), null));
        stringDelimitedList.add("g");
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter("|", null, null, new StringDataTypeFormatter(null, Suppliers.constantOfNotNull("<null>")), null));
        testFormat(stringDelimitedList, CollectionDataTypeFormatter.withDelimiter(";", null, ";", new StringDataTypeFormatter(null, Suppliers.constantOfNotNull("<null>")), null));

        System.out.println("---CollectionDataTypeFormatter withDelimiter integerDelimitedList");
        List<@Nullable Integer> integerDelimitedList = new ArrayList<>();
        integerDelimitedList.add(42);
        testFormat(integerDelimitedList, CollectionDataTypeFormatter.withDelimiter("; ", null, null, GenericDataTypeFormatter.forInteger(10, null), null));
        integerDelimitedList.add(7);
        testFormat(integerDelimitedList, CollectionDataTypeFormatter.withDelimiter("; ", null, null, GenericDataTypeFormatter.forInteger(10, null), null));
        integerDelimitedList.add(23);
        testFormat(integerDelimitedList, CollectionDataTypeFormatter.withDelimiter("; ", null, null, GenericDataTypeFormatter.forInteger(10, null), null));
        integerDelimitedList.add(null);
        testFormat(integerDelimitedList, CollectionDataTypeFormatter.withDelimiter("; ", null, null, GenericDataTypeFormatter.forInteger(10, null), null));

        System.out.println("---CollectionDataTypeFormatter withLength stringLengthList");
        List<@Nullable String> stringLengthList = new ArrayList<>();
        stringLengthList.add("He");
        stringLengthList.add("ll");
        stringLengthList.add("o ");
        stringLengthList.add("wo");
        stringLengthList.add("rl");
        stringLengthList.add("d!");
        testFormat(stringLengthList, CollectionDataTypeFormatter.withLength(2, "\"", "\"", StringDataTypeFormatter.identity(), null));
        stringLengthList.add("!");
        testFormat(stringLengthList, CollectionDataTypeFormatter.withLength(2, "\"", "\"", StringDataTypeFormatter.identity(), null));
        stringLengthList.removeLast();
        stringLengthList.add(null);
        testFormat(stringLengthList, CollectionDataTypeFormatter.withLength(2, "\"", "\"", StringDataTypeFormatter.identity(), null));

        System.out.println("---CollectionDataTypeFormatter withLength stringBitLengthList");
        List<@Nullable String> stringBitLengthList = TextSplitters.splitByLength("1001101100", 1).toList();
        testFormat(stringBitLengthList, CollectionDataTypeFormatter.withLength(1, null, null, StringDataTypeFormatter.identity(), null));

        System.out.println("---CollectionDataTypeFormatter withLength integerLengthList");
        List<@Nullable Integer> integerLengthList = new ArrayList<>();
        integerLengthList.add(2);
        testFormat(integerLengthList, CollectionDataTypeFormatter.withLength(1, null, null, GenericDataTypeFormatter.forInteger(10, null), null));
        integerLengthList.add(8);
        integerLengthList.add(1);
        integerLengthList.add(9);
        testFormat(integerLengthList, CollectionDataTypeFormatter.withLength(1, "(", ")", GenericDataTypeFormatter.forInteger(10, null), null));
        integerLengthList.add(99);
        testFormat(integerLengthList, CollectionDataTypeFormatter.withLength(1, "(", ")", GenericDataTypeFormatter.forInteger(10, null), null));

        System.out.println("---CollectionDataTypeParser withDelimiterAsList String");
        testParse(null, CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse(" ", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[ ]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a, b]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a, b, c]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a, b, c, ]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a, b, c, , e]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[a, b, c, , e, ]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));

        testParse("[, ]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[, , ]", CollectionDataTypeParser.withDelimiterAsList(", ", "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));

        testParse("", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse(" ", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a, b", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a, b, c", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a, b, c, ", CollectionDataTypeParser.withDelimiterAsList(", ", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));

        testParse("", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse(".", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("..", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a.", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse(".a", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse(".a.", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a.b", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("a", CollectionDataTypeParser.withDelimiterAsList(".", null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));

        System.out.println("---CollectionDataTypeParser withDelimiterAsList Integer");
        testParse("", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse(" ", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("; ", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23;", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));
        testParse("42; 7; 23; ", CollectionDataTypeParser.withDelimiterAsList("; ", null, null, GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()), null, Suppliers.constantNull()));

        System.out.println("---CollectionDataTypeParser withLengthAsList String");
        testParse("", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("[]", CollectionDataTypeParser.withLengthAsList(2, "[", "]", StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("H", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("He", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("Hel", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("Hello world", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));
        testParse("Hello world!", CollectionDataTypeParser.withLengthAsList(2, null, null, StringDataTypeParser.identity(), null, Suppliers.constantNull()));

        System.out.println("---CollectionDataTypeParser String splitTextByCodePointsFunction");
        Comparator<String> stringComparator = StringComparators.collatorComparator(Locale.GERMAN);
        testParse("a\uD83D\uDE00o\u0308äAaA", new CollectionDataTypeParser<String, Set<String>>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(new TreeSet<>(stringComparator), CollectionDataTypeParser.ConverterValidator.INITIALLY_EMPTY), null, Suppliers.constantNull()));
        testParse("aa", new CollectionDataTypeParser<>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(new TreeSet<>(stringComparator), CollectionDataTypeParser.ConverterValidator.IDENTICAL_SIZE), null, Suppliers.constantNull()));
        testParse("abcd", new CollectionDataTypeParser<>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(new TreeSet<>(stringComparator), CollectionDataTypeParser.ConverterValidator.SAME_ORDER), null, Suppliers.constantNull()));
        testParse("abdc", new CollectionDataTypeParser<>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(new TreeSet<>(stringComparator), CollectionDataTypeParser.ConverterValidator.SAME_ORDER), null, Suppliers.constantNull()));
        NavigableSet<String> treeSet = new TreeSet<>(stringComparator);
        treeSet.add("b");
        treeSet.add("e");
        testParse("abcd", new CollectionDataTypeParser<>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(treeSet, CollectionDataTypeParser.ConverterValidator.INITIALLY_EMPTY), null, Suppliers.constantNull()));
        testParse("abcd", new CollectionDataTypeParser<>(null, null, TextSplitters.splitByCodePointsFunction(), StringDataTypeParser.identity(), CollectionDataTypeParser.streamToSetConverter(treeSet, CollectionDataTypeParser.ConverterValidator.NO_VALIDATION), null, Suppliers.constantNull()));

        System.out.println("---CollectionDataTypeFormatter withDelimiter List of List");
        List<List<Integer>> listOfList = List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9));
        testFormat(listOfList,
                CollectionDataTypeFormatter.withDelimiter("\n", null, null,
                        CollectionDataTypeFormatter.withDelimiter(", ", "(", ")",
                                GenericDataTypeFormatter.forInteger(10, null),
                                null),
                        null)
        );
        System.out.println("---CollectionDataTypeParser withDelimiter List of List");
        String listOfListString = """
                (1, 2, 3)
                (4, 5, 6)
                (7, 8, 9)""";
        testParse(listOfListString,
                CollectionDataTypeParser.withDelimiterAsList("\n", null, null,
                        CollectionDataTypeParser.withDelimiterAsList(", ", "(", ")", GenericDataTypeParser.forInteger(10, null, Suppliers.constantNull()),
                                null, Suppliers.constantNull()),
                        null, Suppliers.constantNull())
        );
    }

}
