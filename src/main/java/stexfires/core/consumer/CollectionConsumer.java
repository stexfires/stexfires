package stexfires.core.consumer;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class CollectionConsumer<T extends Record, R extends Collection<String>> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    protected final R collection;
    protected final RecordMessage<? super T> recordMessage;

    public CollectionConsumer(R collection, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(recordMessage);
        this.collection = collection;
        this.recordMessage = recordMessage;
    }

    @Override
    public final void consume(T record) throws UncheckedConsumerException {
        String message = recordMessage.createMessage(record);
        synchronized (lock) {
            collection.add(message);
        }
    }

    public final R getCollection() {
        return collection;
    }

}
