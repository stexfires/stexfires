package stexfires.record.modifier;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.message.NotNullRecordMessage;
import stexfires.record.message.RecordMessage;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Aggregator / Group By.
 *
 * @since 0.1
 */
public class GroupModifier<T extends TextRecord, R extends TextRecord> implements RecordStreamModifier<T, R> {

    private final Function<? super T, ?> groupByFunction;
    private final Predicate<List<? super T>> havingPredicate;
    private final Function<List<T>, ? extends R> aggregateFunction;

    public GroupModifier(Function<? super T, ?> groupByFunction,
                         Function<List<T>, ? extends R> aggregateFunction) {
        this(groupByFunction, list -> true, aggregateFunction);
    }

    public GroupModifier(Function<? super T, ?> groupByFunction,
                         Predicate<List<? super T>> havingPredicate,
                         Function<List<T>, ? extends R> aggregateFunction) {
        Objects.requireNonNull(groupByFunction);
        Objects.requireNonNull(havingPredicate);
        Objects.requireNonNull(aggregateFunction);
        this.groupByFunction = groupByFunction;
        this.havingPredicate = havingPredicate;
        this.aggregateFunction = aggregateFunction;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByCategory() {
        return TextRecord::categoryOrElseThrow;
    }

    public static <T extends TextRecord> Function<? super T, Long> groupByRecordId() {
        return TextRecord::recordIdOrElseThrow;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByMessage(NotNullRecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return recordMessage.asFunction();
    }

    public static <T extends KeyRecord> Function<? super T, String> groupByKey() {
        return KeyRecord::key;
    }

    public static <T extends ValueRecord> Function<? super T, String> groupByValue() {
        return t -> t.valueAsOptional().orElseThrow();
    }

    public static <T extends CommentRecord> Function<? super T, String> groupByComment() {
        return t -> t.commentAsOptional().orElseThrow();
    }

    public static <T extends TextRecord> Function<? super T, String> groupByTextAt(int index) {
        return r -> r.textAtAsOptional(index).orElseThrow();
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSize(IntPredicate sizePredicate) {
        Objects.requireNonNull(sizePredicate);
        return list -> sizePredicate.test(list.size());
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSizeEqualTo(int size) {
        return list -> list.size() == size;
    }

    public static <T extends TextRecord> Predicate<List<? super T>> havingSizeGreaterThan(int size) {
        return list -> list.size() > size;
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, @Nullable String> categoryFunction,
                                                                                         Function<List<T>, @Nullable String> valueFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(categoryFunction.apply(list), null,
                valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, @Nullable String> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTexts(Function<List<T>, @Nullable String> categoryFunction,
                                                                                        Function<List<T>, List<@Nullable String>> textsFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(textsFunction);
        return list -> new ManyFieldsRecord(categoryFunction.apply(list), null,
                textsFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTexts(Function<List<T>, List<@Nullable String>> textsFunction) {
        Objects.requireNonNull(textsFunction);
        return list -> new ManyFieldsRecord(textsFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTextsWithMessage(RecordMessage<? super T> textMessage) {
        Objects.requireNonNull(textMessage);
        return list -> new ManyFieldsRecord(list.stream()
                                                .map(textMessage.asFunction())
                                                .toList());
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTextsWithMessage(RecordMessage<? super T> categoryMessage,
                                                                                                   RecordMessage<? super T> textMessage) {
        Objects.requireNonNull(categoryMessage);
        Objects.requireNonNull(textMessage);
        return list -> new ManyFieldsRecord(categoryMessage.createMessage(list.getFirst()), null,
                list.stream()
                    .map(textMessage.asFunction())
                    .toList());
    }

    public static <T extends TextRecord> Function<List<T>, @Nullable String> categoryOfFirstElement() {
        return list -> list.getFirst().category();
    }

    public static <T extends TextRecord> Function<List<T>, @Nullable String> messageOfFirstElement(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return list -> recordMessage.createMessage(list.getFirst());
    }

    public static <T extends TextRecord> Function<List<T>, List<@Nullable String>> collectTexts(Collector<@Nullable String, ?, Optional<String>> textCollector,
                                                                                                Function<Optional<String>, @Nullable String> finalTextMapper) {
        Objects.requireNonNull(textCollector);
        Objects.requireNonNull(finalTextMapper);

        // Own variable to avoid warnings during inspection
        Function<TextField, @Nullable String> groupingTextMapper = TextField::text;

        return list -> list.stream()
                           .flatMap(TextRecord::streamOfFields)
                           .collect(Collectors.collectingAndThen(
                                   Collectors.groupingBy(
                                           TextField::index,
                                           TreeMap::new,
                                           Collectors.mapping(
                                                   groupingTextMapper,
                                                   textCollector)),
                                   map ->
                                           map.values()
                                              .stream()
                                              .map(finalTextMapper)
                                              .toList()));
    }

    public static <T extends TextRecord> Function<List<T>, List<@Nullable String>> collectMaxTexts(Comparator<@Nullable String> textComparator,
                                                                                                   Function<Optional<String>, @Nullable String> finalTextMapper) {
        return collectTexts(Collectors.maxBy(textComparator), finalTextMapper);
    }

    public static <T extends TextRecord> Function<List<T>, List<@Nullable String>> collectMinTexts(Comparator<@Nullable String> textComparator,
                                                                                                   Function<Optional<String>, @Nullable String> finalTextMapper) {
        return collectTexts(Collectors.minBy(textComparator), finalTextMapper);
    }

    @Override
    public final Stream<R> modify(Stream<T> recordStream) {
        return recordStream
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(groupByFunction), r -> r.values().stream()))
                .filter(havingPredicate)
                .map(aggregateFunction);
    }

}
