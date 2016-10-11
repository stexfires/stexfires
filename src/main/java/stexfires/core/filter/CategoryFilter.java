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
public class CategoryFilter implements RecordFilter<Record> {

    protected final Predicate<String> stringPredicate;

    public CategoryFilter(String equalCategory) {
        this(StringComparisonType.stringPredicate(StringComparisonType.EQUALS, equalCategory));
    }

    public CategoryFilter(StringComparisonType stringComparisonType, String compareCategory) {
        this(stringComparisonType.stringPredicate(compareCategory));
    }

    public CategoryFilter(StringCheckType stringCheckType) {
        this(stringCheckType.stringPredicate());
    }

    public CategoryFilter(Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        this.stringPredicate = stringPredicate;
    }

    public static RecordFilter<Record> notNull() {
        return new CategoryFilter(StringCheckType.NOT_NULL);
    }

    @Override
    public boolean isValid(Record record) {
        return stringPredicate.test(record.getCategory());
    }

}
