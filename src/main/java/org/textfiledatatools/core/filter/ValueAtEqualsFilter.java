package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueAtEqualsFilter implements RecordFilter<Record> {

    protected final int index;
    protected final String compareValue;
    protected final String defaultValue;

    public ValueAtEqualsFilter(int index,
                               String compareValue) {
        this(index, compareValue, null);
    }

    public ValueAtEqualsFilter(int index,
                               String compareValue,
                               String defaultValue) {
        this.index = index;
        this.compareValue = compareValue;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isValid(Record record) {
        return Objects.equals(record.isValidIndex(index) ? record.getValueAt(index) : defaultValue, compareValue);
    }

}
