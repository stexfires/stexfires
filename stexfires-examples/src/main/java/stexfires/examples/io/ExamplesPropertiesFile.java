package stexfires.examples.io;

import org.jspecify.annotations.Nullable;
import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.properties.PropertiesFileSpec;
import stexfires.io.properties.PropertiesUtil;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.MapConsumer;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.*;

import static stexfires.util.CommonCharsetNames.*;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "HardcodedLineSeparator", "UnnecessaryUnicodeEscape"})
public final class ExamplesPropertiesFile {

    private ExamplesPropertiesFile() {
    }

    private static Stream<KeyValueRecord> generateStream() {
        return Stream.of(
                new KeyValueFieldsRecord("CategoryA", 0L, "Key1", "Value1"),
                new KeyValueFieldsRecord("CategoryA", 1L, "Key2", "Value2"),
                new KeyValueFieldsRecord("CategoryB", 2L, "Key_ValueWithSpaces", " Value Value\tValue "),
                new KeyValueFieldsRecord("CategoryB", 3L, "Key_ValueWithLineBreak", "ValueLine0\nValueLine1\r\nValueLine2"),
                new KeyValueFieldsRecord("CategoryC", 4L, "Key_ValueEmpty", ""),
                new KeyValueFieldsRecord("CategoryC", 5L, "Key_ValueNull", null),
                new KeyValueFieldsRecord("CategoryD€", 6L, "Key_ValueWithSpecialCharacter", "# ! = :"),
                new KeyValueFieldsRecord("CategoryD€", 7L, "Key_ValueWithNotAscii", "äÄß€\uD83D\uDE00\u00e7c\u0327"),
                new KeyValueFieldsRecord("CategoryB", 8L, "Key With Spaces", "Value")
        );
    }

