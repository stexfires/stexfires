package stexfires.io.internal;

import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordProducer;
import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
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

    private final BufferedReader reader;
    private final Consumer<RecordRawData> recordRawDataLogger;

    private AbstractRecordRawDataIterator iterator;
    private ReadableProducerState state;

    protected AbstractReadableProducer(BufferedReader reader) {
        this(reader, null);
    }

    @SuppressWarnings("SameParameterValue")
    protected AbstractReadableProducer(BufferedReader reader,
                                       @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        Objects.requireNonNull(reader);
        this.reader = reader;
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
    public Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException {
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
        reader.close();
    }

    protected final BufferedReader getReader() {
        return reader;
    }

}
