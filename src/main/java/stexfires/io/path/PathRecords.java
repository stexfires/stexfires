package stexfires.io.path;

import stexfires.record.TextRecord;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@link java.nio.file.Path}s and {@link stexfires.io.path.PathRecord}s.
 *
 * @since 0.1
 */
public final class PathRecords {

    private PathRecords() {
    }

    public static PathType toPathType(BasicFileAttributes fileAttributes) {
        Objects.requireNonNull(fileAttributes);
        if (fileAttributes.isDirectory()) {
            return PathType.DIRECTORY;
        } else if (fileAttributes.isRegularFile()) {
            return PathType.REGULAR_FILE;
        } else if (fileAttributes.isSymbolicLink()) {
            return PathType.SYMBOLIC_LINK;
        }
        return PathType.OTHER;
    }

    public static PathType readPathType(Path path) throws IOException {
        Objects.requireNonNull(path);
        BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        return toPathType(fileAttributes);
    }

    public static DosPathRecord newDosPathRecordFollowLinks(Path path) throws UncheckedIOException, UnsupportedOperationException {
        Objects.requireNonNull(path);
        try {
            return DosPathFieldsRecord.newDosPathFieldsRecord(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static DosPathRecord newDosPathRecordNoFollowLinks(Path path) throws UncheckedIOException, UnsupportedOperationException {
        Objects.requireNonNull(path);
        try {
            return DosPathFieldsRecord.newDosPathFieldsRecord(path, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    @SuppressWarnings("resource")
    public static <R extends TextRecord> Stream<R> listRecords(Path path, Function<Path, R> pathMapper) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(pathMapper);
        return Files.list(path).map(pathMapper);
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> listDosPathRecordsFollowLinks(Path path) throws IOException {
        Objects.requireNonNull(path);
        return listRecords(path, PathRecords::newDosPathRecordFollowLinks);
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> listDosPathRecordsNoFollowLinks(Path path) throws IOException {
        Objects.requireNonNull(path);
        return listRecords(path, PathRecords::newDosPathRecordNoFollowLinks);
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    @SuppressWarnings("resource")
    public static <R extends TextRecord> Stream<R> walkRecords(Path path, Function<Path, R> pathMapper,
                                                               int maxDepth, FileVisitOption... fileVisitOptions) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(pathMapper);
        if (maxDepth < 0) {
            throw new IllegalArgumentException("'maxDepth' is negative");
        }
        return Files.walk(path, maxDepth, fileVisitOptions).map(pathMapper);
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> walkDosPathRecordsFollowLinks(Path path,
                                                                      int maxDepth) throws IOException {
        Objects.requireNonNull(path);
        if (maxDepth < 0) {
            throw new IllegalArgumentException("'maxDepth' is negative");
        }
        return walkRecords(path, PathRecords::newDosPathRecordFollowLinks, maxDepth, FileVisitOption.FOLLOW_LINKS);
    }

    /**
     * This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> walkDosPathRecordsNoFollowLinks(Path path,
                                                                        int maxDepth) throws IOException {
        Objects.requireNonNull(path);
        if (maxDepth < 0) {
            throw new IllegalArgumentException("'maxDepth' is negative");
        }
        return walkRecords(path, PathRecords::newDosPathRecordNoFollowLinks, maxDepth);
    }

}
