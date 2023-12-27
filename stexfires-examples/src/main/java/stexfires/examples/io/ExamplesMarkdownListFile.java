package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFileSpec;
import stexfires.io.RecordFiles;
import stexfires.io.markdown.list.MarkdownListFileSpec;
import stexfires.io.markdown.list.MarkdownListMarker;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesMarkdownListFile {

    private ExamplesMarkdownListFile() {
    }

    private static void splitAndLog(String line) {
        System.out.println("-splitAndLog---");
        System.out.println(line);
        System.out.println(MarkdownListMarker.split(line));
    }

    private static void testSplit() {
        System.out.println("-testSplit---");

        splitAndLog("*");
        splitAndLog("* ");
        splitAndLog("*  ");
        splitAndLog("*\t");
        splitAndLog("*\t ");

        splitAndLog("*a");
        splitAndLog("* a");
        splitAndLog("*  a");
        splitAndLog("*\ta");
        splitAndLog("*\t a");

        splitAndLog(" * b");
        splitAndLog("  * b");
        splitAndLog("  *         b           ");
        splitAndLog(" \t     * b");
        splitAndLog("\t* b");

        splitAndLog("1*");
        splitAndLog("1* ");
        splitAndLog("1* c");

        splitAndLog("1.");
        splitAndLog("1. ");
        splitAndLog("1. d");

        splitAndLog(" 2.");
        splitAndLog(" 2. ");
        splitAndLog(" 2. e");

        splitAndLog("0) Error");
        splitAndLog("-1. Error");
        splitAndLog("a) Error");

        splitAndLog(" 0) Error");
        splitAndLog(" -1. Error");
        splitAndLog(" a) Error");
    }

    private static Stream<ValueRecord> generateStream(LineSeparator lineSeparator) {
        Objects.requireNonNull(lineSeparator);
        return TextRecordStreams.of(
                new ValueFieldRecord("a"),
                new ValueFieldRecord(""),
                new ValueFieldRecord(" c "),
                new ValueFieldRecord(null),
                new ValueFieldRecord("e"),
                new ValueFieldRecord(" "),
                new ValueFieldRecord("g" + lineSeparator.string()),
                new ValueFieldRecord("h")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpecWrite =
                MarkdownListFileSpec.consumerFileSpec(
                        RecordFileSpec.DEFAULT_CHARSET_CODING,
                        lineSeparator,
                        MarkdownListMarker.ORDERED_PERIOD);
        var fileSpecRead =
                MarkdownListFileSpec.producerFileSpec(
                        RecordFileSpec.DEFAULT_CHARSET_CODING);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(lineSeparator), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpecRead, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var fileSpecWrite =
                MarkdownListFileSpec.consumerFileSpec(
                        RecordFileSpec.DEFAULT_CHARSET_CODING,
                        lineSeparator,
                        MarkdownListMarker.BULLET_ASTERISK,
                        "Example" + lineSeparator.string() + "* Before" + lineSeparator.repeat(2),
                        "    + After",
                        true);
        var fileSpecRead =
                MarkdownListFileSpec.producerFileSpec(
                        RecordFileSpec.DEFAULT_CHARSET_CODING,
                        1,
                        MarkdownListFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        1, 0,
                        true,
                        true,
                        true);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(lineSeparator), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpecRead, RecordSystemOutUtil.RECORD_CONSUMER, path);
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
            testSplit();
            test1(Path.of(args[0], "MarkdownListFile_1.md"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "MarkdownListFile_2.md"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
