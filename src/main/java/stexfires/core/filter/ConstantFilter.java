package stexfires.core.filter;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFilter implements RecordFilter<Record> {

    protected final boolean constantValidity;

    public ConstantFilter(boolean constantValidity) {
        this.constantValidity = constantValidity;
    }

    @Override
    public boolean isValid(Record record) {
        return constantValidity;
    }

}
