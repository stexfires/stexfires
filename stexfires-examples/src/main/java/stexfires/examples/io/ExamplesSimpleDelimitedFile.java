package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "SpellCheckingInspection"})
public final class ExamplesSimpleDelimitedFile {

    private ExamplesSimpleDelimitedFile() {
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ManyFieldsRecord(null, 0L, "a", "0", "AAAA", "ä"),
                new ManyFieldsRecord(null, 1L, "b", "1", "BB", "€"),
                new ManyFieldsRecord(null, 2L, "c", "2", "\uD83D\uDE00o\u0308", ""),
                new ManyFieldsRecord(null, 3L, "d", "3.33", "dDDDDd", null),
                new ManyFieldsRecord(null, 4L, "e", "004", "eEEEEEEEEEEEEEEEEEe", "    "),
                new ManyFieldsRecord(null, 5L, "f", null, "    ", "fff"),
                new ManyFieldsRecord(null, 6L, null, null, null, null),
                new ManyFieldsRecord(null, 7L, "", "", "", ""),
                new ManyFieldsRecord(null, 8L, "  ", "  ", "  ", "  "),
                new ManyFieldsRecord(null, 9L, "j", "9", "k", "l", "m", "n", "o"),
                TextRecords.empty(),
                new ManyFieldsRecord(null, 11L),
                new ManyFieldsRecord(null, 12L, "---"),
                new ManyFieldsRecord(null, 13L, "z", "z", "z", "z")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpec =
                new SimpleDelimitedFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SimpleDelimitedFileSpec.FIELD_DELIMITER_COMMA,
                        0,
                        SimpleDelimitedFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        1, 1,
                        false,
                        lineSeparator,
                        "Column0,Column1,Column2,Column3",
                        "Column0,Column1,Column2,Column3",
                        SimpleDelimitedFileSpec.newFieldSpecs(4)
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var singleValueFileWriteSpec =
                SingleValueFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        lineSeparator,
                        "!!!Example for skip!!!",
                        SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        false);

        // Write
        System.out.println("write (prepare read with SingleValueFile): " + path);
        Stream<ValueRecord> oneValueRecordStream = Stream.of(
                new ValueFieldRecord("Header"),
                new ValueFieldRecord("------"),
                new ValueFieldRecord("A1"),
                new ValueFieldRecord(""),
                new ValueFieldRecord(null),
                new ValueFieldRecord(" "),
                new ValueFieldRecord("B1,B2"),
                new ValueFieldRecord(","),
                new ValueFieldRecord(" , "),
                new ValueFieldRecord("C1,C2,C3,"),
                new ValueFieldRecord(",,D3"),
                new ValueFieldRecord(",,,"),
                new ValueFieldRecord(" , , ,"),
                new ValueFieldRecord("Footer")
        );
        RecordFiles.writeStreamIntoFile(singleValueFileWriteSpec, oneValueRecordStream, path);

        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        var simpleDelimitedFileSpec =
                SimpleDelimitedFileSpec.producerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SimpleDelimitedFileSpec.FIELD_DELIMITER_COMMA,
                        1,
                        ProducerReadLineHandling.SKIP_BLANK_LINE,
                        2, 1,
                        true,
                        fieldSpecs
                );

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(simpleDelimitedFileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test3---");

        var fileSpec =
                new SimpleDelimitedFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SimpleDelimitedFileSpec.FIELD_DELIMITER_CHARACTER_TABULATION,
                        0,
                        ProducerReadLineHandling.THROW_EXCEPTION_ON_EMPTY_LINE,
                        0, 0,
                        true,
                        lineSeparator,
                        SimpleDelimitedFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE,
                        SimpleDelimitedFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        SimpleDelimitedFileSpec.newFieldSpecs(7)
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            test1(Path.of(args[0], "SimpleDelimitedFile_1.csv"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "SimpleDelimitedFile_2.csv"), LineSeparator.systemLineSeparator());
            test3(Path.of(args[0], "SimpleDelimitedFile_3.csv"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
