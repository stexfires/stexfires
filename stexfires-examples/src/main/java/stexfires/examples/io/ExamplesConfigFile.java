package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFileSpec;
import stexfires.io.RecordFiles;
import stexfires.io.config.ConfigFileSpec;
import stexfires.io.config.ConfigModifier;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.function.StringPredicates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.WINDOWS_1252;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "UnnecessaryUnicodeEscape", "StringConcatenationMissingWhitespace", "SpellCheckingInspection", "HardcodedLineSeparator"})
public final class ExamplesConfigFile {

    private ExamplesConfigFile() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ManyFieldsRecord("Section000", 0L, "", "value0"),
                new KeyValueFieldsRecord("", 1L, "property1", "value1"),
                new KeyValueFieldsRecord("    ", 2L, "property2", "value2"),
                new KeyValueFieldsRecord(null, 3L, "property3", "value3"),
                new KeyValueFieldsRecord(LineSeparator.LF.string(), 4L, "property4", "value4"),
                new KeyValueFieldsRecord("\t", 5L, "property5", "value5"),
                new KeyValueFieldsRecord(LineSeparator.CR_LF.string(), 6L, "property6", "value6"),
                new KeyValueFieldsRecord("", 7L, "property7", "ä ö ü ß µ ÷"),
                new ManyFieldsRecord("Section000", 8L, "field0", "field1", "field2", "field3", "field4"),
                new KeyValueFieldsRecord("A\nB", 9L, "property9", "value9"),

                TextRecords.empty(),

                new KeyValueFieldsRecord("section", 1000L, "property", "value1000"),

                new KeyValueFieldsRecord("Section700", 700L, "property100", "value700"),
                new KeyValueFieldsRecord("Section700", 701L, "property200", "value701"),
                new KeyValueFieldsRecord("Section700", 702L, "property300", "value702"),
                new KeyValueFieldsRecord("Section700", 703L, "property400", "value703"),
                new KeyValueFieldsRecord("Section700", 704L, "property500", "value704"),
                new KeyValueFieldsRecord("Section700", 705L, "property600", "value705"),

                new KeyValueFieldsRecord("section", 1001L, "property", "value1001"),

                new KeyValueFieldsRecord(" Section600 ", 600L, "property600", "value600"),
                new KeyValueFieldsRecord("   Section600   ", 601L, "property601", "value601"),
                new KeyValueFieldsRecord(" Section 600 ", 600L, "property600", "value600"),
                new KeyValueFieldsRecord("   Section 600   ", 601L, "property601", "value601"),

                new KeyValueFieldsRecord("section", 1002L, "property", "value1002"),

                new KeyValueCommentFieldsRecord("Section500", 500L, "property500", "value500", "comment500"),
                new KeyValueCommentFieldsRecord("section500", 501L, "property501", "value501", "comment501"),
                new KeyValueCommentFieldsRecord("Section500", 502L, "property502", "value502", "comment502"),
                new KeyValueCommentFieldsRecord("Section500", 503L, "property500", "value503", "comment503"),

                new KeyValueFieldsRecord("section", 1003L, "property", "value1003"),

                new TwoFieldsRecord("Section200", 200L, "", null),
                new TwoFieldsRecord("Section200", 201L, "A", "201"),
                new TwoFieldsRecord("Section200", 202L, "A", "202"),
                new TwoFieldsRecord("Section200", 203L, "A", "203"),
                new TwoFieldsRecord("Section200", 204L, "a", "204"),
                new TwoFieldsRecord("Section200", 205L, "A", null),
                new TwoFieldsRecord("Section200.A", 206L, "A", "206"),

                new KeyValueFieldsRecord("section", 1004L, "property", "value1004"),

                new KeyValueFieldsRecord("section800", 806L, "property0", "\u20AC \u0178"),
                new KeyValueFieldsRecord("SECTION800", 807L, "property1", "\u00A6 \u00BC \u00B4 \u00B8"),
                new KeyValueFieldsRecord("sECTION800", 808L, "property2", "\u007E \u007F \u0080 \u009F \u00A0"),
                new KeyValueFieldsRecord("\r\n\t\t Section800 \t\t\r\n", 809L, "property3", "\uD83D\uDE00, o\u0308, A\u030a"),
                new KeyValueFieldsRecord("Section800", 810L, "property4", "äÄáß@{[²µ^°1234567890ß\"§$%&/()?`+*'-_.,;<>|~"),

                new TwoFieldsRecord("section900.subsection.subsection", 900L, "property900", "value900"),
                new TwoFieldsRecord("section900.subsection", 901L, "property901", "value901"),
                new TwoFieldsRecord("section900", 902L, "property902", "value902"),

                new ValueFieldRecord("Section300", 300L, "property300"),
                new ValueFieldRecord("Section300", 301L, (String) null),

                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 400L, "property400", "value400"),
                new TwoFieldsRecord("Section400", 401L, "property400", "value401"),
                new TwoFieldsRecord("Section400", 401L, "property401", "value401"),

                new KeyValueFieldsRecord("Section100", 100L, "", null),
                new KeyValueFieldsRecord("Section100", 101L, "", ""),
                new KeyValueFieldsRecord("Section100", 102L, "", " "),
                new KeyValueFieldsRecord("Section100", 103L, "  ", null),
                new KeyValueFieldsRecord("Section100", 104L, "  ", ""),
                new KeyValueFieldsRecord("Section100", 105L, "  ", " ")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpec =
                new ConfigFileSpec(
                        CharsetCoding.replacingErrorsWithDefaults(WINDOWS_1252),
                        ConfigFileSpec.DEFAULT_VALUE_DELIMITER,
                        ConfigFileSpec.DEFAULT_COMMENT_LINE_PREFIX,
                        lineSeparator,
                        false,
                        false,
                        "My comment first line" + lineSeparator + "my comment second line"
                );

        // Write
        System.out.println("write: " + path);
        // generate, modify and log stream
        var recordStream = new ConfigModifier<>(ConfigModifier.categoryTrimAndUppercase(Locale.ENGLISH),
                false, true, 0, 1, 2)
                .modify(generateStream());
        RecordFiles.writeStreamIntoFile(fileSpec, recordStream, path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var fileSpec =
                new ConfigFileSpec(
                        RecordFileSpec.DEFAULT_CHARSET_CODING,
                        ConfigFileSpec.ALTERNATIVE_VALUE_DELIMITER_COLON,
                        ConfigFileSpec.ALTERNATIVE_COMMENT_LINE_PREFIX_NUMBER_SIGN,
                        lineSeparator,
                        true,
                        true,
                        "My comment first line" + lineSeparator + "my comment second line"
                );

        // Write
        System.out.println("write: " + path);
        // generate, modify and filter stream
        var recordStream = new ConfigModifier<>(ConfigModifier.categoryTrimAndLowercase(Locale.ENGLISH),
                true, true, 0, 1, 2)
                .modify(generateStream()
                        .filter(record -> StringPredicates.isNullOrBlank().negate().test(record.category()))
                );
        RecordFiles.writeStreamIntoFile(fileSpec, recordStream, path);

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
            test1(Path.of(args[0], "ConfigFile_1.ini"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "ConfigFile_2.ini"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
