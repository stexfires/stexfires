package stexfires.core.message;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueAtIndexMessage<T extends Record> implements RecordMessage<T> {

    protected final int index;

    public ValueAtIndexMessage(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Illegal index! index=" + index);
        }
        this.index = index;
    }

    @Override
    public String createMessage(T record) {
        return record.getValueAt(index);
    }

}
