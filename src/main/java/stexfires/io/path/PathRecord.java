package stexfires.io.path;

import org.jetbrains.annotations.Nullable;
import stexfires.core.impl.StandardRecord;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * A PathRecord is a {@link stexfires.core.TextRecord} containing information about a file path.
 * The category contains the {@link PathType}.
 * All other information is stored in the values.
 *
 * @author Mathias Kalb
 * @see java.nio.file.Path
 * @see stexfires.io.path.PathType
 * @since 0.1
 */
public class PathRecord extends StandardRecord {

    public static final int FILE_NAME_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PARENT_INDEX = 2;
    public static final int PATH_NAME_COUNT_INDEX = 3;
    public static final int FILE_SIZE_INDEX = 4;
    public static final int CREATION_TIME_INDEX = 5;
    public static final int LAST_MODIFIED_TIME_INDEX = 6;
    public static final int LAST_ACCESS_TIME_INDEX = 7;

    protected static final int FIELD_SIZE = 8;

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    protected PathRecord(PathType pathType, String[] values) {
        super(pathType.name(), null, values);
    }

    protected static String[] createBasicValues(int fieldSize, Path path,
                                                BasicFileAttributes fileAttributes) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileAttributes);
        Path fileName = path.getFileName();
        Path parent = path.getParent();
        String[] values = new String[fieldSize];
        values[FILE_NAME_INDEX] = (fileName != null ? fileName.toString() : null);
        values[PATH_INDEX] = path.toString();
        values[PARENT_INDEX] = (parent != null ? parent.toString() : null);
        values[PATH_NAME_COUNT_INDEX] = String.valueOf(path.getNameCount());
        values[FILE_SIZE_INDEX] = String.valueOf(fileAttributes.size());
        values[CREATION_TIME_INDEX] = fileAttributes.creationTime().toInstant().toString();
        values[LAST_MODIFIED_TIME_INDEX] = fileAttributes.lastModifiedTime().toInstant().toString();
        values[LAST_ACCESS_TIME_INDEX] = fileAttributes.lastAccessTime().toInstant().toString();
        return values;
    }

    public final PathType pathType() {
        return PathType.valueOf(category());
    }

    public final @Nullable String fileName() {
        return valueAt(FILE_NAME_INDEX);
    }

    public final Optional<String> fileNameAsOptional() {
        return Optional.ofNullable(fileName());
    }

    public final Path path() {
        return Paths.get(Objects.requireNonNull(valueAt(PATH_INDEX)));
    }

    public final @Nullable Path parent() {
        String parent = valueAt(PARENT_INDEX);
        return parent != null ? Paths.get(parent) : null;
    }

    public final Optional<Path> parentAsOptional() {
        return Optional.ofNullable(parent());
    }

    public final int pathNameCount() {
        return Integer.parseInt(Objects.requireNonNull(valueAt(PATH_NAME_COUNT_INDEX)));
    }

    public final long fileSize() {
        return Long.parseLong(Objects.requireNonNull(valueAt(FILE_SIZE_INDEX)));
    }

    public final Instant creationTime() {
        return Instant.parse(Objects.requireNonNull(valueAt(CREATION_TIME_INDEX)));
    }

    public final Instant lastModifiedTime() {
        return Instant.parse(Objects.requireNonNull(valueAt(LAST_MODIFIED_TIME_INDEX)));
    }

    public final Instant lastAccessTime() {
        return Instant.parse(Objects.requireNonNull(valueAt(LAST_ACCESS_TIME_INDEX)));
    }

    @Override
    public String toString() {
        return "PathRecord{" +
                "category(pathType)=" + category() +
                ", fileName=" + valueAt(FILE_NAME_INDEX) +
                ", path=" + valueAt(PATH_INDEX) +
                ", parent=" + valueAt(PARENT_INDEX) +
                ", pathNameCount=" + valueAt(PATH_NAME_COUNT_INDEX) +
                ", fileSize=" + valueAt(FILE_SIZE_INDEX) +
                ", creationTime=" + valueAt(CREATION_TIME_INDEX) +
                ", lastModifiedTime=" + valueAt(LAST_MODIFIED_TIME_INDEX) +
                ", lastAccessTime=" + valueAt(LAST_ACCESS_TIME_INDEX) +
                '}';
    }

}
