package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.message.NotNullRecordMessage;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @since 0.1
 */
public class WriterConsumer<T extends TextRecord, R extends Writer> implements ClosableRecordConsumer<T> {

    protected final Object lock = new Object();

    private final R writer;
    private final NotNullRecordMessage<? super T> recordMessage;

    public WriterConsumer(R writer, NotNullRecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(writer);
        Objects.requireNonNull(recordMessage);
        this.writer = writer;
        this.recordMessage = recordMessage;
    }

    @Override
    public final void consume(T record) throws UncheckedConsumerException {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            try {
                writer.write(message);
            } catch (IOException e) {
                throw new UncheckedConsumerException(new ConsumerException(record, e));
            }
        }
    }

    @Override
    public final void close() throws IOException {
        synchronized (lock) {
            writer.close();
        }
    }

    public final R getWriter() {
        return writer;
    }

}
