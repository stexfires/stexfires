package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueAtFilter implements RecordFilter<Record> {

    protected final int index;
    protected final Predicate<String> stringPredicate;

    public ValueAtFilter(int index, StringComparisonType stringComparisonType, String compareValue) {
        this(index, stringComparisonType.stringPredicate(compareValue));
    }

    public ValueAtFilter(int index, StringCheckType stringCheckType) {
        this(index, stringCheckType.stringPredicate());
    }

    public ValueAtFilter(int index, Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        this.index = index;
        this.stringPredicate = stringPredicate;
    }

    @Override
    public boolean isValid(Record record) {
        return stringPredicate.test(record.getValueAt(index));
    }

}
