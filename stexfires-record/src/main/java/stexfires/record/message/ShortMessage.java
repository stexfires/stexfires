package stexfires.record.message;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ShortMessage<T extends TextRecord> implements NotNullRecordMessage<T> {

    public ShortMessage() {
    }

    @Override
    public final String createMessage(T record) {
        return record.getClass().getSimpleName()
                + "[" + record.size()
                + (record.hasCategory() ? ", '" + record.category() + "'" : "")
                + (record.hasRecordId() ? ", #" + record.recordId() : "")
                + "]";
    }

}
