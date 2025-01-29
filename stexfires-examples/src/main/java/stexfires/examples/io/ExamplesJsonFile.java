package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.json.*;
import stexfires.io.json.JsonFileSpec.RecordJsonType;
import stexfires.io.producer.ProducerReadLineHandling;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.supplier.SequenceSupplier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static stexfires.io.json.JsonFieldSpec.NullHandling.*;
import static stexfires.io.json.JsonFieldSpec.ValidityCheck.CHECK_VALUE;
import static stexfires.io.json.JsonFieldSpec.ValueType.*;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "HardcodedLineSeparator"})
public final class ExamplesJsonFile {

    private ExamplesJsonFile() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ManyFieldsRecord("category1000", 1_000L,
                        "abc", "abc", "\"abc\"", "0", "true", null, "   1,2,3", "[1, 2, 3]", "   \"a\": 1, \"b\": 2", "{ \"a\": 1, \"b\": 2 }"),
                new EmptyRecord(),
                new ManyFieldsRecord("category1002", 1_002L,
                        "A ä € \t \r\n A", "a \\n A", "\"a \\t A\"", "1234.5678", "false", null, null, null, null, null),
                new ManyFieldsRecord("category1003", 1_003L,
                        " ", "", "\"\"", null, null, "", "true", "[]", null, null)
        );
    }

    private static void testJsonArrayFileSpec1(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonArrayFileSpec1---");

        var fileSpecWrite =
                JsonArrayFileSpec.consumerFileSpecAsObjectWithSingleMember(
                        RecordJsonType.OBJECT,
                        true,
                        JsonUtil.escapeJsonString("Array\tname"),
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.numberType("number", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_FIELD, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);
    }

    private static void testJsonArrayFileSpec2(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonArrayFileSpec2---");

        var fileSpecWrite =
                JsonArrayFileSpec.consumerFileSpecAsSingleArray(
                        RecordJsonType.ARRAY,
                        false,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.numberType("number", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_FIELD, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);
    }

    private static void testJsonMembersFileSpec1(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonMembersFileSpec1---");

        LongSupplier sequenceSupplier = SequenceSupplier.asPrimitiveLong(0L);

        var fileSpecWrite =
                JsonMembersFileSpec.consumerFileSpec(
                        RecordJsonType.OBJECT,
                        true,
                        true,
                        record -> "record_" + sequenceSupplier.getAsLong(),
                        false,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.numberType("number", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_FIELD, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);
    }

    private static void testJsonMembersFileSpec2(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonMembersFileSpec2---");

        LongSupplier sequenceSupplier = SequenceSupplier.asPrimitiveLong(0L);

        var fileSpecWrite =
                JsonMembersFileSpec.consumerFileSpec(
                        RecordJsonType.ARRAY,
                        false,
                        false,
                        record -> "record_" + sequenceSupplier.getAsLong(),
                        false,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.numberType("number", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_FIELD, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);
    }

    private static void testJsonStreamingFileSpec1(Path path) throws ProducerException, ConsumerException, IOException {
        System.out.println("-testJsonStreamingFileSpec1---");

        var fileSpecWrite =
                JsonStreamingFileSpec.consumerFileSpec(
                        JsonFileSpec.DEFAULT_RECORD_JSON_TYPE,
                        false,
                        true,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.numberType("number", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_USE_LITERAL, CHECK_VALUE)
                        )
                );

        var fileSpecRead =
                JsonStreamingFileSpec.producerFileSpec(
                        JsonFileSpec.DEFAULT_RECORD_JSON_TYPE,
                        false,
                        ProducerReadLineHandling.SKIP_BLANK_LINE,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.numberType("number", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_USE_LITERAL, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_USE_LITERAL, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_USE_LITERAL, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpecRead, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void testJsonStreamingFileSpec2(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonStreamingFileSpec2---");

        var fileSpecWrite =
                JsonStreamingFileSpec.consumerFileSpec(
                        RecordJsonType.ARRAY,
                        true,
                        false,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_FIELD),
                                JsonFieldSpec.numberType("number", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean", ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayElements", ARRAY_ELEMENTS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("array", ARRAY, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("objectMembers", OBJECT_MEMBERS, ALLOWED_OMIT_FIELD, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_FIELD, CHECK_VALUE)
                        )
                );

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpecWrite, generateStream(), path);
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
            testJsonArrayFileSpec1(Path.of(args[0], "JsonArray1.json"));
            testJsonArrayFileSpec2(Path.of(args[0], "JsonArray2.json"));
            testJsonMembersFileSpec1(Path.of(args[0], "JsonMembers1.json"));
            testJsonMembersFileSpec2(Path.of(args[0], "JsonMembers2.json"));
            testJsonStreamingFileSpec1(Path.of(args[0], "JsonStreaming1.ndjson"));
            testJsonStreamingFileSpec2(Path.of(args[0], "JsonStreaming2.json"));
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
