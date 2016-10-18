package stexfires.core.filter;

import stexfires.core.record.KeyRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyEqualsFilter<T extends KeyRecord> implements RecordFilter<T> {

    protected final String key;

    public KeyEqualsFilter(String key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    @Override
    public boolean isValid(T record) {
        return key.equals(record.getValueOfKeyField());
    }

}
