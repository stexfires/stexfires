package stexfires.io.path;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DosPathRecord extends PathRecord {

    public static final int ARCHIVE_INDEX = PathRecord.FIELD_SIZE;
    public static final int READ_ONLY_INDEX = PathRecord.FIELD_SIZE + 1;
    public static final int HIDDEN_INDEX = PathRecord.FIELD_SIZE + 2;
    public static final int SYSTEM_INDEX = PathRecord.FIELD_SIZE + 3;
    public static final int FILE_EXTENSION_INDEX = PathRecord.FIELD_SIZE + 4;

    protected static final int DOS_FIELD_SIZE = PathRecord.FIELD_SIZE + 5;

    protected static final String EXTENSION_SEPARATOR = ".";

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    protected DosPathRecord(PathType pathType, String[] values) {
        super(pathType, values);
    }

    protected static String[] createDosValues(Path path, DosFileAttributes fileAttributes) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileAttributes);
        String[] values = PathRecord.createBasicValues(DOS_FIELD_SIZE, path, fileAttributes);
        values[ARCHIVE_INDEX] = String.valueOf(fileAttributes.isArchive());
        values[READ_ONLY_INDEX] = String.valueOf(fileAttributes.isReadOnly());
        values[HIDDEN_INDEX] = String.valueOf(fileAttributes.isHidden());
        values[SYSTEM_INDEX] = String.valueOf(fileAttributes.isSystem());
        values[FILE_EXTENSION_INDEX] = extractFileExtension(path, fileAttributes);

        return values;
    }

    protected static String extractFileExtension(Path path, DosFileAttributes fileAttributes) {
        String fileExtension;
        Path fileName = path.getFileName();
        if (fileAttributes.isRegularFile() && (fileName != null)) {
            String strFileName = fileName.toString();
            int lastIndex = strFileName.lastIndexOf(EXTENSION_SEPARATOR);
            if ((lastIndex > 0) && ((lastIndex + 1) < strFileName.length())) {
                fileExtension = strFileName.substring(lastIndex + 1);
            } else {
                fileExtension = null;
            }
        } else {
            fileExtension = null;
        }
        return fileExtension;
    }

    public final boolean isArchive() {
        return Boolean.parseBoolean(valueAt(ARCHIVE_INDEX));
    }

    public final boolean isReadOnly() {
        return Boolean.parseBoolean(valueAt(READ_ONLY_INDEX));
    }

    public final boolean isHidden() {
        return Boolean.parseBoolean(valueAt(HIDDEN_INDEX));
    }

    public final boolean isSystem() {
        return Boolean.parseBoolean(valueAt(SYSTEM_INDEX));
    }

    public final @Nullable String fileExtension() {
        return valueAt(FILE_EXTENSION_INDEX);
    }

    public final Optional<String> fileExtensionAsOptional() {
        return Optional.ofNullable(fileExtension());
    }

    @Override
    public String toString() {
        return "DosPathRecord{" +
                "category(pathType)=" + category() +
                ", fileName=" + valueAt(FILE_NAME_INDEX) +
                ", path=" + valueAt(PATH_INDEX) +
                ", parent=" + valueAt(PARENT_INDEX) +
                ", pathNameCount=" + valueAt(PATH_NAME_COUNT_INDEX) +
                ", fileSize=" + valueAt(FILE_SIZE_INDEX) +
                ", creationTime=" + valueAt(CREATION_TIME_INDEX) +
                ", lastModifiedTime=" + valueAt(LAST_MODIFIED_TIME_INDEX) +
                ", lastAccessTime=" + valueAt(LAST_ACCESS_TIME_INDEX) +
                ", archive=" + valueAt(ARCHIVE_INDEX) +
                ", readOnly=" + valueAt(READ_ONLY_INDEX) +
                ", hidden=" + valueAt(HIDDEN_INDEX) +
                ", system=" + valueAt(SYSTEM_INDEX) +
                ", fileExtension=" + valueAt(FILE_EXTENSION_INDEX) +
                '}';
    }

}
