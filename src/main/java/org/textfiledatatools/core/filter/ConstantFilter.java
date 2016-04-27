package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFilter implements RecordFilter<Record> {

    private final boolean isValid;

    public ConstantFilter(boolean isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean isValid(Record record) {
        return isValid;
    }

}
