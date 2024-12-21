package stexfires.util.function;

import org.jspecify.annotations.Nullable;
import stexfires.util.CodePoint;
import stexfires.util.Strings;

import java.text.Normalizer;
import java.util.Collection;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * @see java.lang.String
 * @see java.lang.Character
 * @see java.text.Normalizer
 * @see java.util.function.Predicate
 * @see java.util.function.IntPredicate
 * @see stexfires.util.CodePoint
 * @see stexfires.util.Strings
 * @since 0.1
 */
public final class StringPredicates {

    private StringPredicates() {
    }

    private static boolean checkNullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }

    public static Predicate<@Nullable String> applyOperatorAndTest(UnaryOperator<@Nullable String> stringOperator,
                                                                   Predicate<@Nullable String> stringPredicate) {
        Objects.requireNonNull(stringOperator);
        Objects.requireNonNull(stringPredicate);
        return s -> stringPredicate.test(stringOperator.apply(s));
    }

    public static <T> Predicate<@Nullable String> applyFunctionAndTest(Function<@Nullable String, T> stringFunction,
                                                                       Predicate<? super T> predicate) {
        Objects.requireNonNull(stringFunction);
        Objects.requireNonNull(predicate);
        return s -> predicate.test(stringFunction.apply(s));
    }

    public static Predicate<@Nullable String> concatAnd(Predicate<@Nullable String> firstStringPredicate,
                                                        Predicate<@Nullable String> secondStringPredicate) {
        Objects.requireNonNull(firstStringPredicate);
        Objects.requireNonNull(secondStringPredicate);
        return s -> firstStringPredicate.test(s) && secondStringPredicate.test(s);
    }

    public static Predicate<@Nullable String> concatAnd(Stream<Predicate<@Nullable String>> stringPredicates) {
        Objects.requireNonNull(stringPredicates);
        return stringPredicates.reduce(s -> true, Predicate::and);
    }

    public static Predicate<@Nullable String> concatOr(Predicate<@Nullable String> firstStringPredicate,
                                                       Predicate<@Nullable String> secondStringPredicate) {
        Objects.requireNonNull(firstStringPredicate);
        Objects.requireNonNull(secondStringPredicate);
        return s -> firstStringPredicate.test(s) || secondStringPredicate.test(s);
    }

    public static Predicate<@Nullable String> concatOr(Stream<Predicate<@Nullable String>> stringPredicates) {
        Objects.requireNonNull(stringPredicates);
        return stringPredicates.reduce(s -> false, Predicate::or);
    }

    public static Predicate<@Nullable String> isNullOr(Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        return s -> s == null || stringPredicate.test(s);
    }

    public static Predicate<@Nullable String> isNotNullAnd(Predicate<String> stringPredicate) {
        Objects.requireNonNull(stringPredicate);
        return s -> s != null && stringPredicate.test(s);
    }

    public static Predicate<@Nullable String> allLinesMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().allMatch(linePredicate);
    }

    public static Predicate<@Nullable String> anyLineMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().anyMatch(linePredicate);
    }

    public static Predicate<@Nullable String> noneLineMatch(Predicate<String> linePredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(linePredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.lines().noneMatch(linePredicate);
    }

    public static Predicate<@Nullable String> allCodePointsMatch(Predicate<CodePoint> codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : Strings.codePointStreamOfNotNull(s).allMatch(codePointPredicate);
    }

    public static Predicate<@Nullable String> anyCodePointMatch(Predicate<CodePoint> codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : Strings.codePointStreamOfNotNull(s).anyMatch(codePointPredicate);
    }

    public static Predicate<@Nullable String> noneCodePointMatch(Predicate<CodePoint> codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : Strings.codePointStreamOfNotNull(s).noneMatch(codePointPredicate);
    }

    public static Predicate<@Nullable String> allIntCodePointsMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().allMatch(codePointPredicate);
    }

    public static Predicate<@Nullable String> anyIntCodePointMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().anyMatch(codePointPredicate);
    }

    public static Predicate<@Nullable String> noneIntCodePointMatch(IntPredicate codePointPredicate, boolean resultForNullOrEmpty) {
        Objects.requireNonNull(codePointPredicate);
        return s -> checkNullOrEmpty(s) ? resultForNullOrEmpty : s.codePoints().noneMatch(codePointPredicate);
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<@Nullable String> isNull() {
        return s -> s == null;
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Predicate<@Nullable String> isNotNull() {
        return s -> s != null;
    }

    public static Predicate<@Nullable String> isEmpty() {
        return s -> s != null && s.isEmpty();
    }

    public static Predicate<@Nullable String> isNullOrEmpty() {
        return s -> s == null || s.isEmpty();
    }

    public static Predicate<@Nullable String> isNotNullAndNotEmpty() {
        return s -> s != null && !s.isEmpty();
    }

    public static Predicate<@Nullable String> isBlank() {
        return s -> s != null && s.isBlank();
    }

    public static Predicate<@Nullable String> isNullOrBlank() {
        return s -> s == null || s.isBlank();
    }

    public static Predicate<@Nullable String> isNotNullAndNotBlank() {
        return s -> s != null && !s.isBlank();
    }

    public static Predicate<@Nullable String> equals(@Nullable String compareValue) {
        return (compareValue == null) ? Objects::isNull : compareValue::equals;
    }

    public static Predicate<@Nullable String> equalsIgnoreCase(@Nullable String compareValue) {
        return (compareValue == null) ? Objects::isNull : compareValue::equalsIgnoreCase;
    }

    public static Predicate<@Nullable String> equalsChar(char character) {
        String charString = Character.toString(character);
        return charString::equals;
    }

    public static Predicate<@Nullable String> equalsIntCodePoint(int codePoint) {
        String codePointString = Character.toString(codePoint);
        return codePointString::equals;
    }

    public static Predicate<@Nullable String> equalsFunction(Function<@Nullable String, @Nullable String> stringFunction) {
        Objects.requireNonNull(stringFunction);
        return s -> Objects.equals(s, stringFunction.apply(s));
    }

    public static Predicate<@Nullable String> equalsOperator(UnaryOperator<@Nullable String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return s -> Objects.equals(s, stringUnaryOperator.apply(s));
    }

    public static Predicate<@Nullable String> equalsSupplier(Supplier<@Nullable String> stringSupplier) {
        Objects.requireNonNull(stringSupplier);
        return s -> Objects.equals(s, stringSupplier.get());
    }

    public static Predicate<@Nullable String> constantTrue() {
        return s -> true;
    }

    public static Predicate<@Nullable String> constantFalse() {
        return s -> false;
    }

    public static Predicate<@Nullable String> constant(boolean constant) {
        return s -> constant;
    }

    public static Predicate<@Nullable String> supplier(BooleanSupplier booleanSupplier) {
        Objects.requireNonNull(booleanSupplier);
        return s -> booleanSupplier.getAsBoolean();
    }

    public static Predicate<@Nullable String> alphabetic() {
        return allIntCodePointsMatch(Character::isAlphabetic, false);
    }

    public static Predicate<@Nullable String> ascii() {
        return allCodePointsMatch(CodePoint::isASCII, false);
    }

    public static Predicate<@Nullable String> digit() {
        return allIntCodePointsMatch(Character::isDigit, false);
    }

    public static Predicate<@Nullable String> letter() {
        return allIntCodePointsMatch(Character::isLetter, false);
    }

    public static Predicate<@Nullable String> letterOrDigit() {
        return allIntCodePointsMatch(Character::isLetterOrDigit, false);
    }

    public static Predicate<@Nullable String> lowerCase() {
        return allIntCodePointsMatch(Character::isLowerCase, false);
    }

    public static Predicate<@Nullable String> upperCase() {
        return allIntCodePointsMatch(Character::isUpperCase, false);
    }

    public static Predicate<@Nullable String> spaceChar() {
        return allIntCodePointsMatch(Character::isSpaceChar, false);
    }

    public static Predicate<@Nullable String> whitespace() {
        return allIntCodePointsMatch(Character::isWhitespace, false);
    }

    public static Predicate<@Nullable String> normalizedNFD() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFD);
    }

    public static Predicate<@Nullable String> normalizedNFC() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFC);
    }

    public static Predicate<@Nullable String> normalizedNFKD() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFKD);
    }

    public static Predicate<@Nullable String> normalizedNFKC() {
        return s -> (s != null) && !s.isEmpty() && Normalizer.isNormalized(s, Normalizer.Form.NFKC);
    }

    public static Predicate<@Nullable String> contains(String compareValue) {
        Objects.requireNonNull(compareValue);
        return s -> (s != null) && !s.isEmpty() && s.contains(compareValue);
    }

    public static Predicate<@Nullable String> startsWith(String prefix) {
        Objects.requireNonNull(prefix);
        return s -> (s != null) && !s.isEmpty() && s.startsWith(prefix);
    }

    public static Predicate<@Nullable String> endsWith(String suffix) {
        Objects.requireNonNull(suffix);
        return s -> (s != null) && !s.isEmpty() && s.endsWith(suffix);
    }

    public static Predicate<@Nullable String> surroundedBy(String prefix, String suffix) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        return s -> (s != null) && !s.isEmpty() && s.startsWith(prefix) && s.endsWith(suffix);
    }

    public static Predicate<@Nullable String> containedIn(Collection<String> strings) {
        Objects.requireNonNull(strings);
        return s -> s != null && strings.contains(s);
    }

    public static Predicate<@Nullable String> charAt(int index, char character) {
        return s -> s != null && index >= 0 && index < s.length() && s.charAt(index) == character;
    }

    public static Predicate<@Nullable String> intCodePointAt(int index, int codePoint) {
        return s -> s != null && index >= 0 && index < s.length() && s.codePointAt(index) == codePoint;
    }

    public static Predicate<@Nullable String> matches(String regEx) {
        Objects.requireNonNull(regEx);
        return s -> (s != null) && !s.isEmpty() && s.matches(regEx);
    }

    public static Predicate<@Nullable String> length(int length) {
        return s -> (s == null ? 0 : s.length()) == length;
    }

    public static Predicate<@Nullable String> lengthGreaterThan(int length) {
        return s -> (s == null ? 0 : s.length()) > length;
    }

    public static Predicate<@Nullable String> length(IntPredicate lengthPredicate) {
        Objects.requireNonNull(lengthPredicate);
        return s -> lengthPredicate.test(s == null ? 0 : s.length());
    }

    public static Predicate<@Nullable String> countCodePoints(int count) {
        return s -> s == null ? count == 0 : s.codePoints().count() == count;
    }

    public static Predicate<@Nullable String> countLines(int count) {
        return s -> s == null ? count == 0 : s.lines().count() == count;
    }

}
