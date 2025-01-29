package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.container.*;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.path.DosPathFieldsRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.*;
import stexfires.record.producer.KeyValueRecordProducer;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "CallToPrintStackTrace", "MagicNumber"})
public final class ExamplesContainer {

    private ExamplesContainer() {
    }

    private static void showPackAndUnpack() throws IOException {
        System.out.println("-showPackAndUnpack---");

        KeyValueFieldsRecord keyValueFieldsRecord = new KeyValueFieldsRecord("cat", 1L, "key", "value");
        KeyValueCommentFieldsRecord keyValueCommentFieldsRecord = new KeyValueCommentFieldsRecord(null, null, "key", null, "comment");
        ManyFieldsRecord manyFieldsRecord = new ManyFieldsRecord("category", 1L, "value0", "value1", null, "value3", "", "value4");
        TwoFieldsRecord twoFieldsRecord = new TwoFieldsRecord(null, 1_000L, "firstField", "secondField");
        ValueFieldRecord valueFieldRecord = new ValueFieldRecord(null, null, "value0");
        DosPathFieldsRecord dosPathFieldsRecord = DosPathFieldsRecord.newDosPathFieldsRecord(Paths.get("C:\\"));

        showPackAndUnpack(TextRecords.empty());
        showPackAndUnpack(keyValueCommentFieldsRecord);
        showPackAndUnpack(keyValueFieldsRecord);
        showPackAndUnpack(manyFieldsRecord);
        showPackAndUnpack(twoFieldsRecord);
        showPackAndUnpack(valueFieldRecord);
        showPackAndUnpack(dosPathFieldsRecord);
    }

    private static void showPackAndUnpack(TextRecord originalRecord) {
        RecordContainer recordContainerSmall = new RecordContainerSmall();
        RecordContainer recordContainerMedium = new RecordContainerMedium();
        RecordContainer recordContainerLarge = new RecordContainerLarge();

        TextRecord packedSmall = recordContainerSmall.pack(originalRecord);
        TextRecord packedMedium = recordContainerMedium.pack(originalRecord);
        TextRecord packedLarge = recordContainerLarge.pack(originalRecord);

        UnpackResult unpackedSmall = recordContainerSmall.unpack(packedSmall);
        UnpackResult unpackedMedium = recordContainerMedium.unpack(packedMedium);
        UnpackResult unpackedLarge = recordContainerLarge.unpack(packedLarge);

        System.out.println("-pack");
        System.out.print("r ");
        RecordSystemOutUtil.printlnRecord(originalRecord);

        System.out.print("s ");
        RecordSystemOutUtil.printlnRecord(packedSmall);
        System.out.print("m ");
        RecordSystemOutUtil.printlnRecord(packedMedium);
        System.out.print("l ");
        RecordSystemOutUtil.printlnRecord(packedLarge);

        System.out.println("-unpack");
        System.out.print("r ");
        RecordSystemOutUtil.printlnRecord(originalRecord);
        System.out.print("s ");
        RecordSystemOutUtil.printlnOptionalRecord(unpackedSmall.record());
        System.out.print("m ");
        RecordSystemOutUtil.printlnOptionalRecord(unpackedMedium.record());
        System.out.print("l ");
        RecordSystemOutUtil.printlnOptionalRecord(unpackedLarge.record());

        System.out.print("s ");
        System.out.println(unpackedSmall.errorMessage().orElse(""));
        System.out.print("m ");
        System.out.println(unpackedMedium.errorMessage().orElse(""));
        System.out.print("l ");
        System.out.println(unpackedLarge.errorMessage().orElse(""));

        System.out.println("unpackAsStream s " + recordContainerSmall.unpackAsStream(packedSmall).toList());
        System.out.println("unpackAsStream m " + recordContainerMedium.unpackAsStream(packedMedium).toList());
        System.out.println("unpackAsStream l " + recordContainerLarge.unpackAsStream(packedLarge).toList());
        System.out.println();
    }

    private static void showErrorMessages() {
        System.out.println("-showErrorMessages---");

        RecordContainer recordContainerMedium = new RecordContainerMedium();
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, "", "", "", "")).errorMessage().orElse(""));
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, null, "", "", "")).errorMessage().orElse(""));
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, "Unknown", "", "")).errorMessage().orElse(""));
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, "stexfires.record.impl.ValueFieldRecord", "", "a", "")).errorMessage().orElse(""));
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, "stexfires.record.impl.ValueFieldRecord", "", "")).errorMessage().orElse(""));
        System.out.println(recordContainerMedium.unpack(new ManyFieldsRecord("category", 1L, "stexfires.record.impl.ValueFieldRecord")).errorMessage().orElse(""));
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private static void showRecordOfRecords() {
        System.out.println("-showRecordOfRecords---");

        KeyValueRecordProducer producer = new KeyValueRecordProducer(Map.of(
                "key0", "value0",
                "key1", "value1",
                "key2", "value2"));

        // 5 FieldSpecs = 3 Fields RecordContainerMedium and 2 Fields KeyValueRecord
        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());

        SimpleDelimitedFileSpec fileSpec = new SimpleDelimitedFileSpec(CharsetCoding.UTF_8_REPORTING, ";",
                0,
                SimpleDelimitedFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                0, 0, false,
                LineSeparator.LF, null, null,
                fieldSpecs
        );

        RecordContainer container = new RecordContainerMedium();

        // pack
        System.out.println(container.packStream(producer.produceStream()).toList());

        TextRecord recordOfRecords = container.packRecordOfRecords(fileSpec, producer.produceStream());
        RecordSystemOutUtil.printlnRecord(recordOfRecords);

        System.out.println(recordOfRecords.streamOfTexts().toList());
        System.out.println(producer.produceStream().toList());

        // unpack
        Stream<TextRecord> unpackedRecordStream = container.unpackRecordOfRecords(fileSpec, recordOfRecords);

        System.out.println(unpackedRecordStream.toList());
    }

    public static void main(String... args) {
        try {
            showPackAndUnpack();
            showErrorMessages();
            showRecordOfRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
