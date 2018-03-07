package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToStringMessage<T extends Record> implements RecordMessage<T> {

    public ToStringMessage() {
    }

    @Override
    public final String createMessage(T record) {
        return record.toString();
    }

}
