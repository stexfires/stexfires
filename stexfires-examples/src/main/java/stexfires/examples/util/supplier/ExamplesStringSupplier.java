package stexfires.examples.util.supplier;

import stexfires.examples.util.ExamplesStrings;
import stexfires.util.TextSplitters;
import stexfires.util.supplier.RandomStringSuppliers;
import stexfires.util.supplier.Suppliers;

import java.util.*;
import java.util.function.*;
import java.util.random.*;
import java.util.stream.*;

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

    private static void showStringSuppliers() {
        System.out.println("-showStringSuppliers---");

        generateAndPrintStream("randomUUIDAsString", Suppliers.randomUUIDAsString());
        generateAndPrintStream("localTimeAsString", Suppliers.localTimeNowAsString());
        generateAndPrintStream("threadNameAsString", Suppliers.threadNameAsString());
        generateAndPrintStream("stringCutting", Suppliers.stringCutting(
                () -> 4 * RANDOM.nextInt(0, 5),
                () -> 4,
                "0123ABCD4567abcdXYZ"));
    }

    @SuppressWarnings("CharUsedInArithmeticContext")
    private static void showRandomStringSuppliers() {
        System.out.println("-showRandomStringSuppliers---");

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
    }

    public static void main(String... args) {
        showStringSuppliers();
        showRandomStringSuppliers();
    }

}
