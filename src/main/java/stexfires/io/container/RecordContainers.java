package stexfires.io.container;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordContainers {

    private RecordContainers() {
    }

    @SuppressWarnings("Convert2streamapi")
    public static TextRecord pack(TextRecord originalTextRecord, List<ContainerField> containerFields, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(originalTextRecord);
        Objects.requireNonNull(containerFields);

        List<String> texts = new ArrayList<>(containerFields.size() + originalTextRecord.size());
        // Add extract container fields
        for (ContainerField containerField : containerFields) {
            texts.add(containerField.extractFunction().apply(originalTextRecord));
        }
        // Add text of original fields
        for (TextField textField : originalTextRecord.listOfFields()) {
            texts.add(textField.text());
        }
        return new ManyFieldsRecord(category, recordId, texts);
    }

    public static UnpackResult<? extends TextRecord> unpack(TextRecord packedTextRecord, String className, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        if (className == null || className.isBlank()) {
            return new UnpackResult<>(Optional.empty(), Optional.of("ClassName is null or blank!"));
        }
        if (packedTextRecord.size() < containerFieldsSize) {
            return new UnpackResult<>(Optional.empty(), Optional.of("Wrong record size!"));
        }

        if (EmptyRecord.class.getName().equals(className)) {
            return RecordContainers.unpackEmptyRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (KeyValueCommentFieldsRecord.class.getName().equals(className)) {
            return RecordContainers.unpackKeyValueCommentFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (KeyValueFieldsRecord.class.getName().equals(className)) {
            return RecordContainers.unpackKeyValueFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (ManyFieldsRecord.class.getName().equals(className)) {
            return RecordContainers.unpackManyFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (TwoFieldsRecord.class.getName().equals(className)) {
            return RecordContainers.unpackTwoFieldsRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else if (ValueFieldRecord.class.getName().equals(className)) {
            return RecordContainers.unpackValueFieldRecord(packedTextRecord, containerFieldsSize, category, recordId);
        } else {
            return RecordContainers.unpackUnknownRecord(packedTextRecord, className, containerFieldsSize, category, recordId);
        }
    }

    public static UnpackResult<EmptyRecord> unpackEmptyRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
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
        return new UnpackResult<>(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult<KeyValueCommentFieldsRecord> unpackKeyValueCommentFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
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
        return new UnpackResult<>(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult<KeyValueFieldsRecord> unpackKeyValueFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
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
        return new UnpackResult<>(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult<ManyFieldsRecord> unpackManyFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        ManyFieldsRecord unpackedRecord = new ManyFieldsRecord(category, recordId, packedTextRecord.streamOfFields().skip(containerFieldsSize).map(TextField::text).toList());

        return new UnpackResult<>(Optional.of(unpackedRecord), Optional.empty());
    }

    public static UnpackResult<TwoFieldsRecord> unpackTwoFieldsRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
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
        return new UnpackResult<>(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult<ValueFieldRecord> unpackValueFieldRecord(TextRecord packedTextRecord, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        ValueFieldRecord unpackedRecord = null;
        String errorMessage = null;

        String value = packedTextRecord.textAt(containerFieldsSize + ValueFieldRecord.VALUE_INDEX);
        if (packedTextRecord.size() - containerFieldsSize != ValueFieldRecord.FIELD_SIZE) {
            errorMessage = "Wrong record size!";
        } else {
            unpackedRecord = new ValueFieldRecord(category, recordId, value);
        }
        return new UnpackResult<>(Optional.ofNullable(unpackedRecord), Optional.ofNullable(errorMessage));
    }

    public static UnpackResult<TextRecord> unpackUnknownRecord(TextRecord packedTextRecord, String className, int containerFieldsSize, @Nullable String category, @Nullable Long recordId) {
        Objects.requireNonNull(packedTextRecord);

        TextRecord unpackedRecord = new ManyFieldsRecord(category, recordId, packedTextRecord.streamOfFields().skip(containerFieldsSize).map(TextField::text).toList());
        String errorMessage = "Unknown class name! " + className;

        return new UnpackResult<>(Optional.of(unpackedRecord), Optional.of(errorMessage));
    }

}
