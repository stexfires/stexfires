package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.function.NumberPredicates.PrimitiveIntPredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SizeFilter<T extends TextRecord> implements RecordFilter<T> {

    private final IntPredicate sizePredicate;

    public SizeFilter(IntPredicate sizePredicate) {
        Objects.requireNonNull(sizePredicate);
        this.sizePredicate = sizePredicate;
    }

    public static <T extends TextRecord> SizeFilter<T> equalTo(int compareSize) {
        return new SizeFilter<>(PrimitiveIntPredicates.equalTo(compareSize));
    }

    public static <T extends TextRecord> SizeFilter<T> isEmpty() {
        return new SizeFilter<>(PrimitiveIntPredicates.zero());
    }

    public static <T extends TextRecord> SizeFilter<T> isNotEmpty() {
        return new SizeFilter<>(PrimitiveIntPredicates.positive());
    }

    public static <T extends TextRecord> SizeFilter<T> containedIn(Collection<Integer> sizes) {
        return new SizeFilter<>(PrimitiveIntPredicates.containedIn(sizes));
    }

    public static <T extends TextRecord> SizeFilter<T> containedIn(Integer... sizes) {
        return containedIn(Arrays.asList(sizes));
    }

    public static <T extends TextRecord> SizeFilter<T> between(int from, int to) {
        return new SizeFilter<>(PrimitiveIntPredicates.between(from, to));
    }

    @Override
    public final boolean isValid(T record) {
        return sizePredicate.test(record.size());
    }

}
