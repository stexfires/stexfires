package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.List;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordContainerSmall implements RecordContainer {

    public static final ContainerField FIELD_CLASS_NAME = new ContainerField("ClassName", 0, record -> record.getClass().getName());

    @SuppressWarnings("StaticCollection")
    public static final List<ContainerField> CONTAINER_FIELDS = List.of(
            FIELD_CLASS_NAME
    );

    public RecordContainerSmall() {
    }

    @Override
    public TextRecord pack(TextRecord originalTextRecord) {
        return RecordContainers.pack(originalTextRecord, CONTAINER_FIELDS, null, null);
    }

    @Override
    public UnpackResult<? extends TextRecord> unpack(TextRecord packedTextRecord) {
        String className = packedTextRecord.textAt(FIELD_CLASS_NAME.index());
        return RecordContainers.unpack(packedTextRecord, className, CONTAINER_FIELDS.size(), null, null);
    }

}
