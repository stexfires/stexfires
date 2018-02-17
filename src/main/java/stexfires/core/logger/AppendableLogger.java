package stexfires.core.logger;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AppendableLogger<T extends Record, R extends Appendable> implements RecordLogger<T> {

    protected final Object lock = new Object();

    protected final R appendable;
    protected final RecordMessage<? super T> recordMessage;

    public AppendableLogger(R appendable, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(appendable);
        Objects.requireNonNull(recordMessage);
        this.appendable = appendable;
        this.recordMessage = recordMessage;
    }

    @Override
    public void log(T record) throws UncheckedIOException {
        synchronized (lock) {
            try {
                appendable.append(recordMessage.createMessage(record));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public R getAppendable() {
        return appendable;
    }

}
