package stexfires.io.path;

import stexfires.core.TextRecord;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on {@link Path}s and {@link PathRecord}s.
 *
 * @author Mathias Kalb
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
        BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        return toPathType(fileAttributes);
    }

    public static PathRecord newPathRecord(Path path) throws UncheckedIOException, UnsupportedOperationException {
        Objects.requireNonNull(path);
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);

            return new PathRecord(toPathType(fileAttributes),
                    PathRecord.createBasicValues(PathRecord.FIELD_SIZE, path, fileAttributes));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static DosPathRecord newDosPathRecord(Path path) throws UncheckedIOException, UnsupportedOperationException {
        Objects.requireNonNull(path);
        try {
            DosFileAttributes fileAttributes = Files.readAttributes(path, DosFileAttributes.class);

            return new DosPathRecord(toPathType(fileAttributes),
                    DosPathRecord.createDosValues(path, fileAttributes));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * @apiNote This method must be used within a try-with-resources statement or similar
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
     * @apiNote This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<PathRecord> listPathRecords(Path path) throws IOException {
        Objects.requireNonNull(path);
        return listRecords(path, PathRecords::newPathRecord);
    }

    /**
     * @apiNote This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> listDosPathRecords(Path path) throws IOException {
        Objects.requireNonNull(path);
        return listRecords(path, PathRecords::newDosPathRecord);
    }

    /**
     * @apiNote This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    @SuppressWarnings("resource")
    public static <R extends TextRecord> Stream<R> walkRecords(Path path, Function<Path, R> pathMapper) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(pathMapper);
        return Files.walk(path).map(pathMapper);
    }

    /**
     * @apiNote This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<PathRecord> walkPathRecords(Path path) throws IOException {
        Objects.requireNonNull(path);
        return walkRecords(path, PathRecords::newPathRecord);
    }

    /**
     * @apiNote This method must be used within a try-with-resources statement or similar
     * control structure to ensure that the stream's open directory is closed
     * promptly after the stream's operations have completed.
     */
    public static Stream<DosPathRecord> walkDosPathRecords(Path path) throws IOException {
        Objects.requireNonNull(path);
        return walkRecords(path, PathRecords::newDosPathRecord);
    }

}
