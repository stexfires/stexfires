package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeRangeFilter implements RecordFilter<Record> {

    private final int minSize;
    private final int maxSize;

    public SizeRangeFilter(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public boolean isValid(Record record) {
        return (record.size() >= minSize) && (record.size() <= maxSize);
    }

}
