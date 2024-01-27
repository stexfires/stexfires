package stexfires.examples.util.supplier;

import stexfires.util.supplier.RandomNumberSuppliers;
import stexfires.util.supplier.SequenceSupplier;
import stexfires.util.supplier.Suppliers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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

    private static void showSuppliersMapTo() {
        System.out.println("-showSuppliersMapTo---");

        generateAndPrintStream("mapTo sequenceAsString Long::valueOf",
                Suppliers.mapTo(SequenceSupplier.asString(1), Long::valueOf));
        generateAndPrintStream("mapTo sequenceAsString Integer::valueOf",
                Suppliers.mapTo(SequenceSupplier.asString(1), Integer::valueOf));
        generateAndPrintStream("mapTo sequenceAsString Double::valueOf",
                Suppliers.mapTo(SequenceSupplier.asString(1), Double::valueOf));

        generateAndPrintLongStream("mapToPrimitiveLong sequenceAsString Long::valueOf",
                Suppliers.mapToPrimitiveLong(SequenceSupplier.asString(1), Long::valueOf));
        generateAndPrintIntStream("mapToPrimitiveInt sequenceAsString Integer::valueOf",
                Suppliers.mapToPrimitiveInt(SequenceSupplier.asString(1), Integer::valueOf));
        generateAndPrintDoubleStream("mapToPrimitiveDouble sequenceAsString Double::valueOf",
                Suppliers.mapToPrimitiveDouble(SequenceSupplier.asString(1), Double::valueOf));
    }

    private static void showRandomSupplier() {
        System.out.println("-showRandomSupplier---");

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

    private static void showRandomSelectionSupplier() {
        System.out.println("-showRandomSelectionSupplier---");

        generateAndPrintIntStream("primitiveIntSelection Array 1",
                RandomNumberSuppliers.primitiveIntSelection(RANDOM, 42));
        generateAndPrintIntStream("primitiveIntSelection Array 3",
                RandomNumberSuppliers.primitiveIntSelection(RANDOM, 42, 23, 1024));

        generateAndPrintStream("randomListSelection Integer",
                Suppliers.randomListSelection(RANDOM, List.of(42, 23, 1024)));
        generateAndPrintStream("randomSelection Varargs Integer",
                Suppliers.randomSelection(RANDOM, 42, 23, 1024));

        generateAndPrintStream("intSupplierListSelection Integer always 1",
                Suppliers.intSupplierListSelection(() -> 1, List.of(42, 23, 1024)));
        generateAndPrintStream("intSupplierSelection Varargs Integer always 1",
                Suppliers.intSupplierSelection(() -> 1, 42, 23, 1024));

        generateAndPrintLongStream("primitiveLongSelection Array 1",
                RandomNumberSuppliers.primitiveLongSelection(RANDOM, 42L));
        generateAndPrintLongStream("primitiveLongSelection Array 3",
                RandomNumberSuppliers.primitiveLongSelection(RANDOM, 42L, 23L, 1024L));
        generateAndPrintStream("randomListSelection Long",
                Suppliers.randomListSelection(RANDOM, List.of(42L, 23L, 1024L)));
        generateAndPrintStream("randomSelection Varargs Long",
                Suppliers.randomSelection(RANDOM, 42L, 23L, 1024L));

        generateAndPrintDoubleStream("primitiveDoubleSelection Array 1",
                RandomNumberSuppliers.primitiveDoubleSelection(RANDOM, 42.0d));
        generateAndPrintDoubleStream("primitiveDoubleSelection Array 3",
                RandomNumberSuppliers.primitiveDoubleSelection(RANDOM, 42.0d, 23.0d, 1024.0d));
        generateAndPrintStream("randomListSelection Double",
                Suppliers.randomListSelection(RANDOM, List.of(42.0d, 23.0d, 1024.0d)));
        generateAndPrintStream("randomSelection Varargs Double",
                Suppliers.randomSelection(RANDOM, 42.0d, 23.0d, 1024.0d));

        generateAndPrintStream("randomListSelection Float",
                Suppliers.randomListSelection(RANDOM, List.of(42.0f, 23.0f, 1024.0f)));
        generateAndPrintStream("randomSelection Varargs Float",
                Suppliers.randomSelection(RANDOM, 42.0f, 23.0f, 1024.0f));

        generateAndPrintStream("randomListSelection BigInteger",
                Suppliers.randomListSelection(RANDOM, List.of(BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L))));
        generateAndPrintStream("randomSelection Varargs BigInteger",
                Suppliers.randomSelection(RANDOM, BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L)));

        generateAndPrintStream("randomListSelection BigDecimal",
                Suppliers.randomListSelection(RANDOM, List.of(BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d))));
        generateAndPrintStream("randomSelection Varargs BigDecimal",
                Suppliers.randomSelection(RANDOM, BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d)));
    }

    public static void main(String... args) {
        showSuppliersMapTo();
        showRandomSupplier();
        showRandomSelectionSupplier();
    }

}
