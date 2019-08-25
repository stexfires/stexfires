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

    @Nullable
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
        return Boolean.parseBoolean(getValueAt(ARCHIVE_INDEX));
    }

    public final boolean isReadOnly() {
        return Boolean.parseBoolean(getValueAt(READ_ONLY_INDEX));
    }

    public final boolean isHidden() {
        return Boolean.parseBoolean(getValueAt(HIDDEN_INDEX));
    }

    public final boolean isSystem() {
        return Boolean.parseBoolean(getValueAt(SYSTEM_INDEX));
    }

    public final @Nullable String fileExtension() {
        return getValueAt(FILE_EXTENSION_INDEX);
    }

    public final Optional<String> fileExtensionAsOptional() {
        return Optional.ofNullable(fileExtension());
    }

    @Override
    public String toString() {
        return "DosPathRecord{" +
                "category(pathType)=" + getCategory() +
                ", fileName=" + getValueAt(FILE_NAME_INDEX) +
                ", path=" + getValueAt(PATH_INDEX) +
                ", parent=" + getValueAt(PARENT_INDEX) +
                ", pathNameCount=" + getValueAt(PATH_NAME_COUNT_INDEX) +
                ", fileSize=" + getValueAt(FILE_SIZE_INDEX) +
                ", creationTime=" + getValueAt(CREATION_TIME_INDEX) +
                ", lastModifiedTime=" + getValueAt(LAST_MODIFIED_TIME_INDEX) +
                ", lastAccessTime=" + getValueAt(LAST_ACCESS_TIME_INDEX) +
                ", archive=" + getValueAt(ARCHIVE_INDEX) +
                ", readOnly=" + getValueAt(READ_ONLY_INDEX) +
                ", hidden=" + getValueAt(HIDDEN_INDEX) +
                ", system=" + getValueAt(SYSTEM_INDEX) +
                ", fileExtension=" + getValueAt(FILE_EXTENSION_INDEX) +
                '}';
    }

}
