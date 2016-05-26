package stexfires.core.message;

import stexfires.core.record.ValueRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMessage implements RecordMessage<ValueRecord> {

    @Override
    public String createMessage(ValueRecord record) {
        return record.getValueOfValueField();
    }

}
