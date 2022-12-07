package stexfires.io.container;

import stexfires.record.TextRecord;
import stexfires.record.message.CategoryMessage;
import stexfires.record.message.ClassNameMessage;
import stexfires.record.message.ConstantMessage;
import stexfires.record.message.RecordIdMessage;
import stexfires.record.message.SizeMessage;

import java.util.List;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordContainerLarge implements RecordContainer {

    public static final String RECORD_CONTAINER_NAME = "RecordContainerLarge";
    public static final ContainerField FIELD_RECORD_CONTAINER_NAME = new ContainerField("RecordContainerName", 0, new ConstantMessage<>(RECORD_CONTAINER_NAME));
    public static final ContainerField FIELD_CLASS_NAME = new ContainerField("ClassName", 1, new ClassNameMessage<>());
    public static final ContainerField FIELD_SIZE = new ContainerField("Size", 2, new SizeMessage<>());
    public static final ContainerField FIELD_CATEGORY = new ContainerField("Category", 3, new CategoryMessage<>());
    public static final ContainerField FIELD_RECORD_ID = new ContainerField("RecordId", 4, new RecordIdMessage<>(null, null));

    @SuppressWarnings("StaticCollection")
    public static final List<ContainerField> CONTAINER_FIELDS = List.of(
            FIELD_RECORD_CONTAINER_NAME,
            FIELD_CLASS_NAME,
            FIELD_SIZE,
            FIELD_CATEGORY,
            FIELD_RECORD_ID
    );

    public RecordContainerLarge() {
    }

    @Override
    public TextRecord pack(TextRecord originalTextRecord) {
        return RecordContainers.pack(originalTextRecord, CONTAINER_FIELDS, null, null);
    }

    @Override
    public UnpackResult<? extends TextRecord> unpack(TextRecord packedTextRecord) {
        String errorMessage = null;

        String containerClassName = FIELD_RECORD_CONTAINER_NAME.getText(packedTextRecord);

        if (!RECORD_CONTAINER_NAME.equals(containerClassName)) {
            errorMessage = "Wrong container class name! " + containerClassName;
        } else {
            String className = FIELD_CLASS_NAME.getText(packedTextRecord);
            String category = FIELD_CATEGORY.getText(packedTextRecord);
            String recordIdString = FIELD_RECORD_ID.getText(packedTextRecord);
            String sizeString = FIELD_SIZE.getText(packedTextRecord);

            Long recordId = null;
            if (recordIdString != null && !recordIdString.isBlank()) {
                try {
                    recordId = Long.valueOf(recordIdString);
                } catch (NumberFormatException e) {
                    errorMessage = "RecordId has wrong format! " + recordIdString;
                }
            }

            Integer size = null;
            if (sizeString != null && !sizeString.isBlank()) {
                try {
                    size = Integer.valueOf(sizeString);
                    if (packedTextRecord.size() != CONTAINER_FIELDS.size() + size) {
                        errorMessage = "Wrong size! " + packedTextRecord.size();
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Size has wrong format! " + size;
                }
            } else {
                errorMessage = "Size is null or blank!";
            }

            if (errorMessage == null) {
                return RecordContainers.unpack(packedTextRecord, className, CONTAINER_FIELDS.size(), category, recordId);
            }
        }

        return new UnpackResult<>(Optional.empty(), Optional.of(errorMessage));
    }

}
