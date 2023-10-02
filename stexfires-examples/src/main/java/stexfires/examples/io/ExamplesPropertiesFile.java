package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.properties.PropertiesFileSpec;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.KeyRecord;
import stexfires.record.KeyValueRecord;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.MapConsumer;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.CodePoint;
import stexfires.util.CommonCharsetNames;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.ISO_8859_1;
import static stexfires.util.CommonCharsetNames.UTF_8;

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

    @SuppressWarnings("CollectionDeclaredAsConcreteClass")
    private static void compare(Properties properties, SortedMap<String, String> map) {

        SortedMap<String, String> propMap = new TreeMap<>();
        properties.forEach((key, value) -> propMap.put((String) key, (String) value));

        SortedMap<String, String> propMap2 = new TreeMap<>();
        properties.forEach((key, value) -> propMap2.put((String) key, (String) value));

        if (propMap.equals(map)) {
            System.out.println("---------------------------------");
            System.out.println("Properties and Map are equal!");
        } else {
            System.out.println("---------------------------------");
            System.out.println("Properties and Map are NOT equal!");
            System.out.println(" Unique entries at Properties:");
            map.forEach(propMap2::remove);
            propMap2.forEach((key, value) -> System.out.println("  " + key + " = " + value));
            System.out.println(" Unique entries at Map:");
            propMap.forEach(map::remove);
            map.forEach((key, value) -> System.out.println("  " + key + " = " + value));
        }
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1-----------------------------------------------------------------------------------");

        var fileSpec =
                new PropertiesFileSpec(
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

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(fileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2-----------------------------------------------------------------------------------");

        var fileSpec =
                new PropertiesFileSpec(
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

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(fileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, IOException {
        System.out.println("-test3-----------------------------------------------------------------------------------");

        Properties properties = new Properties();
        generateStream().filter(record -> record.valueField().isNotNull())
                        .forEachOrdered(record -> properties.setProperty(record.key(), record.value()));
        try (Writer writer = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) {
            properties.store(writer, "Comment from Properties.store");
        }

        var fileSpec = PropertiesFileSpec.producerFileSpec(CharsetCoding.UTF_8_REPORTING,
                "",
                true);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(fileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    private static void test4(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test4-----------------------------------------------------------------------------------");

        var fileSpec =
                new PropertiesFileSpec(
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

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(fileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test5(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test5-----------------------------------------------------------------------------------");

        var fileSpec =
                new PropertiesFileSpec(
                        CharsetCoding.reportingErrors(CommonCharsetNames.ISO_8859_1),
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
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.ISO_8859_1)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        System.out.println("read with Properties.load(InputStream): " + path);
        Properties properties2 = new Properties();
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            properties2.load(inputStream);
        }
        properties2.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(fileSpec, consumer, path);
        compare(properties, consumer.getMap());
        compare(properties2, consumer.getMap());
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test6(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test6-----------------------------------------------------------------------------------");

        SingleValueFileSpec consumerValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(ISO_8859_1),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        writePreparedPropertiesFile(path, consumerValueFileSpec, false);

        var producerFileSpec =
                PropertiesFileSpec.producerFileSpec(CharsetCoding.reportingErrors(StandardCharsets.ISO_8859_1),
                        "",
                        false);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            properties.load(inputStream);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(producerFileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    private static void test7(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test7-----------------------------------------------------------------------------------");

        SingleValueFileSpec consumerValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(UTF_8),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        writePreparedPropertiesFile(path, consumerValueFileSpec, true);

        var producerFileSpec =
                PropertiesFileSpec.producerFileSpec(CharsetCoding.reportingErrors(StandardCharsets.UTF_8),
                        "",
                        false);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(producerFileSpec, consumer, path);
        compare(properties, consumer.getMap());
    }

    private static void test8(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test8-----------------------------------------------------------------------------------");

        SingleValueFileSpec consumerValueFileSpec = SingleValueFileSpec.consumerFileSpec(
                CharsetCoding.reportingErrors(UTF_8),
                null,
                lineSeparator,
                null,
                null,
                true
        );
        writePreparedPropertiesFileLong(path, consumerValueFileSpec);

        var producerFileSpec =
                PropertiesFileSpec.producerFileSpec(CharsetCoding.reportingErrors(StandardCharsets.UTF_8),
                        "",
                        false);

        // Read with Properties
        System.out.println("read with Properties.load: " + path);
        Properties properties = new Properties();
        try (Reader reader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        properties.forEach((key, value) -> System.out.println("[" + key + "]=[" + value + "]"));

        // Compare
        MapConsumer<KeyValueRecord, TreeMap<String, String>> consumer = new MapConsumer<>(new TreeMap<>(), KeyRecord::key, ValueRecord::value);
        RecordFiles.readAndConsumeFile(producerFileSpec, consumer, path);
        compare(properties, consumer.getMap());
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
