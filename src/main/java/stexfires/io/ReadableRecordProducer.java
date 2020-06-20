package stexfires.io;

import stexfires.core.TextRecord;
import stexfires.core.producer.ProducerException;
import stexfires.core.producer.RecordProducer;
import stexfires.core.producer.UncheckedProducerException;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("RedundantThrows")
public interface ReadableRecordProducer<T extends TextRecord> extends RecordProducer<T>, Closeable {

    void readBefore() throws ProducerException, UncheckedProducerException, IOException;

    Stream<T> readRecords() throws ProducerException, UncheckedProducerException, IOException;

    void readAfter() throws ProducerException, UncheckedProducerException, IOException;

    @Override
    default Stream<T> produceStream() throws UncheckedProducerException {
        try {
            return readRecords();
        } catch (ProducerException | IOException e) {
            throw new UncheckedProducerException(e);
        }
    }

}
