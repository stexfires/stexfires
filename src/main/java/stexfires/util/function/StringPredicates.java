package stexfires.util.function;

import java.text.Normalizer;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static stexfires.util.Strings.MAX_ASCII_CODE_POINT;
import static stexfires.util.Strings.MIN_ASCII_CODE_POINT;

/**
 * @author Mathias Kalb
 * @see java.lang.String
 * @see java.lang.Character
 * @see java.text.Normalizer
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 * @see stexfires.util.Strings
 * @since 0.1
 */
public final class StringPredicates {

    private StringPredicates() {
    }

    public static IntPredicate codePointBetween(int minCodePoint, int maxCodePoint) {
        return codePoint -> codePoint >= minCodePoint && codePoint <= maxCodePoint;
    }

    public static IntPredicate codePointASCII() {
        return codePointBetween(MIN_ASCII_CODE_POINT, MAX_ASCII_CODE_POINT);
    }

    public static IntPredicate codePointCharacterType(int characterType) {
        return codePoint -> Character.getType(codePoint) == characterType;
    }

    public static IntPredicate codePointUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
        Objects.requireNonNull(unicodeBlock);
        return codePoint -> Objects.equals(Character.UnicodeBlock.of(codePoint), unicodeBlock);
    }

    public static IntPredicate codePointCharCount1() {
        return codePoint -> Character.charCount(codePoint) == 1;
    }

    public static IntPredicate codePointCharCount2() {
        return codePoint -> Character.charCount(codePoint) == 2;
    }

    private static boolean checkNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static Predicate<String> applyOperatorAndTest(UnaryOperator<String> stringOperator,
                                                         Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringOperator);
        Objects.requireNonNull(stringPredicate);
        return s -> stringPredicate.test(stringOperator.apply(s));
    }

    public static <T> Predicate<String> applyFunctionAndTest(Function<String, T> stringFunction,
                                                             Predicate<? super T> predicate) {
        Objects.requireNonNull(stringFunction);
        Objects.requireNonNull(predicate);
        return s -> predicate.test(stringFunction.apply(s));
    }

    public static Predicate<String> concatAnd(Predicate<String> firstStringPredicate,
                                              Predicate<String> secondStringPredicate) {
        Objects.requireNonNull(firstStringPredicate);
        Objects.requireNonNull(secondStringPredicate);
        return s -> firstStringPredicate.test(s) && secondStringPredicate.test(s);
    }

    public static Predicate<String> concatOr(Predicate<String> firstStringPredicate,
                                             Predicate<String> secondStringPredicate) {
        Objects.requireNonNull(firstStringPredicate);
        Objects.requireNonNull(secondStringPredicate);
        return s -> firstStringPredicate.test(s) || secondStringPredicate.test(s);
    }

    public static Predicate<String> isNullOr(Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        return s -> s == null || stringPredicate.test(s);
    }

    public static Predicate<String> isNotNullAnd(Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        return s -> s != null && stringPredicate.test(s);
    }

    public static Predicate<String> allLinesMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().allMatch(linePredicate);
    }

    public static Predicate<String> anyLineMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().anyMatch(linePredicate);
    }

    public static Predicate<String> noneLineMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().noneMatch(linePredicate);
    }

    public static Predicate<String> allCodePointsMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().allMatch(codePointPredicate);
    }

    public static Predicate<String> anyCodePointMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().anyMatch(codePointPredicate);
    }

    public static Predicate<String> noneCodePointMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().noneMatch(codePointPredicate);
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<String> isNull() {
        return s -> s == null;
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<String> isNotNull() {
        return s -> s != null;
    }

    public static Predicate<String> isEmpty() {
        return s -> s != null && s.isEmpty();
    }

    public static Predicate<String> isNullOrEmpty() {
        return s -> s == null || s.isEmpty();
    }

    public static Predicate<String> isBlank() {
        return s -> s != null && s.isBlank();
    }

    public static Predicate<String> isNullOrBlank() {
        return s -> s == null || s.isBlank();
    }

    public static Predicate<String> equals(String compareValue) {
        return (compareValue == null) ? Objects::isNull : compareValue::equals;
    }

    public static Predicate<String> equalsIgnoreCase(String compareValue) {
        return (compareValue == null) ? Objects::isNull : compareValue::equalsIgnoreCase;
    }

    public static Predicate<String> equalsChar(char character) {
        return s -> Character.toString(character).equals(s);
    }

    public static Predicate<String> equalsCodePoint(int codePoint) {
        return s -> Character.toString(codePoint).equals(s);
    }

    public static Predicate<String> equalsFunction(Function<String, String> stringFunction) {
        Objects.requireNonNull(stringFunction);
        return s -> Objects.equals(s, stringFunction.apply(s));
    }

    public static Predicate<String> equalsOperator(UnaryOperator<String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return s -> Objects.equals(s, stringUnaryOperator.apply(s));
    }

    public static Predicate<String> equalsSupplier(Supplier<String> stringSupplier) {
        Objects.requireNonNull(stringSupplier);
        return s -> Objects.equals(s, stringSupplier.get());
    }

    public static Predicate<String> constantTrue() {
        return s -> true;
    }

    public static Predicate<String> constantFalse() {
        return s -> false;
    }

    public static Predicate<String> constant(boolean constant) {
        return s -> constant;
    }

    public static Predicate<String> supplier(BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(booleanSupplier);
        return s -> booleanSupplier.getAsBoolean();
    }

    public static Predicate<String> alphabetic() {
        return allCodePointsMatch(Character::isAlphabetic, false);
    }

    public static Predicate<String> ascii() {
        return allCodePointsMatch(codePointASCII(), false);
    }

    public static Predicate<String> digit() {
        return allCodePointsMatch(Character::isDigit, false);
    }

    public static Predicate<String> letter() {
        return allCodePointsMatch(Character::isLetter, false);
    }

    public static Predicate<String> letterOrDigit() {
        return allCodePointsMatch(Character::isLetterOrDigit, false);
    }

    public static Predicate<String> lowerCase() {
        return allCodePointsMatch(Character::isLowerCase, false);
    }

    public static Predicate<String> upperCase() {
        return allCodePointsMatch(Character::isUpperCase, false);
    }

    public static Predicate<String> spaceChar() {
        return allCodePointsMatch(Character::isSpaceChar, false);
    }

    public static Predicate<String> whitespace() {
        return allCodePointsMatch(Character::isWhitespace, false);
    }

    public static Predicate<String> normalizedNFD() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFD);
    }

    public static Predicate<String> normalizedNFC() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFC);
    }

    public static Predicate<String> normalizedNFKD() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFKD);
    }

    public static Predicate<String> normalizedNFKC() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFKC);
    }

    public static Predicate<String> contains(String compareValue) {
        Objects.requireNonNull(compareValue);
        return s -> (s != null) && !s.isEmpty() && s.contains(compareValue);
    }

    public static Predicate<String> startsWith(String prefix) {
        Objects.requireNonNull(prefix);
        return s -> (s != null) && !s.isEmpty() && s.startsWith(prefix);
    }

    public static Predicate<String> endsWith(String suffix) {
        Objects.requireNonNull(suffix);
        return s -> (s != null) && !s.isEmpty() && s.endsWith(suffix);
    }

    public static Predicate<String> surroundedBy(String prefix, String suffix) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        return s -> (s != null) && !s.isEmpty() && s.startsWith(prefix) && s.endsWith(suffix);
    }

    public static Predicate<String> containedIn(Collection<String> strings) {
        Objects.requireNonNull(strings);
        return s -> s != null && strings.contains(s);
    }

    public static Predicate<String> charAt(int index, char character) {
        return s -> s != null && index >= 0 && index < s.length() && s.charAt(index) == character;
    }

    public static Predicate<String> matches(String regEx) {
        Objects.requireNonNull(regEx);
        return s -> (s != null) && !s.isEmpty() && s.matches(regEx);
    }

    public static Predicate<String> length(int length) {
        return s -> (s == null ? 0 : s.length()) == length;
    }

    public static Predicate<String> lengthGreaterThan(int length) {
        return s -> (s == null ? 0 : s.length()) > length;
    }

    public static Predicate<String> length(IntPredicate lengthPredicate) {
        Objects.requireNonNull(lengthPredicate);
        return s -> lengthPredicate.test(s == null ? 0 : s.length());
    }

    public static Predicate<String> countCodePoints(int count) {
        return s -> s == null ? count == 0 : s.codePoints().count() == count;
    }

    public static Predicate<String> countLines(int count) {
        return s -> s == null ? count == 0 : s.lines().count() == count;
    }

}
