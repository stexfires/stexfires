package stexfires.record.filter;

import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.util.function.StringPredicates;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class TextFilter<T extends TextRecord> implements RecordFilter<T> {

    public static final boolean DEFAULT_NULL_FIELD_VALIDITY = false;

    private final Function<? super T, TextField> fieldFunction;
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

    public TextFilter(Function<? super T, TextField> fieldFunction,
                      Predicate<String> textPredicate) {
        this(fieldFunction, DEFAULT_NULL_FIELD_VALIDITY, textPredicate);
    }

    public TextFilter(Function<? super T, TextField> fieldFunction,
                      boolean nullFieldValidity,
                      Predicate<String> textPredicate) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(textPredicate);
        this.fieldFunction = fieldFunction;
        this.nullFieldValidity = nullFieldValidity;
        this.textPredicate = textPredicate;
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(int index,
                                                               String compareText) {
        return new TextFilter<>(index, StringPredicates.equals(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(Function<? super T, TextField> fieldFunction,
                                                               String compareText) {
        return new TextFilter<>(fieldFunction, StringPredicates.equals(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(int index) {
        return new TextFilter<>(index, StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(Function<? super T, TextField> fieldFunction) {
        return new TextFilter<>(fieldFunction, StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   Collection<String> texts) {
        return new TextFilter<>(index, texts::contains);
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, TextField> fieldFunction,
                                                                   Collection<String> texts) {
        return new TextFilter<>(fieldFunction, texts::contains);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   String... texts) {
        return containedIn(index, Arrays.asList(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, TextField> fieldFunction,
                                                                   String... texts) {
        return containedIn(fieldFunction, Arrays.asList(texts));
    }

    @Override
    public final boolean isValid(T record) {
        TextField field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldValidity;
        }
        return textPredicate.test(field.text());
    }

}
