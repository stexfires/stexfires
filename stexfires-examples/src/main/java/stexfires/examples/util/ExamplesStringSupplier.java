package stexfires.examples.util;

import stexfires.util.TextSplitters;
import stexfires.util.function.RandomBooleanSupplier;
import stexfires.util.function.RandomNumberSuppliers;
import stexfires.util.function.RandomStringSuppliers;
import stexfires.util.function.Suppliers;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesStringSupplier {

    private ExamplesStringSupplier() {
    }

    private static void printStream(String title, Stream<String> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showSuppliers() {
        System.out.println("-showSuppliers---");

        printStream("constant", Stream.generate(Suppliers.constant("Test")).limit(2));
        printStream("constantNull", Stream.generate(Suppliers.<String>constantNull()).limit(2));
        printStream("combine", Stream.generate(Suppliers.combine(() -> "X", () -> "Y", (x, y) -> x + "-" + y)).limit(2));

        printStream("mapTo sequenceAsLong", Stream.generate(Suppliers.mapTo(Suppliers.sequenceAsLong(0), String::valueOf)));

        printStream("localTimeAsString", Stream.generate(Suppliers.localTimeAsString()));
        printStream("threadNameAsString", Stream.generate(Suppliers.threadNameAsString()));
        printStream("sequenceAsString 1.000", Stream.generate(Suppliers.sequenceAsString(1_000L)));

        printStream("conditional", Stream.generate(Suppliers.conditional(new RandomBooleanSupplier(60, new Random(100)).asPrimitiveBooleanSupplier(),
                Suppliers.constant("Test"), Suppliers.constantNull())));
    }

    @SuppressWarnings("CharUsedInArithmeticContext")
    private static void showRandomStringSuppliers() {
        System.out.println("-showRandomStringSuppliers---");

        RandomGenerator randomGenerator = new Random();

        printStream("uuid",
                Stream.generate(
                        RandomStringSuppliers.uuid()));

        // randomSelection
        printStream("randomSelection List 0",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, List.of())));
        printStream("randomSelection List 1",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, List.of("Aaa"))));
        printStream("randomSelection List 3",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, List.of("Aaa", "Bbb", "Ccc"))));
        printStream("randomSelection Array 0",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, new String[]{})));
        printStream("randomSelection Array 1",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, new String[]{"Aaa"})));
        printStream("randomSelection Array 3",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator, new String[]{"Aaa", "Bbb", "Ccc"})));
        printStream("randomSelection List splitTextByCharacterBreaks",
                Stream.generate(
                        Suppliers.randomSelection(randomGenerator,
                                TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList())));

        // intSupplierSelection
        printStream("intSupplierSelection List 0",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 0, List.of())));
        printStream("intSupplierSelection List 1",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 0, List.of("Aaa"))));
        printStream("intSupplierSelection List 3 always 2",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 2, List.of("Aaa", "Bbb", "Ccc"))));
        printStream("intSupplierSelection List 3 random",
                Stream.generate(
                        Suppliers.intSupplierSelection(RandomNumberSuppliers.primitiveIntSelection(randomGenerator, 1, 1, 1, 2), List.of("Aaa", "Bbb", "Ccc"))));
        printStream("intSupplierSelection Array 0",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 0, new String[]{})));
        printStream("intSupplierSelection Array 1",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 0, new String[]{"Aaa"})));
        printStream("intSupplierSelection Array 3 always 2",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 2, new String[]{"Aaa", "Bbb", "Ccc"})));
        printStream("intSupplierSelection Array 3 random",
                Stream.generate(
                        Suppliers.intSupplierSelection(RandomNumberSuppliers.primitiveIntSelection(randomGenerator, 1, 1, 1, 2), new String[]{"Aaa", "Bbb", "Ccc"})));

        printStream("codePointConcatenation Boundary A-z isAlphabetic",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20), 'A', 'z',
                                Character::isAlphabetic
                        )));
        printStream("codePointConcatenation Boundary 0-255 isLetterOrDigit",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20), 0, 255,
                                Character::isLetterOrDigit
                        )));
        printStream("codePointConcatenation Boundary 32-255 DASH_PUNCTUATION || CURRENCY_SYMBOL",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20), 32, 255,
                                c -> {
                                    var type = Character.getType(c);
                                    return (type == Character.DASH_PUNCTUATION) || (type == Character.CURRENCY_SYMBOL);
                                }
                        )));
        printStream("codePointConcatenation Boundary 128512-128515 smileys",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20), 128512, 128515,
                                codePoint -> true
                        )));

        printStream("codePointConcatenation Boundary large codePoints isLetterOrDigit",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20), Character.MAX_VALUE + 1, Integer.MAX_VALUE - 1,
                                codePoint -> Character.isValidCodePoint(codePoint) && Character.isDefined(codePoint) && Character.isLetterOrDigit(codePoint)
                        )));

        printStream("codePointConcatenation List 3",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                List.of(65, "€".codePointAt(0), 128512))));
        printStream("codePointConcatenation Array 3",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                65, "€".codePointAt(0), 128512)));
        printStream("codePointConcatenation String",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                "AbcdEfghIjklmnOpqrstUvwxyz")));
        printStream("codePointConcatenation String special",
                Stream.generate(
                        RandomStringSuppliers.codePointConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                ExamplesStrings.SPECIAL_CHARACTERS)));
        printStream("characterConcatenation List 3",
                Stream.generate(
                        RandomStringSuppliers.characterConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                List.of('A', 'B', 'C'))));

        printStream("characterConcatenation Array 4",
                Stream.generate(
                        RandomStringSuppliers.characterConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                'a', 'b', 'c', '€')));

        printStream("stringConcatenation List",
                Stream.generate(
                        RandomStringSuppliers.stringConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                List.of("A", "Bb", "Ccc"))));
        printStream("stringConcatenation splitTextByCharacterBreaks",
                Stream.generate(
                        RandomStringSuppliers.stringConcatenation(randomGenerator, () -> randomGenerator.nextInt(5, 20),
                                TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList())));

        printStream("stringCutting",
                Stream.generate(
                        RandomStringSuppliers.stringCutting(() -> 4 * randomGenerator.nextInt(0, 5), () -> 4,
                                "0123ABCD4567abcdXYZ")));
    }

    public static void main(String... args) {
        showSuppliers();
        showRandomStringSuppliers();
    }

}
