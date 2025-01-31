package stexfires.io.path;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

/**
 * A PathRecord is a {@link stexfires.record.TextRecord} containing information about a file path.
 * The category contains the {@link PathType}.
 * All other information is stored in the values.
 *
 * @see java.nio.file.Path
 * @see stexfires.io.path.PathType
 * @since 0.1
 */
public interface PathRecord extends TextRecord {

    PathType pathType();

    TextField fileNameField();

    TextField pathField();

    TextField parentField();

    TextField pathNameCountField();

    TextField fileSizeField();

    TextField creationTimeField();

    TextField lastModifiedTimeField();

    TextField lastAccessTimeField();

    TextField absoluteField();

    @Override
    default String category() {
        return pathType().name();
    }

    @Override
    default @Nullable Long recordId() {
        return null;
    }

    @Override
    default boolean hasRecordId() {
        return false;
    }

    @Override
    default boolean isNotEmpty() {
        return true;
    }

    @Override
    default boolean isEmpty() {
        return false;
    }

    default @Nullable String fileName() {
        return fileNameField().text();
    }

    default Optional<String> fileNameAsOptional() {
        return fileNameField().asOptional();
    }

    default Path path() {
        return Path.of(Objects.requireNonNull(pathField().text()));
    }

    default @Nullable Path parent() {
        String parent = parentField().text();
        return (parent != null) ? Path.of(parent) : null;
    }

    default Optional<Path> parentAsOptional() {
        return Optional.ofNullable(parent());
    }

    default int pathNameCount() {
        return Integer.parseInt(Objects.requireNonNull(pathNameCountField().text()));
    }

    default long fileSize() {
        return Long.parseLong(Objects.requireNonNull(fileSizeField().text()));
    }

    default Instant creationTime() {
        return Instant.parse(Objects.requireNonNull(creationTimeField().text()));
    }

    default Instant lastModifiedTime() {
        return Instant.parse(Objects.requireNonNull(lastModifiedTimeField().text()));
    }

    default Instant lastAccessTime() {
        return Instant.parse(Objects.requireNonNull(lastAccessTimeField().text()));
    }

    default boolean isAbsolute() {
        return Boolean.parseBoolean(Objects.requireNonNull(absoluteField().text()));
    }

}
