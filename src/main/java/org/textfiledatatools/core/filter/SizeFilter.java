package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.util.NumberComparisonType;

import java.util.function.IntPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeFilter implements RecordFilter<Record> {

    protected final IntPredicate intPredicate;

    public SizeFilter(int compareSize) {
        this(NumberComparisonType.intPredicate(NumberComparisonType.EQUAL_TO, compareSize));
    }

    public SizeFilter(NumberComparisonType numberComparisonType, int compareSize) {
        this(numberComparisonType.intPredicate(compareSize));
    }

    public SizeFilter(IntPredicate intPredicate) {
        this.intPredicate = intPredicate;
    }

    public static SizeFilter equalTo(int size) {
        return new SizeFilter(NumberComparisonType.EQUAL_TO, size);
    }

    public static SizeFilter notEqualTo(int size) {
        return new SizeFilter(NumberComparisonType.NOT_EQUAL_TO, size);
    }

    public static SizeFilter lessThan(int size) {
        return new SizeFilter(NumberComparisonType.LESS_THAN, size);
    }

    public static SizeFilter lessThanOrEqualTo(int size) {
        return new SizeFilter(NumberComparisonType.LESS_THAN_OR_EQUAL_TO, size);
    }

    public static SizeFilter greaterThanOrEqualTo(int size) {
        return new SizeFilter(NumberComparisonType.GREATER_THAN_OR_EQUAL_TO, size);
    }

    public static SizeFilter greaterThan(int size) {
        return new SizeFilter(NumberComparisonType.GREATER_THAN, size);
    }

    @Override
    public boolean isValid(Record record) {
        return intPredicate.test(record.size());
    }

}
