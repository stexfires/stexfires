package stexfires.io.path;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record DosPathFieldsRecord(
        PathType pathType,
        TextField fileNameField,
        TextField pathField,
        TextField parentField,
        TextField pathNameCountField,
        TextField fileSizeField,
        TextField creationTimeField,
        TextField lastModifiedTimeField,
        TextField lastAccessTimeField,
        TextField absoluteField,
        TextField archiveField,
        TextField readOnlyField,
        TextField hiddenField,
        TextField systemField,
        TextField fileExtensionField
) implements DosPathRecord, Serializable {

    public static final int FILE_NAME_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PARENT_INDEX = 2;
    public static final int PATH_NAME_COUNT_INDEX = 3;
    public static final int FILE_SIZE_INDEX = 4;
    public static final int CREATION_TIME_INDEX = 5;
    public static final int LAST_MODIFIED_TIME_INDEX = 6;
    public static final int LAST_ACCESS_TIME_INDEX = 7;
    public static final int ABSOLUTE_INDEX = 8;
    public static final int ARCHIVE_INDEX = 9;
    public static final int READ_ONLY_INDEX = 10;
    public static final int HIDDEN_INDEX = 11;
    public static final int SYSTEM_INDEX = 12;
    public static final int FILE_EXTENSION_INDEX = 13;

    public static final int MAX_INDEX = 13;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    static void checkField(TextField textField, int index, boolean nullable, String parameterName) {
        Objects.requireNonNull(textField);
        Objects.requireNonNull(parameterName);
        if (textField.index() != index) {
            throw new IllegalArgumentException("Wrong 'index' of " + parameterName + ": " + textField);
        }
        if (textField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of " + parameterName + ": " + textField);
        }
        if (!nullable && textField.isNull()) {
            throw new IllegalArgumentException("Text is null of " + parameterName + ": " + textField);
        }
    }

    public DosPathFieldsRecord {
        Objects.requireNonNull(pathType);

        // Path
        checkField(fileNameField, FILE_NAME_INDEX, true, "fileNameField");
        checkField(pathField, PATH_INDEX, false, "pathField");
        checkField(parentField, PARENT_INDEX, true, "parentField");
        checkField(pathNameCountField, PATH_NAME_COUNT_INDEX, false, "pathNameCountField");
        checkField(fileSizeField, FILE_SIZE_INDEX, false, "fileSizeField");
        checkField(creationTimeField, CREATION_TIME_INDEX, false, "creationTimeField");
        checkField(lastModifiedTimeField, LAST_MODIFIED_TIME_INDEX, false, "lastModifiedTimeField");
        checkField(lastAccessTimeField, LAST_ACCESS_TIME_INDEX, false, "lastAccessTimeField");
        checkField(absoluteField, ABSOLUTE_INDEX, false, "absoluteField");

        // DosPath
        checkField(archiveField, ARCHIVE_INDEX, false, "archiveField");
        checkField(readOnlyField, READ_ONLY_INDEX, false, "readOnlyField");
        checkField(hiddenField, HIDDEN_INDEX, false, "hiddenField");
        checkField(systemField, SYSTEM_INDEX, false, "systemField");
        checkField(fileExtensionField, FILE_EXTENSION_INDEX, true, "fileExtensionField");
    }

    static @Nullable String extractDosFileNameExtension(@Nullable Path fileName, boolean regularFile) {
        String fileExtension;
        if ((fileName != null) && regularFile) {
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

    public static DosPathFieldsRecord newDosPathFieldsRecord(Path path, LinkOption... linkOptions) throws IOException, UnsupportedOperationException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(linkOptions);
        DosFileAttributes fileAttributes = Files.readAttributes(path, DosFileAttributes.class, linkOptions);
        Path fileName = path.getFileName();
        Path parent = path.getParent();
        return new DosPathFieldsRecord(
                PathType.ofAttributes(fileAttributes),
                new TextField(FILE_NAME_INDEX, MAX_INDEX, (fileName != null ? fileName.toString() : null)),
                new TextField(PATH_INDEX, MAX_INDEX, path.toString()),
                new TextField(PARENT_INDEX, MAX_INDEX, (parent != null ? parent.toString() : null)),
                new TextField(PATH_NAME_COUNT_INDEX, MAX_INDEX, String.valueOf(path.getNameCount())),
                new TextField(FILE_SIZE_INDEX, MAX_INDEX, String.valueOf(fileAttributes.size())),
                new TextField(CREATION_TIME_INDEX, MAX_INDEX, fileAttributes.creationTime().toInstant().toString()),
                new TextField(LAST_MODIFIED_TIME_INDEX, MAX_INDEX, fileAttributes.lastModifiedTime().toInstant().toString()),
                new TextField(LAST_ACCESS_TIME_INDEX, MAX_INDEX, fileAttributes.lastAccessTime().toInstant().toString()),
                new TextField(ABSOLUTE_INDEX, MAX_INDEX, String.valueOf(path.isAbsolute())),
                new TextField(ARCHIVE_INDEX, MAX_INDEX, String.valueOf(fileAttributes.isArchive())),
                new TextField(READ_ONLY_INDEX, MAX_INDEX, String.valueOf(fileAttributes.isReadOnly())),
                new TextField(HIDDEN_INDEX, MAX_INDEX, String.valueOf(fileAttributes.isHidden())),
                new TextField(SYSTEM_INDEX, MAX_INDEX, String.valueOf(fileAttributes.isSystem())),
                new TextField(FILE_EXTENSION_INDEX, MAX_INDEX, extractDosFileNameExtension(fileName, fileAttributes.isRegularFile()))
        );
    }

    @Override
    public TextField[] arrayOfFields() {
        return new TextField[]{
                fileNameField,
                pathField,
                parentField,
                pathNameCountField,
                fileSizeField,
                creationTimeField,
                lastModifiedTimeField,
                lastAccessTimeField,
                absoluteField,
                archiveField,
                readOnlyField,
                hiddenField,
                systemField,
                fileExtensionField
        };
    }

    @Override
    public List<TextField> listOfFields() {
        return List.of(
                fileNameField,
                pathField,
                parentField,
                pathNameCountField,
                fileSizeField,
                creationTimeField,
                lastModifiedTimeField,
                lastAccessTimeField,
                absoluteField,
                archiveField,
                readOnlyField,
                hiddenField,
                systemField,
                fileExtensionField
        );
    }

    @Override
    public List<TextField> listOfFieldsReversed() {
        return List.of(
                fileExtensionField,
                systemField,
                hiddenField,
                readOnlyField,
                archiveField,
                absoluteField,
                lastAccessTimeField,
                lastModifiedTimeField,
                creationTimeField,
                fileSizeField,
                pathNameCountField,
                parentField,
                pathField,
                fileNameField
        );
    }

    @Override
    public Stream<TextField> streamOfFields() {
        return Stream.of(
                fileNameField,
                pathField,
                parentField,
                pathNameCountField,
                fileSizeField,
                creationTimeField,
                lastModifiedTimeField,
                lastAccessTimeField,
                absoluteField,
                archiveField,
                readOnlyField,
                hiddenField,
                systemField,
                fileExtensionField
        );
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return switch (index) {
            case FILE_NAME_INDEX -> fileNameField;
            case PATH_INDEX -> pathField;
            case PARENT_INDEX -> parentField;
            case PATH_NAME_COUNT_INDEX -> pathNameCountField;
            case FILE_SIZE_INDEX -> fileSizeField;
            case CREATION_TIME_INDEX -> creationTimeField;
            case LAST_MODIFIED_TIME_INDEX -> lastModifiedTimeField;
            case LAST_ACCESS_TIME_INDEX -> lastAccessTimeField;
            case ABSOLUTE_INDEX -> absoluteField;
            case ARCHIVE_INDEX -> archiveField;
            case READ_ONLY_INDEX -> readOnlyField;
            case HIDDEN_INDEX -> hiddenField;
            case SYSTEM_INDEX -> systemField;
            case FILE_EXTENSION_INDEX -> fileExtensionField;
            default -> null;
        };
    }

    @Override
    public int size() {
        return FIELD_SIZE;
    }

    @Override
    public boolean isValidIndex(int index) {
        return index >= TextField.FIRST_FIELD_INDEX && index <= MAX_INDEX;
    }

}
