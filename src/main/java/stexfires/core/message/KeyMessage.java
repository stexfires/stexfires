package stexfires.core.message;

import stexfires.core.record.KeyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyMessage<T extends KeyRecord> implements RecordMessage<T> {

    @Override
    public String createMessage(T record) {
        return record.getValueOfKeyField();
    }

}
