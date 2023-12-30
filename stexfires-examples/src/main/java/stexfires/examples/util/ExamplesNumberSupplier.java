package stexfires.examples.util;

import stexfires.util.function.RandomNumberSuppliers;
import stexfires.util.function.Suppliers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberSupplier {

    private ExamplesNumberSupplier() {
    }

    private static void printStream(String title, Stream<? extends Number> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, IntStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, LongStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, DoubleStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showSuppliersSequence() {
        System.out.println("-showSuppliersSequence---");

        printStream("sequenceAsLong 1.000",
                Stream.generate(
                        Suppliers.sequenceAsLong(1_000L)));
        printStream("sequenceAsLong -10",
                Stream.generate(
                        Suppliers.sequenceAsLong(-10L)));
        printStream("sequenceAsLong -1",
                Stream.generate(
                        Suppliers.sequenceAsLong(-1L)));

        printStream("sequenceAsPrimitiveLong 1.000",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(1_000L)));
        printStream("sequenceAsPrimitiveLong -10",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(-10L)));
        printStream("sequenceAsPrimitiveLong -1",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(-1L)));
    }

    private static void showSuppliersConstant() {
        System.out.println("-showSuppliersConstant---");

        printStream("constant Long.MAX_VALUE",
                Stream.generate(
                        Suppliers.constantOfNotNull(Long.MAX_VALUE)).limit(2));
        printStream("constant Integer.MAX_VALUE",
                Stream.generate(
                        Suppliers.constantOfNotNull(Integer.MAX_VALUE)).limit(2));
        printStream("constant Double.MAX_VALUE",
                Stream.generate(
                        Suppliers.constantOfNotNull(Double.MAX_VALUE)).limit(2));

        printStream("constantNull",
                Stream.generate(
                        Suppliers.constantNull()));
        printStream("constantNull <Integer>",
                Stream.generate(
                        Suppliers.<Integer>constantNull()).limit(2));

        printStream("constantPrimitiveLong Long.MAX_VALUE",
                LongStream.generate(
                        Suppliers.constantPrimitiveLong(Long.MAX_VALUE)).limit(2));
        printStream("constantPrimitiveInt Integer.MAX_VALUE",
                IntStream.generate(
                        Suppliers.constantPrimitiveInt(Integer.MAX_VALUE)).limit(2));
        printStream("constantPrimitiveDouble Double.MAX_VALUE",
                DoubleStream.generate(
                        Suppliers.constantPrimitiveDouble(Double.MAX_VALUE)).limit(2));
    }

    private static void showSuppliersCombine() {
        System.out.println("-showSuppliersCombine---");

        printStream("combine Long::sum",
                Stream.generate(
                        Suppliers.combine(() -> 1L, () -> 2L, Long::sum)).limit(2));
        printStream("combine Integer::sum",
                Stream.generate(
                        Suppliers.combine(() -> 1, () -> 2, Integer::sum)).limit(2));
        printStream("combine Double::sum",
                Stream.generate(
                        Suppliers.combine(() -> 1.0d, () -> 2.0d, Double::sum)).limit(2));

        printStream("combinePrimitiveLong Long.sum",
                LongStream.generate(
                        Suppliers.combinePrimitiveLong(() -> 1L, () -> 2L, Long::sum)).limit(2));
        printStream("combinePrimitiveInt Integer.sum",
                IntStream.generate(
                        Suppliers.combinePrimitiveInt(() -> 1, () -> 2, Integer::sum)).limit(2));
        printStream("combinePrimitiveDouble Double.sum",
                DoubleStream.generate(
                        Suppliers.combinePrimitiveDouble(() -> 1.0d, () -> 2.0d, Double::sum)).limit(2));
    }

    private static void showSuppliersMapTo() {
        System.out.println("-showSuppliersMapTo---");

        printStream("mapTo sequenceAsString Long::valueOf",
                Stream.generate(Suppliers.mapTo(Suppliers.sequenceAsString(1), Long::valueOf)));
        printStream("mapTo sequenceAsString Integer::valueOf",
                Stream.generate(Suppliers.mapTo(Suppliers.sequenceAsString(1), Integer::valueOf)));
        printStream("mapTo sequenceAsString Double::valueOf",
                Stream.generate(Suppliers.mapTo(Suppliers.sequenceAsString(1), Double::valueOf)));

        printStream("mapToPrimitiveLong sequenceAsString Long::valueOf",
                LongStream.generate(Suppliers.mapToPrimitiveLong(Suppliers.sequenceAsString(1), Long::valueOf)));
        printStream("mapToPrimitiveInt sequenceAsString Integer::valueOf",
                IntStream.generate(Suppliers.mapToPrimitiveInt(Suppliers.sequenceAsString(1), Integer::valueOf)));
        printStream("mapToPrimitiveDouble sequenceAsString Double::valueOf",
                DoubleStream.generate(Suppliers.mapToPrimitiveDouble(Suppliers.sequenceAsString(1), Double::valueOf)));
    }

    private static void showRandomSupplier() {
        System.out.println("-showRandomSupplier---");

        RandomGenerator randomGenerator = new Random();

        printStream("randomPrimitiveInt",
                IntStream.generate(
                        RandomNumberSuppliers.randomPrimitiveInt(randomGenerator, 10, 100)));
        printStream("randomInteger",
                Stream.generate(
                        RandomNumberSuppliers.randomInteger(randomGenerator, Integer.MIN_VALUE, Integer.MAX_VALUE)));

        printStream("randomPrimitiveLong",
                LongStream.generate(
                        RandomNumberSuppliers.randomPrimitiveLong(randomGenerator, 10, 100)));
        printStream("randomLong",
                Stream.generate(
                        RandomNumberSuppliers.randomLong(randomGenerator, Long.MIN_VALUE, Long.MAX_VALUE)));

        printStream("randomPrimitiveDouble",
                DoubleStream.generate(
                        RandomNumberSuppliers.randomPrimitiveDouble(randomGenerator, 10.0d, 100.0d)));
        printStream("randomDouble",
                Stream.generate(
                        RandomNumberSuppliers.randomDouble(randomGenerator, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomFloat",
                Stream.generate(
                        RandomNumberSuppliers.randomFloat(randomGenerator, Float.MIN_VALUE, Float.MAX_VALUE)));
        printStream("randomBigInteger",
                Stream.generate(
                        RandomNumberSuppliers.randomBigInteger(randomGenerator, Long.MIN_VALUE, Long.MAX_VALUE)));
        printStream("randomBigDecimal",
                Stream.generate(
                        RandomNumberSuppliers.randomBigDecimal(randomGenerator, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomPrimitiveDoubleGaussian",
                DoubleStream.generate(
                        RandomNumberSuppliers.randomPrimitiveDoubleGaussian(randomGenerator, 50.0d, 10.0d)));
        printStream("randomDoubleGaussian",
                Stream.generate(
                        RandomNumberSuppliers.randomDoubleGaussian(randomGenerator, 50.0d, 10.0d)));

    }

    private static void showRandomSelectionSupplier() {
        System.out.println("-showRandomSelectionSupplier---");

        RandomGenerator random = new Random();

        printStream("primitiveIntSelection Array 1",
                IntStream.generate(
                        RandomNumberSuppliers.primitiveIntSelection(random, 42)));
        printStream("primitiveIntSelection Array 3",
                IntStream.generate(
                        RandomNumberSuppliers.primitiveIntSelection(random, 42, 23, 1024)));

        printStream("randomListSelection Integer",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(42, 23, 1024))));
        printStream("randomSelection Varargs Integer",
                Stream.generate(
                        Suppliers.randomSelection(random, 42, 23, 1024)));

        printStream("intSupplierListSelection Integer always 1",
                Stream.generate(
                        Suppliers.intSupplierListSelection(() -> 1, List.of(42, 23, 1024))));
        printStream("intSupplierSelection Varargs Integer always 1",
                Stream.generate(
                        Suppliers.intSupplierSelection(() -> 1, 42, 23, 1024)));

        printStream("primitiveLongSelection Array 1",
                LongStream.generate(
                        RandomNumberSuppliers.primitiveLongSelection(random, 42L)));
        printStream("primitiveLongSelection Array 3",
                LongStream.generate(
                        RandomNumberSuppliers.primitiveLongSelection(random, 42L, 23L, 1024L)));
        printStream("randomListSelection Long",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(42L, 23L, 1024L))));
        printStream("randomSelection Varargs Long",
                Stream.generate(
                        Suppliers.randomSelection(random, 42L, 23L, 1024L)));

        printStream("primitiveDoubleSelection Array 1",
                DoubleStream.generate(
                        RandomNumberSuppliers.primitiveDoubleSelection(random, 42.0d)));
        printStream("primitiveDoubleSelection Array 3",
                DoubleStream.generate(
                        RandomNumberSuppliers.primitiveDoubleSelection(random, 42.0d, 23.0d, 1024.0d)));
        printStream("randomListSelection Double",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(42.0d, 23.0d, 1024.0d))));
        printStream("randomSelection Varargs Double",
                Stream.generate(
                        Suppliers.randomSelection(random, 42.0d, 23.0d, 1024.0d)));

        printStream("randomListSelection Float",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(42.0f, 23.0f, 1024.0f))));
        printStream("randomSelection Varargs Float",
                Stream.generate(
                        Suppliers.randomSelection(random, 42.0f, 23.0f, 1024.0f)));

        printStream("randomListSelection BigInteger",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L)))));
        printStream("randomSelection Varargs BigInteger",
                Stream.generate(
                        Suppliers.randomSelection(random, BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L))));

        printStream("randomListSelection BigDecimal",
                Stream.generate(
                        Suppliers.randomListSelection(random, List.of(BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d)))));
        printStream("randomSelection Varargs BigDecimal",
                Stream.generate(
                        Suppliers.randomSelection(random, BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d))));
    }

    public static void main(String... args) {
        showSuppliersSequence();
        showSuppliersConstant();
        showSuppliersCombine();
        showSuppliersMapTo();
        showRandomSupplier();
        showRandomSelectionSupplier();
    }

}
