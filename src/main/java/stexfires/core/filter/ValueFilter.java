package stexfires.core.filter;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueFilter<T extends Record> implements RecordFilter<T> {

    public static final boolean DEFAULT_NULL_FIELD_VALIDITY = false;

    protected final Function<? super T, Field> fieldFunction;
    protected final boolean nullFieldValidity;
    protected final Predicate<String> valuePredicate;

    public ValueFilter(int index,
                       Predicate<String> valuePredicate) {
        this(record -> record.getFieldAt(index), DEFAULT_NULL_FIELD_VALIDITY, valuePredicate);
    }

    public ValueFilter(int index,
                       boolean nullFieldValidity,
                       Predicate<String> valuePredicate) {
        this(record -> record.getFieldAt(index), nullFieldValidity, valuePredicate);
    }

    public ValueFilter(Function<? super T, Field> fieldFunction,
                       Predicate<String> valuePredicate) {
        this(fieldFunction, DEFAULT_NULL_FIELD_VALIDITY, valuePredicate);
    }

    public ValueFilter(Function<? super T, Field> fieldFunction,
                       boolean nullFieldValidity,
                       Predicate<String> valuePredicate) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(valuePredicate);
        this.fieldFunction = fieldFunction;
        this.nullFieldValidity = nullFieldValidity;
        this.valuePredicate = valuePredicate;
    }

    public static <T extends Record> ValueFilter<T> compare(int index,
                                                            StringComparisonType stringComparisonType,
                                                            String compareValue) {
        return new ValueFilter<>(index,
                stringComparisonType.stringPredicate(compareValue));
    }

    public static <T extends Record> ValueFilter<T> compare(Function<? super T, Field> fieldFunction,
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

    public static <T extends Record> ValueFilter<T> check(Function<? super T, Field> fieldFunction,
                                                          StringCheckType stringCheckType) {
        return new ValueFilter<>(fieldFunction,
                stringCheckType.stringPredicate());
    }

    public static <T extends Record> ValueFilter<T> equalTo(int index,
                                                            String compareValue) {
        return new ValueFilter<>(index,
                StringComparisonType.EQUALS.stringPredicate(compareValue));
    }

    public static <T extends Record> ValueFilter<T> equalTo(Function<? super T, Field> fieldFunction,
                                                            String compareValue) {
        return new ValueFilter<>(fieldFunction,
                StringComparisonType.EQUALS.stringPredicate(compareValue));
    }

    public static <T extends Record> ValueFilter<T> notNull(int index) {
        return new ValueFilter<>(index,
                StringCheckType.NOT_NULL.stringPredicate());
    }

    public static <T extends Record> ValueFilter<T> notNull(Function<? super T, Field> fieldFunction) {
        return new ValueFilter<>(fieldFunction,
                StringCheckType.NOT_NULL.stringPredicate());
    }

    public static <T extends Record> ValueFilter<T> containedIn(int index,
                                                                Collection<String> values) {
        return new ValueFilter<>(index, values::contains);
    }

    public static <T extends Record> ValueFilter<T> containedIn(Function<? super T, Field> fieldFunction,
                                                                Collection<String> values) {
        return new ValueFilter<>(fieldFunction, values::contains);
    }

    @Override
    public boolean isValid(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldValidity;
        }
        return valuePredicate.test(field.getValue());
    }

}
