package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.path.DosPathRecord;
import stexfires.io.path.PathRecord;
import stexfires.io.path.PathRecords;
import stexfires.io.path.PathType;
import stexfires.io.path.PathTypeFilter;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.ConsumerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesPathRecord {

    private ExamplesPathRecord() {
    }

    private static DosPathRecord createAndLogNewDosPathRecord(String pathAsString) {
        Objects.requireNonNull(pathAsString);
        System.out.println("---createAndLogNewDosPathRecord---");
        Path path = Path.of(pathAsString);
        System.out.println("pathAsString       : " + pathAsString);
        System.out.println("Path toString      : " + path);
        System.out.println("Path normalize     : " + path.normalize());
        System.out.println("Path toAbsolutePath: " + path.toAbsolutePath());

        DosPathRecord pathRecord = PathRecords.newDosPathRecordNoFollowLinks(path);
        System.out.println(pathRecord);
        System.out.println(pathRecord.category());
        System.out.println(pathRecord.recordId());
        System.out.println(pathRecord.hasRecordId());
        System.out.println(pathRecord.isNotEmpty());
        System.out.println(pathRecord.isEmpty());
        System.out.println(pathRecord.size());
        System.out.println(pathRecord.isValidIndex(-1) + " " + pathRecord.isValidIndex(0) + " " + pathRecord.isValidIndex(7) + " " + pathRecord.isValidIndex(8) + " " + pathRecord.isValidIndex(13) + " " + pathRecord.isValidIndex(14));
        System.out.println(pathRecord.pathType());
        System.out.println(pathRecord.fileName());
        System.out.println(pathRecord.fileNameAsOptional());
        System.out.println(pathRecord.path());
        System.out.println(pathRecord.parent());
        System.out.println(pathRecord.parentAsOptional());
        System.out.println(pathRecord.pathNameCount());
        System.out.println(pathRecord.fileSize());
        System.out.println(pathRecord.creationTime());
        System.out.println(pathRecord.lastModifiedTime());
        System.out.println(pathRecord.lastAccessTime());
        System.out.println(pathRecord.isAbsolute());
        System.out.println(pathRecord.isArchive());
        System.out.println(pathRecord.isReadOnly());
        System.out.println(pathRecord.isHidden());
        System.out.println(pathRecord.isSystem());
        System.out.println(pathRecord.fileExtension());
        System.out.println(pathRecord.fileExtensionAsOptional());

        return pathRecord;
    }

    private static void test1(String pathAsString) throws IOException {
        Objects.requireNonNull(pathAsString);
        System.out.println("-test1---");

        DosPathRecord pathRecord = createAndLogNewDosPathRecord(pathAsString);
        Path path = Path.of(pathAsString);

        System.out.println("---listDosPathRecordsFollowLinks printLines---");
        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecordsFollowLinks(path)) {
            TextRecordStreams.printLines(pathStream);
        }

        System.out.println("---listDosPathRecordsNoFollowLinks joinMessages fileName---");
        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecordsNoFollowLinks(path)) {
            System.out.println(TextRecordStreams.joinMessages(pathStream, PathRecord::fileName, ", "));
        }

        System.out.println("---walkDosPathRecordsNoFollowLinks printLines Parent 2 filter---");
        try (Stream<DosPathRecord> pathStream = PathRecords.walkDosPathRecordsNoFollowLinks(path.getParent(), 2)) {
            TextRecordStreams.printLines(pathStream.filter(new PathTypeFilter<>(PathType.DIRECTORY).negate()::isValid));
        }

        System.out.println("---walkDosPathRecordsFollowLinks printLines Parent 2 filter---");
        try (Stream<DosPathRecord> pathStream = PathRecords.walkDosPathRecordsFollowLinks(path.getParent(), 2)) {
            TextRecordStreams.printLines(pathStream.filter(new PathTypeFilter<>(PathType.DIRECTORY).negate()::isValid));
        }

        System.out.println("---parents---");
        DosPathRecord parentDosPathRecord = pathRecord;
        while (parentDosPathRecord != null) {
            System.out.println(parentDosPathRecord);
            parentDosPathRecord = parentDosPathRecord.parentAsOptional()
                                                     .map(PathRecords::newDosPathRecordNoFollowLinks)
                                                     .orElse(null);
        }
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ConsumerException, IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(lineSeparator);
        System.out.println("-test2---");

        System.out.println("---listDosPathRecords writeFile---");
        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());

        var fileSpec =
                SimpleDelimitedFileSpec.consumerFileSpec(
                        CharsetCoding.reportingErrors(StandardCharsets.ISO_8859_1),
                        ",", lineSeparator,
                        "File name,Path,Parent,Path name count,File size",
                        SimpleDelimitedFileSpec.DEFAULT_CONSUMER_TEXT_AFTER,
                        fieldSpecs);

        try (Stream<DosPathRecord> pathStream = PathRecords.listDosPathRecordsFollowLinks(path)) {
            RecordFiles.writeStreamIntoFile(fileSpec, pathStream, path.resolve("PathRecord_1.csv"));
        }
    }

    private static void test3() {
        System.out.println("-test3---");

        createAndLogNewDosPathRecord("");

        createAndLogNewDosPathRecord("..");

        createAndLogNewDosPathRecord(".");
        createAndLogNewDosPathRecord("./");
        createAndLogNewDosPathRecord(".\\");

        createAndLogNewDosPathRecord("/.");
        createAndLogNewDosPathRecord("\\.");

        createAndLogNewDosPathRecord("/");
        createAndLogNewDosPathRecord("\\");

        createAndLogNewDosPathRecord("C:");
        createAndLogNewDosPathRecord("C:/");
        createAndLogNewDosPathRecord("C:\\");
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
            test1(args[0]);
            test2(Path.of(args[0]), LineSeparator.systemLineSeparator());
            test3();
        } catch (ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