    private static void appendInBothDirections(SingleValueFileSpec consumerValueFileSpec, Path path, String name, String specialChars) throws ConsumerException, IOException {
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(name + " = " + specialChars), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(specialChars + " = " + name), path, StandardOpenOption.APPEND);
    }

    private static boolean isUsableCodePoint(CodePoint cp) {
        return Stream.of(
                             CodePoint.Type.LOWERCASE_LETTER,
                             CodePoint.Type.MODIFIER_LETTER,
                             CodePoint.Type.OTHER_LETTER,
                             CodePoint.Type.TITLECASE_LETTER,
                             CodePoint.Type.UPPERCASE_LETTER,

                             CodePoint.Type.DECIMAL_DIGIT_NUMBER,
                             CodePoint.Type.LETTER_NUMBER,
                             CodePoint.Type.OTHER_NUMBER,

                             CodePoint.Type.CURRENCY_SYMBOL,
                             CodePoint.Type.MATH_SYMBOL,
                             CodePoint.Type.MODIFIER_SYMBOL,
                             CodePoint.Type.OTHER_SYMBOL,
                             CodePoint.Type.FORMAT,

                             CodePoint.Type.CONNECTOR_PUNCTUATION,
                             CodePoint.Type.DASH_PUNCTUATION,
                             CodePoint.Type.END_PUNCTUATION,
                             CodePoint.Type.FINAL_QUOTE_PUNCTUATION,
                             CodePoint.Type.INITIAL_QUOTE_PUNCTUATION,
                             CodePoint.Type.OTHER_PUNCTUATION,
                             CodePoint.Type.START_PUNCTUATION
                     )
                     .anyMatch(type -> cp.type() == type);
    }

    private static void writePreparedPropertiesFile(Path path, SingleValueFileSpec consumerValueFileSpec, boolean unicode) throws ConsumerException, IOException {
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("a=b"), path);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" ! x = x"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" # y = y"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" c  =  d  "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" e  =    "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" \t "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("   = f   "), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("a=b2"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("g1:g1"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("g2=g2"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("g3 g3"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("g4\tg4"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("i1\\:i1=i1"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("i2\\=i2=i2"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("i3\\ i3=i3"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("i4\\\ti4=i4"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord(" fruits                           apple, banana, pear, \\"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("                                  cantaloupe, watermelon, \\"), path, StandardOpenOption.APPEND);
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("                                  kiwi, mango"), path, StandardOpenOption.APPEND);

        if (unicode) {
            String s0 = "unescaped_\u00E4\u00C4\u00DF\u20AC\uD83D\uDE00\u00E7c\u0327";
            appendInBothDirections(consumerValueFileSpec, path, "s0", s0);
        }
        String s1 = "escaped_\\u00E4\\u00C4\\u00DF\\u20AC\\uD83D\\uDE00\\u00E7c\\u0327";
        appendInBothDirections(consumerValueFileSpec, path, "s1", s1);
        String s2 = "\\b\\z";
        appendInBothDirections(consumerValueFileSpec, path, "s2", s2);

        StringBuilder s3 = new StringBuilder();
        for (int c = 65; c < 256; c++) {
            s3.appendCodePoint(c);
        }
        appendInBothDirections(consumerValueFileSpec, path, "s3", s3.toString());

    }

    private static void writePreparedPropertiesFileLong(Path path, SingleValueFileSpec consumerValueFileSpec) throws ConsumerException, IOException {
        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("#################"), path);

        StringBuilder bmpCodePoints = new StringBuilder();
        for (int c = 65; c <= CodePoint.MAX_BMP_CODE_POINT; c++) {
            CodePoint cp = new CodePoint(c);
            if (isUsableCodePoint(cp)) {
                bmpCodePoints.appendCodePoint(c);
            }
        }
        appendInBothDirections(consumerValueFileSpec, path, "bmpCodePoints", bmpCodePoints.toString());

        StringBuilder supCodePoints = new StringBuilder();
        for (int c = CodePoint.MIN_SUPPLEMENTARY_CODE_POINT; c <= CodePoint.MAX_SUPPLEMENTARY_CODE_POINT; c++) {
            CodePoint cp = new CodePoint(c);
            if (isUsableCodePoint(cp)) {
                supCodePoints.appendCodePoint(c);
            }
        }
        appendInBothDirections(consumerValueFileSpec, path, "supCodePoints", supCodePoints.toString());

        RecordFiles.writeRecordIntoFile(consumerValueFileSpec, new ValueFieldRecord("#################"), path, StandardOpenOption.APPEND);
    }

    private static void compare(Properties properties, Map<String, @Nullable String> map) {
        if (PropertiesUtil.propertiesAndMapEquals(properties, map)) {
            System.out.println("---------------------------------");
            System.out.println("Properties and Map are equal!");
        } else {
            NavigableMap<String, @Nullable String> propMap = PropertiesUtil.convertPropertiesToNavigableMap(properties, String::compareTo);
            NavigableMap<String, @Nullable String> propMap2 = PropertiesUtil.convertPropertiesToNavigableMap(properties, String::compareTo);
            NavigableMap<String, @Nullable String> map2 = new TreeMap<>(map);

            System.out.println("---------------------------------");
            System.out.println("Properties and Map are NOT equal!");
            System.out.println(" Unique entries at Properties:");
            map2.forEach(propMap2::remove);
            propMap2.forEach((key, value) -> System.out.println("  " + key + " = " + value));
            System.out.println(" Unique entries at Map:");
            propMap.forEach(map2::remove);
            map2.forEach((key, value) -> System.out.println("  " + key + " = " + value));
        }
    }

    private static Map<String, @Nullable String> readIntoMapAndLog(PropertiesFileSpec fileSpec, Path path) throws ProducerException, IOException {
        MapConsumer<KeyValueRecord, NavigableMap<String, @Nullable String>> consumer = PropertiesUtil.navigableMapConsumer(String::compareTo);
        RecordFiles.readAndConsumeFile(fileSpec, RecordConsumer.concat(RecordSystemOutUtil.RECORD_CONSUMER, consumer), path);
        return consumer.getMap();
    }

    private static Properties readPropertiesAndLog(Path path, Charset charset) throws IOException {
        Properties properties = PropertiesUtil.loadFromFile(path, charset);
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));
        return properties;
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1-----------------------------------------------------------------------------------");

        var fileSpec = new PropertiesFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                PropertiesFileSpec.DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                PropertiesFileSpec.DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                lineSeparator,
                PropertiesFileSpec.DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                PropertiesFileSpec.DEFAULT_CONSUMER_ESCAPE_UNICODE,
                PropertiesFileSpec.DEFAULT_CONSUMER_DATE_COMMENT,
                PropertiesFileSpec.DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX,
                PropertiesFileSpec.DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER
        );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(properties, map);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2-----------------------------------------------------------------------------------");

        var fileSpec = new PropertiesFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                "<null>",
                true,
                lineSeparator,
                PropertiesFileSpec.DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                true,
                true,
                PropertiesFileSpec.DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX,
                PropertiesFileSpec.DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER
        );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(properties, map);
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test3-----------------------------------------------------------------------------------");

        var fileSpec = new PropertiesFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                PropertiesFileSpec.DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                PropertiesFileSpec.DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                lineSeparator,
                PropertiesFileSpec.DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                PropertiesFileSpec.DEFAULT_CONSUMER_ESCAPE_UNICODE,
                PropertiesFileSpec.DEFAULT_CONSUMER_DATE_COMMENT,
                true,
                PropertiesFileSpec.DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER
        );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(properties, map);
    }

    private static void test4(Path path, LineSeparator lineSeparator) throws ProducerException, IOException {
        System.out.println("-test4-----------------------------------------------------------------------------------");

        var fileSpec = PropertiesFileSpec.producerFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                "",
                true
        );

        Properties propertiesWrite = PropertiesUtil.consumeIntoProperties(generateStream());

        // Write
        System.out.println("write with Properties: " + path);
        PropertiesUtil.storeToFile(propertiesWrite, path, StandardCharsets.UTF_8, "Comment from Properties.store");

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(propertiesWrite, map);
        compare(properties, map);
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test5(Path path, LineSeparator lineSeparator) throws ProducerException, IOException, ConsumerException {
        System.out.println("-test5-----------------------------------------------------------------------------------");

        var fileSpec = new PropertiesFileSpec(
                CharsetCoding.reportingErrors(ISO_8859_1),
                PropertiesFileSpec.DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                PropertiesFileSpec.DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                lineSeparator,
                PropertiesFileSpec.DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                true,
                true,
                true,
                PropertiesFileSpec.DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER
        );

        // Write
        System.out.println("write ISO_8859_1: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read
        System.out.println("read ISO_8859_1: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties ISO_8859_1: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.ISO_8859_1);

        System.out.println("read with Properties InputStream: " + path);
        Properties properties2 = new Properties();
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            properties2.load(inputStream);
        }
        properties2.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        compare(properties, map);
        compare(properties2, map);
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test6(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test6-----------------------------------------------------------------------------------");

        var consumerSingleValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(ISO_8859_1),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        var fileSpec = PropertiesFileSpec.producerFileSpec(
                CharsetCoding.reportingErrors(StandardCharsets.ISO_8859_1),
                "",
                false
        );

        // Write
        System.out.println("write SingleValueFileSpec ISO_8859_1: " + path);
        writePreparedPropertiesFile(path, consumerSingleValueFileSpec, false);

        // Read
        System.out.println("read ISO_8859_1: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties InputStream: " + path);
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            properties.load(inputStream);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        compare(properties, map);
    }

    private static void test7(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test7-----------------------------------------------------------------------------------");

        var consumerSingleValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(UTF_8),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        var fileSpec = PropertiesFileSpec.producerFileSpec(
                CharsetCoding.reportingErrors(StandardCharsets.UTF_8),
                "",
                false
        );

        // Write
        System.out.println("write SingleValueFileSpec: " + path);
        writePreparedPropertiesFile(path, consumerSingleValueFileSpec, true);

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(properties, map);
    }

    private static void test8(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test8-----------------------------------------------------------------------------------");

        var consumerSingleValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(UTF_8),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        var fileSpec = PropertiesFileSpec.producerFileSpec(
                CharsetCoding.reportingErrors(StandardCharsets.UTF_8),
                "",
                false
        );

        // Write
        System.out.println("write SingleValueFileSpec: " + path);
        writePreparedPropertiesFileLong(path, consumerSingleValueFileSpec);

        // Read
        System.out.println("read: " + path);
        var map = readIntoMapAndLog(fileSpec, path);

        // Read with Properties
        System.out.println("read with Properties: " + path);
        Properties properties = readPropertiesAndLog(path, StandardCharsets.UTF_8);

        // Compare
        compare(properties, map);
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
            test1(Path.of(args[0], "PropertiesFile_1.properties"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "PropertiesFile_2.properties"), LineSeparator.systemLineSeparator());
            test3(Path.of(args[0], "PropertiesFile_3.properties"), LineSeparator.systemLineSeparator());
            test4(Path.of(args[0], "PropertiesFile_4.properties"), LineSeparator.systemLineSeparator());
            test5(Path.of(args[0], "PropertiesFile_5.properties"), LineSeparator.systemLineSeparator());
            test6(Path.of(args[0], "PropertiesFile_6.properties"), LineSeparator.systemLineSeparator());
            test7(Path.of(args[0], "PropertiesFile_7.properties"), LineSeparator.systemLineSeparator());
            test8(Path.of(args[0], "PropertiesFile_8.properties"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
