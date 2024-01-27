package stexfires.examples.util.supplier;

import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.Suppliers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;
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

    private static void printBooleans(String title, BooleanSupplier supplier) {
        System.out.println(title);
        for (int i = 0; i < STREAM_LIMIT; i++) {
            System.out.println(supplier.getAsBoolean());
        }
    }

    private static void showBooleanSuppliers() {
        System.out.println("-showBooleanSuppliers---");

        printBooleans("randomPrimitiveBoolean", Suppliers.randomPrimitiveBoolean(RANDOM));
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

        printBooleans("Random: 50% primitive boolean",
                new RandomBooleanSupplier(50, RANDOM).asPrimitiveBooleanSupplier());
    }

    public static void main(String... args) {
        showBooleanSuppliers();
        showRandomBooleanSupplier();
    }

}
