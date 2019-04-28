package stexfires.core;

import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.filter.RecordFilter;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.message.RecordMessage;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.RecordProducer;
import stexfires.core.producer.UncheckedProducerException;

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
 * for operating on {@link Record} {@link Stream}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.Record
 * @see stexfires.core.Records
 * @see java.util.stream.Stream
 * @since 0.1
 */
public final class RecordStreams {

    private RecordStreams() {
    }

    public static <T extends Record> Stream<T> empty() {
        return Stream.empty();
    }

    public static <T extends Record> Stream<T> of(T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> of(T... records) {
        Objects.requireNonNull(records);
        return Stream.of(records);
    }

    public static <T extends Record> Stream<T> ofNullable(T record) {
        return record == null ? Stream.empty() : Stream.of(record);
    }

    public static <T extends Record> Stream<T> produce(RecordProducer<T> recordProducer) throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream();
    }

    public static <T extends Record> Stream<T> produceAndRestrict(RecordProducer<T> recordProducer,
                                                                  long skipFirst,
                                                                  long limitMaxSize) throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream().skip(skipFirst).limit(limitMaxSize);
    }

    public static <T extends Record> Stream<T> generate(Supplier<T> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        return Stream.generate(recordSupplier);
    }

    public static <R extends Record> Stream<R> concat(Stream<? extends R> firstRecordStream,
                                                      Stream<? extends R> secondRecordStream) {
        Objects.requireNonNull(firstRecordStream);
        Objects.requireNonNull(secondRecordStream);
        return Stream.concat(firstRecordStream, secondRecordStream);
    }

    @SafeVarargs
    public static <R extends Record> Stream<R> concat(Stream<? extends R>... recordStreams) {
        Objects.requireNonNull(recordStreams);
        return Stream.of(recordStreams).flatMap(Function.identity());
    }

    public static <R extends Record, T extends R> RecordConsumer<R> consume(
            Stream<T> recordStream,
            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordConsumer);
        recordStream.forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> modifyAndConsume(
            Stream<T> recordStream,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);
        recordStreamModifier.modify(recordStream).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends R> RecordConsumer<R> logAndConsume(
            Stream<T> recordStream,
            RecordLogger<? super T> recordLogger,
            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        Objects.requireNonNull(recordConsumer);
        recordStream.peek(recordLogger::log).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> mapAndConsume(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper,
            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);
        recordStream.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <T extends Record> List<T> collect(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return recordStream.collect(Collectors.toList());
    }

    public static <T extends Record> List<String> collectMessages(
            Stream<T> recordStream,
            RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.toList());
    }

    public static <T extends Record> Map<String, List<String>> groupAndCollectMessages(
            Stream<T> recordStream,
            RecordMessage<? super T> keyMessage,
            RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        return recordStream.collect(Collectors.groupingBy(keyMessage::createMessage, HashMap::new,
                Collectors.mapping(valueMessage::createMessage, Collectors.toList())));
    }

    public static <T extends Record> String joinMessages(
            Stream<T> recordStream,
            RecordMessage<? super T> recordMessage,
            String delimiter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.joining(delimiter));
    }

    public static <T extends Record> void printLines(Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        consume(recordStream, new SystemOutConsumer<>());
    }

    public static <T extends Record> Stream<T> log(
            Stream<T> recordStream,
            RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        return recordStream.peek(recordLogger::log);
    }

    public static <T extends Record> Stream<T> filter(
            Stream<T> recordStream,
            RecordFilter<? super T> recordFilter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordFilter);
        return recordStream.filter(recordFilter::isValid);
    }

    public static <R extends Record, T extends Record> Stream<R> map(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        return recordStream.map(recordMapper::map);
    }

    public static <T extends Record> Stream<T> sort(
            Stream<T> recordStream,
            Comparator<? super T> recordComparator) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordComparator);
        return recordStream.sorted(recordComparator);
    }

    public static <T extends Record, R extends Record> Stream<R> modify(
            Stream<T> recordStream,
            RecordStreamModifier<T, R> recordStreamModifier) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        return recordStreamModifier.modify(recordStream);
    }

}
