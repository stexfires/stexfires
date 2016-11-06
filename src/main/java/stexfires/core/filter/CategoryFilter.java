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
public class CategoryFilter<T extends Record> implements RecordFilter<T> {

    protected final Predicate<String> categoryPredicate;

    public CategoryFilter(Predicate<String> categoryPredicate) {
        Objects.requireNonNull(categoryPredicate);
        this.categoryPredicate = categoryPredicate;
    }

    public static <T extends Record> CategoryFilter<T> compare(StringComparisonType stringComparisonType,
                                                               String compareCategory) {
        return new CategoryFilter<>(stringComparisonType.stringPredicate(compareCategory));
    }

    public static <T extends Record> CategoryFilter<T> check(StringCheckType stringCheckType) {
        return new CategoryFilter<>(stringCheckType.stringPredicate());
    }

    public static <T extends Record> CategoryFilter<T> equalTo(String compareCategory) {
        return new CategoryFilter<>(StringComparisonType.EQUALS.stringPredicate(compareCategory));
    }

    public static <T extends Record> CategoryFilter<T> notNull() {
        return new CategoryFilter<>(StringCheckType.NOT_NULL.stringPredicate());
    }

    @Override
    public boolean isValid(T record) {
        return categoryPredicate.test(record.getCategory());
    }

}
