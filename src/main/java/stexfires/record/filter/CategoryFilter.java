package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.function.StringPredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

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

    public static <T extends TextRecord> CategoryFilter<T> equalTo(String compareCategory) {
        return new CategoryFilter<>(StringPredicates.equals(compareCategory));
    }

    public static <T extends TextRecord> CategoryFilter<T> isNotNull() {
        return new CategoryFilter<>(StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> CategoryFilter<T> isNull() {
        return new CategoryFilter<>(StringPredicates.isNull());
    }

    public static <T extends TextRecord> CategoryFilter<T> containedIn(Collection<String> categories) {
        return new CategoryFilter<>(categories::contains);
    }

    public static <T extends TextRecord> CategoryFilter<T> containedIn(String... categories) {
        return containedIn(Arrays.asList(categories));
    }

    @Override
    public final boolean isValid(T record) {
        return categoryPredicate.test(record.category());
    }

}
