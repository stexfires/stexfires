package stexfires.record.message;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ShortMessage<T extends TextRecord> implements RecordMessage<T> {

    public ShortMessage() {
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return record.getClass().getSimpleName()
                + "[" + record.size()
                + (record.hasCategory() ? ", '" + record.category() + "'" : "")
                + (record.hasRecordId() ? ", #" + record.recordId() : "")
                + "]";
    }

}
