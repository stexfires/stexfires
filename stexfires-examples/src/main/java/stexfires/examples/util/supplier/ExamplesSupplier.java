package stexfires.examples.util.supplier;

import org.jspecify.annotations.Nullable;
import stexfires.examples.util.ExamplesStrings;
import stexfires.util.Alignment;
import stexfires.util.SortNulls;
import stexfires.util.TextSplitters;
import stexfires.util.function.NumberPredicates;
import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.RandomNumberSuppliers;
import stexfires.util.supplier.RepeatingPatternSupplier;
import stexfires.util.supplier.SequenceSupplier;
import stexfires.util.supplier.Suppliers;
import stexfires.util.supplier.SwitchingSupplier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "ThrowablePrintedToSystemOut", "MagicNumber"})
public final class ExamplesSupplier {

    private static final RandomGenerator RANDOM = new Random();
    private static final long STREAM_LIMIT = 10L;

    private ExamplesSupplier() {
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

    private static void printBooleans(String title, BooleanSupplier supplier) {
        System.out.println(title);
        for (int i = 0; i < STREAM_LIMIT; i++) {
            System.out.println(supplier.getAsBoolean());
        }
    }

    private static void showSequenceSupplier() {
        System.out.println("-showSequenceSupplier---");

        // asString
        generateAndPrintStream("asString 1.000", SequenceSupplier.asString(1_000L));

        // asLong
        generateAndPrintStream("asLong 1.000",
                SequenceSupplier.asLong(1_000L));
        generateAndPrintStream("asLong -1",
                SequenceSupplier.asLong(-1L));

        // asPrimitiveLong
        generateAndPrintLongStream("asPrimitiveLong 1.000",
                SequenceSupplier.asPrimitiveLong(1_000L));
        generateAndPrintLongStream("asPrimitiveLong -1",
                SequenceSupplier.asPrimitiveLong(-1L));
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

    private static void showConstant() {
        System.out.println("-showConstant---");

        // constantOfNotNull
        generateAndPrintStream("constantOfNotNull Test", Suppliers.constantOfNotNull("Test"));
        generateAndPrintStream("constantOfNotNull 0", Suppliers.constantOfNotNull(0));
        generateAndPrintStream("constantOfNotNull Boolean TRUE", Suppliers.constantOfNotNull(Boolean.TRUE));
        generateAndPrintStream("constantOfNotNull Enum FIRST", Suppliers.constantOfNotNull(SortNulls.FIRST));
        generateAndPrintStream("constantOfNotNull Set<String>", Suppliers.constantOfNotNull(Set.of("A", "B", "C")));

        // constantOfNullable
        generateAndPrintStream("constantOfNullable null", Suppliers.constantOfNullable(null));
        generateAndPrintStream("constantOfNullable Test", Suppliers.constantOfNullable("Test"));

        // constantNull
        generateAndPrintStream("constantNull", Suppliers.constantNull());
        Supplier<@Nullable Integer> constantNullInteger = Suppliers.constantNull();
        generateAndPrintStream("constantNull <Integer>", constantNullInteger);
        generateAndPrintStream("constantNull <Integer>", Suppliers.<@Nullable Integer>constantNull());

        // constantPrimitive
        printBooleans("constantPrimitiveBoolean true", Suppliers.constantPrimitiveBoolean(true));
        generateAndPrintLongStream("constantPrimitiveLong Long.MAX_VALUE", Suppliers.constantPrimitiveLong(Long.MAX_VALUE));
        generateAndPrintIntStream("constantPrimitiveInt Integer.MAX_VALUE", Suppliers.constantPrimitiveInt(Integer.MAX_VALUE));
        generateAndPrintDoubleStream("constantPrimitiveDouble Double.MAX_VALUE", Suppliers.constantPrimitiveDouble(Double.MAX_VALUE));
    }

    private static void showCombine() {
        System.out.println("-showCombine---");

        generateAndPrintStream("combine String", Suppliers.combine(() -> "X", () -> "Y", (x, y) -> x + "-" + y));

        generateAndPrintStream("combine Long::sum", Suppliers.combine(() -> 1L, () -> 2L, Long::sum));
        generateAndPrintStream("combine Integer::sum", Suppliers.combine(() -> 1, () -> 2, Integer::sum));
        generateAndPrintStream("combine Double::sum", Suppliers.combine(() -> 1.0d, () -> 2.0d, Double::sum));

        generateAndPrintStream("combine Boolean::logicalAnd", Suppliers.combine(() -> Boolean.TRUE, () -> Boolean.FALSE, Boolean::logicalAnd));

        generateAndPrintStream("combine Alignment", Suppliers.combine(() -> Alignment.START, () -> Alignment.END, (a0, a1) -> a0 == a1 ? a0 : Alignment.CENTER));
        generateAndPrintStream("combine Set addAll", Suppliers.combine(() -> Set.of("A", "B"), () -> Set.of("B", "C", "D"), (s0, s1) -> {
            Set<String> set = HashSet.newHashSet(s0.size() + s1.size());
            set.addAll(s0);
            set.addAll(s1);
            return set;
        }));

        printBooleans("combinePrimitiveBoolean &&", Suppliers.combinePrimitiveBoolean(() -> true, () -> false, (x, y) -> x && y));
        generateAndPrintIntStream("combinePrimitiveInt Integer.sum", Suppliers.combinePrimitiveInt(() -> 1, () -> 2, Integer::sum));
        generateAndPrintLongStream("combinePrimitiveLong Long.sum", Suppliers.combinePrimitiveLong(() -> 1L, () -> 2L, Long::sum));
        generateAndPrintDoubleStream("combinePrimitiveDouble Double.sum", Suppliers.combinePrimitiveDouble(() -> 1.0d, () -> 2.0d, Double::sum));
    }

    private static void showMapTo() {
        System.out.println("-showMapTo---");

        generateAndPrintStream("mapTo String::valueOf", Suppliers.mapTo(SequenceSupplier.asLong(0), String::valueOf));
        generateAndPrintStream("mapTo Boolean::parseBoolean", Suppliers.mapTo(Suppliers.constantOfNotNull("true"), Boolean::parseBoolean));
        generateAndPrintStream("mapTo Integer::valueOf", Suppliers.mapTo(SequenceSupplier.asString(1), Integer::valueOf));
        generateAndPrintStream("mapTo Long::valueOf", Suppliers.mapTo(SequenceSupplier.asString(1), Long::valueOf));
        generateAndPrintStream("mapTo Double::valueOf", Suppliers.mapTo(SequenceSupplier.asString(1), Double::valueOf));
        generateAndPrintStream("mapTo Alignment::valueOf", Suppliers.mapTo(Suppliers.constantOfNotNull("START"), Alignment::valueOf));
        generateAndPrintStream("mapTo Set::of", Suppliers.mapTo(Suppliers.constantOfNotNull("A"), Set::of));

        // mapToPrimitive
        printBooleans("mapToPrimitiveBoolean", Suppliers.mapToPrimitiveBoolean(Suppliers.constantOfNotNull("false"), Boolean::parseBoolean));
        generateAndPrintIntStream("mapToPrimitiveInt", Suppliers.mapToPrimitiveInt(SequenceSupplier.asString(1), Integer::valueOf));
        generateAndPrintLongStream("mapToPrimitiveLong", Suppliers.mapToPrimitiveLong(SequenceSupplier.asString(1), Long::valueOf));
        generateAndPrintDoubleStream("mapToPrimitiveDouble", Suppliers.mapToPrimitiveDouble(SequenceSupplier.asString(1), Double::valueOf));
    }

    private static void showConditional() {
        System.out.println("-showConditional---");

        generateAndPrintStream("conditional String", Suppliers.conditional(
                new RandomBooleanSupplier(60, new Random(100)).asPrimitiveBooleanSupplier(),
                Suppliers.constantOfNotNull("A"),
                Suppliers.constantOfNotNull("B")));
        generateAndPrintStream("conditional Alignment", Suppliers.conditional(
                new RandomBooleanSupplier(60, new Random(100)).asPrimitiveBooleanSupplier(),
                Suppliers.constantOfNotNull(Alignment.START),
                Suppliers.constantOfNotNull(Alignment.END)));
    }

    private static void showRandomListSelection() {
        System.out.println("-showRandomListSelection---");

        generateAndPrintStream("String1", Suppliers.randomListSelection(RANDOM, List.of("Aaa")));
        generateAndPrintStream("String 3", Suppliers.randomListSelection(RANDOM, List.of("Aaa", "Bbb", "Ccc")));
        generateAndPrintStream("Boolean 1", Suppliers.randomListSelection(RANDOM, List.of(Boolean.TRUE)));
        generateAndPrintStream("Alignment 2", Suppliers.randomListSelection(RANDOM, List.of(Alignment.START, Alignment.END)));
        generateAndPrintStream("Alignment 3", Suppliers.randomListSelection(RANDOM, Arrays.asList(Alignment.values())));
        generateAndPrintStream("Integer 3", Suppliers.randomListSelection(RANDOM, List.of(42, 23, 1024)));
        generateAndPrintStream("Long 3", Suppliers.randomListSelection(RANDOM, List.of(42L, 23L, 1024L)));
        generateAndPrintStream("Double 3", Suppliers.randomListSelection(RANDOM, List.of(42.0d, 23.0d, 1024.0d)));
        generateAndPrintStream("Float 3", Suppliers.randomListSelection(RANDOM, List.of(42.0f, 23.0f, 1024.0f)));
        generateAndPrintStream("BigInteger 3", Suppliers.randomListSelection(RANDOM, List.of(BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L))));
        generateAndPrintStream("BigDecimal 3", Suppliers.randomListSelection(RANDOM, List.of(BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d))));

        generateAndPrintStream("splitTextByCharacterBreaks", Suppliers.randomListSelection(RANDOM, TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList()));
    }

