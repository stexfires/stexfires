package stexfires.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static java.util.Comparator.comparing;

/**
 * This class consists of {@code static} utility methods
 * for constructing comparators for {@link String} values.
 *
 * @author Mathias Kalb
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

}
