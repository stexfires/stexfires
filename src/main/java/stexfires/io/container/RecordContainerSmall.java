package stexfires.io.container;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.message.ClassNameMessage;

import java.util.List;

/**
 * @since 0.1
 */
public final class RecordContainerSmall implements RecordContainer {

    public static final ContainerField FIELD_CLASS_NAME = new ContainerField("ClassName", 0, new ClassNameMessage<>());

    @SuppressWarnings("StaticCollection")
    public static final List<ContainerField> CONTAINER_FIELDS = List.of(
            FIELD_CLASS_NAME
    );

    public RecordContainerSmall() {
    }

    @Override
    public @NotNull TextRecord pack(@NotNull TextRecord originalTextRecord) {
        return RecordContainers.pack(originalTextRecord, CONTAINER_FIELDS, null, null);
    }

    @Override
    public @NotNull UnpackResult unpack(@NotNull TextRecord packedTextRecord) {
        String className = FIELD_CLASS_NAME.getText(packedTextRecord);
        return RecordContainers.unpack(packedTextRecord, className, CONTAINER_FIELDS.size(), null, null);
    }

}
