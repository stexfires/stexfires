package stexfires.io.container;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.util.Objects;

/**
 * @since 0.1
 */
public record ContainerField(
        @NotNull String name,
        int index,
        @NotNull RecordMessage<TextRecord> recordMessage) {

    public ContainerField {
        Objects.requireNonNull(name);
        Objects.requireNonNull(recordMessage);
        if (index < 0) {
            throw new IllegalArgumentException("index < 0");
        }
    }

    public @Nullable String createText(TextRecord record) {
        Objects.requireNonNull(record);
        return recordMessage.createMessage(record);
    }

    public @Nullable String getText(TextRecord record) {
        Objects.requireNonNull(record);
        return record.textAt(index);
    }

}
