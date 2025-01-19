package stexfires.record.modifier;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public class PivotModifier<T extends TextRecord> extends GroupModifier<T, TextRecord> {

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, @Nullable String> newCategoryFunction,
                         Function<List<T>, List<@Nullable String>> pivotTextsFunction) {
        super(groupByFunction,
                GroupModifier.aggregateToTexts(
                        list -> newCategoryFunction.apply(list.getFirst()),
                        pivotTextsFunction));
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(newCategoryFunction);
        Objects.requireNonNull(pivotTextsFunction);
    }

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, @Nullable String> newCategoryFunction,
                         Function<? super T, Stream<String>> newFirstTextsFunction,
                         Function<? super T, @Nullable String> textFunction,
                         String nullText,
                         Function<? super T, @Nullable String> textClassificationFunction,
                         List<String> textClassifications) {
        this(groupByFunction,
                newCategoryFunction,
                pivotTextsFunctionWithClassifications(
                        newFirstTextsFunction,
                        textFunction,
                        nullText,
                        textClassificationFunction,
                        textClassifications));
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(newCategoryFunction);
        Objects.requireNonNull(newFirstTextsFunction);
        Objects.requireNonNull(textFunction);
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textClassificationFunction);
        Objects.requireNonNull(textClassifications);
    }

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, @Nullable String> newCategoryFunction,
                         Function<? super T, Stream<String>> newFirstTextsFunction,
                         int newRecordSize,
                         String nullText,
                         List<Integer> textIndexes) {
        this(groupByFunction,
                newCategoryFunction,
                pivotTextsFunctionWithIndexes(
                        newFirstTextsFunction,
                        newRecordSize,
                        nullText,
                        textIndexes));
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(newCategoryFunction);
        Objects.requireNonNull(newFirstTextsFunction);
    }

    public static <T extends TextRecord> Function<? super T, @Nullable String> withoutCategory() {
        return r -> null;
    }

    public static <T extends TextRecord> PivotModifier<T> pivotWithClassifications(int keyIndex,
                                                                                   int textIndex,
                                                                                   String nullText,
                                                                                   int textClassificationIndex,
                                                                                   List<String> textClassifications) {
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textClassifications);
        return new PivotModifier<>(
                GroupModifier.<T>groupByTextAt(keyIndex),
                withoutCategory(),
                r -> Stream.of(r.textAt(keyIndex)),
                r -> r.textAt(textIndex),
                nullText,
                r -> r.textAt(textClassificationIndex),
                textClassifications);
    }

    public static <T extends TextRecord> PivotModifier<T> pivotWithClassifications(Function<? super T, String> keyFunction,
                                                                                   Function<? super T, String> textFunction,
                                                                                   String nullText,
                                                                                   Function<? super T, @Nullable String> textClassificationFunction,
                                                                                   List<String> textClassifications) {
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(textFunction);
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textClassificationFunction);
        Objects.requireNonNull(textClassifications);
        return new PivotModifier<>(keyFunction,
                withoutCategory(),
                r -> Stream.of(keyFunction.apply(r)),
                textFunction,
                nullText,
                textClassificationFunction,
                textClassifications);
    }

    public static <T extends TextRecord> PivotModifier<T> pivotWithIndexes(int keyIndex,
                                                                           int recordsPerKey,
                                                                           String nullText,
                                                                           int... textIndexes) {
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textIndexes);
        return pivotWithIndexes(keyIndex, recordsPerKey, nullText,
                Arrays.stream(textIndexes).boxed().toList());
    }

    public static <T extends TextRecord> PivotModifier<T> pivotWithIndexes(int keyIndex,
                                                                           int recordsPerKey,
                                                                           String nullText,
                                                                           List<Integer> textIndexes) {
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textIndexes);
        return new PivotModifier<>(
                GroupModifier.<T>groupByTextAt(keyIndex),
                withoutCategory(),
                r -> Stream.of(r.textAt(keyIndex)),
                1 + (recordsPerKey * textIndexes.size()),
                nullText,
                textIndexes);
    }

    public static <T extends TextRecord> Function<List<T>, List<@Nullable String>> pivotTextsFunctionWithClassifications(
            Function<? super T, Stream<String>> newFirstTextsFunction,
            Function<? super T, @Nullable String> textFunction,
            String nullText,
            Function<? super T, @Nullable String> textClassificationFunction,
            List<String> textClassifications) {
        Objects.requireNonNull(newFirstTextsFunction);
        Objects.requireNonNull(textFunction);
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textClassificationFunction);
        Objects.requireNonNull(textClassifications);
        Function<List<T>, Stream<String>> newTextsFunction = list ->
                textClassifications.stream()
                                   .map(vc ->
                                           list.stream()
                                               .filter(r -> Objects.equals(vc, textClassificationFunction.apply(r)))
                                               .map(textFunction)
                                               .map(v -> (v == null) ? nullText : v)
                                               .findFirst()
                                               .orElse(nullText));
        return list ->
                Stream.concat(
                              newFirstTextsFunction.apply(list.getFirst()),
                              newTextsFunction.apply(list))
                      .toList();
    }

    public static <T extends TextRecord> Function<List<T>, List<@Nullable String>> pivotTextsFunctionWithIndexes(
            Function<? super T, Stream<String>> newFirstTextsFunction,
            int newRecordSize,
            String nullText,
            List<Integer> textIndexes) {
        Objects.requireNonNull(newFirstTextsFunction);
        Objects.requireNonNull(nullText);
        Objects.requireNonNull(textIndexes);
        if (newRecordSize < 0) {
            throw new IllegalArgumentException("newRecordSize=" + newRecordSize);
        }
        Objects.requireNonNull(textIndexes);
        Function<List<T>, Stream<String>> newTextsFunction = list ->
                list.stream()
                    .flatMap(r -> textIndexes.stream()
                                             .map(i -> r.textAtOrElse(i, nullText)));
        return list ->
                Stream.concat(Stream.concat(
                                      newFirstTextsFunction.apply(list.getFirst()),
                                      newTextsFunction.apply(list)),
                              Stream.generate(() -> nullText))
                      .limit(newRecordSize)
                      .toList();
    }

}
