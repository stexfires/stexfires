package stexfires.io;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.RecordProducer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on record files.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordFiles {

    private RecordFiles() {
    }

    public static <R extends Record, T extends R> RecordConsumer<R> read(
            ReadableRecordFile<T, ?> readableFile,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordConsumer);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().forEachOrdered(recordConsumer::consume);
            producer.readAfter();
        }
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> read(
            ReadableRecordFile<T, ?> readableFile,
            RecordMapper<? super T, ? extends R> recordMapper,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().map(recordMapper::map).forEachOrdered(recordConsumer::consume);
            producer.readAfter();
        }
        return recordConsumer;
    }

    public static <R extends Record, T extends Record> RecordConsumer<R> read(
            ReadableRecordFile<T, ?> readableFile,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            RecordConsumer<R> recordConsumer) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            recordStreamModifier.modify(producer.produceStream()).forEachOrdered(recordConsumer::consume);
            producer.readAfter();
        }
        return recordConsumer;
    }

    public static <R extends Record, T extends R> RecordLogger<R> log(
            ReadableRecordFile<T, ?> readableFile,
            RecordLogger<R> recordLogger) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordLogger);
        LoggerConsumer<R> consumer = new LoggerConsumer<>(recordLogger);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer()) {
            producer.readBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            producer.readAfter();
        }
        return recordLogger;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            Stream<T> recordStream,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordStream.forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> write(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordStream.map(recordMapper::map).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> write(
            Stream<T> recordStream,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordStreamModifier.modify(recordStream).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            RecordProducer<T> recordProducer,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordProducer.produceStream().forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> write(
            RecordProducer<T> recordProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordProducer.produceStream().map(recordMapper::map).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> write(
            RecordProducer<T> recordProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            recordStreamModifier.modify(recordProducer.produceStream()).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            T record,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(writableFile);
        try (WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            consumer.writeBefore();
            RecordStreams.of(record).forEachOrdered(consumer::consume);
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> convert(
            ReadableRecordFile<T, ?> readableFile,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(writableFile);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            producer.readBefore();
            consumer.writeBefore();
            producer.produceStream().forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> convert(
            ReadableRecordFile<T, ?> readableFile,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            producer.readBefore();
            consumer.writeBefore();
            producer.produceStream().map(recordMapper::map).forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
        return writableFile;
    }

    public static <R extends Record, T extends Record> WritableRecordFile<R, ?> convert(
            ReadableRecordFile<T, ?> readableFile,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);
        try (ReadableRecordProducer<T> producer = readableFile.openProducer();
             WritableRecordConsumer<R> consumer = writableFile.openConsumer(writeOptions)) {
            producer.readBefore();
            consumer.writeBefore();
            recordStreamModifier.modify(producer.produceStream()).forEachOrdered(consumer::consume);
            producer.readAfter();
            consumer.writeAfter();
        }
        return writableFile;
    }

}
