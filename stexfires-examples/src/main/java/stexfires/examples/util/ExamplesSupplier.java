package stexfires.examples.util;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.NumberPredicates;
import stexfires.util.supplier.RepeatingPatternSupplier;
import stexfires.util.supplier.SwitchingSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
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

    private static <T> void generateAndPrintStream(String title, Supplier<T> supplier) {
        printStream(title, Stream.generate(supplier));
    }

    private static void printIntStream(String title, IntStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void generateAndPrintIntStream(String title, IntSupplier supplier) {
        printIntStream(title, IntStream.generate(supplier));
    }

    private static void printLongStream(String title, LongStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void generateAndPrintLongStream(String title, LongSupplier supplier) {
        printLongStream(title, LongStream.generate(supplier));
    }

    private static void showRepeatingPatternSupplier() {
        System.out.println("-showRepeatingPatternSupplier---");

        // singletonList
        generateAndPrintStream("singletonList [TRUE]",
                new RepeatingPatternSupplier<>(Collections.singletonList(Boolean.TRUE)));
        generateAndPrintStream("singletonList [42]",
                new RepeatingPatternSupplier<>(Collections.singletonList(1)));
        // List.of
        generateAndPrintStream("List.of [A, B, C]",
                new RepeatingPatternSupplier<>(List.of("A", "B", "C")));
        // Set.of
        generateAndPrintStream("Set.of [C, B, A]",
                new RepeatingPatternSupplier<>(Set.of("C", "B", "A")));
        // HashSet
        Collection<String> hashSet = HashSet.newHashSet(5);
        hashSet.add("first");
        hashSet.add("second");
        hashSet.add("third");
        hashSet.add("fourth");
        hashSet.add("fifth");
        generateAndPrintStream("HashSet [first, second, third, fourth, fifth]",
                new RepeatingPatternSupplier<>(hashSet));

        // list
        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        generateAndPrintStream("List<Boolean> [TRUE, TRUE, FALSE]",
                new RepeatingPatternSupplier<>(booleanList));
        List<Integer> integerList = new ArrayList<>();
        integerList.add(0);
        integerList.add(2);
        integerList.add(2);
        generateAndPrintStream("List<Integer> [0, 2, 2]",
                new RepeatingPatternSupplier<>(integerList));

        generateAndPrintStream("ofPrimitiveBoolean: true, true, false",
                RepeatingPatternSupplier.ofPrimitiveBoolean(true, true, false));
        generateAndPrintStream("ofPrimitiveInt: 0, 2, 2",
                RepeatingPatternSupplier.ofPrimitiveInt(0, 2, 2));
        generateAndPrintStream("ofPrimitiveLong: 0, 2, 2",
                RepeatingPatternSupplier.ofPrimitiveLong(0L, 2L, 2L));

        generateAndPrintIntStream("IntStream ofPrimitiveInt: 0, 2, 2",
                RepeatingPatternSupplier.ofPrimitiveInt(0, 2, 2)::get);
        generateAndPrintLongStream("LongStream ofPrimitiveLong: 0, 2, 2",
                RepeatingPatternSupplier.ofPrimitiveLong(0L, 2L, 2L)::get);

        // Test empty pattern
        System.out.println("Test empty pattern");
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

        // Test pattern with null values
        System.out.println("Test pattern with null values");
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

    private static void showSwitchingSupplier() {
        System.out.println("-showSwitchingSupplier---");

        generateAndPrintStream("FALSE, TRUE, i -> i == 2 || i == 5",
                new SwitchingSupplier<>(Boolean.FALSE, Boolean.TRUE,
                        i -> i == 2 || i == 5));

        generateAndPrintStream("A, B, DEFAULT_START_INDEX, even",
                new SwitchingSupplier<>("A", "B", SwitchingSupplier.DEFAULT_START_INDEX,
                        NumberPredicates.PrimitiveIntPredicates.even()));

        generateAndPrintStream("A, B, DEFAULT_START_INDEX, multipleOf 3",
                new SwitchingSupplier<>("A", "B", SwitchingSupplier.DEFAULT_START_INDEX,
                        NumberPredicates.PrimitiveIntPredicates.multipleOf(3)));

        generateAndPrintStream("atIndex: A, B, 3",
                SwitchingSupplier.atIndex("A", "B", 3));

        generateAndPrintStream("everyTime: A, B",
                SwitchingSupplier.everyTime("A", "B"));

        generateAndPrintStream("everyTime: TRUE, FALSE",
                SwitchingSupplier.everyTime(Boolean.TRUE, Boolean.FALSE));

        generateAndPrintIntStream("IntStream atIndex: -100, 100, 4",
                SwitchingSupplier.atIndex(-100, 100, 4)::get);

        generateAndPrintLongStream("LongStream everyTime: MIN_VALUE, MAX_VALUE",
                SwitchingSupplier.everyTime(Long.MIN_VALUE, Long.MAX_VALUE)::get);
    }

    public static void main(String... args) {
        showRepeatingPatternSupplier();
        showSwitchingSupplier();
    }

}
