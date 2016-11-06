package stexfires.core.filter;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueFilter<T extends Record> implements RecordFilter<T> {

    protected final Function<T, Field> fieldFunction;
    protected final Predicate<String> valuePredicate;

    public ValueFilter(int index,
                       Predicate<String> valuePredicate) {
        this(record -> record.getFieldAt(index), valuePredicate);
    }

    public ValueFilter(Function<T, Field> fieldFunction,
                       Predicate<String> valuePredicate) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(valuePredicate);
        this.fieldFunction = fieldFunction;
        this.valuePredicate = valuePredicate;
    }

    public static <T extends Record> ValueFilter<T> compare(int index,
                                                            StringComparisonType stringComparisonType,
                                                            String compareValue) {
        return new ValueFilter<>(index,
                stringComparisonType.stringPredicate(compareValue));
    }

    public static <T extends Record> ValueFilter<T> compare(Function<T, Field> fieldFunction,
                                                            StringComparisonType stringComparisonType,
                                                            String compareValue) {
        return new ValueFilter<>(fieldFunction,
                stringComparisonType.stringPredicate(compareValue));
    }

    public static <T extends Record> ValueFilter<T> check(int index,
                                                          StringCheckType stringCheckType) {
        return new ValueFilter<>(index,
                stringCheckType.stringPredicate());
    }

    public static <T extends Record> ValueFilter<T> check(Function<T, Field> fieldFunction,
                                                          StringCheckType stringCheckType) {
        return new ValueFilter<>(fieldFunction,
                stringCheckType.stringPredicate());
    }

    @Override
    public boolean isValid(T record) {
        return valuePredicate.test(fieldFunction.apply(record).getValue());
    }

}
