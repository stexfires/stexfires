package stexfires.io;

import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.RecordProducer;
import stexfires.record.producer.UncheckedProducerException;

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
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException(e));
        }
    }

}
