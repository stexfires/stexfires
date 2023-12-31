package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.RecordIOStreams;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.io.singlevalue.SingleValueConsumer;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.io.singlevalue.SingleValueProducer;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.ISO_8859_1;
import static stexfires.util.CommonCharsetNames.US_ASCII;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesSingleValueFile {

    private ExamplesSingleValueFile() {
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private static Stream<ValueRecord> generateStream() {
        return Stream.of(
                new ValueFieldRecord("Start---"),

                new ValueFieldRecord("A"),
                new ValueFieldRecord(" \t "),
                new ValueFieldRecord("C"),
                new ValueFieldRecord("    "),
                new ValueFieldRecord("E"),
                new ValueFieldRecord(" "),
                new ValueFieldRecord("G"),
                new ValueFieldRecord(""),
                new ValueFieldRecord("I"),
                new ValueFieldRecord(null),
                new ValueFieldRecord("K"),
                new ValueFieldRecord("//value1"),
                new ValueFieldRecord("value2 value2 value2"),
                new ValueFieldRecord(" value3 "),
                new ValueFieldRecord("\t"),
                new ValueFieldRecord("\tvalue4\t\tvalue4\t"),

                new ValueFieldRecord(LineSeparator.LF.string()),
                new ValueFieldRecord(LineSeparator.CR.string()),
                new ValueFieldRecord(LineSeparator.CR_LF.string()),
                new ValueFieldRecord(LineSeparator.LF.string() + "value11"),
                new ValueFieldRecord("value12.1" + LineSeparator.LF.string() + "value12.2" + LineSeparator.CR.string() + "value12.3\fvalue12.4"),

                new ValueFieldRecord("\\ =:#! \u000B \u0004 \u0015"),
                new ValueFieldRecord("\""),
                new ValueFieldRecord("<entry>"),
                new ValueFieldRecord("\u20AC \u0178"),
                new ValueFieldRecord("\u00A6 \u00BC \u00B4 \u00B8"),
                new ValueFieldRecord("\u007E \u007F \u0080 \u009F \u00A0"),
                new ValueFieldRecord("\uD83D\uDE00, o\u0308, A\u030a"),
                new ValueFieldRecord("äÄáß@{[²µ^°1234567890ß\"§$%&/()?`+*'-_.,;<>|~"),
                new KeyValueCommentFieldsRecord("Category", 0L, "Key", "Value", "Comment"),
                new ValueFieldRecord("End---")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.reportingErrors(US_ASCII),
                        " - ",
                        SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                        SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        0, 0,
                        false,
                        false,
                        lineSeparator,
                        null, null,
                        false
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeRecordIntoFile(fileSpec, new ValueFieldRecord("0. Test"), path);
        RecordFiles.writeRecordIntoFile(fileSpec, new ValueFieldRecord(""), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(fileSpec, new ValueFieldRecord(null), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(fileSpec, new ValueFieldRecord("    "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(fileSpec, new ValueFieldRecord("  4. Test  "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(fileSpec, new KeyValueFieldsRecord("Key", "5. Test"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(fileSpec, new KeyValueCommentFieldsRecord("Key", "6. Test", "Comment"), path, StandardOpenOption.APPEND);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var fileSpecWrite =
                SingleValueFileSpec.consumerFileSpec(
                        CharsetCoding.replacingErrors(ISO_8859_1, "?", "?"),
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        lineSeparator,
                        "ISO_8859_1",
                        SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        true);

        var fileSpecRead =
                SingleValueFileSpec.producerFileSpec(
                        CharsetCoding.replacingErrors(US_ASCII, "?", "?"),
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        1,
                        SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        2, 3,
                        SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                        false
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpecRead, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test3---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                        SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        1, 1,
                        SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                        false,
                        lineSeparator,
                        "Header---", "Footer---",
                        true
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test4(Path path, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test4---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                        ProducerReadLineHandling.SKIP_EMPTY_LINE,
                        0, 0,
                        SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                        false,
                        lineSeparator,
                        SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE, SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        false
                );

        // Write
        System.out.println("write: " + path);
        try (var singleValueConsumer = fileSpec.openFileAsConsumer(path)) {
            RecordIOStreams.writeStream(singleValueConsumer, generateStream());
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (var singleValueProducer = fileSpec.openFileAsProducer(path)) {
            RecordIOStreams.readAndConsume(singleValueProducer, RecordSystemOutUtil.RECORD_CONSUMER);
        }
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test5(Path path, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test5---");

        var fileSpec = new SingleValueFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                0, 0,
                SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                false,
                lineSeparator,
                SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE, SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                false
        );

        // Write
        System.out.println("write: " + path);
        try (SingleValueConsumer singleValueConsumer = fileSpec.consumer(new FileOutputStream(path.toFile()))) {
            RecordIOStreams.writeStream(singleValueConsumer, Stream.empty());
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (SingleValueProducer singleValueProducer = fileSpec.producer(new FileInputStream(path.toFile()))) {
            RecordIOStreams.readAndConsume(singleValueProducer, RecordSystemOutUtil.RECORD_CONSUMER);
        }
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
            test1(Path.of(args[0], "SingleValueFile_1.txt"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "SingleValueFile_2.txt"), LineSeparator.systemLineSeparator());
            test3(Path.of(args[0], "SingleValueFile_3.txt"), LineSeparator.systemLineSeparator());
            test4(Path.of(args[0], "SingleValueFile_4.txt"), LineSeparator.systemLineSeparator());
            test5(Path.of(args[0], "SingleValueFile_5.txt"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
