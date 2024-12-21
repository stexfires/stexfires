package stexfires.io.container;

import org.jspecify.annotations.Nullable;
import stexfires.io.path.DosPathFieldsRecord;
import stexfires.io.path.PathType;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @since 0.1
 */
public final class RecordContainers {

    private RecordContainers() {
    }

    @SuppressWarnings("Convert2streamapi")
    public static TextRecord pack(TextRecord originalTextRecord, List<ContainerField> containerFields, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(originalTextRecord);
        Objects.requireNonNull(containerFields);

        List<@Nullable String> texts = new ArrayList<>(containerFields.size() + originalTextRecord.size());
        // Add extract container fields
        for (ContainerField containerField : containerFields) {
            texts.add(containerField.createText(originalTextRecord));
        }
        // Add text of original fields
        for (TextField textField : originalTextRecord.listOfFields()) {
            texts.add(textField.text());
        }
        return new ManyFieldsRecord(category, recordId, texts);
    }

    public static UnpackResult unpack(TextRecord packedTextRecord, @Nullable String className, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);
        if (className == null || className.isBlank()) {
            return RecordContainers.errorMessageUnpackResult("ClassName is null or blank!");
        }
        if (packedTextRecord.size() < containerFieldsSize) {
            return RecordContainers.errorMessageUnpackResult("Wrong record size!");
        }

