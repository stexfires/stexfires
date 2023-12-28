package stexfires.record.message;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ToStringMessage<T extends TextRecord> implements RecordMessage<T> {

    public ToStringMessage() {
    }

    @Override
    public final String createMessage(T record) {
        return record.toString();
    }

}
