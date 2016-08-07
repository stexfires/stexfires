package stexfires.core.filter;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueNotNullFilter implements RecordFilter<Record> {

    protected final int index;

    public ValueNotNullFilter(int index) {
        this.index = index;
    }

    @Override
    public boolean isValid(Record record) {
        return record.getValueAt(index) != null;
    }

}
