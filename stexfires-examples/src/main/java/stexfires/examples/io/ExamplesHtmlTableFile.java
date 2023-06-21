package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.html.table.HtmlTableFieldSpec;
import stexfires.io.html.table.HtmlTableFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.mapper.AddTextMapper;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "SpellCheckingInspection"})
public final class ExamplesHtmlTableFile {

    private ExamplesHtmlTableFile() {
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        List<HtmlTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new HtmlTableFieldSpec("property & property"));
        fieldSpecs.add(new HtmlTableFieldSpec());
        fieldSpecs.add(new HtmlTableFieldSpec("<section>"));
        var fileSpec =
                HtmlTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        "<!DOCTYPE html>" + lineSeparator
                                + "<html lang=\"en\">" + lineSeparator
                                + "<head>" + lineSeparator
                                + "  <meta charset=\"UTF-8\"/>" + lineSeparator
                                + "  <title>Test 1</title>" + lineSeparator
                                + "  <style>table, th, td { border: 1px solid black; }</style>" + lineSeparator
                                + "</head>" + lineSeparator
                                + "<body>" + lineSeparator
                                + "  <h1>Header</h1>",
                        "</body>" + lineSeparator + "</html>",
                        "  ", fieldSpecs
                );

        // Write
        System.out.println("write: " + path);
        Stream<KeyValueRecord> stream = TextRecordStreams.of(
                new KeyValueFieldsRecord("", null, "<br>", "</td>"),
                new KeyValueFieldsRecord("", null, "\"", "\"aa\""),
                new KeyValueFieldsRecord("", null, "&& &", "&auml;"),
                new KeyValueFieldsRecord("", 2L, "property2", "value2"),
                new KeyValueFieldsRecord("Section1", 6L, "property6", "value6"),
                new KeyValueFieldsRecord("    ", 11L, "property11", "value11"),
                new KeyValueFieldsRecord("Section2", 8L, "property8", "value8"),
                new KeyValueFieldsRecord("section1", 4L, "property4", "value4"),
                new KeyValueFieldsRecord(null, 1L, "property1", "value1"),
                new KeyValueFieldsRecord("Missing value", 20L, "", null),
                new KeyValueFieldsRecord("Missing value", 21L, "", ""),
                new KeyValueFieldsRecord("Missing value", 22L, " ", " "),
                new KeyValueFieldsRecord("Section1", 5L, "property5", "value5"),
                new KeyValueFieldsRecord("", 3L, "property3", "value3"),
                new KeyValueFieldsRecord("\t", 12L, "property12", "value12"),
                new KeyValueFieldsRecord(" Section3 ", 14L, "property14", "value14"),
                new KeyValueFieldsRecord("   Section3   ", 15L, "property15", "value15"),
                new KeyValueFieldsRecord("Section1", 6L, "property6", "value6b"),
                new KeyValueFieldsRecord("Section2", 7L, "property7", "value7"),
                new KeyValueFieldsRecord("Section2", 10L, "property6", "value6/section2"),
                new KeyValueFieldsRecord(lineSeparator.string(), 13L, "property13", "value13")
        );
        RecordFiles.writeStreamIntoFile(fileSpec, stream.map(AddTextMapper.category()::map), path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        List<HtmlTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new HtmlTableFieldSpec("A"));
        fieldSpecs.add(new HtmlTableFieldSpec("B"));
        fieldSpecs.add(new HtmlTableFieldSpec("C"));
        var fileSpec =
                HtmlTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        "<!DOCTYPE html>" + lineSeparator
                                + "<html lang=\"en\">" + lineSeparator
                                + "<head>" + lineSeparator
                                + "  <meta charset=\"UTF-8\"/>" + lineSeparator
                                + "  <title>Test 2</title>" + lineSeparator
                                + "  <style>table, th, td { border: 1px solid black; }</style>" + lineSeparator
                                + "</head>" + lineSeparator
                                + "<body>" + lineSeparator
                                + "  <h1>Header</h1>",
                        "</body>" + lineSeparator + "</html>",
                        "  ", fieldSpecs
                );

        // Write
        System.out.println("write: " + path);
        Stream<ManyFieldsRecord> stream = TextRecordStreams.of(
                new ManyFieldsRecord(Stream.of("A0", "B0", "C0")),
                new ManyFieldsRecord(Stream.of("A1", "B1")),
                new ManyFieldsRecord(Stream.of("A2", null, "C2")),
                new ManyFieldsRecord(Stream.of(null, "B3")),
                new ManyFieldsRecord(Stream.of("A4", "B4", "C4", "D4", "E4")),
                new ManyFieldsRecord()
        );
        RecordFiles.writeStreamIntoFile(fileSpec, stream, path);
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
            test1(Path.of(args[0], "HtmlTableFile_1.html"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "HtmlTableFile_2.html"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
