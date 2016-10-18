package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeMessage<T extends Record> implements RecordMessage<T> {

    @Override
    public String createMessage(T record) {
        return String.valueOf(record.size());
    }

}
