package stexfires.io;

import stexfires.core.TextRecord;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.RecordProducer;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on
 * readable record producers and writable record consumers.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordIOStreams {

    private RecordIOStreams() {
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordConsumer);

        readableProducer.readBefore();
        readableProducer.produceStream().forEachOrdered(recordConsumer::consume);
        readableProducer.readAfter();

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);

        readableProducer.readBefore();
        readableProducer.produceStream().map(recordMapper::map).forEachOrdered(recordConsumer::consume);
        readableProducer.readAfter();

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> read(
            ReadableRecordProducer<T> readableProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);

        readableProducer.readBefore();
        recordStreamModifier.modify(readableProducer.produceStream()).forEachOrdered(recordConsumer::consume);
        readableProducer.readAfter();

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> RecordLogger<R> log(
            ReadableRecordProducer<T> readableProducer,
            RecordLogger<R> recordLogger) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordLogger);

        LoggerConsumer<R> consumer = new LoggerConsumer<>(recordLogger);

        readableProducer.readBefore();
        readableProducer.produceStream().forEachOrdered(consumer::consume);
        readableProducer.readAfter();

        return recordLogger;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordStream.forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordStream.map(recordMapper::map).forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            Stream<T> recordStream,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordStreamModifier.modify(recordStream).forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordProducer.produceStream().forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordProducer.produceStream().map(recordMapper::map).forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> write(
            RecordProducer<T> recordProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        recordStreamModifier.modify(recordProducer.produceStream()).forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> write(
            T record,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(writableConsumer);

        writableConsumer.writeBefore();
        RecordStreams.of(record).forEachOrdered(writableConsumer::consume);
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(writableConsumer);

        readableProducer.readBefore();
        writableConsumer.writeBefore();
        readableProducer.produceStream().forEachOrdered(writableConsumer::consume);
        readableProducer.readAfter();
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumer);

        readableProducer.readBefore();
        writableConsumer.writeBefore();
        readableProducer.produceStream().map(recordMapper::map).forEachOrdered(writableConsumer::consume);
        readableProducer.readAfter();
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> convert(
            ReadableRecordProducer<T> readableProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordConsumer<R> writableConsumer) throws IOException {
        Objects.requireNonNull(readableProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumer);

        readableProducer.readBefore();
        writableConsumer.writeBefore();
        recordStreamModifier.modify(readableProducer.produceStream()).forEachOrdered(writableConsumer::consume);
        readableProducer.readAfter();
        writableConsumer.writeAfter();
        writableConsumer.flush();

        return writableConsumer;
    }

}
