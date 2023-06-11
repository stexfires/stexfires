package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordIOStreams;
import stexfires.io.combined.CombinedReadableRecordProducer;
import stexfires.io.combined.CombinedWritableRecordConsumer;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesCombinedFile {

    private ExamplesCombinedFile() {
    }

    private static Stream<KeyValueRecord> generateStream() {
        return Stream.of(
                new KeyValueFieldsRecord("SectionA", 1L, "key1", "value1a"),
                new KeyValueFieldsRecord("SectionB", 2L, "key2", "value2"),
                new KeyValueFieldsRecord("SectionA", 3L, "key3", "value3"),
                new KeyValueFieldsRecord(null, 4L, "", null),
                new KeyValueFieldsRecord("SectionA", 5L, "key1", "value1b"),
                new KeyValueFieldsRecord(null, 6L, "", ""),
                new KeyValueFieldsRecord("SectionC", 7L, "key7", "value7")
        );
    }

    private static void test1(Path pathConfig, Path pathSingleValue, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test1---");

        var configFileSpec =
                new ConfigFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        ConfigFileSpec.DEFAULT_VALUE_DELIMITER,
                        ConfigFileSpec.DEFAULT_COMMENT_LINE_PREFIX,
                        lineSeparator,
                        ConfigFileSpec.DEFAULT_CONSUMER_SEPARATE_CATEGORIES_BY_LINE,
                        ConfigFileSpec.DEFAULT_CONSUMER_SEPARATE_BY_WHITESPACE,
                        ConfigFileSpec.DEFAULT_CONSUMER_COMMENT_LINES_BEFORE
                );

        var singleValueFileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        SingleValueFileSpec.DEFAULT_PRODUCER_SKIP_FIRST_LINES,
                        SingleValueFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                        0, 0,
                        SingleValueFileSpec.DEFAULT_PRODUCER_TRIM_VALUE_TO_EMPTY,
                        true,
                        lineSeparator,
                        SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE, SingleValueFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        true
                );

        // Write
        System.out.println("write: " + pathConfig + " " + pathSingleValue);
        try (var configConsumer = configFileSpec.openFileAsConsumer(pathConfig);
             var singleValueConsumer = singleValueFileSpec.openFileAsConsumer(pathSingleValue);
             var combinedConsumer = new CombinedWritableRecordConsumer<>(configConsumer, singleValueConsumer)) {
            RecordIOStreams.writeStream(combinedConsumer,
                    new ConfigModifier<KeyValueRecord>(ConfigModifier.categoryTrimAndUppercase(Locale.ENGLISH),
                            true, true, 0, 1).modify(generateStream()));
        }

        // Read / log
        System.out.println("read/log: " + pathConfig + " " + pathSingleValue);
        try (var configProducer = configFileSpec.openFileAsProducer(pathConfig);
             var singleValueProducer = singleValueFileSpec.openFileAsProducer(pathSingleValue);
             var combinedProducer = new CombinedReadableRecordProducer<>(configProducer, singleValueProducer)) {
            RecordIOStreams.readAndConsume(combinedProducer, RecordSystemOutUtil.RECORD_CONSUMER);
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
            test1(Path.of(args[0], "Combined_ConfigFile_1.ini"),
                    Path.of(args[0], "Combined_SingleValueFile_1.txt"),
                    LineSeparator.systemLineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
