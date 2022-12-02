package stexfires.io;

import stexfires.record.TextRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.modifier.RecordStreamModifier;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.IOException;
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

    public static <R, PTR extends TextRecord> R read(
            ReadableRecordProducer<PTR> readableRecordProducer,
            Function<Stream<PTR>, R> streamFunction)
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

    public static <R extends TextRecord, PTR extends R> RecordConsumer<R> readAndConsume(
            ReadableRecordProducer<PTR> readableRecordProducer,
            RecordConsumer<R> recordConsumer)
            throws UncheckedProducerException {
        Objects.requireNonNull(readableRecordProducer);
        Objects.requireNonNull(recordConsumer);

        return read(readableRecordProducer, andConsume(recordConsumer));
    }

    // WritableRecordConsumer ------------------------------------------------------------------------------------------

    public static <CTR extends TextRecord, TR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC writeStream(
            WRC writableRecordConsumer,
            Stream<TR> recordStream)
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

    public static <CTR extends TextRecord, TR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC writeRecord(
            WRC writableRecordConsumer,
            TR textRecord)
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

    public static <CTR extends TextRecord, TR extends CTR> String writeStreamIntoString(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            Stream<TR> recordStream)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(recordStream);

        String result;
        try (var consumer = new StringWritableRecordConsumer<>(writableRecordFileSpec)) {
            result = writeStream(consumer, recordStream).consumedString();
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return result;
    }

    public static <CTR extends TextRecord, TR extends CTR> String writeRecordIntoString(
            WritableRecordFileSpec<CTR, ?> writableRecordFileSpec,
            TR textRecord)
            throws UncheckedConsumerException {
        Objects.requireNonNull(writableRecordFileSpec);
        Objects.requireNonNull(textRecord);

        String result;
        try (var consumer = new StringWritableRecordConsumer<>(writableRecordFileSpec)) {
            result = writeRecord(consumer, textRecord).consumedString();
        } catch (IOException e) {
            throw new UncheckedConsumerException(new ConsumerException(e));
        }
        return result;
    }

    // ReadableRecordProducer and WritableRecordConsumer ---------------------------------------------------------------

    public static <CTR extends TextRecord, PTR extends CTR, WRC extends WritableRecordConsumer<CTR>> WRC transfer(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer)
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

    public static <CTR extends TextRecord, PTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>> WRC transferMapped(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer,
            RecordMapper<? super PTR, ? extends CTR> recordMapper)
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

    public static <CTR extends TextRecord, PTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>> WRC transferModified(
            ReadableRecordProducer<PTR> readableRecordProducer,
            WRC writableRecordConsumer,
            RecordStreamModifier<PTR, ? extends CTR> recordStreamModifier)
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
