package stexfires.record.modifier;

import stexfires.record.TextRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.util.Strings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UnpivotModifier<T extends TextRecord, R extends TextRecord> implements RecordStreamModifier<T, R> {

    private final Function<? super T, Stream<? extends R>> unpivotFunction;

    public UnpivotModifier(Function<? super T, Stream<? extends R>> unpivotFunction) {
        Objects.requireNonNull(unpivotFunction);
        this.unpivotFunction = unpivotFunction;
    }

    public static <T extends TextRecord, R extends TextRecord> UnpivotModifier<T, R> of(Function<? super T, Collection<? extends R>> unpivotFunction) {
        Objects.requireNonNull(unpivotFunction);
        return new UnpivotModifier<>(record -> unpivotFunction.apply(record).stream());
    }

    public static <T extends TextRecord> UnpivotModifier<T, TextRecord> oneRecordPerValue(Collection<Integer> keyIndexes,
                                                                                          IntFunction<String> valueIndexToIdentifier,
                                                                                          boolean onlyExistingValues,
                                                                                          Collection<Integer> valueIndexes) {
        Objects.requireNonNull(keyIndexes);
        Objects.requireNonNull(valueIndexToIdentifier);
        Objects.requireNonNull(valueIndexes);
        return new UnpivotModifier<>(record ->
                valueIndexes.stream()
                            .filter(valueIndex -> !onlyExistingValues || record.isValidIndex(valueIndex))
                            .map(valueIndex ->
                                    new StandardRecord(record.category(), record.recordId(),
                                            Strings.concat(
                                                    // keys
                                                    keyIndexes.stream().map(record::textAt),
                                                    // identifier
                                                    Stream.of(valueIndexToIdentifier.apply(valueIndex)),
                                                    // one value
                                                    Stream.of(record.textAt(valueIndex)))
                                    )));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static <T extends TextRecord> UnpivotModifier<T, TextRecord> oneRecordPerValue(int keyIndex,
                                                                                          IntFunction<String> valueIndexToIdentifier,
                                                                                          boolean onlyExistingValues,
                                                                                          int... valueIndexes) {
        Objects.requireNonNull(valueIndexToIdentifier);
        Objects.requireNonNull(valueIndexes);
        return oneRecordPerValue(
                Collections.singleton(keyIndex),
                valueIndexToIdentifier,
                onlyExistingValues,
                Arrays.stream(valueIndexes).boxed().collect(Collectors.toList())
        );
    }

    public static <T extends TextRecord> UnpivotModifier<T, TextRecord> oneRecordPerValue(int keyIndex,
                                                                                          boolean onlyExistingValues,
                                                                                          Map<Integer, String> valueIndexesAndIdentifiers) {
        Objects.requireNonNull(valueIndexesAndIdentifiers);
        return oneRecordPerValue(
                Collections.singleton(keyIndex),
                valueIndexesAndIdentifiers::get,
                onlyExistingValues,
                valueIndexesAndIdentifiers.keySet()
        );
    }

    @SuppressWarnings("ReturnOfNull")
    @SafeVarargs
    public static <T extends TextRecord> UnpivotModifier<T, TextRecord> oneRecordPerValues(Collection<Integer> keyIndexes,
                                                                                           IntFunction<String> recordIndexToIdentifier,
                                                                                           Collection<Integer>... valueIndexes) {
        Objects.requireNonNull(keyIndexes);
        Objects.requireNonNull(recordIndexToIdentifier);
        Objects.requireNonNull(valueIndexes);
        return new UnpivotModifier<>(record ->
                IntStream.range(0, valueIndexes.length)
                         .mapToObj(recordIndex ->
                                 new StandardRecord(record.category(), record.recordId(),
                                         Strings.concat(
                                                 // keys
                                                 keyIndexes.stream().map(record::textAt),
                                                 // identifier
                                                 Stream.of(recordIndexToIdentifier.apply(recordIndex)),
                                                 // many values
                                                 valueIndexes[recordIndex].stream().map(valueIndex -> valueIndex != null ? record.textAt(valueIndex) : null))
                                 )));
    }

    @Override
    public final Stream<R> modify(Stream<T> recordStream) {
        return recordStream.flatMap(unpivotFunction);
    }

}
