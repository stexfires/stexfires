package stexfires.io.internal;

import org.jspecify.annotations.Nullable;
import stexfires.io.producer.AbstractReadableProducer;
import stexfires.io.producer.RecordRawData;
import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static stexfires.io.internal.ReadableProducerState.*;

/**
 * @since 0.1
 */
@SuppressWarnings("RedundantThrows")
public abstract sealed class AbstractInternalReadableProducer<T extends TextRecord> extends AbstractReadableProducer<T>
        permits
        stexfires.io.config.ConfigProducer,
        stexfires.io.delimited.simple.SimpleDelimitedProducer,
        stexfires.io.fixedwidth.FixedWidthProducer,
        stexfires.io.json.JsonProducer,
        stexfires.io.markdown.list.MarkdownListProducer,
        stexfires.io.properties.PropertiesProducer,
        stexfires.io.singlevalue.SingleValueProducer {

    private ReadableProducerState state;

    protected AbstractInternalReadableProducer(BufferedReader bufferedReader) {
        this(bufferedReader, null);
    }

    @SuppressWarnings("SameParameterValue")
    protected AbstractInternalReadableProducer(BufferedReader bufferedReader,
                                               @Nullable Consumer<RecordRawData> recordRawDataLogger) {
        super(bufferedReader, recordRawDataLogger);
        state = OPEN;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_BEFORE.validate(state);
        super.readBefore();
    }

    @Override
    public Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_RECORDS.validate(state);
        return super.readRecords();
    }

    @Override
    public void readAfter() throws ProducerException, UncheckedProducerException, IOException {
        state = READ_AFTER.validate(state);
        super.readAfter();
    }

    @Override
    public void close() throws IOException {
        state = CLOSE.validate(state);
        super.close();
    }

    protected final long recordCount() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().currentRecordIndex();
    }

    protected final List<RecordRawData> firstIgnored() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().first();
    }

    protected final List<RecordRawData> lastIgnored() {
        if (state != READ_AFTER && state != CLOSE) {
            throw new IllegalStateException("Illegal state! " + state);
        }
        if (getIterator() == null) {
            throw new IllegalStateException("Iterator is null!");
        }
        return getIterator().last();
    }

}
