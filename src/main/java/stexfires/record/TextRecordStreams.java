package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.logger.RecordLogger;
import stexfires.record.mapper.RecordMapper;
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
 * @author Mathias Kalb
 * @see TextRecord
 * @see TextRecords
 * @see java.util.stream.Stream
 * @since 0.1
 */
public final class TextRecordStreams {

    private TextRecordStreams() {
    }

    public static <T extends TextRecord> Stream<T> empty() {
        return Stream.empty();
    }

    public static <T extends TextRecord> Stream<T> of(@NotNull T record) {
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

    public static <T extends TextRecord> Stream<T> produce(@NotNull RecordProducer<T> recordProducer)
            throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream();
    }

    public static <T extends TextRecord> Stream<T> produceAndRestrict(@NotNull RecordProducer<T> recordProducer,
                                                                      long skipFirst,
                                                                      long limitMaxSize)
            throws UncheckedProducerException {
        Objects.requireNonNull(recordProducer);
        return recordProducer.produceStream().skip(skipFirst).limit(limitMaxSize);
    }

    public static <T extends TextRecord> Stream<T> generate(@NotNull Supplier<T> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        return Stream.generate(recordSupplier);
    }

    public static <R extends TextRecord> Stream<R> concat(@NotNull Stream<? extends R> firstRecordStream,
                                                          @NotNull Stream<? extends R> secondRecordStream) {
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

    public static <R extends TextRecord, T extends R> RecordConsumer<R> consume(@NotNull Stream<T> recordStream,
                                                                                @NotNull RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordConsumer);
        recordStream.forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> sortAndConsume(@NotNull Stream<T> recordStream,
                                                                                       @NotNull Comparator<? super T> recordComparator,
                                                                                       @NotNull RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordComparator);
        Objects.requireNonNull(recordConsumer);
        recordStream.sorted(recordComparator).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> modifyAndConsume(@NotNull Stream<T> recordStream,
                                                                                                  @NotNull RecordStreamModifier<T, ? extends R> recordStreamModifier,
                                                                                                  @NotNull RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);
        recordStreamModifier.modify(recordStream).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> logAndConsume(@NotNull Stream<T> recordStream,
                                                                                      @NotNull RecordLogger<? super T> recordLogger,
                                                                                      @NotNull RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        Objects.requireNonNull(recordConsumer);
        recordStream.peek(recordLogger::log).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> mapAndConsume(@NotNull Stream<T> recordStream,
                                                                                               @NotNull RecordMapper<? super T, ? extends R> recordMapper,
                                                                                               @NotNull RecordConsumer<R> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);
        recordStream.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
        return recordConsumer;
    }

    public static <T extends TextRecord> List<T> collect(@NotNull Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        return recordStream.collect(Collectors.toList());
    }

    public static <T extends TextRecord> List<String> collectMessages(@NotNull Stream<T> recordStream,
                                                                      @NotNull RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.toList());
    }

    public static <T extends TextRecord> Map<String, List<String>> groupAndCollectMessages(@NotNull Stream<T> recordStream,
                                                                                           @NotNull RecordMessage<? super T> keyMessage,
                                                                                           @NotNull RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        return recordStream.collect(Collectors.groupingBy(keyMessage::createMessage, HashMap::new,
                Collectors.mapping(valueMessage::createMessage, Collectors.toList())));
    }

    public static <T extends TextRecord> String joinMessages(@NotNull Stream<T> recordStream,
                                                             @NotNull RecordMessage<? super T> recordMessage,
                                                             @NotNull String delimiter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.joining(delimiter));
    }

    public static <T extends TextRecord> Stream<String> mapToMessage(@NotNull Stream<T> recordStream,
                                                                     @NotNull RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage);
    }

    public static <T extends TextRecord> void printLines(@NotNull Stream<T> recordStream) {
        Objects.requireNonNull(recordStream);
        consume(recordStream, new SystemOutConsumer<>());
    }

    public static <T extends TextRecord> Stream<T> log(@NotNull Stream<T> recordStream,
                                                       @NotNull RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordLogger);
        return recordStream.peek(recordLogger::log);
    }

    public static <T extends TextRecord> Stream<T> filter(@NotNull Stream<T> recordStream,
                                                          @NotNull RecordFilter<? super T> recordFilter) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordFilter);
        return recordStream.filter(recordFilter::isValid);
    }

    public static <R extends TextRecord, T extends TextRecord> Stream<R> map(@NotNull Stream<T> recordStream,
                                                                             @NotNull RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        return recordStream.map(recordMapper::map);
    }

    public static <T extends TextRecord> Stream<T> sort(@NotNull Stream<T> recordStream,
                                                        @NotNull Comparator<? super T> recordComparator) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordComparator);
        return recordStream.sorted(recordComparator);
    }

    public static <T extends TextRecord, R extends TextRecord> Stream<R> modify(@NotNull Stream<T> recordStream,
                                                                                @NotNull RecordStreamModifier<T, R> recordStreamModifier) {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        return recordStreamModifier.modify(recordStream);
    }

}
