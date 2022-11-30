package stexfires.io;

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
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on
 * record files.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordFiles {

    private RecordFiles() {
    }

    public static <R extends TextRecord, PTR extends R> void readAndConsumeFile(
            ReadableRecordFileSpec<PTR> readableRecordFileSpec,
            RecordConsumer<R> recordConsumer,
            Path path,
            OpenOption... readOptions)
            throws ProducerException, IOException {
        Objects.requireNonNull(readableRecordFileSpec);
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(path);

        try (ReadableRecordProducer<PTR> readableProducer = readableRecordFileSpec.openFileAsProducer(path, readOptions)) {
            RecordIOStreams.readAndConsume(readableProducer, recordConsumer);
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        }
    }

    public static <CTR extends TextRecord, T extends CTR> void writeStreamIntoFile(
            WritableRecordFileSpec<CTR> writableRecordFileSpec,
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
            WritableRecordFileSpec<CTR> writableRecordFileSpec,
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
