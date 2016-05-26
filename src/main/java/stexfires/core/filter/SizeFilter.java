package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;

import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeFilter implements RecordFilter<Record> {

    protected final IntPredicate intPredicate;

    public SizeFilter(int equalSize) {
        this(NumberComparisonType.intPredicate(NumberComparisonType.EQUAL_TO, equalSize));
    }

    public SizeFilter(NumberComparisonType numberComparisonType, int compareSize) {
        this(numberComparisonType.intPredicate(compareSize));
    }

    public SizeFilter(NumberCheckType numberCheckType) {
        this(numberCheckType.intPredicate());
    }

    public SizeFilter(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        this.intPredicate = intPredicate;
    }

    public static RecordFilter<Record> getSizeBetweenFilter(int from, int to) {
        return new SizeFilter(NumberComparisonType.GREATER_THAN_OR_EQUAL_TO, from).and(new SizeFilter(NumberComparisonType.LESS_THAN, to));
    }

    @Override
    public boolean isValid(Record record) {
        return intPredicate.test(record.size());
    }

}
