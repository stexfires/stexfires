package stexfires.io.producer;

import org.jspecify.annotations.Nullable;
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

/**
 * @since 0.1
 */
public abstract class AbstractReadableProducer<T extends TextRecord> implements ReadableRecordProducer<T> {

    private final BufferedReader bufferedReader;
    private final @Nullable Consumer<RecordRawData> recordRawDataLogger;

    private @Nullable AbstractRecordRawDataIterator iterator;

    protected AbstractReadableProducer(BufferedReader bufferedReader,
                                       @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        Objects.requireNonNull(bufferedReader);
        this.bufferedReader = bufferedReader;
        this.recordRawDataLogger = recordRawDataLogger;
    }

    public final BufferedReader bufferedReader() {
        return bufferedReader;
    }

    public final @Nullable Consumer<RecordRawData> recordRawDataLogger() {
        return recordRawDataLogger;
    }

    protected final @Nullable AbstractRecordRawDataIterator getIterator() {
        return iterator;
    }

    protected abstract AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException;

    protected abstract Optional<T> createRecord(RecordRawData recordRawData) throws UncheckedProducerException;

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        iterator = createIterator();
        iterator.fillQueue(true);
    }

    @Override
    public Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException {
        Stream<T> recordStream;
        if (iterator != null && iterator.hasNext()) {
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
    }

    @Override
    public void close() throws IOException {
        bufferedReader().close();
    }

}
