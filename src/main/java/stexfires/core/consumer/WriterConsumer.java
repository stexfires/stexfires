package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class WriterConsumer<T extends Record, R extends Writer> implements ClosableRecordConsumer<T> {

    protected final Object lock = new Object();

    protected final R writer;
    protected final RecordMessage<? super T> recordMessage;

    public WriterConsumer(R writer, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(writer);
        Objects.requireNonNull(recordMessage);
        this.writer = writer;
        this.recordMessage = recordMessage;
    }

    @Override
    public void consume(T record) throws UncheckedConsumerException {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            try {
                writer.write(message);
            } catch (IOException e) {
                throw new UncheckedConsumerException(record, e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (lock) {
            writer.close();
        }
    }

    public R getWriter() {
        return writer;
    }

}
