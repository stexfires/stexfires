package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.List;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordContainerLarge implements RecordContainer {

    public static final String RECORD_CONTAINER_NAME = "RecordContainerLarge";
    public static final ContainerField FIELD_RECORD_CONTAINER_NAME = new ContainerField("RecordContainerName", 0, record -> RECORD_CONTAINER_NAME);
    public static final ContainerField FIELD_CLASS_NAME = new ContainerField("ClassName", 1, record -> record.getClass().getName());
    public static final ContainerField FIELD_SIZE = new ContainerField("Size", 2, record -> String.valueOf(record.size()));
    public static final ContainerField FIELD_CATEGORY = new ContainerField("Category", 3, TextRecord::category);
    @SuppressWarnings({"ReturnOfNull", "DataFlowIssue"})
    public static final ContainerField FIELD_RECORD_ID = new ContainerField("RecordId", 4, record -> record.hasRecordId() ? record.recordId().toString() : null);

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

        String containerClassName = packedTextRecord.textAt(FIELD_RECORD_CONTAINER_NAME.index());

        if (!RECORD_CONTAINER_NAME.equals(containerClassName)) {
            errorMessage = "Wrong container class name! " + containerClassName;
        } else {
            String className = packedTextRecord.textAt(FIELD_CLASS_NAME.index());
            String category = packedTextRecord.textAt(FIELD_CATEGORY.index());
            String recordIdString = packedTextRecord.textAt(FIELD_RECORD_ID.index());
            String sizeString = packedTextRecord.textAt(FIELD_SIZE.index());

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