    private static void showRandomSelection() {
        System.out.println("-showRandomSelection---");

        generateAndPrintStream("String 1", Suppliers.randomSelection(RANDOM, "Aaa"));
        generateAndPrintStream("String 3", Suppliers.randomSelection(RANDOM, "Aaa", "Bbb", "Ccc"));
        generateAndPrintStream("Boolean 1", Suppliers.randomSelection(RANDOM, Boolean.TRUE));
        generateAndPrintStream("Alignment 2", Suppliers.randomSelection(RANDOM, Alignment.START, Alignment.END));
        generateAndPrintStream("Alignment 3", Suppliers.randomSelection(RANDOM, Alignment.values()));
        generateAndPrintStream("Integer 3", Suppliers.randomSelection(RANDOM, 42, 23, 1024));
        generateAndPrintStream("Long 3", Suppliers.randomSelection(RANDOM, 42L, 23L, 1024L));
        generateAndPrintStream("Double 3", Suppliers.randomSelection(RANDOM, 42.0d, 23.0d, 1024.0d));
        generateAndPrintStream("Float 3", Suppliers.randomSelection(RANDOM, 42.0f, 23.0f, 1024.0f));
        generateAndPrintStream("BigInteger 3", Suppliers.randomSelection(RANDOM, BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L)));
        generateAndPrintStream("BigDecimal 3", Suppliers.randomSelection(RANDOM, BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d)));
    }

    private static void showIntSupplierListSelection() {
        System.out.println("-showIntSupplierListSelection---");

        IntSupplier intSupplier = RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 0, 3);

        generateAndPrintStream("String 1", Suppliers.intSupplierListSelection(intSupplier, List.of("Aaa")));
        generateAndPrintStream("String 3", Suppliers.intSupplierListSelection(intSupplier, List.of("Aaa", "Bbb", "Ccc")));
        generateAndPrintStream("Boolean 1", Suppliers.intSupplierListSelection(intSupplier, List.of(Boolean.TRUE)));
        generateAndPrintStream("Alignment 2", Suppliers.intSupplierListSelection(intSupplier, List.of(Alignment.START, Alignment.END)));
        generateAndPrintStream("Alignment 3", Suppliers.intSupplierListSelection(intSupplier, Arrays.asList(Alignment.values())));
        generateAndPrintStream("Integer 3", Suppliers.intSupplierListSelection(intSupplier, List.of(42, 23, 1024)));

        generateAndPrintStream("splitTextByCharacterBreaks", Suppliers.intSupplierListSelection(intSupplier, TextSplitters.breakByCharacter(ExamplesStrings.SPECIAL_CHARACTERS, Locale.US).toList()));
    }

    private static void showIntSupplierSelection() {
        System.out.println("-showIntSupplierSelection---");

        IntSupplier intSupplier = RandomNumberSuppliers.randomPrimitiveInt(RANDOM, 0, 3);

        generateAndPrintStream("String 1", Suppliers.intSupplierSelection(intSupplier, "Aaa"));
        generateAndPrintStream("String 3", Suppliers.intSupplierSelection(intSupplier, "Aaa", "Bbb", "Ccc"));
        generateAndPrintStream("Boolean 1", Suppliers.intSupplierSelection(intSupplier, Boolean.TRUE));
        generateAndPrintStream("Alignment 2", Suppliers.intSupplierSelection(intSupplier, Alignment.START, Alignment.END));
        generateAndPrintStream("Alignment 3", Suppliers.intSupplierSelection(intSupplier, Alignment.values()));
        generateAndPrintStream("Integer 3", Suppliers.intSupplierSelection(intSupplier, 42, 23, 1024));
    }

    private static void showRandomUUID() {
        System.out.println("-showRandomUUID---");

        generateAndPrintStream("randomUUID", Suppliers.randomUUID());
    }

    public static void main(String... args) {
        showSequenceSupplier();
        showRepeatingPatternSupplier();
        showSwitchingSupplier();
        showConstant();
        showCombine();
        showMapTo();
        showConditional();
        showRandomListSelection();
        showRandomSelection();
        showIntSupplierListSelection();
        showIntSupplierSelection();
        showRandomUUID();
    }

}
