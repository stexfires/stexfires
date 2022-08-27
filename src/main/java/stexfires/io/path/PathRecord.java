package stexfires.io.path;

import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A PathRecord is a {@link stexfires.record.TextRecord} containing information about a file path.
 * The category contains the {@link PathType}.
 * All other information is stored in the values.
 *
 * @author Mathias Kalb
 * @see java.nio.file.Path
 * @see stexfires.io.path.PathType
 * @since 0.1
 */
public class PathRecord implements TextRecord {

    public static final int FILE_NAME_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PARENT_INDEX = 2;
    public static final int PATH_NAME_COUNT_INDEX = 3;
    public static final int FILE_SIZE_INDEX = 4;
    public static final int CREATION_TIME_INDEX = 5;
    public static final int LAST_MODIFIED_TIME_INDEX = 6;
    public static final int LAST_ACCESS_TIME_INDEX = 7;

    protected static final int FIELD_SIZE = 8;

    private final String category;
    private final Field[] fields;

    private final int hashCode;

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    protected PathRecord(PathType pathType, String[] values) {
        this.category = pathType.name();
        this.fields = Fields.newArray(values);

        hashCode = Objects.hash(category, Arrays.hashCode(fields));
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

    @Override
    public final Field[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public final List<Field> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Arrays.stream(arrayOfFields());
    }

    @Override
    public final String category() {
        return category;
    }

    @Override
    public final @Nullable Long recordId() {
        return null;
    }

    @Override
    public final int size() {
        return fields.length;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final Field fieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        PathRecord record = (PathRecord) obj;
        return Objects.equals(category, record.category) &&
                Arrays.equals(fields, record.fields);
    }

    @Override
    public int hashCode() {
        return hashCode;
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

}
