package stexfires.io.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.ReadableRecordProducer;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static stexfires.io.internal.ReadableProducerState.CLOSE;
import static stexfires.io.internal.ReadableProducerState.OPEN;
import static stexfires.io.internal.ReadableProducerState.READ_AFTER;
import static stexfires.io.internal.ReadableProducerState.READ_BEFORE;
import static stexfires.io.internal.ReadableProducerState.READ_RECORDS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("RedundantThrows")
public abstract class AbstractReadableProducer<T extends TextRecord> implements ReadableRecordProducer<T> {

    private final BufferedReader bufferedReader;
    private final Consumer<RecordRawData> recordRawDataLogger;

    private AbstractRecordRawDataIterator iterator;
    private ReadableProducerState state;

    protected AbstractReadableProducer(BufferedReader bufferedReader) {
        this(bufferedReader, null);
    }

    @SuppressWarnings("SameParameterValue")
    protected AbstractReadableProducer(BufferedReader bufferedReader,
                                       @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        Objects.requireNonNull(bufferedReader);
        this.bufferedReader = bufferedReader;
        this.recordRawDataLogger = recordRawDataLogger;
        state = OPEN;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_BEFORE.validate(state);
        iterator = createIterator();
        iterator.fillQueue(true);
    }

    protected abstract AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException;

    protected abstract Optional<T> createRecord(RecordRawData recordRawData) throws UncheckedProducerException;

    @Override
    public @NotNull Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_RECORDS.validate(state);
        Stream<T> recordStream;
        if (iterator.hasNext()) {
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

    @Override
    public void readAfter() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_AFTER.validate(state);
    }

    @Override
    public void close() throws IOException {
        state = CLOSE.validate(state);
        bufferedReader.close();
    }

    protected final BufferedReader bufferedReader() {
        return bufferedReader;
    }

    protected final long recordCount() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        return iterator.currentRecordIndex();
    }

    protected final List<RecordRawData> firstIgnored() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        return iterator.first();
    }

    protected final List<RecordRawData> lastIgnored() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        return iterator.last();
    }

}
