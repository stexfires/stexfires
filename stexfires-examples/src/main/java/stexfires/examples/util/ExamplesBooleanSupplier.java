package stexfires.examples.util;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.NumberPredicates;
import stexfires.util.function.RandomBooleanSupplier;
import stexfires.util.function.RepeatingPatternBooleanSupplier;
import stexfires.util.function.Suppliers;
import stexfires.util.function.SwitchingBooleanSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesBooleanSupplier {

    private ExamplesBooleanSupplier() {
    }

    private static void printStream(String title, Stream<Boolean> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printBoolean(String title, boolean value) {
        System.out.println(title);
        System.out.println(value);
    }

    private static void showSuppliers() {
        System.out.println("-showSuppliers---");

        printStream("constant",
                Stream.generate(
                        Suppliers.constantOfNotNull(Boolean.TRUE)).limit(2));
        printStream("constantNull",
                Stream.generate(
                        Suppliers.<Boolean>constantNull()).limit(2));
        printBoolean("constantPrimitiveBoolean",
                Suppliers.constantPrimitiveBoolean(true).getAsBoolean());

        printStream("combine Boolean::logicalAnd",
                Stream.generate(
                        Suppliers.combine(() -> Boolean.TRUE, () -> Boolean.FALSE, Boolean::logicalAnd)).limit(2));
        printBoolean("combinePrimitiveBoolean",
                Suppliers.combinePrimitiveBoolean(() -> true, () -> false, (x, y) -> x && y).getAsBoolean());

        printStream("mapTo constant parseBoolean",
                Stream.generate(
                        Suppliers.mapTo(Suppliers.constantOfNotNull("true"), Boolean::parseBoolean)).limit(2));
        printBoolean("mapToPrimitiveBoolean constant parseBoolean",
                Suppliers.mapToPrimitiveBoolean(Suppliers.constantOfNotNull("false"), Boolean::parseBoolean).getAsBoolean());

        printStream("randomListSelection List",
                Stream.generate(
                        Suppliers.randomListSelection(new Random(), List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE))));
        printStream("randomSelection Varargs",
                Stream.generate(
                        Suppliers.randomSelection(new Random(), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)));
    }

    private static void showRandomBooleanSupplier() {
        System.out.println("-showRandomBooleanSupplier---");

        RandomGenerator randomGenerator = new Random();

        printStream("Random: 101%",
                Stream.generate(new RandomBooleanSupplier(101, randomGenerator)));
        printStream("Random: 100%",
                Stream.generate(new RandomBooleanSupplier(100, randomGenerator)));
        printStream("Random: 99%",
                Stream.generate(new RandomBooleanSupplier(99, randomGenerator)));
        printStream("Random: 75%",
                Stream.generate(new RandomBooleanSupplier(75, randomGenerator)));
        printStream("Random: 50%",
                Stream.generate(new RandomBooleanSupplier(50, randomGenerator)));
        printStream("Random:  1%",
                Stream.generate(new RandomBooleanSupplier(1, randomGenerator)));
        printStream("Random:  0%",
                Stream.generate(new RandomBooleanSupplier(0, randomGenerator)));
        printStream("Random:  -1%",
                Stream.generate(new RandomBooleanSupplier(-1, randomGenerator)));

        System.out.println("Random: 99% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(99, randomGenerator))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println("Random: 50% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(50, randomGenerator))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println("Random: 1% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(1, randomGenerator))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        printStream("Random: 50% Random Seed 1L",
                Stream.generate(new RandomBooleanSupplier(50, new Random(1L))));

        printStream("Random: 50% ThreadLocalRandom",
                Stream.generate(new RandomBooleanSupplier(50, ThreadLocalRandom.current())));

        printBoolean("Random: 50% primitive boolean",
                new RandomBooleanSupplier(50, randomGenerator).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    @SuppressWarnings({"ThrowablePrintedToSystemOut", "NullableProblems"})
    private static void showRepeatingPatternBooleanSupplier() {
        System.out.println("-showRepeatingPatternBooleanSupplier---");

        printStream("Pattern: List [TRUE]",
                Stream.generate(
                        new RepeatingPatternBooleanSupplier(Collections.singletonList(Boolean.TRUE))));

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        printStream("Pattern: List [TRUE, TRUE, FALSE]",
                Stream.generate(
                        new RepeatingPatternBooleanSupplier(booleanList)));

        printStream("Pattern: true, true, false",
                Stream.generate(
                        RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, false)));

        printStream("Pattern: true, true, true, false",
                Stream.generate(
                        RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, true, false)));

        printBoolean("Pattern: List [FALSE]  primitive boolean",
                new RepeatingPatternBooleanSupplier(List.of(Boolean.FALSE)).asPrimitiveBooleanSupplier().getAsBoolean());

        List<@Nullable Boolean> nullBooleanList = new ArrayList<>();
        nullBooleanList.add(null);
        try {
            var supplier = new RepeatingPatternBooleanSupplier(nullBooleanList);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        try {
            var supplier = new RepeatingPatternBooleanSupplier(List.of());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    private static void showSwitchingBooleanSupplier() {
        System.out.println("-showSwitchingBooleanSupplier---");

        printStream("Switching: Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, i -> i == 2",
                Stream.generate(
                        new SwitchingBooleanSupplier(Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, i -> i == 2)));

        printStream("Switching: Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, even",
                Stream.generate(
                        new SwitchingBooleanSupplier(Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, NumberPredicates.PrimitiveIntPredicates.even())));

        printStream("Switching: Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, multipleOf 3",
                Stream.generate(
                        new SwitchingBooleanSupplier(Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, NumberPredicates.PrimitiveIntPredicates.multipleOf(3))));

        printStream("Switching: onlyOnce at index 3",
                Stream.generate(
                        SwitchingBooleanSupplier.onlyOnce(false, 3)));

        printStream("Switching: everyTime",
                Stream.generate(
                        SwitchingBooleanSupplier.everyTime(false)));

        printBoolean("Switching: everyTime primitive boolean",
                SwitchingBooleanSupplier.everyTime(true).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    public static void main(String... args) {
        showSuppliers();
        showRandomBooleanSupplier();
        showRepeatingPatternBooleanSupplier();
        showSwitchingBooleanSupplier();
    }

}
