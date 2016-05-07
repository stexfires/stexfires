package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.record.KeyRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyEqualsFilter implements RecordFilter<KeyRecord> {

    protected final String key;

    public KeyEqualsFilter(String key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    @Override
    public boolean isValid(KeyRecord record) {
        return key.equals(record.getValueOfKeyField());
    }

}
