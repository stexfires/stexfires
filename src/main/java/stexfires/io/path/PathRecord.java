package stexfires.io.path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
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
    private final TextField[] fields;

    private final int hashCode;

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    protected PathRecord(PathType pathType, String[] values) {
        this.category = pathType.name();
        this.fields = TextFields.newArray(values);

        hashCode = Objects.hash(category, Arrays.hashCode(fields));
    }

    protected static String[] createBasicTexts(int fieldSize, Path path,
                                               BasicFileAttributes fileAttributes) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileAttributes);
        Path fileName = path.getFileName();
        Path parent = path.getParent();
        String[] texts = new String[fieldSize];
        texts[FILE_NAME_INDEX] = (fileName != null ? fileName.toString() : null);
        texts[PATH_INDEX] = path.toString();
        texts[PARENT_INDEX] = (parent != null ? parent.toString() : null);
        texts[PATH_NAME_COUNT_INDEX] = String.valueOf(path.getNameCount());
        texts[FILE_SIZE_INDEX] = String.valueOf(fileAttributes.size());
        texts[CREATION_TIME_INDEX] = fileAttributes.creationTime().toInstant().toString();
        texts[LAST_MODIFIED_TIME_INDEX] = fileAttributes.lastModifiedTime().toInstant().toString();
        texts[LAST_ACCESS_TIME_INDEX] = fileAttributes.lastAccessTime().toInstant().toString();
        return texts;
    }

    @Override
    public final TextField[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public final @NotNull List<TextField> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public final @NotNull Stream<TextField> streamOfFields() {
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
    public final TextField fieldAt(int index) {
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
                ", fileName=" + textAt(FILE_NAME_INDEX) +
                ", path=" + textAt(PATH_INDEX) +
                ", parent=" + textAt(PARENT_INDEX) +
                ", pathNameCount=" + textAt(PATH_NAME_COUNT_INDEX) +
                ", fileSize=" + textAt(FILE_SIZE_INDEX) +
                ", creationTime=" + textAt(CREATION_TIME_INDEX) +
                ", lastModifiedTime=" + textAt(LAST_MODIFIED_TIME_INDEX) +
                ", lastAccessTime=" + textAt(LAST_ACCESS_TIME_INDEX) +
                '}';
    }

    public final PathType pathType() {
        return PathType.valueOf(category());
    }

    public final @Nullable String fileName() {
        return textAt(FILE_NAME_INDEX);
    }

    public final Optional<String> fileNameAsOptional() {
        return Optional.ofNullable(fileName());
    }

    public final Path path() {
        return Paths.get(Objects.requireNonNull(textAt(PATH_INDEX)));
    }

    public final @Nullable Path parent() {
        String parent = textAt(PARENT_INDEX);
        return parent != null ? Paths.get(parent) : null;
    }

    public final Optional<Path> parentAsOptional() {
        return Optional.ofNullable(parent());
    }

    public final int pathNameCount() {
        return Integer.parseInt(Objects.requireNonNull(textAt(PATH_NAME_COUNT_INDEX)));
    }

    public final long fileSize() {
        return Long.parseLong(Objects.requireNonNull(textAt(FILE_SIZE_INDEX)));
    }

    public final Instant creationTime() {
        return Instant.parse(Objects.requireNonNull(textAt(CREATION_TIME_INDEX)));
    }

    public final Instant lastModifiedTime() {
        return Instant.parse(Objects.requireNonNull(textAt(LAST_MODIFIED_TIME_INDEX)));
    }

    public final Instant lastAccessTime() {
        return Instant.parse(Objects.requireNonNull(textAt(LAST_ACCESS_TIME_INDEX)));
    }

}
