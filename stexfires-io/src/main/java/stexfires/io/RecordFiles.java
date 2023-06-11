package stexfires.io;

import stexfires.io.consumer.WritableRecordConsumer;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.io.producer.ReadableRecordProducer;
import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on record files.
 *
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class RecordFiles {

    private RecordFiles() {
    }

    public static <R, PTR extends TextRecord> R readFile(
            ReadableRecordFileSpec<PTR, ?> readableRecordFileSpec,
            Function<Stream<PTR>, R> streamFunction,
            Path path,
            OpenOption... readOptions)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(streamFunction);
        Objects.requireNonNull(path);

        R result;
        try (ReadableRecordProducer<PTR> readableProducer = readableRecordFileSpec.openFileAsProducer(path, readOptions)) {
            result = RecordIOStreams.read(readableProducer, streamFunction);
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        }

        return result;
    }

    public static <R extends TextRecord, PTR extends R> RecordConsumer<R> readAndConsumeFile(
            ReadableRecordFileSpec<PTR, ?> readableRecordFileSpec,
            RecordConsumer<R> recordConsumer,
            Path path,
            OpenOption... readOptions)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(path);

        return readFile(readableRecordFileSpec, RecordIOStreams.andConsume(recordConsumer), path, readOptions);
    }

    public static <CTR extends TextRecord, T extends CTR> void writeStreamIntoFile(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            Stream<T> recordStream,
            Path path,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);
        Objects.requireNonNull(path);

        try (WritableRecordConsumer<CTR> writableConsumer = writableRecordFileSpec.openFileAsConsumer(path, writeOptions)) {
            RecordIOStreams.writeStream(writableConsumer, recordStream);
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }
    }

    public static <CTR extends TextRecord, T extends CTR> void writeRecordIntoFile(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            T textRecord,
            Path path,
            OpenOption... writeOptions)
            throws ConsumerException, IOException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(textRecord);
        Objects.requireNonNull(path);

        try (WritableRecordConsumer<CTR> writableConsumer = writableRecordFileSpec.openFileAsConsumer(path, writeOptions)) {
            RecordIOStreams.writeRecord(writableConsumer, textRecord);
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }
    }

}
