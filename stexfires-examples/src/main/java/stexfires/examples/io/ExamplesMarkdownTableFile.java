package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "SpellCheckingInspection"})
public final class ExamplesMarkdownTableFile {

    private ExamplesMarkdownTableFile() {
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private static void test1(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test1---");

        List<MarkdownTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new MarkdownTableFieldSpec("A", 5, null));
        fieldSpecs.add(new MarkdownTableFieldSpec("BB", 6, null));
        fieldSpecs.add(new MarkdownTableFieldSpec("C", 10, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("S5", 5, Alignment.START));
        fieldSpecs.add(new MarkdownTableFieldSpec("C5", 5, Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("E5", 5, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("S6", 6, Alignment.START));
        fieldSpecs.add(new MarkdownTableFieldSpec("C6", 6, Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("E6", 6, Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("", 7, null));
        fieldSpecs.add(new MarkdownTableFieldSpec(" ", 7, null));
        var fileSpec =
                MarkdownTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        fieldSpecs
                );

        // Write
        System.out.println("write: " + path);
        Stream<TextRecord> stream = Stream.of(
                new ManyFieldsRecord("a", "1", "aa"),
                new ManyFieldsRecord("b", "22", "bbbbbb"),
                new ManyFieldsRecord("c", "333", "cccccccccc"),
                new ManyFieldsRecord("d", "4444", "ddddddddddddddddddddd"),
                new ManyFieldsRecord(" ", " ", " "),
                new ManyFieldsRecord("", "", ""),
                new ManyFieldsRecord(null, 1L, null, null, null),
                new ManyFieldsRecord(" "),
                new ManyFieldsRecord(""),
                new ManyFieldsRecord(null, 1L, null, null),
                new ManyFieldsRecord(),
                new ManyFieldsRecord("b", "", "abc")
        );
        RecordFiles.writeStreamIntoFile(fileSpec, stream, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test2---");

        List<MarkdownTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new MarkdownTableFieldSpec("A", 5, null));
        fieldSpecs.add(new MarkdownTableFieldSpec("B|B", 6, null));
        fieldSpecs.add(new MarkdownTableFieldSpec("C", 10, Alignment.CENTER));
        var fileSpec =
                MarkdownTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        MarkdownTableFileSpec.DEFAULT_CONSUMER_TEXT_BEFORE,
                        MarkdownTableFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        MarkdownTableFileSpec.DEFAULT_CONSUMER_ALIGNMENT, fieldSpecs
                );

        // Write
        System.out.println("write: " + path);
        Stream<TextRecord> stream = Stream.of(
                new ManyFieldsRecord("a", "1", "aa"),
                new ManyFieldsRecord("b", "22", "bbbbbb"),
                new ManyFieldsRecord("c", "333", "cccccccccc"),
                new ManyFieldsRecord("d", "4444", "ddddddddddddddddddddd"),
                new ManyFieldsRecord(" ", " ", " "),
                new ManyFieldsRecord("", "", ""),
                new ManyFieldsRecord(null, 1L, null, null, null),
                new ManyFieldsRecord(" "),
                new ManyFieldsRecord(""),
                new ManyFieldsRecord(null, 1L, null, null),
                new ManyFieldsRecord(),
                new ManyFieldsRecord("b", "|", "a|b|c")
        );
        RecordFiles.writeStreamIntoFile(fileSpec, stream, path);

        List<MarkdownTableFieldSpec> fieldSpecs2 = new ArrayList<>();
        fieldSpecs2.add(new MarkdownTableFieldSpec("Column", 10, null));
        var fileSpec2 =
                MarkdownTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        lineSeparator.repeat(2) + "Header second table",
                        "Footer second table",
                        Alignment.START, fieldSpecs2
                );

        // Write
        System.out.println("write APPEND: " + path);
        Stream<TextRecord> stream2 = Stream.of(
                new ManyFieldsRecord("a"),
                new ManyFieldsRecord("b")
        );
        RecordFiles.writeStreamIntoFile(fileSpec2, stream2, path, StandardOpenOption.APPEND);
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        System.out.println("-test3---");

        List<MarkdownTableFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new MarkdownTableFieldSpec("A"));
        fieldSpecs.add(new MarkdownTableFieldSpec("BB", Alignment.START));
        fieldSpecs.add(new MarkdownTableFieldSpec("C", Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("DD", Alignment.CENTER));
        fieldSpecs.add(new MarkdownTableFieldSpec("E", Alignment.END));
        fieldSpecs.add(new MarkdownTableFieldSpec("FF", 8, Alignment.END));
        var fileSpec =
                MarkdownTableFileSpec.consumerFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        fieldSpecs
                );

        // Write
        System.out.println("write: " + path);
        Stream<TextRecord> stream = Stream.of(
                new ManyFieldsRecord(),
                new ManyFieldsRecord("category", 0L, null, null, null, null, null, null),
                new ManyFieldsRecord("", "", "", "", "", ""),
                new ManyFieldsRecord("a", "b", "c", "d", "0", "0.00"),
                new ManyFieldsRecord("aa", "bb", "cc", "dd", "10", "10.00"),
                new ManyFieldsRecord("aaa", "bbb", "ccc", "ddd", "100", "100.00"),
                new ManyFieldsRecord("aaaa", "bbbb", "cccc", "dddd", "1000", "1000.00"),
                new ManyFieldsRecord("aaaaa", "bbbbb", "ccccc", "ddddd", "10000", "10000.00")

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
            test1(Path.of(args[0], "MarkdownTableFile_1.md"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "MarkdownTableFile_2.md"), LineSeparator.systemLineSeparator());
            test3(Path.of(args[0], "MarkdownTableFile_3.md"), LineSeparator.systemLineSeparator());
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