        if (EmptyRecord.class.getName().equals(className)) {
            return unpackEmptyRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (KeyValueCommentFieldsRecord.class.getName().equals(className)) {
            return unpackKeyValueCommentFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (KeyValueFieldsRecord.class.getName().equals(className)) {
            return unpackKeyValueFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (ManyFieldsRecord.class.getName().equals(className)) {
            return unpackManyFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (TwoFieldsRecord.class.getName().equals(className)) {
            return unpackTwoFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (ValueFieldRecord.class.getName().equals(className)) {
            return unpackValueFieldRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (DosPathFieldsRecord.class.getName().equals(className)) {
            return unpackDosPathFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else {
            return unpackUnknownRecord(packedTextRecord, className, containerFieldsSize, category, recordId);
        }
    }

    public static UnpackResult errorMessageUnpackResult(String errorMessage) {
        Objects.requireNonNull(errorMessage);
        return new UnpackResult(Optional.empty(), Optional.of(errorMessage));
    }

    public static UnpackResult unpackEmptyRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        EmptyRecord unpackedRecord = null;
        String errorMessage = null;

        if (packedTextRecord.size() - containerFieldsSize != EmptyRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else if (category != null) {
            errorMessage = "Category is not null! " + category;
        } else if (recordId != null) {
            errorMessage = "RecordId is not null! " + recordId;
        } else {
            unpackedRecord = TextRecords.empty();
        }
        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackKeyValueCommentFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        KeyValueCommentFieldsRecord unpackedRecord = null;
        String errorMessage = null;

        String key = packedTextRecord.textAt(containerFieldsSize + KeyValueCommentFieldsRecord.KEY_INDEX);
        String value = packedTextRecord.textAt(containerFieldsSize + KeyValueCommentFieldsRecord.VALUE_INDEX);
        String comment = packedTextRecord.textAt(containerFieldsSize + KeyValueCommentFieldsRecord.COMMENT_INDEX);

        if (packedTextRecord.size() - containerFieldsSize != KeyValueCommentFieldsRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else if (key == null) {
            errorMessage = "Text is null! 'key'";
        } else {
            unpackedRecord = new KeyValueCommentFieldsRecord(category, recordId, key, value, comment);
        }
        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackKeyValueFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        KeyValueFieldsRecord unpackedRecord = null;
        String errorMessage = null;

        String key = packedTextRecord.textAt(containerFieldsSize + KeyValueFieldsRecord.KEY_INDEX);
        String value = packedTextRecord.textAt(containerFieldsSize + KeyValueFieldsRecord.VALUE_INDEX);
        if (packedTextRecord.size() - containerFieldsSize != KeyValueFieldsRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else if (key == null) {
            errorMessage = "Text is null! 'key'";
        } else {
            unpackedRecord = new KeyValueFieldsRecord(category, recordId, key, value);
        }
        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackManyFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        ManyFieldsRecord unpackedRecord = new ManyFieldsRecord(category, recordId, packedTextRecord.streamOfFields().skip(containerFieldsSize).map(TextField::text).toList());

        return new UnpackResult(Optional.of(unpackedRecord), Optional.empty());
    }

    public static UnpackResult unpackTwoFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        TwoFieldsRecord unpackedRecord = null;
        String errorMessage = null;

        String first = packedTextRecord.textAt(containerFieldsSize + TwoFieldsRecord.FIRST_INDEX);
        String second = packedTextRecord.textAt(containerFieldsSize + TwoFieldsRecord.SECOND_INDEX);
        if (packedTextRecord.size() - containerFieldsSize != TwoFieldsRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else {
            unpackedRecord = new TwoFieldsRecord(category, recordId, first, second);
        }
        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackValueFieldRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        ValueFieldRecord unpackedRecord = null;
        String errorMessage = null;

        String value = packedTextRecord.textAt(containerFieldsSize + ValueFieldRecord.VALUE_INDEX);
        if (packedTextRecord.size() - containerFieldsSize != ValueFieldRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else {
            unpackedRecord = new ValueFieldRecord(category, recordId, value);
        }
        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackDosPathFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        DosPathFieldsRecord unpackedRecord = null;
        String errorMessage = null;

        if (packedTextRecord.size() - containerFieldsSize != DosPathFieldsRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else if (category == null || category.isBlank()) {
            errorMessage = "Category is null or blank!";
        } else if (recordId != null) {
            errorMessage = "RecordId is not null! " + recordId;
        } else {
            TextField[] textFields = TextFields.newArrayOfStream(packedTextRecord.streamOfFields().skip(containerFieldsSize).map(TextField::text));
            try {
                unpackedRecord = new DosPathFieldsRecord(
                        PathType.valueOf(category),
                        textFields[DosPathFieldsRecord.FILE_NAME_INDEX],
                        textFields[DosPathFieldsRecord.PATH_INDEX],
                        textFields[DosPathFieldsRecord.PARENT_INDEX],
                        textFields[DosPathFieldsRecord.PATH_NAME_COUNT_INDEX],
                        textFields[DosPathFieldsRecord.FILE_SIZE_INDEX],
                        textFields[DosPathFieldsRecord.CREATION_TIME_INDEX],
                        textFields[DosPathFieldsRecord.LAST_MODIFIED_TIME_INDEX],
                        textFields[DosPathFieldsRecord.LAST_ACCESS_TIME_INDEX],
                        textFields[DosPathFieldsRecord.ABSOLUTE_INDEX],
                        textFields[DosPathFieldsRecord.ARCHIVE_INDEX],
                        textFields[DosPathFieldsRecord.READ_ONLY_INDEX],
                        textFields[DosPathFieldsRecord.HIDDEN_INDEX],
                        textFields[DosPathFieldsRecord.SYSTEM_INDEX],
                        textFields[DosPathFieldsRecord.FILE_EXTENSION_INDEX]);
            } catch (IllegalArgumentException e) {
                errorMessage = e.getMessage();
            }
        }

        return new UnpackResult(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult unpackUnknownRecord(TextRecord packedTextRecord, String className, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        TextRecord unpackedRecord = new ManyFieldsRecord(category, recordId, packedTextRecord.streamOfFields().skip(containerFieldsSize).map(TextField::text).toList());
        String errorMessage = "Unknown class name! " + className;

        return new UnpackResult(Optional.of(unpackedRecord), Optional.of(errorMessage));
    }

}
