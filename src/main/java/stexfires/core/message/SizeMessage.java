package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeMessage implements RecordMessage<Record> {

    @Override
    public String createMessage(Record record) {
        return String.valueOf(record.size());
    }

}
