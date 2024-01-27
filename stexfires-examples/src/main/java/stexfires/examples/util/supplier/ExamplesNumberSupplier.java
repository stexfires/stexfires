package stexfires.examples.util.supplier;

import stexfires.util.supplier.RandomNumberSuppliers;

import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberSupplier {

    private static final RandomGenerator RANDOM = new Random();
    private static final long STREAM_LIMIT = 10L;

    private ExamplesNumberSupplier() {
    }

    private static <T> void generateAndPrintStream(String title, Supplier<T> supplier) {
        System.out.println(title);
        Stream.generate(supplier)
              .limit(STREAM_LIMIT)
              .forEachOrdered(System.out::println);
    }

    private static void generateAndPrintIntStream(String title, IntSupplier supplier) {
        System.out.println(title);
        IntStream.generate(supplier)
                 .limit(STREAM_LIMIT)
                 .forEachOrdered(System.out::println);
    }

    private static void generateAndPrintLongStream(String title, LongSupplier supplier) {
        System.out.println(title);
        LongStream.generate(supplier)
                  .limit(STREAM_LIMIT)
                  .forEachOrdered(System.out::println);
    }

    private static void generateAndPrintDoubleStream(String title, DoubleSupplier supplier) {
        System.out.println(title);
        DoubleStream.generate(supplier)
                    .limit(STREAM_LIMIT)
                    .forEachOrdered(System.out::println);
    }

    private static void showRandomNumberSuppliers() {
        System.out.println("-showRandomNumberSuppliers---");

        generateAndPrintIntStream("randomPrimitiveInt",
                RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 10, 100));
        generateAndPrintStream("randomInteger",
                RandomNumberSuppliers.randomInteger(RANDOM, Integer.MIN_VALUE, Integer.MAX_VALUE));

        generateAndPrintLongStream("randomPrimitiveLong",
                RandomNumberSuppliers.randomPrimitiveLong(RANDOM, 10, 100));
        generateAndPrintStream("randomLong",
                RandomNumberSuppliers.randomLong(RANDOM, Long.MIN_VALUE, Long.MAX_VALUE));

        generateAndPrintDoubleStream("randomPrimitiveDouble",
                RandomNumberSuppliers.randomPrimitiveDouble(RANDOM, 10.0d, 100.0d));
        generateAndPrintStream("randomDouble",
                RandomNumberSuppliers.randomDouble(RANDOM, Double.MIN_VALUE, Double.MAX_VALUE));

        generateAndPrintStream("randomFloat",
                RandomNumberSuppliers.randomFloat(RANDOM, Float.MIN_VALUE, Float.MAX_VALUE));
        generateAndPrintStream("randomBigInteger",
                RandomNumberSuppliers.randomBigInteger(RANDOM, Long.MIN_VALUE, Long.MAX_VALUE));
        generateAndPrintStream("randomBigDecimal",
                RandomNumberSuppliers.randomBigDecimal(RANDOM, Double.MIN_VALUE, Double.MAX_VALUE));

        generateAndPrintDoubleStream("randomPrimitiveDoubleGaussian",
                RandomNumberSuppliers.randomPrimitiveDoubleGaussian(RANDOM, 50.0d, 10.0d));
        generateAndPrintStream("randomDoubleGaussian",
                RandomNumberSuppliers.randomDoubleGaussian(RANDOM, 50.0d, 10.0d));
    }

    private static void showRandomPrimitiveSelection() {
        System.out.println("-showRandomPrimitiveSelection---");

        generateAndPrintIntStream("primitiveIntSelection Array 1",
                RandomNumberSuppliers.primitiveIntSelection(RANDOM, 42));
        generateAndPrintIntStream("primitiveIntSelection Array 3",
                RandomNumberSuppliers.primitiveIntSelection(RANDOM, 42, 23, 1024));

        generateAndPrintLongStream("primitiveLongSelection Array 1",
                RandomNumberSuppliers.primitiveLongSelection(RANDOM, 42L));
        generateAndPrintLongStream("primitiveLongSelection Array 3",
                RandomNumberSuppliers.primitiveLongSelection(RANDOM, 42L, 23L, 1024L));

        generateAndPrintDoubleStream("primitiveDoubleSelection Array 1",
                RandomNumberSuppliers.primitiveDoubleSelection(RANDOM, 42.42d));
        generateAndPrintDoubleStream("primitiveDoubleSelection Array 3",
                RandomNumberSuppliers.primitiveDoubleSelection(RANDOM, 42.42d, 23.23d, 1024.1024d));
    }

    public static void main(String... args) {
        showRandomNumberSuppliers();
        showRandomPrimitiveSelection();
    }

}
