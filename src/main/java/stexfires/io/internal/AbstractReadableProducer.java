package stexfires.io.internal;

import stexfires.core.Record;
import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;
import stexfires.io.ReadableRecordProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static stexfires.io.internal.ReadableProducerState.OPEN;
import static stexfires.io.internal.ReadableProducerState.READ_AFTER;
import static stexfires.io.internal.ReadableProducerState.READ_BEFORE;
import static stexfires.io.internal.ReadableProducerState.READ_RECORDS;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractReadableProducer<T extends Record> implements ReadableRecordProducer<T> {

    protected final BufferedReader reader;
    protected final Consumer<RecordRawData> recordRawDataLogger;

    protected AbstractRecordRawDataIterator iterator;
    protected ReadableProducerState state;

    protected AbstractReadableProducer(BufferedReader reader) {
        this(reader, null);
    }

    protected AbstractReadableProducer(BufferedReader reader,
                                       Consumer<RecordRawData> recordRawDataLogger) {
        Objects.requireNonNull(reader);
        this.reader = reader;
        this.recordRawDataLogger = recordRawDataLogger;
        state = OPEN;
    }

    @Override
    public void readBefore() throws IOException {
        state = READ_BEFORE.validate(state);
        iterator = createIterator();
        iterator.fillQueue(true);
    }

    protected abstract AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException;

    protected abstract Optional<T> createRecord(RecordRawData recordRawData) throws UncheckedProducerException;

    @Override
    public Stream<T> readRecords() throws IOException, ProducerException {
        state = READ_RECORDS.validate(state);
        Stream<T> recordStream;
        if (iterator.hasNext()) {
            Stream<RecordRawData> rawStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE), false);
            if (recordRawDataLogger != null) {
                rawStream = rawStream.peek(recordRawDataLogger);
            }
            // TODO Java 9: .flatMap(Optional::stream)
            recordStream = rawStream.map(this::createRecord)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get);
        } else {
            recordStream = Stream.empty();
        }
        return recordStream;
    }

    @Override
    public void readAfter() throws IOException {
        state = READ_AFTER.validate(state);
    }

    @Override
    public void close() throws IOException {
        state.validateNotClosed();
        reader.close();
    }

}
