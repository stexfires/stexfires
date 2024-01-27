package stexfires.examples.util.supplier;

import stexfires.examples.util.ExamplesStrings;
import stexfires.util.TextSplitters;
import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.RandomNumberSuppliers;
import stexfires.util.supplier.RandomStringSuppliers;
import stexfires.util.supplier.SequenceSupplier;
import stexfires.util.supplier.Suppliers;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesStringSupplier {

    private static final RandomGenerator RANDOM = new Random();
    private static final long STREAM_LIMIT = 10L;

    private ExamplesStringSupplier() {
    }

    private static void generateAndPrintStream(String title, Supplier<String> supplier) {
        System.out.println(title);
        Stream.generate(supplier)
              .limit(STREAM_LIMIT)
              .forEachOrdered(System.out::println);
    }

    private static void showSuppliers() {
        System.out.println("-showSuppliers---");

        generateAndPrintStream("constant", Suppliers.constantOfNotNull("Test"));
        generateAndPrintStream("constantNull", Suppliers.constantNull());

        generateAndPrintStream("combine", Suppliers.combine(() -> "X", () -> "Y", (x, y) -> x + "-" + y));

        generateAndPrintStream("mapTo sequenceAsLong", Suppliers.mapTo(SequenceSupplier.asLong(0), String::valueOf));

        generateAndPrintStream("localTimeAsString", Suppliers.localTimeAsString());
        generateAndPrintStream("threadNameAsString", Suppliers.threadNameAsString());

        generateAndPrintStream("conditional", Suppliers.conditional(new RandomBooleanSupplier(60, new Random(100)).asPrimitiveBooleanSupplier(),
                Suppliers.constantOfNotNull("Test"), Suppliers.constantNull()));
    }

    @SuppressWarnings("CharUsedInArithmeticContext")
    private static void showRandomStringSuppliers() {
        System.out.println("-showRandomStringSuppliers---");

        // uuid
        generateAndPrintStream("uuid",
                RandomStringSuppliers.uuid());

        // randomListSelection
        generateAndPrintStream("randomListSelection 1",
                Suppliers.randomListSelection(RANDOM, List.of("Aaa")));
        generateAndPrintStream("randomListSelection 3",
                Suppliers.randomListSelection(RANDOM, List.of("Aaa", "Bbb", "Ccc")));
        generateAndPrintStream("randomListSelection splitTextByCharacterBreaks",
                Suppliers.randomListSelection(RANDOM,
                        TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList()));

        // randomSelection
        generateAndPrintStream("randomSelection Varargs 1",
                Suppliers.randomSelection(RANDOM, "Aaa"));
        generateAndPrintStream("randomSelection Varargs 3",
                Suppliers.randomSelection(RANDOM, "Aaa", "Bbb", "Ccc"));

        // intSupplierListSelection
        generateAndPrintStream("intSupplierListSelection 1",
                Suppliers.intSupplierListSelection(() -> 0, List.of("Aaa")));
        generateAndPrintStream("intSupplierListSelection 3 always 2",
                Suppliers.intSupplierListSelection(() -> 2, List.of("Aaa", "Bbb", "Ccc")));
        generateAndPrintStream("intSupplierListSelection 3 random",
                Suppliers.intSupplierListSelection(RandomNumberSuppliers.primitiveIntSelection(RANDOM, 1, 1, 1, 2), List.of("Aaa", "Bbb", "Ccc")));

        // intSupplierSelection
        generateAndPrintStream("intSupplierSelection Varargs 1",
                Suppliers.intSupplierSelection(() -> 0, "Aaa"));
        generateAndPrintStream("intSupplierSelection Varargs 3 always 2",
                Suppliers.intSupplierSelection(() -> 2, "Aaa", "Bbb", "Ccc"));
        generateAndPrintStream("intSupplierSelection Varargs 3 random",
                Suppliers.intSupplierSelection(RandomNumberSuppliers.primitiveIntSelection(RANDOM, 1, 1, 1, 2), "Aaa", "Bbb", "Ccc"));

        // codePointConcatenation
        generateAndPrintStream("codePointConcatenation Boundary A-z isAlphabetic",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20), 'A', 'z',
                        Character::isAlphabetic));
        generateAndPrintStream("codePointConcatenation Boundary 0-255 isLetterOrDigit",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20), 0, 255,
                        Character::isLetterOrDigit));
        generateAndPrintStream("codePointConcatenation Boundary 32-255 DASH_PUNCTUATION || CURRENCY_SYMBOL",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20), 32, 255,
                        c -> {
                            var type = Character.getType(c);
                            return (type == Character.DASH_PUNCTUATION) || (type == Character.CURRENCY_SYMBOL);
                        }
                ));
        generateAndPrintStream("codePointConcatenation Boundary 128512-128515 smileys",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20), 128512, 128515,
                        codePoint -> true
                ));
        generateAndPrintStream("codePointConcatenation Boundary large codePoints isLetterOrDigit",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20), Character.MAX_VALUE + 1, Integer.MAX_VALUE - 1,
                        codePoint -> Character.isValidCodePoint(codePoint) && Character.isDefined(codePoint) && Character.isLetterOrDigit(codePoint)
                ));
        generateAndPrintStream("codePointConcatenation List 3",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        List.of(65, "€".codePointAt(0), 128512)));
        generateAndPrintStream("codePointConcatenation Array 3",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        65, "€".codePointAt(0), 128512));
        generateAndPrintStream("codePointConcatenation String",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        "AbcdEfghIjklmnOpqrstUvwxyz"));
        generateAndPrintStream("codePointConcatenation String special",
                RandomStringSuppliers.codePointConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        ExamplesStrings.SPECIAL_CHARACTERS));

        // characterConcatenation
        generateAndPrintStream("characterConcatenation List 3",
                RandomStringSuppliers.characterConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        List.of('A', 'B', 'C')));
        generateAndPrintStream("characterConcatenation Array 4",
                RandomStringSuppliers.characterConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        'a', 'b', 'c', '€'));

        // stringConcatenation
        generateAndPrintStream("stringConcatenation List",
                RandomStringSuppliers.stringConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        List.of("A", "Bb", "Ccc")));
        generateAndPrintStream("stringConcatenation splitTextByCharacterBreaks",
                RandomStringSuppliers.stringConcatenation(RANDOM, () -> RANDOM.nextInt(5, 20),
                        TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList()));

        // stringCutting
        generateAndPrintStream("stringCutting",
                RandomStringSuppliers.stringCutting(() -> 4 * RANDOM.nextInt(0, 5), () -> 4,
                        "0123ABCD4567abcdXYZ"));
    }

    public static void main(String... args) {
        showSuppliers();
        showRandomStringSuppliers();
    }

}
