package stexfires.record.filter;

import stexfires.record.Field;
import stexfires.record.TextRecord;
import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static stexfires.util.StringCheckType.NOT_NULL;
import static stexfires.util.StringComparisonType.EQUALS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class TextFilter<T extends TextRecord> implements RecordFilter<T> {

    public static final boolean DEFAULT_NULL_FIELD_VALIDITY = false;

    private final Function<? super T, Field> fieldFunction;
    private final boolean nullFieldValidity;
    private final Predicate<String> textPredicate;

    public TextFilter(int index,
                      Predicate<String> textPredicate) {
        this(record -> record.fieldAt(index), DEFAULT_NULL_FIELD_VALIDITY, textPredicate);
    }

    public TextFilter(int index,
                      boolean nullFieldValidity,
                      Predicate<String> textPredicate) {
        this(record -> record.fieldAt(index), nullFieldValidity, textPredicate);
    }

    public TextFilter(Function<? super T, Field> fieldFunction,
                      Predicate<String> textPredicate) {
        this(fieldFunction, DEFAULT_NULL_FIELD_VALIDITY, textPredicate);
    }

    public TextFilter(Function<? super T, Field> fieldFunction,
                      boolean nullFieldValidity,
                      Predicate<String> textPredicate) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(textPredicate);
        this.fieldFunction = fieldFunction;
        this.nullFieldValidity = nullFieldValidity;
        this.textPredicate = textPredicate;
    }

    public static <T extends TextRecord> TextFilter<T> compare(int index,
                                                               StringComparisonType stringComparisonType,
                                                               String compareText) {
        return new TextFilter<>(index,
                stringComparisonType.stringPredicate(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> compare(Function<? super T, Field> fieldFunction,
                                                               StringComparisonType stringComparisonType,
                                                               String compareText) {
        return new TextFilter<>(fieldFunction,
                stringComparisonType.stringPredicate(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> check(int index,
                                                             StringCheckType stringCheckType) {
        return new TextFilter<>(index,
                stringCheckType.stringPredicate());
    }

    public static <T extends TextRecord> TextFilter<T> check(Function<? super T, Field> fieldFunction,
                                                             StringCheckType stringCheckType) {
        return new TextFilter<>(fieldFunction,
                stringCheckType.stringPredicate());
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(int index,
                                                               String compareText) {
        return compare(index, EQUALS, compareText);
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(Function<? super T, Field> fieldFunction,
                                                               String compareText) {
        return compare(fieldFunction, EQUALS, compareText);
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(int index) {
        return check(index, NOT_NULL);
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(Function<? super T, Field> fieldFunction) {
        return check(fieldFunction, NOT_NULL);
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   Collection<String> texts) {
        return new TextFilter<>(index, texts::contains);
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, Field> fieldFunction,
                                                                   Collection<String> texts) {
        return new TextFilter<>(fieldFunction, texts::contains);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   String... texts) {
        return containedIn(index, Arrays.asList(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, Field> fieldFunction,
                                                                   String... texts) {
        return containedIn(fieldFunction, Arrays.asList(texts));
    }

    @Override
    public final boolean isValid(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldValidity;
        }
        return textPredicate.test(field.text());
    }

}
