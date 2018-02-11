package stexfires.core.consumer;

import stexfires.core.record.ValueRecord;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class CollectionConsumer<T extends Collection<String>> implements RecordConsumer<ValueRecord> {

    protected final Object lock = new Object();

    protected final T collection;

    public CollectionConsumer(T collection) {
        Objects.requireNonNull(collection);
        this.collection = collection;
    }

    @Override
    public void consume(ValueRecord record) throws UncheckedConsumerException {
        synchronized (lock) {
            collection.add(record.getValueOfValueField());
        }
    }

    public T getCollection() {
        return collection;
    }

}
