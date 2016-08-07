package stexfires.core;

import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.filter.RecordFilter;
import stexfires.core.logger.RecordLogger;
import stexfires.core.logger.SystemOutLogger;
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
 * This class consists of {@code static} utility methods for operating on record streams.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordStreams {

    private RecordStreams() {
    }

    public static <T extends Record> Stream<T> empty() {
        return Stream.empty();
    }

    public static <T extends Record> Stream<T> of(T record) {
        return Stream.of(record);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> of(T... records) {
        return Stream.of(records);
    }

    public static <T extends Record> Stream<T> ofNullable(T record) {
        return record == null ? Stream.empty() : Stream.of(record);
    }

    public static <T extends Record> Stream<T> produce(RecordProducer<T> recordProducer) throws UncheckedProducerException {
        return recordProducer.produceStream();
    }

    public static <T extends Record> Stream<T> produceAndRestrict(RecordProducer<T> recordProducer,
                                                                  long skipFirst,
                                                                  long limitMaxSize) throws UncheckedProducerException {
        return recordProducer.produceStream().skip(skipFirst).limit(limitMaxSize);
    }

    public static <T extends Record> Stream<T> generate(Supplier<T> recordSupplier) {
        return Stream.generate(recordSupplier);
    }

    public static <T extends Record> Stream<T> concat(Stream<T> firstRecordStream, Stream<T> secondRecordStream) {
        return Stream.concat(firstRecordStream, secondRecordStream);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> concat(Stream<T>... recordStreams) {
        // TODO Validate: concatWithFlatMap or concatWithReduce
        return concatWithFlatMap(recordStreams);
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithFlatMap(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).flatMap(Function.identity());
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithReduce(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).reduce(Stream.empty(), Stream::concat);
    }

    public static <R extends Record, T extends R> RecordConsumer<R> consume(Stream<T> recordStream,
                                                                            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        recordStream.forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> consume(Stream<T> recordStream,
                                                                                 RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                                 RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        recordStreamModifier.modify(recordStream).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends R> RecordConsumer<R> consume(Stream<T> recordStream,
                                                                            RecordLogger<? super T> recordLogger,
                                                                            RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        recordStream.peek(recordLogger::log).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> consume(Stream<T> recordStream,
                                                                                 RecordMapper<? super T, ? extends R> recordMapper,
                                                                                 RecordConsumer<R> recordConsumer) throws UncheckedConsumerException {
        recordStream.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <T extends Record> List<T> collect(Stream<T> recordStream) {
        return recordStream.collect(Collectors.toList());
    }

    public static <T extends Record> List<String> collectMessages(Stream<T> recordStream,
                                                                  RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.toList());
    }

    public static <T extends Record> Map<String, List<String>> collectMessages(Stream<T> recordStream,
                                                                               RecordMessage<? super T> keyMessage,
                                                                               RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        return recordStream.collect(Collectors.groupingBy(keyMessage::createMessage, HashMap::new,
                Collectors.mapping(valueMessage::createMessage, Collectors.toList())));
    }

    public static <T extends Record> String joinMessages(Stream<T> recordStream,
                                                         RecordMessage<? super T> recordMessage,
                                                         String delimiter) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.joining(delimiter));
    }

    public static <T extends Record> void printLines(Stream<T> recordStream) {
        consume(recordStream, new LoggerConsumer<>(new SystemOutLogger()));
    }

    public static <T extends Record> Stream<T> log(Stream<T> recordStream, RecordLogger<? super T> recordLogger) {
        return recordStream.peek(recordLogger::log);
    }

    public static <T extends Record> Stream<T> filter(Stream<T> recordStream, RecordFilter<? super T> recordFilter) {
        return recordStream.filter(recordFilter::isValid);
    }

    public static <T extends Record, R extends Record> Stream<R> map(Stream<T> recordStream, RecordMapper<? super T, ? extends R> recordMapper) {
        return recordStream.map(recordMapper::map);
    }

    public static <T extends Record> Stream<T> sort(Stream<T> recordStream, Comparator<? super T> recordComparator) {
        return recordStream.sorted(recordComparator);
    }

    public static <T extends Record, R extends Record> Stream<R> modify(Stream<T> recordStream, RecordStreamModifier<T, R> recordStreamModifier) {
        return recordStreamModifier.modify(recordStream);
    }

}
