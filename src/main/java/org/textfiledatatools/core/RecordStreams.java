package org.textfiledatatools.core;

import org.textfiledatatools.core.consumer.LoggerConsumer;
import org.textfiledatatools.core.consumer.RecordConsumer;
import org.textfiledatatools.core.consumer.UncheckedConsumerException;
import org.textfiledatatools.core.logger.SystemOutLogger;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.producer.UncheckedProducerException;

import java.util.Comparator;
import java.util.List;
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

    public static Stream<Record> empty() {
        return Stream.empty();
    }

    public static <T extends Record> Stream<T> of(T record) {
        return Stream.of(record);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> of(T... records) {
        return Stream.of(records);
    }

    public static <T extends Record> Stream<T> produceStream(RecordProducer<T> recordProducer) throws UncheckedProducerException {
        return recordProducer.produceStream();
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

    public static <T extends Record> List<T> collect(Stream<T> recordStream) {
        return recordStream.collect(Collectors.toList());
    }

    public static <T extends Record> List<String> collectMessages(Stream<T> recordStream,
                                                                  RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.toList());
    }

    public static <T extends Record> String joinMessages(Stream<T> recordStream,
                                                         RecordMessage<? super T> recordMessage,
                                                         String delimiter) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(delimiter);
        return recordStream.map(recordMessage::createMessage).collect(Collectors.joining(delimiter));
    }

    public static void printLines(Stream<Record> recordStream) {
        consume(recordStream, new LoggerConsumer<>(new SystemOutLogger()));
    }

    public static <T extends Record> Stream<T> distinct(Stream<T> recordStream,
                                                        RecordMessage<? super T> recordCompareMessage) {
        Objects.requireNonNull(recordStream, "Parameter 'recordStream' must not be null");
        Objects.requireNonNull(recordCompareMessage, "Parameter 'recordCompareMessage' must not be null");
        return recordStream
                .map(record -> new DistinctRecordWrapper<>(record, recordCompareMessage.createMessage(record)))
                .distinct()
                .map(DistinctRecordWrapper::getRecord);
    }

    public static <T extends Record> Stream<T> sorted(Stream<T> recordStream, Comparator<? super T> recordComparator) {
        Objects.requireNonNull(recordStream, "Parameter 'recordStream' must not be null");
        Objects.requireNonNull(recordComparator, "Parameter 'recordComparator' must not be null");
        return recordStream.sorted(recordComparator);
    }

    private static final class DistinctRecordWrapper<T extends Record> {

        private final T record;
        private final String equalsString;

        public DistinctRecordWrapper(T record, String equalsString) {
            Objects.requireNonNull(record);
            Objects.requireNonNull(equalsString);
            this.record = record;
            this.equalsString = equalsString;
        }

        public T getRecord() {
            return record;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DistinctRecordWrapper<?> that = (DistinctRecordWrapper<?>) o;
            return equalsString.equals(that.equalsString);
        }

        @Override
        public int hashCode() {
            return equalsString.hashCode();
        }

    }

}
