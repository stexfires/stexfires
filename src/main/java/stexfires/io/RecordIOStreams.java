package stexfires.io;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on
 * {@code ReadableRecordProducer} and {@code WritableRecordConsumer}.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public final class RecordIOStreams {

    private RecordIOStreams() {
    }

    // ReadableRecordProducer ------------------------------------------------------------------------------------------

    public static <R, T extends TextRecord> R read(
            ReadableRecordProducer<T> readableRecordProducer,
            Function<Stream<T>, R> streamFunction)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(streamFunction);

        R result;
        try {
            readableRecordProducer.readBefore();
            result = streamFunction.apply(readableRecordProducer.produceStream());
            readableRecordProducer.readAfter();
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException(e));
        }

        return result;
    }

    public static <R extends TextRecord, T extends R> Function<Stream<T>, RecordConsumer<R>> andConsume(
            RecordConsumer<R> recordConsumer) {
        Objects.requireNonNull(recordConsumer);

        return s -> {
            s.forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, RecordConsumer<R>> andConsumeMapped(
            RecordConsumer<R> recordConsumer,
            RecordMapper<? super T, ? extends R> recordMapper) {
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(recordMapper);

        return s -> {
            s.map(recordMapper::map).forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, RecordConsumer<R>> andConsumeModified(
            RecordConsumer<R> recordConsumer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier) {
        Objects.requireNonNull(recordConsumer);
        Objects.requireNonNull(recordStreamModifier);

        return s -> {
            recordStreamModifier.modify(s).forEachOrdered(recordConsumer::consume);
            return recordConsumer;
        };
    }

    public static <R, T extends TextRecord> Function<Stream<T>, R> andCollect(
            Collector<? super T, ?, R> collector) {
        Objects.requireNonNull(collector);

        return s -> s.collect(collector);
    }

    public static <R, V extends TextRecord, T extends TextRecord> Function<Stream<T>, R> andCollectMapped(
            Collector<? super V, ?, R> collector,
            RecordMapper<? super T, V> recordMapper) {
        Objects.requireNonNull(collector);
        Objects.requireNonNull(recordMapper);

        return s -> s.map(recordMapper::map).collect(collector);
    }

    public static <R, V extends TextRecord, T extends TextRecord> Function<Stream<T>, R> andCollectModified(
            Collector<? super V, ?, R> collector,
            RecordStreamModifier<T, V> recordStreamModifier) {
        Objects.requireNonNull(collector);
        Objects.requireNonNull(recordStreamModifier);

        return s -> recordStreamModifier.modify(s).collect(collector);
    }

    public static <T extends TextRecord> Function<Stream<T>, Optional<T>> andFindFirst() {
        return Stream::findFirst;
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, Optional<R>> andFindFirstMapped(
            RecordMapper<? super T, R> recordMapper) {
        Objects.requireNonNull(recordMapper);

        return s -> s.map(recordMapper::map).findFirst();
    }

    public static <R extends TextRecord, T extends TextRecord> Function<Stream<T>, Optional<R>> andFindFirstModified(
            RecordStreamModifier<T, R> recordStreamModifier) {
        Objects.requireNonNull(recordStreamModifier);

        return s -> recordStreamModifier.modify(s).findFirst();
    }

    public static <R extends TextRecord, T extends R> RecordConsumer<R> readAndConsume(
            ReadableRecordProducer<T> readableRecordProducer,
            RecordConsumer<R> recordConsumer)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(recordConsumer);

        return read(readableRecordProducer, andConsume(recordConsumer));
    }

    // WritableRecordConsumer ------------------------------------------------------------------------------------------

    public static <CTR extends TextRecord, T extends CTR> WritableRecordConsumer<CTR> writeStream(
            WritableRecordConsumer<CTR> writableRecordConsumer,
            Stream<T> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordStream);

        try {
            writableRecordConsumer.writeBefore();
            recordStream.forEachOrdered(writableRecordConsumer::consume);
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (ConsumerException e) {
            throw new UncheckedConsumerException(e);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, T extends CTR> WritableRecordConsumer<CTR> writeRecord(
            WritableRecordConsumer<CTR> writableRecordConsumer,
            T textRecord)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(textRecord);

        try {
            writableRecordConsumer.writeBefore();
            writableRecordConsumer.writeRecord(textRecord);
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (ConsumerException e) {
            throw new UncheckedConsumerException(e);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }

        return writableRecordConsumer;
    }

    public static <CTR extends TextRecord, T extends CTR> String writeStreamIntoString(
            WritableRecordFileSpec<CTR> writableRecordFileSpec,
            Stream<T> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);

        StringWriter stringWriter = new StringWriter();
        try (WritableRecordConsumer<CTR> consumer = writableRecordFileSpec.consumer(new BufferedWriter((stringWriter)))) {
            writeStream(consumer, recordStream);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return stringWriter.toString();
    }

    public static <CTR extends TextRecord, T extends CTR> String writeRecordIntoString(
            WritableRecordFileSpec<CTR> writableRecordFileSpec,
            T textRecord)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(textRecord);

        StringWriter stringWriter = new StringWriter();
        try (WritableRecordConsumer<CTR> consumer = writableRecordFileSpec.consumer(new BufferedWriter((stringWriter)))) {
            writeRecord(consumer, textRecord);
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return stringWriter.toString();
    }

    // ReadableRecordProducer and WritableRecordConsumer ---------------------------------------------------------------

    public static <R extends TextRecord, T extends R> WritableRecordConsumer<R> transfer(
            ReadableRecordProducer<T> readableRecordProducer,
            WritableRecordConsumer<R> writableRecordConsumer)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            readableRecordProducer.produceStream().forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> transferMapped(
            ReadableRecordProducer<T> readableRecordProducer,
            WritableRecordConsumer<R> writableRecordConsumer,
            RecordMapper<? super T, ? extends R> recordMapper)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordMapper);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            readableRecordProducer.produceStream().map(recordMapper::map).forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

    public static <R extends TextRecord, T extends TextRecord> WritableRecordConsumer<R> transferModified(
            ReadableRecordProducer<T> readableRecordProducer,
            WritableRecordConsumer<R> writableRecordConsumer,
            RecordStreamModifier<T, ? extends R> recordStreamModifier)
            throws ProducerException, ConsumerException, IOException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(writableRecordConsumer);
        Objects.requireNonNull(recordStreamModifier);

        try {
            readableRecordProducer.readBefore();
            writableRecordConsumer.writeBefore();
            recordStreamModifier.modify(readableRecordProducer.produceStream()).forEachOrdered(writableRecordConsumer::consume);
            readableRecordProducer.readAfter();
            writableRecordConsumer.writeAfter();
            writableRecordConsumer.flush();
        } catch (UncheckedProducerException e) {
            throw e.getCause();
        } catch (UncheckedConsumerException e) {
            throw e.getCause();
        }

        return writableRecordConsumer;
    }

}
