package stexfires.record;

import org.jspecify.annotations.Nullable;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.logger.RecordLogger;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.NotNullRecordMessage;
import stexfires.record.message.RecordMessage;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.producer.RecordProducer;
import stexfires.record.producer.UncheckedProducerException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@link TextRecord} {@link Stream}s.
 *
 * @see TextRecord
 * @see TextRecords
 * @see java.util.stream.Stream
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class TextRecordStreams {

    private TextRecordStreams() {
    }

    public static <T extends TextRecord> Stream<T> empty() {
        return Stream.empty();
    }

    public static <T extends TextRecord> Stream<T> of(T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> Stream<T> of(T... records) {
        Objects.requireNonNull(records);
        return Stream.of(records);
    }

    public static <T extends TextRecord> Stream<T> ofNullable(@Nullable T record) {
        return Stream.ofNullable(record);
    }

    public static <T extends TextRecord> Stream<T> produce(RecordProducer<T> recordProducer)
            throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream();
    }

    public static <T extends TextRecord> Stream<T> produceAndRestrict(RecordProducer<T> recordProducer,
                                                                      long skipFirst,
                                                                      long limitMaxSize)
            throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream().skip(skipFirst).limit(limitMaxSize);
    }

    public static <T extends TextRecord> Stream<T> generate(Supplier<T> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        return Stream.generate(recordSupplier);
    }

    public static <R extends TextRecord> Stream<R> concat(Stream<? extends R> firstRecordStream,
                                                          Stream<? extends R> secondRecordStream) {
        Objects.requireNonNull(firstRecordStream);
        Objects.requireNonNull(secondRecordStream);
        return Stream.concat(firstRecordStream, secondRecordStream);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <R extends TextRecord> Stream<R> concat(Stream<? extends R>... recordStreams) {
        Objects.requireNonNull(recordStreams);
        return Stream.of(recordStreams).flatMap(Function.identity());
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> consume(Stream<T> recordStream,
                                                                                RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordConsumer);
        recordStream.forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> sortAndConsume(Stream<T> recordStream,
                                                                                       Comparator<? super T> recordComparator,
                                                                                       RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordComparator);
        Objects.requireNonNull(recordConsumer);
        recordStream.sorted(recordComparator).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> modifyAndConsume(Stream<T> recordStream,
                                                                                                  RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                                                  RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);
        recordStreamModifier.modify(recordStream).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> logAndConsume(Stream<T> recordStream,
                                                                                      RecordLogger<? super T> recordLogger,
                                                                                      RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        Objects.requireNonNull(recordConsumer);
        recordStream.peek(recordLogger::log).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> mapAndConsume(Stream<T> recordStream,
                                                                                               RecordMapper<? super T, ? extends R> recordMapper,
                                                                                               RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);
        recordStream.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <T extends TextRecord> List<T> collect(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return recordStream.toList();
    }

    public static <T extends TextRecord> List<@Nullable String> collectMessages(Stream<T> recordStream,
                                                                                RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage).toList();
    }

    public static <T extends TextRecord> Map<String, List<@Nullable String>> groupAndCollectMessages(Stream<T> recordStream,
                                                                                                     NotNullRecordMessage<? super T> keyMessage,
                                                                                                     RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        return recordStream.collect(Collectors.groupingBy(keyMessage::createMessage, HashMap::new,
                Collectors.mapping(valueMessage::createMessage, Collectors.toList())));
    }

    public static <T extends TextRecord> String joinMessages(Stream<T> recordStream,
                                                             RecordMessage<? super T> recordMessage,
                                                             String delimiter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.joining(delimiter));
    }

    public static <T extends TextRecord> Stream<@Nullable String> mapToMessage(Stream<T> recordStream,
                                                                               RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage);
    }

    public static <T extends TextRecord> void printLines(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        consume(recordStream, new SystemOutConsumer<>());
    }

    public static <T extends TextRecord> Stream<T> log(Stream<T> recordStream,
                                                       RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        return recordStream.peek(recordLogger::log);
    }

    public static <T extends TextRecord> Stream<T> filter(Stream<T> recordStream,
                                                          RecordFilter<? super T> recordFilter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordFilter);
        return recordStream.filter(recordFilter::isValid);
    }

    public static <R extends TextRecord, T extends TextRecord> Stream<R> map(Stream<T> recordStream,
                                                                             RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        return recordStream.map(recordMapper::map);
    }

    public static <T extends TextRecord> Stream<T> sort(Stream<T> recordStream,
                                                        Comparator<? super T> recordComparator) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordComparator);
        return recordStream.sorted(recordComparator);
    }

    public static <T extends TextRecord, R extends TextRecord> Stream<R> modify(Stream<T> recordStream,
                                                                                RecordStreamModifier<T, R> recordStreamModifier) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        return recordStreamModifier.modify(recordStream);
    }

}
