package stexfires.record.filter;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class NotNullFilter<T extends TextRecord> implements RecordFilter<T> {

    public NotNullFilter() {
    }

    @Override
    public final boolean isValid(@Nullable T record) {
        return record != null;
    }

}
