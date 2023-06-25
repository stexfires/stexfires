package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import static java.util.Comparator.comparing;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link String} values.
 *
 * @see java.util.Comparator
 * @see java.text.Collator
 * @since 0.1
 */
public final class StringComparators {

    private StringComparators() {
    }

    public static Comparator<String> compareTo() {
        return String::compareTo;
    }

    public static Comparator<String> compareToIgnoreCase() {
        // String.CASE_INSENSITIVE_ORDER
        return String::compareToIgnoreCase;
    }

    public static Comparator<String> collatorComparator(Collator collator) {
        Objects.requireNonNull(collator);
        return collator::compare;
    }

    public static Comparator<String> collatorComparator(Locale locale) {
        Objects.requireNonNull(locale);
        Collator collator = Collator.getInstance(locale);
        return collator::compare;
    }

    public static Comparator<String> collatorComparator(Locale locale,
                                                        int collatorStrength) {
        Objects.requireNonNull(locale);
        Collator collator = Collator.getInstance(locale);
        collator.setStrength(collatorStrength);
        return collator::compare;
    }

    public static Comparator<String> normalizedComparator(UnaryOperator<String> normalizeFunction,
                                                          Comparator<String> comparator) {
        Objects.requireNonNull(normalizeFunction);
        Objects.requireNonNull(comparator);
        return comparing(normalizeFunction, comparator);
    }

    public static Comparator<String> primitiveIntComparator(int nullOrEmptyValue, int notParsableValue) {
        ToIntFunction<String> extractor = s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return Comparator.comparingInt(extractor);
    }

    public static Comparator<String> primitiveLongComparator(long nullOrEmptyValue, long notParsableValue) {
        ToLongFunction<String> extractor = s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return Comparator.comparingLong(extractor);
    }

    public static Comparator<String> primitiveDoubleComparator(double nullOrEmptyValue, double notParsableValue) {
        ToDoubleFunction<String> extractor = s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return Comparator.comparingDouble(extractor);
    }

    public static Comparator<String> integerComparator(@Nullable Integer nullOrEmptyValue,
                                                       @Nullable Integer notParsableValue,
                                                       Comparator<Integer> keyComparator) {
        return extractorComparator(s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        }, keyComparator);
    }

    public static Comparator<String> longComparator(@Nullable Long nullOrEmptyValue,
                                                    @Nullable Long notParsableValue,
                                                    Comparator<Long> keyComparator) {
        return extractorComparator(s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        }, keyComparator);
    }

    public static Comparator<String> doubleComparator(@Nullable Double nullOrEmptyValue,
                                                      @Nullable Double notParsableValue,
                                                      Comparator<Double> keyComparator) {
        return extractorComparator(s -> {
            if (s == null || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        }, keyComparator);
    }

    public static <T> Comparator<String> extractorComparator(Function<String, T> keyExtractor,
                                                             Comparator<T> keyComparator) {
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(keyComparator);
        return Comparator.comparing(keyExtractor, keyComparator);
    }

}
