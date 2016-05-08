package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.util.NumberComparisonType;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdFilter implements RecordFilter<Record> {

    protected final NumberComparisonType numberComparisonType;
    protected final long compareRecordId;

    public RecordIdFilter(NumberComparisonType numberComparisonType, long compareRecordId) {
        this.numberComparisonType = numberComparisonType;
        this.compareRecordId = compareRecordId;
    }

    public static RecordIdFilter equalTo(long recordId) {
        return new RecordIdFilter(NumberComparisonType.EQUAL_TO, recordId);
    }

    public static RecordIdFilter notEqualTo(long recordId) {
        return new RecordIdFilter(NumberComparisonType.NOT_EQUAL_TO, recordId);
    }

    public static RecordIdFilter lessThan(long recordId) {
        return new RecordIdFilter(NumberComparisonType.LESS_THAN, recordId);
    }

    public static RecordIdFilter lessThanOrEqualTo(long recordId) {
        return new RecordIdFilter(NumberComparisonType.LESS_THAN_OR_EQUAL_TO, recordId);
    }

    public static RecordIdFilter greaterThanOrEqualTo(long recordId) {
        return new RecordIdFilter(NumberComparisonType.GREATER_THAN_OR_EQUAL_TO, recordId);
    }

    public static RecordIdFilter greaterThan(long recordId) {
        return new RecordIdFilter(NumberComparisonType.GREATER_THAN, recordId);
    }

    @Override
    public boolean isValid(Record record) {
        return (record.getRecordId() != null)
                && NumberComparisonType.compareLong(record.getRecordId(), numberComparisonType, compareRecordId);
    }

}
