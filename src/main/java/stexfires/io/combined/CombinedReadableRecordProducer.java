package stexfires.io.combined;

import org.jetbrains.annotations.NotNull;
import stexfires.io.producer.ReadableRecordProducer;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CombinedReadableRecordProducer<PTR extends TextRecord> implements ReadableRecordProducer<PTR> {

    private final ReadableRecordProducer<? extends PTR> firstProducer;
    private final ReadableRecordProducer<? extends PTR> secondProducer;

    public CombinedReadableRecordProducer(ReadableRecordProducer<? extends PTR> firstProducer,
                                          ReadableRecordProducer<? extends PTR> secondProducer) {
        Objects.requireNonNull(firstProducer);
        Objects.requireNonNull(secondProducer);
        this.firstProducer = firstProducer;
        this.secondProducer = secondProducer;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        firstProducer.readBefore();
        secondProducer.readBefore();
    }

    @Override
    public @NotNull Stream<PTR> readRecords() throws ProducerException, UncheckedProducerException, IOException {
        Stream<? extends PTR> firstRecordStream = firstProducer.readRecords();
        Stream<? extends PTR> secondRecordStream = secondProducer.readRecords();
        return TextRecordStreams.concat(firstRecordStream, secondRecordStream);
    }

    @Override
    public void readAfter() throws ProducerException, UncheckedProducerException, IOException {
        firstProducer.readAfter();
        secondProducer.readAfter();
    }

    @Override
    public void close() throws IOException {
        IOException e1 = null;
        try {
            firstProducer.close();
        } catch (IOException e) {
            e1 = e;
        }
        IOException e2 = null;
        try {
            secondProducer.close();
        } catch (IOException e) {
            e2 = e;
        }
        if (e2 != null) {
            if (e1 != null) {
                e2.addSuppressed(e1);
            }
            throw e2;
        }
        if (e1 != null) {
            throw e1;
        }
    }

}
