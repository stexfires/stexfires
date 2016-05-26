package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ShortMessage implements RecordMessage<Record> {

    @Override
    public String createMessage(Record record) {
        return record.getClass().getSimpleName()
                + "[" + record.size()
                + (record.getCategory() != null ? ", '" + record.getCategory() + "'" : "")
                + (record.getRecordId() != null ? ", #" + record.getRecordId() : "")
                + "]";
    }

}
