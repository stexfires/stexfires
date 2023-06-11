package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.util.Collection;
import java.util.Objects;

/**
 * @since 0.1
 */
public class CollectionConsumer<T extends TextRecord, R extends Collection<String>> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    private final R collection;
    private final RecordMessage<? super T> recordMessage;

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
