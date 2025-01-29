package stexfires.io.producer;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public abstract class AbstractReadableProducer<T extends TextRecord> implements ReadableRecordProducer<T> {

    private enum ReadableProducerState {
        OPEN,
        READ_BEFORE,
        READ_RECORDS,
        READ_AFTER,
        CLOSE;

        private static void validateStates(ReadableProducerState currentState, ReadableProducerState newState) throws IllegalStateException {
            Objects.requireNonNull(currentState);
            Objects.requireNonNull(newState);
            if ((currentState.ordinal() + 1) != newState.ordinal()) {
                if (newState != CLOSE) {
                    throw new IllegalStateException("Illegal state of AbstractReadableProducer! " + currentState + " -> " + newState);
                }
            }
        }

        private ReadableProducerState validate(ReadableProducerState currentState) throws IllegalStateException {
            validateStates(currentState, this);
            return this;
        }

    }

    private ReadableProducerState state;

    private final BufferedReader bufferedReader;
    private final @Nullable Consumer<RecordRawData> recordRawDataLogger;

    private @Nullable AbstractRecordRawDataIterator iterator;

    protected AbstractReadableProducer(BufferedReader bufferedReader) {
        this(bufferedReader, null);

    }

    @SuppressWarnings("SameParameterValue")
    protected AbstractReadableProducer(BufferedReader bufferedReader,
                                       @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        Objects.requireNonNull(bufferedReader);
        this.bufferedReader = bufferedReader;
        this.recordRawDataLogger = recordRawDataLogger;

        state = ReadableProducerState.OPEN;
    }

    public final BufferedReader bufferedReader() {
        return bufferedReader;
    }

    public final @Nullable Consumer<RecordRawData> recordRawDataLogger() {
        return recordRawDataLogger;
    }

    @SuppressWarnings("unused")
    protected final long recordCount() throws IllegalStateException {
        // Validate and adjust state
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }

        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().currentRecordIndex();
    }

    @SuppressWarnings("unused")
    protected final List<RecordRawData> firstIgnored() throws IllegalStateException {
        // Validate and adjust state
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }

        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().first();
    }

    @SuppressWarnings("unused")
    protected final List<RecordRawData> lastIgnored() throws IllegalStateException {
        // Validate and adjust state
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }

        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().last();
    }

    protected final @Nullable AbstractRecordRawDataIterator getIterator() {
        return iterator;
    }

    protected abstract AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException;

    protected abstract Optional<T> createRecord(RecordRawData recordRawData) throws UncheckedProducerException;

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException, IllegalStateException {
        // Validate and adjust state
        state = ReadableProducerState.READ_BEFORE.validate(state);

        iterator = createIterator();
        iterator.fillQueue(true);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException, IllegalStateException {
        // Validate and adjust state
        state = ReadableProducerState.READ_RECORDS.validate(state);

        Stream<T> recordStream;
        if ((iterator != null) && iterator.hasNext()) {
            Stream<RecordRawData> rawStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
            if (recordRawDataLogger != null) {
                rawStream = rawStream.peek(recordRawDataLogger);
            }
            recordStream = rawStream.map(this::createRecord)
                                    .flatMap(Optional::stream);
        } else {
            recordStream = Stream.empty();
        }
        return recordStream;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void readAfter() throws ProducerException, UncheckedProducerException, IOException, IllegalStateException {
        // Validate and adjust state
        state = ReadableProducerState.READ_AFTER.validate(state);
    }

    @Override
    public void close() throws IOException, IllegalStateException {
        // Validate and adjust state
        state = ReadableProducerState.CLOSE.validate(state);

        bufferedReader().close();
    }

}
