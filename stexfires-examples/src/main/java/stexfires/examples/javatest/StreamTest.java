package stexfires.examples.javatest;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.stream.*;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class StreamTest {

    private StreamTest() {
    }

    private static Stream<String> generateStringStream() {
        return Stream.of("This ", "stream ", "is ", "a ", "test ", "stream ", ".");
    }

    private static IntStream generateIntStream() {
        return IntStream.of(42, 23, 7, 42, 999);
    }

    private static void showIntStreamRange() {
        System.out.println("-showIntStreamRange---");
        System.out.println("range[10,15[");
        IntStream.range(10, 15).forEachOrdered(System.out::println);
        System.out.println("rangeClosed[10,15]");
        IntStream.rangeClosed(10, 15).forEachOrdered(System.out::println);
    }

    private static void showStreamIterate() {
        System.out.println("-showStreamIterate---");
        System.out.println("n+1");
        Stream.iterate(1, n -> n + 1).limit(3L).forEachOrdered(System.out::println);
        System.out.println("n*2");
        Stream.iterate(2, n -> n * 2).limit(3L).forEachOrdered(System.out::println);
        System.out.println("concat '*'");
        Stream.iterate("*", s -> s + "*").limit(3L).forEachOrdered(System.out::println);
        System.out.println("switch Boolean");
        Stream.iterate(Boolean.FALSE, b -> !b).limit(3L).forEachOrdered(System.out::println);
        System.out.println("IntStream with predicate");
        IntStream.iterate(10, i -> i < 20, i -> i + 1).forEachOrdered(System.out::println);
    }

    private static void showStreamGenerate() {
        System.out.println("-showStreamGenerate---");
        System.out.println("constant");
        Stream.generate(() -> "*").limit(3L).forEachOrdered(System.out::println);
        System.out.println("IntStream constant");
        IntStream.generate(() -> 1).limit(3L).forEachOrdered(System.out::println);
        System.out.println("random");
        Stream.generate(Math::random).limit(3L).forEachOrdered(System.out::println);
        System.out.println("Atomic increment");
        Stream.generate(new AtomicInteger()::getAndIncrement).limit(3L).forEachOrdered(System.out::println);
    }

    private static void showPeekAndParallel() {
        System.out.println("-showPeekAndParallel---");
        System.out.println("peek:");
        System.out.println(" -> " + generateStringStream().peek(System.out::print).toList());
        System.out.println();
        System.out.println("parallel peek:");
        System.out.println(" -> " + generateStringStream().parallel().peek(System.out::print).toList());
        System.out.println();
        System.out.println("many peeks:");
        Stream.of("AA", "B", "AA", "CC")
              .peek(p -> System.out.println("1. " + p))
              .distinct()
              .peek(p -> System.out.println("2. " + p))
              .filter(v -> v.length() > 1)
              .peek(p -> System.out.println("3. " + p))
              .forEachOrdered(v -> System.out.println("End: " + v));
    }

    private static void showCount() {
        System.out.println("-showCount---");
        System.out.println("Count stream elements: " + generateStringStream().count());
    }

    private static void showFind() {
        System.out.println("-showFind---");
        System.out.println("findFirst: " + generateStringStream().findFirst());
        System.out.println("parallel findFirst: " + generateStringStream().parallel().findFirst());
        System.out.println("findAny: " + generateStringStream().findAny());
        System.out.println("parallel findAny: " + generateStringStream().parallel().findAny());
    }

    private static void showBuilder() {
        System.out.println("-showBuilder---");
        Stream.builder().add("This ").add("is ").add("a ").add("test ").add("stream.").build().forEachOrdered(System.out::println);
        System.out.println();
        IntStream.builder().add(42).add(23).build().forEachOrdered(System.out::println);
        System.out.println();
    }

    private static void showJoining() {
        System.out.println("-showJoining---");
        System.out.println(generateStringStream().collect(Collectors.joining()));
        System.out.println(generateStringStream().collect(Collectors.joining("-")));
    }

    private static void showReduce() {
        System.out.println("-showReduce---");
        System.out.println(new SplittableRandom().ints(10L, 1, 10)
                                                 .reduce(Integer::sum).orElse(0));
        System.out.println(generateIntStream().reduce(Integer::max).orElse(0));
    }

    private static void showSplittableRandom() {
        System.out.println("-showSplittableRandom---");

        System.out.println("-- ints");
        new SplittableRandom().ints(5L, 1, 4)
                              .forEachOrdered(System.out::println);
        System.out.println("-- longs");
        new SplittableRandom().longs(5L, Long.MAX_VALUE / 2L, Long.MAX_VALUE)
                              .forEachOrdered(System.out::println);
        System.out.println("-- doubles");
        new SplittableRandom().doubles(5L, 0.0d, 1.0d)
                              .forEachOrdered(System.out::println);
        System.out.println("-- ints boxed");
        System.out.println(new SplittableRandom().ints(5L, 1, 4).boxed()
                                                 .toList());
    }

    private static void showConcat() {
        System.out.println("-showConcat---");
        Stream.concat(generateStringStream(), generateStringStream())
              .forEachOrdered(System.out::println);
        IntStream.concat(generateIntStream(), generateIntStream())
                 .forEachOrdered(System.out::println);
    }

    private static void showModification() {
        System.out.println("-showModification---");
        String strA = "a";
        String strB = "b";
        String strC = "c";
        String[] array = new String[]{strA, strB};

        List<String> list1 = new ArrayList<>(1);
        list1.add(strA);

        // create streams
        Stream<String> listStream = list1.stream();
        Stream<String> arrayStream1 = Arrays.stream(array);
        Stream<String> arrayStream2 = Stream.of(array);

        // modify streams
        list1.add(strC);
        array[1] = strC;

        // print streams
        System.out.print("Modified list stream:    ");
        listStream.forEachOrdered(System.out::print);
        System.out.println();
        System.out.print("Modified array stream 1: ");
        arrayStream1.forEachOrdered(System.out::print);
        System.out.println();
        System.out.print("Modified array stream 2: ");
        arrayStream2.forEachOrdered(System.out::print);
        System.out.println();
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static void showCharacteristics() {
        System.out.println("-showCharacteristics---");
        String strA = "a";
        String strB = "b";

        // List
        List<String> list1 = new ArrayList<>(1);
        list1.add(strA);
        Stream<String> streamList1 = list1.stream();
        Stream<String> streamList2 = List.copyOf(list1).stream();
        Stream<String> streamList3 = List.of("a", "b", "c").stream();

        // Single
        Stream<String> streamSingle1 = Stream.of(strA);

        // Array
        String[] array = new String[]{strA, strB};
        Stream<String> streamArray1 = Arrays.stream(array);
        Stream<String> streamArray2 = Stream.of(strA, strB);
        Stream<String> streamArray3 = Stream.of(array);
        Stream<String> streamArray4 = Stream.of();

        // Empty
        Stream<String> streamEmpty1 = Stream.empty();

        // Spliterator
        Spliterator<String> spList1 = streamList1.spliterator();
        Spliterator<String> spList2 = streamList2.spliterator();
        Spliterator<String> spList3 = streamList3.spliterator();

        Spliterator<String> spSingle1 = streamSingle1.spliterator();

        Spliterator<String> spArray1 = streamArray1.spliterator();
        Spliterator<String> spArray2 = streamArray2.spliterator();
        Spliterator<String> spArray3 = streamArray3.spliterator();
        Spliterator<String> spArray4 = streamArray4.spliterator();

        Spliterator<String> spEmpty1 = streamEmpty1.spliterator();

        Map<String, Spliterator<String>> spliterators = new TreeMap<>();
        spliterators.put("spList1  ", spList1);
        spliterators.put("spList2  ", spList2);
        spliterators.put("spList3  ", spList3);
        spliterators.put("spSingle1", spSingle1);
        spliterators.put("spArray1 ", spArray1);
        spliterators.put("spArray2 ", spArray2);
        spliterators.put("spArray3 ", spArray3);
        spliterators.put("spArray4 ", spArray4);
        spliterators.put("spEmpty1 ", spEmpty1);

        System.out.println("Spliterator Overview");
        System.out.println("spList1   " + spList1.characteristics() + " " + spList1.estimateSize() + " " + spList1.getExactSizeIfKnown() + " " + spList1);
        System.out.println("spList2   " + spList2.characteristics() + " " + spList2.estimateSize() + " " + spList2.getExactSizeIfKnown() + " " + spList2);
        System.out.println("spList3   " + spList3.characteristics() + " " + spList3.estimateSize() + " " + spList3.getExactSizeIfKnown() + " " + spList3);
        System.out.println("spSingle1 " + spSingle1.characteristics() + " " + spSingle1.estimateSize() + " " + spSingle1.getExactSizeIfKnown() + " " + spSingle1);
        System.out.println("spArray1  " + spArray1.characteristics() + " " + spArray1.estimateSize() + " " + spArray1.getExactSizeIfKnown() + " " + spArray1);
        System.out.println("spArray2  " + spArray2.characteristics() + " " + spArray2.estimateSize() + " " + spArray2.getExactSizeIfKnown() + " " + spArray2);
        System.out.println("spArray3  " + spArray3.characteristics() + " " + spArray3.estimateSize() + " " + spArray3.getExactSizeIfKnown() + " " + spArray3);
        System.out.println("spArray4  " + spArray4.characteristics() + " " + spArray4.estimateSize() + " " + spArray4.getExactSizeIfKnown() + " " + spArray4);
        System.out.println("spEmpty1  " + spEmpty1.characteristics() + " " + spEmpty1.estimateSize() + " " + spEmpty1.getExactSizeIfKnown() + " " + spEmpty1);

        System.out.println();

        System.out.println("Spliterator Characteristics");
        System.out.println("CONCURRENT");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.CONCURRENT)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("DISTINCT");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.DISTINCT)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("IMMUTABLE");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.IMMUTABLE)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("NONNULL");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.NONNULL)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("ORDERED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.ORDERED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SIZED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SIZED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SORTED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SORTED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);

        System.out.println("SUBSIZED");
        spliterators.entrySet().stream().filter(e -> e.getValue().hasCharacteristics(Spliterator.SUBSIZED)).map(Map.Entry::getKey).forEachOrdered(System.out::println);
    }

    private static void showDistinct() {
        System.out.println("-showDistinct---");
        generateStringStream().distinct().forEachOrdered(System.out::println);
        generateIntStream().distinct().forEachOrdered(System.out::println);
    }

    private static void showSkipAndLimit() {
        System.out.println("-showSkipAndLimit---");
        generateStringStream().skip(1L).limit(2L).forEachOrdered(System.out::println);
        generateIntStream().skip(1L).limit(2L).forEachOrdered(System.out::println);
    }

    private static void showMath() {
        System.out.println("-showMath---");
        System.out.println(generateIntStream().count());
        System.out.println(generateIntStream().sum());
        System.out.println(generateIntStream().min().orElse(0));
        System.out.println(generateIntStream().average().orElse(0));
        System.out.println(generateIntStream().max().orElse(0));
        System.out.println(generateIntStream().summaryStatistics());
    }

    @SuppressWarnings("UnnecessaryToStringCall")
    private static void showConvert() {
        System.out.println("-showConvert---");
        System.out.println(generateStringStream().toString());
        System.out.println(Arrays.toString(generateStringStream().toArray()));
        System.out.println(generateStringStream().toList());

        System.out.println(generateIntStream().toString());
        System.out.println(generateIntStream().boxed().toString());
        System.out.println(Arrays.toString(generateIntStream().toArray()));
        System.out.println(Arrays.toString(generateIntStream().asDoubleStream().toArray()));
        System.out.println(Arrays.toString(generateIntStream().asLongStream().toArray()));
    }

    private static void showMapMulti() {
        System.out.println("-showMapMulti---");

        Stream<TextRecord> recordStream = Stream.of(new ManyFieldsRecord("a", "b"), new ManyFieldsRecord("c", "d"), new ManyFieldsRecord("e", "f", null), new ManyFieldsRecord(), new ManyFieldsRecord("h"));
        System.out.println(
                recordStream.mapMulti((TextRecord record, Consumer<@Nullable String> consumer) -> {
                    // If the record contains multiple text field, the first and last texts are returned.
                    if (record.size() >= 2) {
                        consumer.accept(record.firstText());
                        consumer.accept(record.lastText());
                    }
                }).toList());
    }

    private static void showFlatMap() {
        System.out.println("-showFlatMap---");

        String[][] array = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f", "g"}, {}, {"h"}};

        System.out.println(
                Stream.of(array)
                      .flatMap(Stream::of)
                      .toList());

        Stream<TextRecord> recordStream = Stream.of(new ManyFieldsRecord("a", "b"), new ManyFieldsRecord("c", "d"), new ManyFieldsRecord("e", "f", "g"), new ManyFieldsRecord(), new ManyFieldsRecord("h"));
        System.out.println(
                recordStream.flatMap(TextRecord::streamOfTexts)
                            .toList());
    }

    public static void main(String... args) {
        showIntStreamRange();
        showStreamIterate();
        showStreamGenerate();
        showPeekAndParallel();
        showCount();
        showFind();
        showBuilder();
        showJoining();
        showReduce();
        showSplittableRandom();
        showConcat();
        showModification();
        showCharacteristics();
        showDistinct();
        showSkipAndLimit();
        showMath();
        showConvert();
        showMapMulti();
        showFlatMap();
    }

}
