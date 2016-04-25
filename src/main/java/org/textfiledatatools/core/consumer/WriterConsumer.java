package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class WriterConsumer<T extends Record, R extends Writer> implements ClosableRecordConsumer<T> {

    protected final R writer;
    protected final RecordMessage<? super T> recordMessage;
    protected final String recordPrefix;
    protected final String recordPostfix;
    protected final Object lock;

    public WriterConsumer(R writer, RecordMessage<? super T> recordMessage, String recordSeparator) {
        this(writer, recordMessage, null, recordSeparator);
    }

    public WriterConsumer(R writer, RecordMessage<? super T> recordMessage, String recordPrefix, String recordPostfix) {
        Objects.requireNonNull(writer);
        Objects.requireNonNull(recordMessage);
        this.writer = writer;
        this.recordMessage = recordMessage;
        this.recordPrefix = recordPrefix;
        this.recordPostfix = recordPostfix;
        lock = this;
    }

    @Override
    public void consume(T record) throws UncheckedConsumerException {
        synchronized (lock) {
            try {
                if (recordPrefix != null) {
                    writer.write(recordPrefix);
                }

                writer.write(recordMessage.createMessage(record));

                if (recordPostfix != null) {
                    writer.write(recordPostfix);
                }
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
