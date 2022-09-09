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

    protected static String[] createDosTexts(Path path, DosFileAttributes fileAttributes) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileAttributes);
        String[] texts = PathRecord.createBasicTexts(DOS_FIELD_SIZE, path, fileAttributes);
        texts[ARCHIVE_INDEX] = String.valueOf(fileAttributes.isArchive());
        texts[READ_ONLY_INDEX] = String.valueOf(fileAttributes.isReadOnly());
        texts[HIDDEN_INDEX] = String.valueOf(fileAttributes.isHidden());
        texts[SYSTEM_INDEX] = String.valueOf(fileAttributes.isSystem());
        texts[FILE_EXTENSION_INDEX] = extractFileExtension(path, fileAttributes);

        return texts;
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
        return Boolean.parseBoolean(textAt(ARCHIVE_INDEX));
    }

    public final boolean isReadOnly() {
        return Boolean.parseBoolean(textAt(READ_ONLY_INDEX));
    }

    public final boolean isHidden() {
        return Boolean.parseBoolean(textAt(HIDDEN_INDEX));
    }

    public final boolean isSystem() {
        return Boolean.parseBoolean(textAt(SYSTEM_INDEX));
    }

    public final @Nullable String fileExtension() {
        return textAt(FILE_EXTENSION_INDEX);
    }

    public final Optional<String> fileExtensionAsOptional() {
        return Optional.ofNullable(fileExtension());
    }

    @Override
    public String toString() {
        return "DosPathRecord{" +
                "category(pathType)=" + category() +
                ", fileName=" + textAt(FILE_NAME_INDEX) +
                ", path=" + textAt(PATH_INDEX) +
                ", parent=" + textAt(PARENT_INDEX) +
                ", pathNameCount=" + textAt(PATH_NAME_COUNT_INDEX) +
                ", fileSize=" + textAt(FILE_SIZE_INDEX) +
                ", creationTime=" + textAt(CREATION_TIME_INDEX) +
                ", lastModifiedTime=" + textAt(LAST_MODIFIED_TIME_INDEX) +
                ", lastAccessTime=" + textAt(LAST_ACCESS_TIME_INDEX) +
                ", archive=" + textAt(ARCHIVE_INDEX) +
                ", readOnly=" + textAt(READ_ONLY_INDEX) +
                ", hidden=" + textAt(HIDDEN_INDEX) +
                ", system=" + textAt(SYSTEM_INDEX) +
                ", fileExtension=" + textAt(FILE_EXTENSION_INDEX) +
                '}';
    }

}
