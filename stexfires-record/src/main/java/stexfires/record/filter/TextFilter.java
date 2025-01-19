package stexfires.record.filter;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.util.function.StringPredicates;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class TextFilter<T extends TextRecord> implements RecordFilter<T> {

    public static final boolean DEFAULT_NULL_FIELD_VALIDITY = false;

    private final Function<? super T, @Nullable TextField> fieldFunction;
    private final boolean nullFieldValidity;
    private final Predicate<@Nullable String> textPredicate;

    public TextFilter(int index,
                      Predicate<@Nullable String> textPredicate) {
        this(record -> record.fieldAt(index), DEFAULT_NULL_FIELD_VALIDITY, textPredicate);
    }

    public TextFilter(int index,
                      boolean nullFieldValidity,
                      Predicate<@Nullable String> textPredicate) {
        this(record -> record.fieldAt(index), nullFieldValidity, textPredicate);
    }

    public TextFilter(Function<? super T, TextField> fieldFunction,
                      Predicate<@Nullable String> textPredicate) {
        this(fieldFunction, DEFAULT_NULL_FIELD_VALIDITY, textPredicate);
    }

    public TextFilter(Function<? super T, @Nullable TextField> fieldFunction,
                      boolean nullFieldValidity,
                      Predicate<@Nullable String> textPredicate) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(textPredicate);
        this.fieldFunction = fieldFunction;
        this.nullFieldValidity = nullFieldValidity;
        this.textPredicate = textPredicate;
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(int index,
                                                               @Nullable String compareText) {
        return new TextFilter<>(index, StringPredicates.equals(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> equalTo(Function<? super T, @Nullable TextField> fieldFunction,
                                                               @Nullable String compareText) {
        return new TextFilter<>(fieldFunction, StringPredicates.equals(compareText));
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(int index) {
        return new TextFilter<>(index, StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> TextFilter<T> isNotNull(Function<? super T, @Nullable TextField> fieldFunction) {
        return new TextFilter<>(fieldFunction, StringPredicates.isNotNull());
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   Collection<String> texts) {
        Objects.requireNonNull(texts);
        return new TextFilter<>(index, texts::contains);
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, @Nullable TextField> fieldFunction,
                                                                   Collection<String> texts) {
        Objects.requireNonNull(texts);
        return new TextFilter<>(fieldFunction, texts::contains);
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(int index,
                                                                   String... texts) {
        Objects.requireNonNull(texts);
        return containedIn(index, Arrays.asList(texts));
    }

    public static <T extends TextRecord> TextFilter<T> containedIn(Function<? super T, @Nullable TextField> fieldFunction,
                                                                   String... texts) {
        Objects.requireNonNull(texts);
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
