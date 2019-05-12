package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ShortMessage<T extends Record> implements RecordMessage<T> {

    public ShortMessage() {
    }

    @Override
    public final @NotNull String createMessage(@NotNull T record) {
        return record.getClass().getSimpleName()
                + "[" + record.size()
                + (record.hasCategory() ? ", '" + record.getCategory() + "'" : "")
                + (record.hasRecordId() ? ", #" + record.getRecordId() : "")
                + "]";
    }

}
