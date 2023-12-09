package stexfires.record.modifier;

import org.jetbrains.annotations.NotNull;
import stexfires.record.CommentRecord;
import stexfires.record.KeyRecord;
import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.message.RecordMessage;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return TextRecord::category;
    }

    public static <T extends TextRecord> Function<? super T, Long> groupByRecordId() {
        return TextRecord::recordId;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByMessage(RecordMessage<? super T> recordMessage) {
        return recordMessage.asFunction();
    }

    public static <T extends KeyRecord> Function<? super T, String> groupByKey() {
        return KeyRecord::key;
    }

    public static <T extends ValueRecord> Function<? super T, String> groupByValue() {
        return ValueRecord::value;
    }

    public static <T extends CommentRecord> Function<? super T, String> groupByComment() {
        return CommentRecord::comment;
    }

    public static <T extends TextRecord> Function<? super T, String> groupByTextAt(int index) {
        return r -> r.textAt(index);
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

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> categoryFunction,
                                                                                         Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(categoryFunction.apply(list), null,
                valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, ValueRecord> aggregateToValue(Function<List<T>, String> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return list -> new ValueFieldRecord(valueFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTexts(Function<List<T>, String> categoryFunction,
                                                                                        Function<List<T>, List<String>> textsFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(textsFunction);
        return list -> new ManyFieldsRecord(categoryFunction.apply(list), null,
                textsFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTexts(Function<List<T>, List<String>> textsFunction) {
        Objects.requireNonNull(textsFunction);
        return list -> new ManyFieldsRecord(textsFunction.apply(list));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTextsWithMessage(RecordMessage<? super T> textMessage) {
        Objects.requireNonNull(textMessage);
        return list -> new ManyFieldsRecord(list.stream()
                                                .map(textMessage.asFunction())
                                                .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, TextRecord> aggregateToTextsWithMessage(RecordMessage<? super T> categoryMessage,
                                                                                                   RecordMessage<? super T> textMessage) {
        Objects.requireNonNull(categoryMessage);
        Objects.requireNonNull(textMessage);
        return list -> new ManyFieldsRecord(categoryMessage.createMessage(list.getFirst()), null,
                list.stream()
                    .map(textMessage.asFunction())
                    .collect(Collectors.toList()));
    }

    public static <T extends TextRecord> Function<List<T>, String> categoryOfFirstElement() {
        return list -> list.getFirst().category();
    }

    public static <T extends TextRecord> Function<List<T>, String> messageOfFirstElement(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return list -> recordMessage.createMessage(list.getFirst());
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> collectTexts(Collector<String, ?, Optional<String>> textCollector,
                                                                                      String nullText) {
        return list -> list.stream()
                           .flatMap(TextRecord::streamOfFields)
                           .collect(Collectors.collectingAndThen(
                                   Collectors.groupingBy(
                                           TextField::index,
                                           TreeMap::new,
                                           Collectors.mapping(
                                                   TextField::text,
                                                   textCollector)),
                                   map -> map.values()
                                             .stream()
                                             .map(o -> o.orElse(nullText))
                                             .collect(Collectors.toList())));
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> maxTextNullsFirst(String nullText) {
        return collectTexts(Collectors.maxBy(Comparator.nullsFirst(Comparator.naturalOrder())),
                nullText);
    }

    public static <T extends TextRecord> Function<List<T>, List<String>> minTextNullsLast(String nullText) {
        return collectTexts(Collectors.minBy(Comparator.nullsLast(Comparator.naturalOrder())),
                nullText);
    }

    @Override
    public final @NotNull Stream<R> modify(Stream<T> recordStream) {
        return recordStream
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(groupByFunction), r -> r.values().stream()))
                .filter(havingPredicate)
                .map(aggregateFunction);
    }

}
