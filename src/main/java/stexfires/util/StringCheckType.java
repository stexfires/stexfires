package stexfires.util;

import java.text.Normalizer;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum StringCheckType {

    // NULL and EMPTY
    NULL,
    NOT_NULL,
    EMPTY,
    NULL_OR_EMPTY,
    // Characters
    ALPHABETIC,
    ASCII,
    DIGIT,
    LETTER,
    LETTER_OR_DIGIT,
    LOWER_CASE,
    SPACE_CHAR,
    UPPER_CASE,
    WHITESPACE,
    // NORMALIZED
    NORMALIZED_NFD,
    NORMALIZED_NFC,
    NORMALIZED_NFKD,
    NORMALIZED_NFKC;

    private static final int FIRST_NON_ASCII_CHAR = 128;

    private static boolean checkAllChars(String value, IntPredicate predicate) {
        return (value != null) && !value.isEmpty() && value.chars().allMatch(predicate);
    }

    public static Predicate<String> stringPredicate(StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        return value -> checkInternal(value, stringCheckType);
    }

    private static boolean checkInternal(String value, StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        switch (stringCheckType) {
            case NULL:
                return value == null;
            case NOT_NULL:
                return value != null;
            case EMPTY:
                return (value != null) && value.isEmpty();
            case NULL_OR_EMPTY:
                return (value == null) || value.isEmpty();
            case ALPHABETIC:
                return checkAllChars(value, Character::isAlphabetic);
            case ASCII:
                return checkAllChars(value, intChar -> intChar < FIRST_NON_ASCII_CHAR);
            case DIGIT:
                return checkAllChars(value, Character::isDigit);
            case LETTER:
                return checkAllChars(value, Character::isLetter);
            case LETTER_OR_DIGIT:
                return checkAllChars(value, Character::isLetterOrDigit);
            case LOWER_CASE:
                return checkAllChars(value, Character::isLowerCase);
            case SPACE_CHAR:
                return checkAllChars(value, Character::isSpaceChar);
            case UPPER_CASE:
                return checkAllChars(value, Character::isUpperCase);
            case WHITESPACE:
                return checkAllChars(value, Character::isWhitespace);
            case NORMALIZED_NFD:
                return (value == null) || value.isEmpty()
                        || Normalizer.isNormalized(value, Normalizer.Form.NFD);
            case NORMALIZED_NFC:
                return (value == null) || value.isEmpty()
                        || Normalizer.isNormalized(value, Normalizer.Form.NFC);
            case NORMALIZED_NFKD:
                return (value == null) || value.isEmpty()
                        || Normalizer.isNormalized(value, Normalizer.Form.NFKD);
            case NORMALIZED_NFKC:
                return (value == null) || value.isEmpty()
                        || Normalizer.isNormalized(value, Normalizer.Form.NFKC);
        }
        return false;
    }

    public final Predicate<String> stringPredicate() {
        return value -> checkInternal(value, this);
    }

    public final boolean check(String value) {
        return checkInternal(value, this);
    }

}
