package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AppendableConsumer<T extends Record, R extends Appendable> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    private final R appendable;
    private final RecordMessage<? super T> recordMessage;

    public AppendableConsumer(R appendable, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(appendable);
        Objects.requireNonNull(recordMessage);
        this.appendable = appendable;
        this.recordMessage = recordMessage;
    }

    @Override
    public void consume(T record) throws UncheckedConsumerException {
        synchronized (lock) {
            try {
                appendable.append(recordMessage.createMessage(record));
            } catch (IOException e) {
                throw new UncheckedConsumerException(record, e);
            }
        }
    }

    public R getAppendable() {
        return appendable;
    }

}
