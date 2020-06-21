package stexfires.io;

import stexfires.core.TextRecord;
import stexfires.core.TextRecordStreams;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.ProducerException;
import stexfires.core.producer.RecordProducer;
import stexfires.core.producer.UncheckedProducerException;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on
 * readable record producers ({@code ReadableRecordProducer}) and
 * writable record consumers ({@code WritableRecordConsumer}).
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings({"LambdaUnfriendlyMethodOverload", "UnusedReturnValue"})
public final class RecordIOStreams {

    private RecordIOStreams() {
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordConsumer);

        try {
            readableProducer.readBefore();
            readableProducer.produceStream().forEachOrdered(recordConsumer::consume);
            readableProducer.readAfter();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);

        try {
            readableProducer.readBefore();
            readableProducer.produceStream().map(recordMapper::map).forEachOrdered(recordConsumer::consume);
            readableProducer.readAfter();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);

        try {
            readableProducer.readBefore();
            recordStreamModifier.modify(readableProducer.produceStream()).forEachOrdered(recordConsumer::consume);
            readableProducer.readAfter();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> void log(
            ReadableRecordProducer<T> readableProducer,
            RecordLogger<R> recordLogger)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordLogger);

        read(readableProducer, new LoggerConsumer<>(recordLogger));
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordStream.forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordStream.map(recordMapper::map).forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordStreamModifier.modify(recordStream).forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordProducer.produceStream().forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordProducer.produceStream().map(recordMapper::map).forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            recordStreamModifier.modify(recordProducer.produceStream()).forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            T record,
            WritableRecordConsumer<R> writableConsumer)
            throws ConsumerException, IOException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(writableConsumer);

        try {
            writableConsumer.writeBefore();
            TextRecordStreams.of(record).forEachOrdered(writableConsumer::consume);
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            WritableRecordConsumer<R> writableConsumer)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(writableConsumer);

        try {
            readableProducer.readBefore();
            writableConsumer.writeBefore();
            readableProducer.produceStream().forEachOrdered(writableConsumer::consume);
            readableProducer.readAfter();
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        try {
            readableProducer.readBefore();
            writableConsumer.writeBefore();
            readableProducer.produceStream().map(recordMapper::map).forEachOrdered(writableConsumer::consume);
            readableProducer.readAfter();
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        try {
            readableProducer.readBefore();
            writableConsumer.writeBefore();
            recordStreamModifier.modify(readableProducer.produceStream()).forEachOrdered(writableConsumer::consume);
            readableProducer.readAfter();
            writableConsumer.writeAfter();
            writableConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableConsumer;
    }

}
