package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.record.ValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueEqualsFilter implements RecordFilter<ValueRecord> {

    private final String value;

    public ValueEqualsFilter(String value) {
        this.value = value;
    }

    @Override
    public boolean isValid(ValueRecord record) {
        return Objects.equals(record.getValueOfValueField(), value);
    }

}
