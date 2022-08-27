package stexfires.record.filter;

import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IsValidIndexFilter<T extends TextRecord> implements RecordFilter<T> {

    private final int index;

    public IsValidIndexFilter(int index) {
        this.index = index;
    }

    @Override
    public final boolean isValid(T record) {
        return record.isValidIndex(index);
    }

}
