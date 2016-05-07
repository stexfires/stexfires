package org.textfiledatatools.util;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringOperation {
    LOWER_CASE,
    UPPER_CASE,
    TRIM_TO_NULL,
    TRIM_TO_EMPTY;

    private static final String EMPTY = "";

    public static String operate(String s, StringOperation stringOperation) {
        return operate(s, stringOperation, null);
    }

    public static String operate(String s, StringOperation stringOperation, Locale locale) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(stringOperation);
        String result = null;
        switch (stringOperation) {
            case LOWER_CASE:
                if (s != null) {
                    if (locale != null) {
                        result = s.toLowerCase(locale);
                    } else {
                        result = s.toLowerCase();
                    }
                }
                break;
            case UPPER_CASE:
                if (s != null) {
                    if (locale != null) {
                        result = s.toUpperCase(locale);
                    } else {
                        result = s.toUpperCase();
                    }
                }
                break;
            case TRIM_TO_EMPTY:
                if (s != null) {
                    result = s.trim();
                } else {
                    result = EMPTY;
                }
                break;
            case TRIM_TO_NULL:
                if (s != null) {
                    result = s.trim();
                    if (result.isEmpty()) {
                        result = null;
                    }
                }
                break;
            default:
                result = null;
        }
        return result;
    }

}
