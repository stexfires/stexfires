package stexfires.io.producer;

import stexfires.record.TextRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.RecordProducer;
import stexfires.record.producer.UncheckedProducerException;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
@SuppressWarnings("RedundantThrows")
public interface ReadableRecordProducer<PTR extends TextRecord> extends RecordProducer<PTR>, Closeable {

    void readBefore() throws ProducerException, UncheckedProducerException, IOException;

    Stream<PTR> readRecords() throws ProducerException, UncheckedProducerException, IOException;

    void readAfter() throws ProducerException, UncheckedProducerException, IOException;

    @Override
    default Stream<PTR> produceStream() throws UncheckedProducerException {
        try {
            return readRecords();
        } catch (ProducerException e) {
            throw new UncheckedProducerException(e);
        } catch (IOException e) {
            throw new UncheckedProducerException(new ProducerException(e));
        }
    }

}
