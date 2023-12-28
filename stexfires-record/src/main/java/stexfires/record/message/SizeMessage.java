package stexfires.record.message;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class SizeMessage<T extends TextRecord> implements RecordMessage<T> {

    public SizeMessage() {
    }

    @Override
    public final String createMessage(T record) {
        return String.valueOf(record.size());
    }

}
