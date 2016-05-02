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

    protected final R appendable;
    protected final RecordMessage<? super T> recordMessage;
    protected final String recordPrefix;
    protected final String recordPostfix;

    protected final Object lock = new Object();

    public AppendableConsumer(R appendable, RecordMessage<? super T> recordMessage, String recordSeparator) {
        this(appendable, recordMessage, null, recordSeparator);
    }

    public AppendableConsumer(R appendable, RecordMessage<? super T> recordMessage, String recordPrefix, String recordPostfix) {
        Objects.requireNonNull(appendable);
        Objects.requireNonNull(recordMessage);
        this.appendable = appendable;
        this.recordMessage = recordMessage;
        this.recordPrefix = recordPrefix;
        this.recordPostfix = recordPostfix;
    }

    @Override
    public void consume(T record) throws UncheckedConsumerException {
        synchronized (lock) {
            try {
                if (recordPrefix != null) {
                    appendable.append(recordPrefix);
                }

                appendable.append(recordMessage.createMessage(record));

                if (recordPostfix != null) {
                    appendable.append(recordPostfix);
                }
            } catch (IOException e) {
                throw new UncheckedConsumerException(record, e);
            }
        }
    }

    public R getAppendable() {
        return appendable;
    }

}
