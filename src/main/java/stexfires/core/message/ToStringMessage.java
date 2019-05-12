package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToStringMessage<T extends Record> implements RecordMessage<T> {

    public ToStringMessage() {
    }

    @Override
    public final @NotNull String createMessage(@NotNull T record) {
        return record.toString();
    }

}
