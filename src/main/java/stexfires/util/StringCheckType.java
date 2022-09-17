package stexfires.util;

import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

// TODO chars and codePoints

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

    private static boolean checkAllChars(@Nullable String value, IntPredicate predicate) {
        return (value != null) && !value.isEmpty() && value.chars().allMatch(predicate);
    }

    public static Predicate<String> stringPredicate(StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        return value -> checkStringInternal(value, stringCheckType);
    }

    private static boolean checkStringInternal(@Nullable String value, StringCheckType stringCheckType) {
        Objects.requireNonNull(stringCheckType);
        return switch (stringCheckType) {
            case NULL -> value == null;
            case NOT_NULL -> value != null;
            case EMPTY -> (value != null) && value.isEmpty();
            case NULL_OR_EMPTY -> (value == null) || value.isEmpty();
            case ALPHABETIC -> checkAllChars(value, Character::isAlphabetic);
            case ASCII -> checkAllChars(value, intChar -> intChar < FIRST_NON_ASCII_CHAR);
            case DIGIT -> checkAllChars(value, Character::isDigit);
            case LETTER -> checkAllChars(value, Character::isLetter);
            case LETTER_OR_DIGIT -> checkAllChars(value, Character::isLetterOrDigit);
            case LOWER_CASE -> checkAllChars(value, Character::isLowerCase);
            case SPACE_CHAR -> checkAllChars(value, Character::isSpaceChar);
            case UPPER_CASE -> checkAllChars(value, Character::isUpperCase);
            case WHITESPACE -> checkAllChars(value, Character::isWhitespace);
            case NORMALIZED_NFD -> (value == null) || value.isEmpty()
                    || Normalizer.isNormalized(value, Normalizer.Form.NFD);
            case NORMALIZED_NFC -> (value == null) || value.isEmpty()
                    || Normalizer.isNormalized(value, Normalizer.Form.NFC);
            case NORMALIZED_NFKD -> (value == null) || value.isEmpty()
                    || Normalizer.isNormalized(value, Normalizer.Form.NFKD);
            case NORMALIZED_NFKC -> (value == null) || value.isEmpty()
                    || Normalizer.isNormalized(value, Normalizer.Form.NFKC);
        };
    }

    public final Predicate<String> stringPredicate() {
        return value -> checkStringInternal(value, this);
    }

    public final boolean checkString(@Nullable String value) {
        return checkStringInternal(value, this);
    }

}
