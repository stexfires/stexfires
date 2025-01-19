package stexfires.util;

import org.jspecify.annotations.Nullable;

import java.text.Collator;
import java.util.*;
import java.util.function.*;

import static java.util.Comparator.*;

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

    /**
     * Returns a comparator that compares {@link String} values using {@link String#compareTo(String)}.
     *
     * @return a comparator that compares {@link String} values using {@link String#compareTo(String)}
     * @see String#compareTo(String)
     */
    public static Comparator<String> compareTo() {
        return String::compareTo;
    }

    /**
     * Returns a comparator that compares {@link String} values using {@link String#compareToIgnoreCase(String)}.
     *
     * @return a comparator that compares {@link String} values using {@link String#compareToIgnoreCase(String)}
     * @see String#compareToIgnoreCase(String)
     * @see String#CASE_INSENSITIVE_ORDER
     */
    public static Comparator<String> compareToIgnoreCase() {
        return String::compareToIgnoreCase;
    }

    /**
     * Returns a comparator that compares {@link String} values using a {@link Collator}.
     *
     * @param collator the {@link Collator} for comparing {@link String} values. Must not be {@code null}.
     * @return a comparator that compares {@link String} values using a {@link Collator}
     * @see Collator#compare(String, String)
     */
    public static Comparator<String> collatorComparator(Collator collator) {
        Objects.requireNonNull(collator);
        return collator::compare;
    }

    /**
     * Returns a comparator that compares {@link String} values using a {@link Collator} with the given {@link Locale}.
     *
     * @param locale the {@link Locale} for a new {@link Collator} instance. Must not be {@code null}.
     * @return a comparator that compares {@link String} values using a {@link Collator} with the given {@link Locale}
     * @see Collator#getInstance(Locale)
     * @see Collator#compare(String, String)
     */
    public static Comparator<String> collatorComparator(Locale locale) {
        Objects.requireNonNull(locale);
        Collator collator = Collator.getInstance(locale);
        return collator::compare;
    }

    /**
     * Returns a comparator that compares {@link String} values using a {@link Collator} with the given {@link Locale} and {@link Collator} strength.
     *
     * @param locale           the {@link Locale} for a new {@link Collator} instance. Must not be {@code null}.
     * @param collatorStrength the {@link Collator} strength. Must be one of {@link Collator#PRIMARY}, {@link Collator#SECONDARY}, {@link Collator#TERTIARY}, {@link Collator#IDENTICAL}.
     * @return a comparator that compares {@link String} values using a {@link Collator} with the given {@link Locale} and {@link Collator} strength
     * @see Collator#getInstance(Locale)
     * @see Collator#setStrength(int)
     * @see Collator#compare(String, String)
     */
    public static Comparator<String> collatorComparator(Locale locale,
                                                        int collatorStrength) {
        Objects.requireNonNull(locale);
        Collator collator = Collator.getInstance(locale);
        collator.setStrength(collatorStrength);
        return collator::compare;
    }

    /**
     * Returns a comparator that compares nullable {@link String} values using a {@link UnaryOperator} for normalization
     * and comparing the resulting nullable {@link String} values using {@link stexfires.util.SortNulls} and {@link java.util.Comparator}.
     *
     * @param normalizeFunction the {@link UnaryOperator} for normalization. Must not be {@code null}.
     * @param comparator        the {@link java.util.Comparator} for comparing normalized {@link String} values. Must not be {@code null}.
     * @param sortNulls         the {@link stexfires.util.SortNulls} for comparing {@code null} values. Must not be {@code null}.
     * @return a comparator that compares nullable {@link String} values using a {@link UnaryOperator} for normalization and comparing the resulting nullable {@link String} values using {@link stexfires.util.SortNulls} and {@link java.util.Comparator}
     * @see java.util.Comparator#comparing(Function, Comparator)
     */
    public static Comparator<@Nullable String> normalizedComparator(UnaryOperator<@Nullable String> normalizeFunction,
                                                                    Comparator<String> comparator,
                                                                    SortNulls sortNulls) {
        Objects.requireNonNull(normalizeFunction);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(sortNulls);
        return comparing(normalizeFunction, sortNulls.wrap(comparator));
    }

    /**
     * Returns a comparator that compares {@link String} values by their length.
     *
     * @return a comparator that compares {@link String} values by their length
     * @see String#length()
     * @see Comparator#comparingInt(ToIntFunction)
     */
    public static Comparator<String> lengthComparator() {
        return comparingInt(String::length);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@code int} numbers and then compares them.
     *
     * @param nullOrEmptyValue the {@code int} value that will be used for comparison if the {@link String} is {@code null} or empty
     * @param notParsableValue the {@code int} value that will be used for comparison if the {@link String} does not contain a parsable {@code int} number
     * @return a comparator that converts the {@link String} values to {@code int} numbers and then compares them
     * @see Integer#parseInt(String)
     * @see Comparator#comparingInt(ToIntFunction)
     */
    public static Comparator<@Nullable String> primitiveIntComparator(int nullOrEmptyValue, int notParsableValue) {
        ToIntFunction<@Nullable String> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return comparingInt(extractor);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@code long} numbers and then compares them.
     *
     * @param nullOrEmptyValue the {@code long} value that will be used for comparison if the {@link String} is {@code null} or empty
     * @param notParsableValue the {@code long} value that will be used for comparison if the {@link String} does not contain a parsable {@code long} number
     * @return a comparator that converts the {@link String} values to {@code long} numbers and then compares them
     * @see Long#parseLong(String)
     * @see Comparator#comparingLong(ToLongFunction)
     */
    public static Comparator<@Nullable String> primitiveLongComparator(long nullOrEmptyValue, long notParsableValue) {
        ToLongFunction<@Nullable String> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return comparingLong(extractor);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@code double} numbers and then compares them.
     *
     * @param nullOrEmptyValue the {@code double} value that will be used for comparison if the {@link String} is {@code null} or empty
     * @param notParsableValue the {@code double} value that will be used for comparison if the {@link String} does not contain a parsable {@code double} number
     * @return a comparator that converts the {@link String} values to {@code double} numbers and then compares them
     * @see Double#parseDouble(String)
     * @see Comparator#comparingDouble(ToDoubleFunction)
     */
    public static Comparator<@Nullable String> primitiveDoubleComparator(double nullOrEmptyValue, double notParsableValue) {
        ToDoubleFunction<@Nullable String> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return comparingDouble(extractor);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@link Integer} numbers and then compares them.
     *
     * @param nullOrEmptyValue  the {@link Integer} value that will be used for comparison if the {@link String} is {@code null} or empty. Can be {@code null}.
     * @param notParsableValue  the {@link Integer} value that will be used for comparison if the {@link String} does not contain a parsable {@link Integer} number. Can be {@code null}.
     * @param integerComparator the {@link Comparator} for comparing the {@link Integer} values. Must not be {@code null}.
     * @return a comparator that converts the {@link String} values to {@link Integer} numbers and then compares them
     * @see Integer#parseInt(String)
     * @see StringComparators#extractorComparator(Function, Comparator)
     */
    public static Comparator<@Nullable String> integerComparator(@Nullable Integer nullOrEmptyValue,
                                                                 @Nullable Integer notParsableValue,
                                                                 Comparator<@Nullable Integer> integerComparator) {
        Function<@Nullable String, @Nullable Integer> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return extractorComparator(extractor, integerComparator);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@link Long} numbers and then compares them.
     *
     * @param nullOrEmptyValue the {@link Long} value that will be used for comparison if the {@link String} is {@code null} or empty. Can be {@code null}.
     * @param notParsableValue the {@link Long} value that will be used for comparison if the {@link String} does not contain a parsable {@link Long} number. Can be {@code null}.
     * @param longComparator   the {@link Comparator} for comparing the {@link Long} values. Must not be {@code null}.
     * @return a comparator that converts the {@link String} values to {@link Long} numbers and then compares them
     * @see Long#parseLong(String)
     * @see StringComparators#extractorComparator(Function, Comparator)
     */
    public static Comparator<@Nullable String> longComparator(@Nullable Long nullOrEmptyValue,
                                                              @Nullable Long notParsableValue,
                                                              Comparator<@Nullable Long> longComparator) {
        Function<@Nullable String, @Nullable Long> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return extractorComparator(extractor, longComparator);
    }

    /**
     * Returns a comparator that converts the {@link String} values to {@link Double} numbers and then compares them.
     *
     * @param nullOrEmptyValue the {@link Double} value that will be used for comparison if the {@link String} is {@code null} or empty. Can be {@code null}.
     * @param notParsableValue the {@link Double} value that will be used for comparison if the {@link String} does not contain a parsable {@link Double} number. Can be {@code null}.
     * @param doubleComparator the {@link Comparator} for comparing the {@link Double} values. Must not be {@code null}.
     * @return a comparator that converts the {@link String} values to {@link Double} numbers and then compares them
     * @see Double#parseDouble(String)
     * @see StringComparators#extractorComparator(Function, Comparator)
     */
    public static Comparator<@Nullable String> doubleComparator(@Nullable Double nullOrEmptyValue,
                                                                @Nullable Double notParsableValue,
                                                                Comparator<@Nullable Double> doubleComparator) {
        Function<@Nullable String, @Nullable Double> extractor = s -> {
            if ((s == null) || s.isEmpty()) {
                return nullOrEmptyValue;
            }
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                return notParsableValue;
            }
        };
        return extractorComparator(extractor, doubleComparator);
    }

    /**
     * Returns a comparator that converts the {@link String} values with the given {@code keyExtractor} and then compares them with the given {@code keyComparator}.
     *
     * @param keyExtractor  the function that extracts the key from the {@link String} values. Must not be {@code null}.
     * @param keyComparator the {@link Comparator} for comparing the extracted keys. Must not be {@code null}.
     * @return a comparator that converts the {@link String} values with the given {@code keyExtractor} and then compares them with the given {@code keyComparator}
     * @see Comparator#comparing(Function, Comparator)
     */
    public static <T> Comparator<@Nullable String> extractorComparator(Function<@Nullable String, @Nullable T> keyExtractor,
                                                                       Comparator<@Nullable T> keyComparator) {
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(keyComparator);
        return comparing(keyExtractor, keyComparator);
    }

}
