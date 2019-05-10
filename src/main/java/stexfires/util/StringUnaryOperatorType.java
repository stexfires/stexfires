package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringUnaryOperatorType {

    IDENTITY,
    DUPLICATE,
    REVERSE,
    // NULL and EMPTY
    TO_NULL,
    TO_EMPTY,
    TRIM_TO_NULL,
    TRIM_TO_EMPTY,
    EMPTY_TO_NULL,
    NULL_TO_EMPTY,
    // CASE
    LOWER_CASE,
    UPPER_CASE,
    // WHITESPACE
    REMOVE_HORIZONTAL_WHITESPACE,
    REMOVE_WHITESPACE,
    REMOVE_VERTICAL_WHITESPACE,
    REMOVE_LEADING_HORIZONTAL_WHITESPACE,
    REMOVE_LEADING_WHITESPACE,
    REMOVE_LEADING_VERTICAL_WHITESPACE,
    REMOVE_TRAILING_HORIZONTAL_WHITESPACE,
    REMOVE_TRAILING_WHITESPACE,
    REMOVE_TRAILING_VERTICAL_WHITESPACE,
    TAB_TO_SPACES_2,
    TAB_TO_SPACES_4,
    // NORMALIZE
    NORMALIZE_NFD,
    NORMALIZE_NFC,
    NORMALIZE_NFKD,
    NORMALIZE_NFKC;

    private static final String EMPTY = "";

    public static UnaryOperator<String> prefix(String prefix) {
        Objects.requireNonNull(prefix);
        return value -> value == null ? prefix : prefix + value;
    }

    public static UnaryOperator<String> postfix(String postfix) {
        Objects.requireNonNull(postfix);
        return value -> value == null ? postfix : value + postfix;
    }

    public static UnaryOperator<String> stringUnaryOperator(StringUnaryOperatorType stringUnaryOperatorType) {
        Objects.requireNonNull(stringUnaryOperatorType);
        return value -> operateInternal(stringUnaryOperatorType, value, null);
    }

    public static UnaryOperator<String> stringUnaryOperator(StringUnaryOperatorType stringUnaryOperatorType,
                                                            Locale locale) {
        Objects.requireNonNull(stringUnaryOperatorType);
        Objects.requireNonNull(locale);
        return value -> operateInternal(stringUnaryOperatorType, value, locale);
    }

    private static @Nullable String operateInternal(StringUnaryOperatorType stringUnaryOperatorType,
                                                    @Nullable String value) {
        return operateInternal(stringUnaryOperatorType, value, null);
    }

    @SuppressWarnings("ReplaceNullCheck")
    private static @Nullable String operateInternal(StringUnaryOperatorType stringUnaryOperatorType,
                                                    @Nullable String value,
                                                    @Nullable Locale locale) {
        Objects.requireNonNull(stringUnaryOperatorType);
        String result = null;
        switch (stringUnaryOperatorType) {
            case IDENTITY:
                result = value;
                break;
            case DUPLICATE:
                if (value != null) {
                    result = value + value;
                }
                break;
            case REVERSE:
                if (value != null) {
                    result = new StringBuilder(value).reverse().toString();
                }
                break;
            case TO_NULL:
                break;
            case TO_EMPTY:
                result = EMPTY;
                break;
            case TRIM_TO_NULL:
                if (value != null) {
                    result = value.trim();
                    if (result.isEmpty()) {
                        result = null;
                    }
                }
                break;
            case TRIM_TO_EMPTY:
                if (value != null) {
                    result = value.trim();
                } else {
                    result = EMPTY;
                }
                break;
            case EMPTY_TO_NULL:
                if (value != null && !value.isEmpty()) {
                    result = value;
                }
                break;
            case NULL_TO_EMPTY:
                if (value != null) {
                    result = value;
                } else {
                    result = EMPTY;
                }
                break;
            case LOWER_CASE:
                if (value != null) {
                    if (locale != null) {
                        result = value.toLowerCase(locale);
                    } else {
                        result = value.toLowerCase();
                    }
                }
                break;
            case UPPER_CASE:
                if (value != null) {
                    if (locale != null) {
                        result = value.toUpperCase(locale);
                    } else {
                        result = value.toUpperCase();
                    }
                }
                break;
            case REMOVE_HORIZONTAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceAll("\\h", EMPTY);
                }
                break;
            case REMOVE_WHITESPACE:
                if (value != null) {
                    result = value.replaceAll("\\s", EMPTY);
                }
                break;
            case REMOVE_VERTICAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceAll("\\v", EMPTY);
                }
                break;
            case REMOVE_LEADING_HORIZONTAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("^\\h+", EMPTY);
                }
                break;
            case REMOVE_LEADING_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("^\\s+", EMPTY);
                }
                break;
            case REMOVE_LEADING_VERTICAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("^\\v+", EMPTY);
                }
                break;
            case REMOVE_TRAILING_HORIZONTAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("\\h+$", EMPTY);
                }
                break;
            case REMOVE_TRAILING_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("\\s+$", EMPTY);
                }
                break;
            case REMOVE_TRAILING_VERTICAL_WHITESPACE:
                if (value != null) {
                    result = value.replaceFirst("\\v+$", EMPTY);
                }
                break;
            case TAB_TO_SPACES_2:
                if (value != null) {
                    result = value.replaceAll("\\t", "  ");
                }
                break;
            case TAB_TO_SPACES_4:
                if (value != null) {
                    result = value.replaceAll("\\t", "    ");
                }
                break;
            case NORMALIZE_NFD:
                if (value != null) {
                    result = Normalizer.normalize(value, Normalizer.Form.NFD);
                }
                break;
            case NORMALIZE_NFC:
                if (value != null) {
                    result = Normalizer.normalize(value, Normalizer.Form.NFC);
                }
                break;
            case NORMALIZE_NFKD:
                if (value != null) {
                    result = Normalizer.normalize(value, Normalizer.Form.NFKD);
                }
                break;
            case NORMALIZE_NFKC:
                if (value != null) {
                    result = Normalizer.normalize(value, Normalizer.Form.NFKC);
                }
                break;
        }
        return result;
    }

    public final UnaryOperator<String> stringUnaryOperator() {
        return value -> operateInternal(this, value, null);
    }

    public final UnaryOperator<String> stringUnaryOperator(Locale locale) {
        Objects.requireNonNull(locale);
        return value -> operateInternal(this, value, locale);
    }

    public final @Nullable String operate(@Nullable String value) {
        return operateInternal(this, value);
    }

    public final @Nullable String operate(@Nullable String value, Locale locale) {
        Objects.requireNonNull(locale);
        return operateInternal(this, value, locale);
    }

}
