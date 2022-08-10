package stexfires.core.filter;

import stexfires.core.TextRecord;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import static stexfires.util.StringCheckType.NOT_NULL;
import static stexfires.util.StringCheckType.NULL;
import static stexfires.util.StringComparisonType.EQUALS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryFilter<T extends TextRecord> implements RecordFilter<T> {

    private final Predicate<String> categoryPredicate;

    public CategoryFilter(Predicate<String> categoryPredicate) {
        Objects.requireNonNull(categoryPredicate);
        this.categoryPredicate = categoryPredicate;
    }

    public static <T extends TextRecord> CategoryFilter<T> compare(StringComparisonType stringComparisonType,
                                                                   String compareCategory) {
        return new CategoryFilter<>(stringComparisonType.stringPredicate(compareCategory));
    }

    public static <T extends TextRecord> CategoryFilter<T> check(StringCheckType stringCheckType) {
        return new CategoryFilter<>(stringCheckType.stringPredicate());
    }

    public static <T extends TextRecord> CategoryFilter<T> equalTo(String compareCategory) {
        return compare(EQUALS, compareCategory);
    }

    public static <T extends TextRecord> CategoryFilter<T> isNotNull() {
        return check(NOT_NULL);
    }

    public static <T extends TextRecord> CategoryFilter<T> isNull() {
        return check(NULL);
    }

    public static <T extends TextRecord> CategoryFilter<T> containedIn(Collection<String> categories) {
        return new CategoryFilter<>(categories::contains);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> CategoryFilter<T> containedIn(String... categories) {
        return containedIn(Arrays.asList(categories));
    }

    @Override
    public final boolean isValid(T record) {
        return categoryPredicate.test(record.category());
    }

}
