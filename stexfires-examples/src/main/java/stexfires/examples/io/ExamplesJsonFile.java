package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.json.JsonArrayFileSpec;
import stexfires.io.json.JsonFieldSpec;
import stexfires.io.json.JsonMembersFileSpec;
import stexfires.io.json.JsonStreamingFileSpec;
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
import java.util.List;
import java.util.function.LongSupplier;
import java.util.stream.Stream;

import static stexfires.io.json.JsonUtil.NullHandling.ALLOWED_OMIT_MEMBER;
import static stexfires.io.json.JsonUtil.NullHandling.ALLOWED_USE_LITERAL;
import static stexfires.io.json.JsonUtil.StringEscape.ESCAPE_NOT_NECESSARY;
import static stexfires.io.json.JsonUtil.ValidityCheck.CHECK_VALUE;

import static stexfires.io.json.JsonUtil.ValueType.ARRAY_WITHOUT_BRACKETS;
import static stexfires.io.json.JsonUtil.ValueType.ARRAY_WITH_BRACKETS;
import static stexfires.io.json.JsonUtil.ValueType.OBJECT;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesJsonFile {

    private ExamplesJsonFile() {
    }

    @SuppressWarnings("HardcodedLineSeparator")
    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                //  new ManyFieldsRecord("string3", "3", "TRUE", null),
                //  new ManyFieldsRecord("string3", "a", "true", null),
                new ManyFieldsRecord("category", 0L,
                        "abc", "abc", "\"abc\"", "0", "true", null, " 1,2,3 ", "[1, 2, 3]", "{ \"a\": 1, \"b\": 2 }"),
                new EmptyRecord(),
                new ManyFieldsRecord("category", 2L,
                        "A ä € \t \r\n A", "a \\n A", "\"a \\t A\"", "1234.5678", "false", null, null, null, null)
        );
    }

    private static void testJsonArrayFileSpec1(Path path) throws ConsumerException, IOException {
        System.out.println("-testJsonArrayFileSpec1---");

        var fileSpecWrite =
                JsonArrayFileSpec.consumerFileSpec(
                        true,
                        true,
                        "TextRecords",
                        ESCAPE_NOT_NECESSARY,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.numberType("number ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayWithout", ARRAY_WITHOUT_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("arrayWith", ARRAY_WITH_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_MEMBER, CHECK_VALUE)
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
                        true,
                        true,
                        record -> "record_" + sequenceSupplier.getAsLong(),
                        ESCAPE_NOT_NECESSARY,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.numberType("number ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayWithout", ARRAY_WITHOUT_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("arrayWith", ARRAY_WITH_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_MEMBER, CHECK_VALUE)
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
                        false,
                        true,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.numberType("number ä €", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayWithout", ARRAY_WITHOUT_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("arrayWith", ARRAY_WITH_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_USE_LITERAL, CHECK_VALUE)
                        )
                );

        var fileSpecRead =
                JsonStreamingFileSpec.producerFileSpec(
                        false,
                        ProducerReadLineHandling.SKIP_BLANK_LINE,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_USE_LITERAL),
                                JsonFieldSpec.numberType("number ä €", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", ALLOWED_USE_LITERAL, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null ä €", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayWithout", ARRAY_WITHOUT_BRACKETS, ALLOWED_USE_LITERAL, CHECK_VALUE),
                                new JsonFieldSpec("arrayWith", ARRAY_WITH_BRACKETS, ALLOWED_USE_LITERAL, CHECK_VALUE),
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
                        true,
                        true,
                        List.of(
                                JsonFieldSpec.stringUnescapedType("stringUnescaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedType("stringEscaped", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.stringEscapedWithQuotationMarksType("stringEscapedWithQuotationMarks", ALLOWED_OMIT_MEMBER),
                                JsonFieldSpec.numberType("number ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringUnescapedType("null", ALLOWED_USE_LITERAL),
                                new JsonFieldSpec("arrayWithout", ARRAY_WITHOUT_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("arrayWith", ARRAY_WITH_BRACKETS, ALLOWED_OMIT_MEMBER, CHECK_VALUE),
                                new JsonFieldSpec("object", OBJECT, ALLOWED_OMIT_MEMBER, CHECK_VALUE)
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
            testJsonMembersFileSpec1(Path.of(args[0], "JsonMembers1.json"));
            testJsonStreamingFileSpec1(Path.of(args[0], "JsonStreaming1.ndjson"));
            testJsonStreamingFileSpec2(Path.of(args[0], "JsonStreaming2.json"));
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
