package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringComparisonType {

    EQUALS,
    EQUALS_IGNORE_CASE,
    CONTENT_EQUALS,
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    MATCHES;

    public static Predicate<String> stringPredicate(StringComparisonType stringComparisonType,
                                                    @NotNull String compareValue) {
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareValue);
        return value -> compareStringInternal(value, stringComparisonType, compareValue);
    }

    private static boolean compareStringInternal(@Nullable String value,
                                                 StringComparisonType stringComparisonType,
                                                 @NotNull String compareValue) {
        Objects.requireNonNull(stringComparisonType);
        Objects.requireNonNull(compareValue);
        if (value != null) {
            return switch (stringComparisonType) {
                case EQUALS -> value.equals(compareValue);
                case EQUALS_IGNORE_CASE -> value.equalsIgnoreCase(compareValue);
                case CONTENT_EQUALS -> value.contentEquals(compareValue);
                case CONTAINS -> value.contains(compareValue);
                case STARTS_WITH -> value.startsWith(compareValue);
                case ENDS_WITH -> value.endsWith(compareValue);
                case MATCHES -> value.matches(compareValue);
            };
        }
        return false;
    }

    public final Predicate<String> stringPredicate(@NotNull String compareValue) {
        Objects.requireNonNull(compareValue);
        return value -> compareStringInternal(value, this, compareValue);
    }

    public final boolean compareString(@Nullable String value, @NotNull String compareValue) {
        Objects.requireNonNull(compareValue);
        return compareStringInternal(value, this, compareValue);
    }

}
