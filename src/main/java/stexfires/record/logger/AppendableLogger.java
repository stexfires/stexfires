package stexfires.record.logger;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AppendableLogger<T extends TextRecord, R extends Appendable> implements RecordLogger<T> {

    protected final Object lock = new Object();

    private final R appendable;
    private final RecordMessage<? super T> recordMessage;

    public AppendableLogger(R appendable, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(appendable);
        Objects.requireNonNull(recordMessage);
        this.appendable = appendable;
        this.recordMessage = recordMessage;
    }

    @Override
    public final void log(T record) throws UncheckedIOException {
        try {
            String message = recordMessage.createMessage(record);
            synchronized (lock) {
                appendable.append(message);
            }
        } catch (IOException | RuntimeException e) {
            // Ignore Exception
        }
    }

    public final R getAppendable() {
        return appendable;
    }

}
