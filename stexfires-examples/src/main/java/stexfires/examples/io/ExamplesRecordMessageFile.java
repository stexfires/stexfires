package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.message.RecordMessageFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.message.ExtendedTextsMessage;
import stexfires.record.message.ShortMessage;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesRecordMessageFile {

    private ExamplesRecordMessageFile() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new KeyValueFieldsRecord("key1", "value1"),
                new KeyValueFieldsRecord("key2", ""),
                new KeyValueFieldsRecord("key3", null),

                TextRecords.empty(),

                new TwoFieldsRecord("first1", "second1"),
                new TwoFieldsRecord("first2", "second2"),

                new ValueFieldRecord("value1"),
                new ValueFieldRecord(""),
                new ValueFieldRecord(null),

                new ManyFieldsRecord("text0", "text1", "text2", "text3")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpec =
                RecordMessageFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        "Header",
                        "Footer",
                        new ExtendedTextsMessage<>(", ", "", "[", "]"),
                        true
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        var fileSpec =
                RecordMessageFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        new ShortMessage<>()
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);
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
            test1(Path.of(args[0], "RecordMessageFile_1.txt"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "RecordMessageFile_2.txt"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
