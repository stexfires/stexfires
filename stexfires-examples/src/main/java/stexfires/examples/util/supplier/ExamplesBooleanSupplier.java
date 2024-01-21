package stexfires.examples.util.supplier;

import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.Suppliers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesBooleanSupplier {

    private static final RandomGenerator RANDOM = new Random();
    private static final long STREAM_LIMIT = 10L;

    private ExamplesBooleanSupplier() {
    }

    private static void generateAndPrintStream(String title, Supplier<Boolean> supplier) {
        System.out.println(title);
        Stream.generate(supplier)
              .limit(STREAM_LIMIT)
              .forEachOrdered(System.out::println);
    }

    private static void printBoolean(String title, boolean value) {
        System.out.println(title);
        System.out.println(value);
    }

    private static void showSuppliers() {
        System.out.println("-showSuppliers---");

        generateAndPrintStream("constant",
                Suppliers.constantOfNotNull(Boolean.TRUE));
        generateAndPrintStream("constantNull",
                Suppliers.constantNull());
        printBoolean("constantPrimitiveBoolean",
                Suppliers.constantPrimitiveBoolean(true).getAsBoolean());

        generateAndPrintStream("combine Boolean::logicalAnd",
                Suppliers.combine(() -> Boolean.TRUE, () -> Boolean.FALSE, Boolean::logicalAnd));
        printBoolean("combinePrimitiveBoolean",
                Suppliers.combinePrimitiveBoolean(() -> true, () -> false, (x, y) -> x && y).getAsBoolean());

        generateAndPrintStream("mapTo constant parseBoolean",
                Suppliers.mapTo(Suppliers.constantOfNotNull("true"), Boolean::parseBoolean));
        printBoolean("mapToPrimitiveBoolean constant parseBoolean",
                Suppliers.mapToPrimitiveBoolean(Suppliers.constantOfNotNull("false"), Boolean::parseBoolean).getAsBoolean());

        generateAndPrintStream("randomListSelection List",
                Suppliers.randomListSelection(new Random(), List.of(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)));
        generateAndPrintStream("randomSelection Varargs",
                Suppliers.randomSelection(new Random(), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
    }

    private static void showRandomBooleanSupplier() {
        System.out.println("-showRandomBooleanSupplier---");

        generateAndPrintStream("Random: 101%",
                new RandomBooleanSupplier(101, RANDOM));
        generateAndPrintStream("Random: 100%",
                new RandomBooleanSupplier(100, RANDOM));
        generateAndPrintStream("Random: 99%",
                new RandomBooleanSupplier(99, RANDOM));
        generateAndPrintStream("Random: 75%",
                new RandomBooleanSupplier(75, RANDOM));
        generateAndPrintStream("Random: 50%",
                new RandomBooleanSupplier(50, RANDOM));
        generateAndPrintStream("Random:  1%",
                new RandomBooleanSupplier(1, RANDOM));
        generateAndPrintStream("Random:  0%",
                new RandomBooleanSupplier(0, RANDOM));
        generateAndPrintStream("Random:  -1%",
                new RandomBooleanSupplier(-1, RANDOM));

        System.out.println("Random: 99% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(99, RANDOM))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println("Random: 50% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(50, RANDOM))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println("Random: 1% 10.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(1, RANDOM))
                                 .limit(10_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        generateAndPrintStream("Random: 50% Random Seed 1L",
                new RandomBooleanSupplier(50, new Random(1L)));

        generateAndPrintStream("Random: 50% ThreadLocalRandom",
                new RandomBooleanSupplier(50, ThreadLocalRandom.current()));

        printBoolean("Random: 50% primitive boolean",
                new RandomBooleanSupplier(50, RANDOM).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    public static void main(String... args) {
        showSuppliers();
        showRandomBooleanSupplier();
    }

}
