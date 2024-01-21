package stexfires.examples.util;

import org.jspecify.annotations.Nullable;
import stexfires.util.supplier.RepeatingPatternSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "ThrowablePrintedToSystemOut", "SameParameterValue"})
public final class ExamplesSupplier {

    private ExamplesSupplier() {
    }

    private static <T> void printStream(String title, Stream<T> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printIntStream(String title, IntStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printLongStream(String title, LongStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showRepeatingPatternSupplier() {
        System.out.println("-showRepeatingPatternSupplier---");

        // singletonList
        printStream("Pattern: singletonList [TRUE]",
                Stream.generate(
                        new RepeatingPatternSupplier<>(Collections.singletonList(Boolean.TRUE))));
        printStream("Pattern: singletonList [42]",
                Stream.generate(
                        new RepeatingPatternSupplier<>(Collections.singletonList(1))));

        // list
        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        printStream("Pattern: List<Boolean> [TRUE, TRUE, FALSE]",
                Stream.generate(
                        new RepeatingPatternSupplier<>(booleanList)));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(0);
        integerList.add(2);
        integerList.add(2);
        printStream("Pattern: List<Integer> [0, 2, 2]",
                Stream.generate(
                        new RepeatingPatternSupplier<>(integerList)));

        printStream("ofPrimitiveBoolean: true, true, false",
                Stream.generate(
                        RepeatingPatternSupplier.ofPrimitiveBoolean(true, true, false)));
        printStream("ofPrimitiveInt: 0, 2, 2",
                Stream.generate(
                        RepeatingPatternSupplier.ofPrimitiveInt(0, 2, 2)));
        printStream("ofPrimitiveLong: 0, 2, 2",
                Stream.generate(
                        RepeatingPatternSupplier.ofPrimitiveLong(0L, 2L, 2L)));

        printIntStream("IntStream ofPrimitiveInt: 0, 2, 2",
                IntStream.generate(
                        RepeatingPatternSupplier.ofPrimitiveInt(0, 2, 2)::get));
        printLongStream("LongStream ofPrimitiveLong: 0, 2, 2",
                LongStream.generate(
                        RepeatingPatternSupplier.ofPrimitiveLong(0L, 2L, 2L)::get));
        // Test empty pattern
        try {
            var supplier = new RepeatingPatternSupplier<>(List.of());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            var supplier = RepeatingPatternSupplier.ofPrimitiveBoolean();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            var supplier = RepeatingPatternSupplier.ofPrimitiveInt();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            var supplier = RepeatingPatternSupplier.ofPrimitiveLong();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        // Test pattern with null value
        try {
            List<@Nullable Boolean> nullBooleanList = new ArrayList<>(3);
            nullBooleanList.add(null);
            nullBooleanList.add(Boolean.FALSE);
            nullBooleanList.add(null);
            var supplier = new RepeatingPatternSupplier<>(nullBooleanList);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public static void main(String... args) {
        showRepeatingPatternSupplier();
    }

}
