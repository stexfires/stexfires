package stexfires.core.logger;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class CollectionLogger<T extends Record, R extends Collection<String>> implements RecordLogger<T> {

    protected final Object lock = new Object();

    protected final R collection;
    protected final RecordMessage<? super T> recordMessage;

    public CollectionLogger(R collection, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(recordMessage);
        this.collection = collection;
        this.recordMessage = recordMessage;
    }

    @Override
    public void log(T record) {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            collection.add(message);
        }
    }

    public R getCollection() {
        return collection;
    }

}
