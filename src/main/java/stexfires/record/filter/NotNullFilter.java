package stexfires.record.filter;

import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NotNullFilter<T extends TextRecord> implements RecordFilter<T> {

    public NotNullFilter() {
    }

    @Override
    public final boolean isValid(T record) {
        return record != null;
    }

}
