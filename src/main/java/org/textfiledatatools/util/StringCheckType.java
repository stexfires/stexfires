package org.textfiledatatools.util;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringCheckType {
    NULL,
    NOT_NULL,
    EMPTY;

    public static Predicate<String> stringPredicate(StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        return value -> check(value, stringCheckType);
    }

    private static boolean check(String value, StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        switch (stringCheckType) {
            case NULL:
                return value == null;
            case NOT_NULL:
                return value != null;
            case EMPTY:
                return (value != null) && value.isEmpty();
            default:
                return false;
        }
    }

    public Predicate<String> stringPredicate() {
        return value -> check(value, this);
    }

    public boolean check(String value) {
        return check(value, this);
    }

}