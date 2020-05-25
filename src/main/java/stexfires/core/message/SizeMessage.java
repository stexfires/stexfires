package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeMessage<T extends Record> implements RecordMessage<T> {

    public SizeMessage() {
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return String.valueOf(record.size());
    }

}
