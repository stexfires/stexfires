package stexfires.record.message;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Objects;

/**
 * @since 0.1
 */
public class ToIdentityStringMessage<T extends TextRecord> implements RecordMessage<T> {

    public ToIdentityStringMessage() {
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return Objects.toIdentityString(record);
    }

}
