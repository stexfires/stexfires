package stexfires.io;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.logger.RecordLogger;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.RecordProducer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on
 * record files ({@code ReadableRecordFile} and {@code WritableRecordFile}).
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings({"LambdaUnfriendlyMethodOverload", "OverloadedVarargsMethod"})
public final class RecordFiles {

    private RecordFiles() {
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> readFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordConsumer);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordConsumer);
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> readFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordMapper<? super T, ? extends R> recordMapper,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(recordConsumer);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordMapper, recordConsumer);
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> RecordConsumer<R> readFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            RecordConsumer<R> recordConsumer)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(recordConsumer);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.read(readableProducer, recordStreamModifier, recordConsumer);
        }

        return recordConsumer;
    }

    public static <R extends TextRecord, T extends R> void logFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordLogger<R> recordLogger)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordLogger);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer()) {
            RecordIOStreams.log(readableProducer, recordLogger);
        }
    }

    public static <R extends TextRecord, T extends R> WritableRecordFile<R, ?> writeFile(
            Stream<T> recordStream,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> writeFile(
            Stream<T> recordStream,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, recordMapper, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> writeFile(
            Stream<T> recordStream,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordStream, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends R> WritableRecordFile<R, ?> writeFile(
            RecordProducer<T> recordProducer,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> writeFile(
            RecordProducer<T> recordProducer,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, recordMapper, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> writeFile(
            RecordProducer<T> recordProducer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(recordProducer);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(recordProducer, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends R> WritableRecordFile<R, ?> writeFile(
            T record,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(writableFile);

        try (WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.write(record, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends R> WritableRecordFile<R, ?> convertFile(
            ReadableRecordFile<T, ?> readableFile,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(writableFile);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> convertFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordMapper<? super T, ? extends R> recordMapper,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableFile);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, recordMapper, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordFile<R, ?> convertFile(
            ReadableRecordFile<T, ?> readableFile,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            WritableRecordFile<R, ?> writableFile,
            OpenOption... writeOptions)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableFile);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableFile);

        try (ReadableRecordProducer<T> readableProducer = readableFile.openProducer();
             WritableRecordConsumer<R> writableConsumer = writableFile.openConsumer(writeOptions)) {
            RecordIOStreams.convert(readableProducer, recordStreamModifier, writableConsumer);
        }

        return writableFile;
    }

    public static <R extends TextRecord, T extends R> void convertSupplier(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, writableConsumer);
        }
    }

    public static <R extends TextRecord, T extends TextRecord> void convertSupplier(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            RecordMapper<? super T, ? extends R> recordMapper,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(recordMapper);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, recordMapper, writableConsumer);
        }
    }

    public static <R extends TextRecord, T extends TextRecord> void convertSupplier(
            Supplier<? extends ReadableRecordProducer<T>> readableProducerSupplier,
            RecordStreamModifier<T, ? extends R> recordStreamModifier,
            Supplier<? extends WritableRecordConsumer<R>> writableConsumerSupplier)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableProducerSupplier);
        Objects.requireNonNull(recordStreamModifier);
        Objects.requireNonNull(writableConsumerSupplier);

        try (ReadableRecordProducer<T> readableProducer = readableProducerSupplier.get();
             WritableRecordConsumer<R> writableConsumer = writableConsumerSupplier.get()) {
            RecordIOStreams.convert(readableProducer, recordStreamModifier, writableConsumer);
        }
    }

}
