package stexfires.record.filter;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ConstantFilter<T extends TextRecord> implements RecordFilter<T> {

    private final boolean constantValidity;

    public ConstantFilter(boolean constantValidity) {
        this.constantValidity = constantValidity;
    }

    @Override
    public final boolean isValid(T record) {
        return constantValidity;
    }

}
