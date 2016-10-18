package stexfires.core.message;

import stexfires.core.record.ValueRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMessage<T extends ValueRecord> implements RecordMessage<T> {

    @Override
    public String createMessage(T record) {
        return record.getValueOfValueField();
    }

}
