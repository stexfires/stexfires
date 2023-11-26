package stexfires.examples.data;

import org.jetbrains.annotations.NotNull;
import stexfires.data.DataType;
import stexfires.data.DataTypes;
import stexfires.data.NumberDataTypeFormatter;
import stexfires.data.TimeDataTypeFormatter;
import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.generator.CategoryGenerator;
import stexfires.record.generator.GeneratorInterimResult;
import stexfires.record.generator.GeneratorProducer;
import stexfires.record.generator.RecordGenerator;
import stexfires.record.generator.RecordIdGenerator;
import stexfires.util.function.DateTimeSuppliers;
import stexfires.util.function.RandomBooleanSupplier;
import stexfires.util.function.RandomNumberSuppliers;
import stexfires.util.function.RandomStringSuppliers;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesDataTypeGenerator {

    private ExamplesDataTypeGenerator() {
    }

    private static <T extends TextRecord> void produceAndPrint(@NotNull RecordGenerator<T> generator) {
        TextRecordStreams.produce(GeneratorProducer.knownSize(generator, 10))
                         .forEach(RecordSystemOutUtil::printlnRecord);
    }

    public static void main(String... args) {
        // uuid
        DataType<String> uuidDataType = DataTypes.stringDataType(
                RandomStringSuppliers.uuid());

        // Integer
        DataType<Integer> integerDataTypeA = DataTypes.integerDataType(
                0,
                Locale.GERMANY,
                RandomNumberSuppliers.randomInteger(new Random(), 0, 100));
        DataType<Integer> integerDataTypeB = integerDataTypeA.withSupplier(
                RandomNumberSuppliers.randomInteger(new Random(), 1000, 10000));

        // Boolean
        DataType<Boolean> booleanDataType = DataTypes.booleanDataType(
                false,
                "TRUE",
                "FALSE",
                new RandomBooleanSupplier(20, new Random()));

        // LocalTime
        DataType<LocalTime> localTimeDataType = DataType.of(
                LocalTime.class,
                LocalTime.MIN,
                new TimeDataTypeFormatter<>(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMANY), null),
                DateTimeSuppliers.localTimeOfSecondOfDay(DateTimeSuppliers.randomSecondOfDayInclusive(new Random(), LocalTime.MIN, LocalTime.MAX)));

        {
            System.out.println("--- textRecordOfSuppliers");

            Function<GeneratorInterimResult<TextRecord>, Stream<Supplier<String>>> textFunction =
                    interimResult -> interimResult.context().recordIndex() % 3 == 0 ?
                            Stream.of(uuidDataType.newTextSupplier(), integerDataTypeA.newTextSupplier(), booleanDataType.newTextSupplier(), localTimeDataType.newTextSupplier())
                            : Stream.of(uuidDataType.newTextSupplier(), integerDataTypeB.newTextSupplier(), booleanDataType.newTextSupplier(), localTimeDataType.newTextSupplier());

            System.out.println("--- textRecordOfSupplierStreamFunction function");
            produceAndPrint(RecordGenerator.textRecordOfSupplierStreamFunction(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    textFunction));

            System.out.println("--- textRecordOfListFunction");
            Supplier<String> uuidNotFirstSupplier = uuidDataType.newTextSupplier();
            produceAndPrint(RecordGenerator.textRecordOfListFunction(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    (interimResult) -> {
                        List<String> texts = new ArrayList<>();
                        texts.add(String.valueOf(interimResult.context()
                                                              .recordIndex()));
                        if (!interimResult.context().first()) {
                            texts.add(uuidNotFirstSupplier.get());
                        } else {
                            texts.add(null);
                        }
                        return texts;
                    }));

            System.out.println("--- textRecordOfSuppliers 1 supplier");
            produceAndPrint(RecordGenerator.textRecordOfSuppliers(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    uuidDataType.newTextSupplier()));

            System.out.println("--- textRecordOfSuppliers 4 suppliers");
            produceAndPrint(RecordGenerator.textRecordOfSuppliers(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    uuidDataType.newTextSupplier(),
                    integerDataTypeA.newTextSupplier(),
                    booleanDataType.newTextSupplier(),
                    localTimeDataType.newTextSupplier()));
        }

        System.out.println("--- textRecordOfFunctions");
        {
            var textFunction0 = uuidDataType.newTextGeneratorFunction();
            var textFunction1 = booleanDataType.newTextGeneratorFunction();
            Function<GeneratorInterimResult<TextRecord>, String> textFunction2 =
                    (interimResult) -> interimResult.parsedTextAt(1, booleanDataType.parserAsFunction()).orElse(Boolean.FALSE) ? "yes" : "no";
            Function<GeneratorInterimResult<TextRecord>, String> textFunction3 =
                    (interimResult) -> String.valueOf(interimResult.context().recordIndex());
            var textFunction4 = integerDataTypeA.newTextGeneratorFunction();
            DataType<Long> longDataType = DataType.of(
                    Long.class,
                    0L,
                    new NumberDataTypeFormatter<>(NumberFormat.getIntegerInstance(Locale.GERMANY), () -> "NULL"));
            var textFunction5 = longDataType.newTextGeneratorFunction(
                    (interimResult) -> interimResult.context().recordIndex() * 100_000_000_000L
                            + interimResult.parsedTextAt(4, integerDataTypeA.parserAsFunction()).orElse(0));

            System.out.println("--- textRecordOfFunctions 0 functions");
            produceAndPrint(RecordGenerator.textRecordOfFunctions(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    List.of()));

            System.out.println("--- textRecordOfFunctions 1 functions");
            produceAndPrint(RecordGenerator.textRecordOfFunctions(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    List.of(textFunction0)));

            System.out.println("--- textRecordOfFunctions 2 functions");
            produceAndPrint(RecordGenerator.textRecordOfFunctions(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    List.of(textFunction0, textFunction1)));

            System.out.println("--- textRecordOfFunctions 5 functions");
            produceAndPrint(RecordGenerator.textRecordOfFunctions(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    List.of(textFunction0, textFunction1, textFunction2, textFunction3, textFunction4, textFunction5)));
        }

        System.out.println("--- valueRecord");
        {
            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction0 = booleanDataType.newTextGeneratorFunction();

            produceAndPrint(RecordGenerator.valueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    valueFunction0));

            Function<GeneratorInterimResult<ValueRecord>, String> valueFunction1 =
                    booleanDataType.newTextGeneratorFunction(interimResult -> interimResult.context().recordIndex() % 2 == 0);

            produceAndPrint(RecordGenerator.valueRecord(
                    CategoryGenerator.constantNull(),
                    RecordIdGenerator.recordIndex(),
                    valueFunction1));
        }
    }

}
