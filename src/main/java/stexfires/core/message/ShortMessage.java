package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ShortMessage<T extends Record> implements RecordMessage<T> {

    public ShortMessage() {
    }

    @Override
    public String createMessage(T record) {
        return record.getClass().getSimpleName()
                + "[" + record.size()
                + (record.hasCategory() ? ", '" + record.getCategory() + "'" : "")
                + (record.hasRecordId() ? ", #" + record.getRecordId() : "")
                + "]";
    }

}
