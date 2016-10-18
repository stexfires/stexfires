package stexfires.core.filter;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ClassFilter<T extends Record> implements RecordFilter<T> {

    protected final Class<? extends Record> recordClass;

    public ClassFilter(Class<? extends Record> recordClass) {
        Objects.requireNonNull(recordClass);
        this.recordClass = recordClass;
    }

    @Override
    public boolean isValid(T record) {
        return recordClass.equals(record.getClass());
    }

}
