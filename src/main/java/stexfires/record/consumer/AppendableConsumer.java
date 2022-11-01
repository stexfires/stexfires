package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AppendableConsumer<T extends TextRecord, R extends Appendable> implements RecordConsumer<T> {

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
    public final void consume(T record) throws UncheckedConsumerException {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            try {
                appendable.append(message);
            } catch (IOException e) {
                throw new UncheckedConsumerException(new ConsumerException(record, e));
            }
        }
    }

    public final R getAppendable() {
        return appendable;
    }

}
