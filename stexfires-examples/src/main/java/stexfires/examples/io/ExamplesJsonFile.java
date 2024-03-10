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

import static stexfires.io.json.JsonUtil.NullHandling.OMIT_JSON_MEMBER;
import static stexfires.io.json.JsonUtil.NullHandling.USE_NULL_LITERAL_FOR_VALUE;
import static stexfires.io.json.JsonUtil.StringEscape.ESCAPE_NOT_NECESSARY;
import static stexfires.io.json.JsonUtil.ValidityCheck.CHECK_VALUE;
import static stexfires.io.json.JsonUtil.ValueType.ARRAY;
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
                new ManyFieldsRecord("category", 0L, "abc", "0", "true", null, "[1, 2, 3]", "{ \"a\": 1, \"b\": 2 }"),
                new EmptyRecord(),
                new ManyFieldsRecord("category", 2L, "A ä € \t \r\n A", "1234.5678", "false", null, null, null)
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
                                JsonFieldSpec.stringType("string ä €", OMIT_JSON_MEMBER),
                                JsonFieldSpec.numberType("number ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringType("null ä €", USE_NULL_LITERAL_FOR_VALUE),
                                new JsonFieldSpec("array", ARRAY, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY),
                                new JsonFieldSpec("object", OBJECT, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY)
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
                                JsonFieldSpec.stringType("string ä €", OMIT_JSON_MEMBER),
                                JsonFieldSpec.numberType("number ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringType("null ä €", USE_NULL_LITERAL_FOR_VALUE),
                                new JsonFieldSpec("array", ARRAY, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY),
                                new JsonFieldSpec("object", OBJECT, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY)
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
                                JsonFieldSpec.stringType("string ä €", USE_NULL_LITERAL_FOR_VALUE),
                                JsonFieldSpec.numberType("number ä €", USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE),
                                JsonFieldSpec.stringType("null ä €", USE_NULL_LITERAL_FOR_VALUE),
                                new JsonFieldSpec("array", ARRAY, USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE, ESCAPE_NOT_NECESSARY),
                                new JsonFieldSpec("object", OBJECT, USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE, ESCAPE_NOT_NECESSARY)
                        )
                );

        var fileSpecRead =
                JsonStreamingFileSpec.producerFileSpec(
                        false,
                        ProducerReadLineHandling.SKIP_BLANK_LINE,
                        List.of(
                                JsonFieldSpec.stringType("string ä €", USE_NULL_LITERAL_FOR_VALUE),
                                JsonFieldSpec.numberType("number ä €", USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE),
                                JsonFieldSpec.stringType("null ä €", USE_NULL_LITERAL_FOR_VALUE),
                                new JsonFieldSpec("array", ARRAY, USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE, ESCAPE_NOT_NECESSARY),
                                new JsonFieldSpec("object", OBJECT, USE_NULL_LITERAL_FOR_VALUE, CHECK_VALUE, ESCAPE_NOT_NECESSARY)
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
                                JsonFieldSpec.stringType("string ä €", OMIT_JSON_MEMBER),
                                JsonFieldSpec.numberType("number ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.booleanType("boolean ä €", OMIT_JSON_MEMBER, CHECK_VALUE),
                                JsonFieldSpec.stringType("null", USE_NULL_LITERAL_FOR_VALUE),
                                new JsonFieldSpec("array", ARRAY, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY),
                                new JsonFieldSpec("object", OBJECT, OMIT_JSON_MEMBER, CHECK_VALUE, ESCAPE_NOT_NECESSARY)
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
