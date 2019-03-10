package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntPredicate;

import static stexfires.util.NumberComparisonType.EQUAL_TO;
import static stexfires.util.NumberComparisonType.GREATER_THAN_OR_EQUAL_TO;
import static stexfires.util.NumberComparisonType.LESS_THAN;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeFilter<T extends Record> implements RecordFilter<T> {

    private final IntPredicate sizePredicate;

    public SizeFilter(IntPredicate sizePredicate) {
        Objects.requireNonNull(sizePredicate);
        this.sizePredicate = sizePredicate;
    }

    public static <T extends Record> SizeFilter<T> compare(NumberComparisonType numberComparisonType,
                                                           int compareSize) {
        return new SizeFilter<>(numberComparisonType.intPredicate(compareSize));
    }

    public static <T extends Record> SizeFilter<T> check(NumberCheckType numberCheckType) {
        return new SizeFilter<>(numberCheckType.intPredicate());
    }

    public static <T extends Record> SizeFilter<T> equalTo(int compareSize) {
        return compare(EQUAL_TO, compareSize);
    }

    public static <T extends Record> SizeFilter<T> isEmpty() {
        return compare(EQUAL_TO, 0);
    }

    public static <T extends Record> SizeFilter<T> containedIn(Collection<Integer> sizes) {
        return new SizeFilter<>(sizes::contains);
    }

    public static <T extends Record> SizeFilter<T> containedIn(Integer... sizes) {
        return containedIn(Arrays.asList(sizes));
    }

    public static <T extends Record> SizeFilter<T> between(int from, int to) {
        return new SizeFilter<>(
                GREATER_THAN_OR_EQUAL_TO.intPredicate(from)
                                        .and(LESS_THAN.intPredicate(to)));
    }

    @Override
    public final boolean isValid(T record) {
        return sizePredicate.test(record.size());
    }

}
