package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AppendableConsumer<T extends Record, R extends Appendable> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    protected final R appendable;
    protected final RecordMessage<? super T> recordMessage;

    public AppendableConsumer(R appendable, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(appendable);
        Objects.requireNonNull(recordMessage);
        this.appendable = appendable;
        this.recordMessage = recordMessage;
    }

    @Override
    public final void consume(T record) throws UncheckedConsumerException {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            try {
                appendable.append(message);
            } catch (IOException e) {
                throw new UncheckedConsumerException(record, e);
            }
        }
    }

    public final R getAppendable() {
        return appendable;
    }

}
