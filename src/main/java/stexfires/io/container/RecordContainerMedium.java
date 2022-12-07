package stexfires.io.container;

import stexfires.record.TextRecord;
import stexfires.record.message.CategoryMessage;
import stexfires.record.message.ClassNameMessage;
import stexfires.record.message.RecordIdMessage;

import java.util.List;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordContainerMedium implements RecordContainer {

    public static final String RECORD_CONTAINER_NAME = "RecordContainerMedium";
    public static final ContainerField FIELD_CLASS_NAME = new ContainerField("ClassName", 0, new ClassNameMessage<>());
    public static final ContainerField FIELD_CATEGORY = new ContainerField("Category", 1, new CategoryMessage<>());
    public static final ContainerField FIELD_RECORD_ID = new ContainerField("RecordId", 2, new RecordIdMessage<>(null, null));

    @SuppressWarnings("StaticCollection")
    public static final List<ContainerField> CONTAINER_FIELDS = List.of(
            FIELD_CLASS_NAME,
            FIELD_CATEGORY,
            FIELD_RECORD_ID
    );

    public RecordContainerMedium() {
    }

    @Override
    public TextRecord pack(TextRecord originalTextRecord) {
        return RecordContainers.pack(originalTextRecord, CONTAINER_FIELDS, null, null);
    }

    @Override
    public UnpackResult<? extends TextRecord> unpack(TextRecord packedTextRecord) {
        String errorMessage = null;

        String className = FIELD_CLASS_NAME.getText(packedTextRecord);
        String category = FIELD_CATEGORY.getText(packedTextRecord);
        String recordIdString = FIELD_RECORD_ID.getText(packedTextRecord);

        Long recordId = null;
        if (recordIdString != null && !recordIdString.isBlank()) {
            try {
                recordId = Long.valueOf(recordIdString);
            } catch (NumberFormatException e) {
                errorMessage = "RecordId has wrong format! " + recordIdString;
            }
        }

        if (errorMessage == null) {
            return RecordContainers.unpack(packedTextRecord, className, CONTAINER_FIELDS.size(), category, recordId);
        }

        return new UnpackResult<>(Optional.empty(), Optional.of(errorMessage));
    }

}
