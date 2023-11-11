package stexfires.examples.record;

import org.jetbrains.annotations.NotNull;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.generator.CategoryGenerator;
import stexfires.record.generator.GeneratorContext;
import stexfires.record.generator.GeneratorInterimResult;
import stexfires.record.generator.GeneratorProducer;
import stexfires.record.generator.RecordGenerator;
import stexfires.record.generator.RecordIdGenerator;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.util.SortNulls;
import stexfires.util.function.RandomNumberSuppliers;
import stexfires.util.function.RandomStringSuppliers;
import stexfires.util.function.Suppliers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesGenerator {

    private ExamplesGenerator() {
    }

    private static void produceAndPrint(@NotNull GeneratorProducer<?> producer) {
        TextRecordStreams.produce(producer)
                         .forEachOrdered(RecordSystemOutUtil::printlnRecord);
    }

    private static void produceAndPrint(@NotNull GeneratorProducer<?> producer, long maxSize) {
        TextRecordStreams.produce(producer)
                         .limit(maxSize)
                         .forEachOrdered(RecordSystemOutUtil::printlnRecord);
    }

    @SuppressWarnings("DataFlowIssue")
    private static void showGeneratorProducer() {
        System.out.println("-showGeneratorProducer---");

        RecordGenerator<ValueRecord> generator0 = (context) ->
                new ValueFieldRecord("category0", context.recordIndex(), context.last() ? "last" : "not last");
        RecordGenerator<ValueRecord> generator1 = (context) ->
                new ValueFieldRecord("category1", context.recordIndex(), context.last() ? "last" : "not last");

        long firstSize = 3;
        long secondSize = 2;

        System.out.println("--- GeneratorProducer.knownSize (first call)");
        GeneratorProducer<ValueRecord> producer = GeneratorProducer.knownSize(generator0, firstSize);
        produceAndPrint(producer);
        System.out.println("--- GeneratorProducer.knownSize (second call)");
        produceAndPrint(producer);

        System.out.println("--- GeneratorProducer.knownSizeConcatenated");
        produceAndPrint(
                GeneratorProducer.knownSizeConcatenated(generator0, generator1, firstSize, secondSize));

        System.out.println("--- GeneratorProducer.unknownSize (BiPredicate -> true)");
        produceAndPrint(
                GeneratorProducer.unknownSize(generator0, (context, record) -> true));
        System.out.println("--- GeneratorProducer.unknownSize (BiPredicate -> recordIndex == 3)");
        produceAndPrint(
                GeneratorProducer.unknownSize(generator0, (context, record) -> context.recordIndex() == 3));
        System.out.println("--- GeneratorProducer.unknownSize (Predicate -> recordId == 3)");
        produceAndPrint(
                GeneratorProducer.unknownSize(generator0, (record) -> record.recordId() == 3));
        System.out.println("--- GeneratorProducer.unknownSize (without Predicate but with limit)");
        produceAndPrint(
                GeneratorProducer.unknownSize(generator0), firstSize);
    }

    @SuppressWarnings({"ReturnOfNull"})
    private static void showRecordGenerator() {
        System.out.println("-showRecordGenerator---");

        long size = 5;

        System.out.println("--- valueRecord");
        {
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    (interimResult) -> "Value_" + interimResult.context().recordIndex() % 2);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- keyValueRecord");
        {
            RecordGenerator<KeyValueRecord> generator = RecordGenerator.keyValueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    (interimResult) -> "Key_" + interimResult.context().recordIndex() % 2,
                    (interimResult) -> "Value for " + interimResult.textAt(RecordGenerator.KEY_VALUE_RECORD_INDEX_KEY).orElse(""));

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- keyValueRecord parsedTextAt");
        {
            RecordGenerator<KeyValueRecord> generator = RecordGenerator.keyValueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    (interimResult) -> String.valueOf(interimResult.context().recordIndex() * 10),
                    (interimResult) -> String.valueOf(interimResult.parsedTextAt(RecordGenerator.KEY_VALUE_RECORD_INDEX_KEY, Long::parseLong).orElseThrow() * 2));

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- keyValueCommentRecord");
        {
            RecordGenerator<KeyValueCommentRecord> generator = RecordGenerator.keyValueCommentRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    (interimResult) -> "Key_" + interimResult.context().recordIndex(),
                    (interimResult) -> "Value for " + interimResult.textAt(RecordGenerator.KEY_VALUE_RECORD_INDEX_KEY).orElse(""),
                    (interimResult) -> "Comment for " + interimResult.textAt(RecordGenerator.KEY_VALUE_COMMENT_RECORD_INDEX_KEY).orElse("") + " with value: " + interimResult.textAt(RecordGenerator.KEY_VALUE_COMMENT_RECORD_INDEX_VALUE).orElse(""));

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord constantNull");
        {
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    (interimResult) -> null);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord constant");
        {
            var categoryGenerator = CategoryGenerator.<ValueRecord>constant("category");
            var recordIdGenerator = RecordIdGenerator.<ValueRecord>constant(100L);
            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction = ir -> "value";
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(categoryGenerator, recordIdGenerator, valueFunction);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord");
        {
            CategoryGenerator<ValueRecord> categoryGenerator = (GeneratorContext<ValueRecord> context) -> context.previousRecord().map(r -> r.category() + "_cat").orElse("cat");
            RecordIdGenerator<ValueRecord> recordIdGenerator = (context) -> context.recordIndex() * 100_000L;
            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction = (interimResult) -> interimResult.context().recordIndex() % 2 == 0 ? "even" : "odd";
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(categoryGenerator, recordIdGenerator, valueFunction);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord time");
        {
            CategoryGenerator<ValueRecord> categoryGenerator = (context) -> ZonedDateTime.ofInstant(context.time(), ZoneId.systemDefault()).getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.GERMANY);
            RecordIdGenerator<ValueRecord> recordIdGenerator = (context) -> context.time().getEpochSecond();
            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction = (interimResult) -> interimResult.context().time().toString();
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(categoryGenerator, recordIdGenerator, valueFunction);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord random");
        {
            Supplier<String> randomCategorySupplier = Suppliers.randomSelection(new Random(0L), List.of("cat1", "cat2", "cat3", "cat4", "cat5"));
            CategoryGenerator<ValueRecord> categoryGenerator = (context) -> randomCategorySupplier.get();
            Supplier<Long> recordIdSupplier = RandomNumberSuppliers.randomLong(new Random(0L), 100L, 200L);
            RecordIdGenerator<ValueRecord> recordIdGenerator = (context) -> recordIdSupplier.get();
            Supplier<String> randomValueSupplier = RandomStringSuppliers.uuid();
            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction = (interimResult) -> randomValueSupplier.get();
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(categoryGenerator, recordIdGenerator, valueFunction);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- valueRecord previousAdjusted");
        {
            RecordGenerator<ValueRecord> generator = RecordGenerator.valueRecord(
                    CategoryGenerator.previousAdjusted(() -> "", s -> s + "-"),
                    RecordIdGenerator.previousAdjusted(() -> 1, id -> id * 2),
                    (interimResult) -> null);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- textRecordOfSuppliers");
        {
            Function<GeneratorInterimResult<TextRecord>, Stream<Supplier<String>>> textFunction =
                    interimResult -> interimResult.context().first() ?
                            Stream.of(() -> "A", () -> "B", () -> "000") :
                            Stream.of(() -> "aa", () -> "bb", () -> String.valueOf(interimResult.context().recordIndex()));

            RecordGenerator<TextRecord> generator = RecordGenerator.textRecordOfSuppliers(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    textFunction);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }

        System.out.println("--- textRecordOfFunctions");
        {
            List<Function<GeneratorInterimResult<TextRecord>, String>> textFunctions = new ArrayList<>(3);
            textFunctions.add(interimResult -> "A");
            textFunctions.add(interimResult -> "B" + interimResult.textAt(0).orElse(""));
            textFunctions.add(interimResult -> String.valueOf(interimResult.context().recordIndex()));

            RecordGenerator<TextRecord> generator = RecordGenerator.textRecordOfFunctions(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.constantNull(),
                    textFunctions);

            produceAndPrint(GeneratorProducer.knownSize(generator, size));
        }
    }

    private static void measureTime() {
        System.out.println("-measureTime---");

        RecordGenerator<ValueRecord> generator = (context) ->
                new ValueFieldRecord("category", context.recordIndex(), context.last() ? "last" : "not last");
        long maxRecordIndex = 100_000_000L;
        GeneratorProducer<ValueRecord> producer = GeneratorProducer.unknownSize(generator, (context, record) -> context.recordIndex() == maxRecordIndex);

        {
            long t1 = System.currentTimeMillis();
            System.out.println("count=" +
                    TextRecordStreams.produce(
                            producer).count());
            long t2 = System.currentTimeMillis();
            System.out.println("sequential duration (count): " + (t2 - t1) + " ms");
            System.out.println("count=" +
                    TextRecordStreams.produce(
                            producer).parallel().count());
            long t3 = System.currentTimeMillis();
            System.out.println("parallel duration (count): " + (t3 - t2) + " ms");
        }
        {
            long t1 = System.currentTimeMillis();
            System.out.println("max=" +
                    TextRecordStreams.produce(
                            producer).max(RecordComparators.recordId(SortNulls.FIRST)));
            long t2 = System.currentTimeMillis();
            System.out.println("sequential duration (max): " + (t2 - t1) + " ms");
            System.out.println("max=" +
                    TextRecordStreams.produce(
                            producer).parallel().max(RecordComparators.recordId(SortNulls.FIRST)));
            long t3 = System.currentTimeMillis();
            System.out.println("parallel duration (max): " + (t3 - t2) + " ms");
        }
    }

    public static void main(String... args) {
        showGeneratorProducer();
        showRecordGenerator();
        measureTime();
    }

}
