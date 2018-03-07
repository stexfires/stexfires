package stexfires.core.filter;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFilter<T extends Record> implements RecordFilter<T> {

    protected final boolean constantValidity;

    public ConstantFilter(boolean constantValidity) {
        this.constantValidity = constantValidity;
    }

    @Override
    public final boolean isValid(T record) {
        return constantValidity;
    }

}
