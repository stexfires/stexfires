package stexfires.io.internal;

import org.jspecify.annotations.Nullable;
import stexfires.io.producer.AbstractReadableProducer;
import stexfires.io.producer.RecordRawData;
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
@SuppressWarnings("RedundantThrows")
public abstract class AbstractInternalReadableProducer<T extends TextRecord> extends AbstractReadableProducer<T> {

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
                    throw new IllegalStateException("Wrong ReadableProducerState! " + currentState + " -> " + newState);
                }
            }
        }

        final ReadableProducerState validate(ReadableProducerState currentState) throws IllegalStateException {
            validateStates(currentState, this);
            return this;
        }

    }

    private ReadableProducerState state;

    protected AbstractInternalReadableProducer(BufferedReader bufferedReader) {
        this(bufferedReader, null);
    }

    @SuppressWarnings("SameParameterValue")
    protected AbstractInternalReadableProducer(BufferedReader bufferedReader,
                                               @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        super(bufferedReader, recordRawDataLogger);
        state = ReadableProducerState.OPEN;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        state = ReadableProducerState.READ_BEFORE.validate(state);
        super.readBefore();
    }

    @Override
    public Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException {
        state = ReadableProducerState.READ_RECORDS.validate(state);
        return super.readRecords();
    }

    @Override
    public void readAfter() throws ProducerException, UncheckedProducerException, IOException {
        state = ReadableProducerState.READ_AFTER.validate(state);
        super.readAfter();
    }

    @Override
    public void close() throws IOException {
        state = ReadableProducerState.CLOSE.validate(state);
        super.close();
    }

    protected final long recordCount() {
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().currentRecordIndex();
    }

    protected final List<RecordRawData> firstIgnored() {
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().first();
    }

    protected final List<RecordRawData> lastIgnored() {
        if ((state != ReadableProducerState.READ_AFTER) && (state != ReadableProducerState.CLOSE)) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().last();
    }

}
