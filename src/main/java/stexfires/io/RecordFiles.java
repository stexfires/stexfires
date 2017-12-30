package stexfires.io;

import stexfires.core.Record;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.modifier.RecordStreamModifier;
import stexfires.core.producer.RecordProducer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.function.Supplier;
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

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordConsumer);
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

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordMapper, recordConsumer);
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

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordStreamModifier, recordConsumer);
        }

        return recordConsumer;
    }

    public static <R extends Record, T extends R> RecordLogger<R> log(
            ReadableRecordFile<T, ?> readableFile,
            RecordLogger<R> recordLogger) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordLogger);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.log(readableProducer, recordLogger);
        }

        return recordLogger;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            Stream<T> recordStream,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, writableConsumer);
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

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, recordMapper, writableConsumer);
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

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            RecordProducer<T> recordProducer,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, writableConsumer);
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

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, recordMapper, writableConsumer);
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

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> write(
            T record,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(record, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends Record, T extends R> WritableRecordFile<R, ?> convert(
            ReadableRecordFile<T, ?> readableFile,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(writableFile);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, writableConsumer);
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

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, recordMapper, writableConsumer);
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

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends Record, T extends R> void convert(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier) throws IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, writableConsumer);
        }
    }

    public static <R extends Record, T extends Record> void convert(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            RecordMapper<? super T, ? extends R> recordMapper,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier) throws IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, recordMapper, writableConsumer);
        }
    }

    public static <R extends Record, T extends Record> void convert(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier) throws IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, recordStreamModifier, writableConsumer);
        }
    }

}
