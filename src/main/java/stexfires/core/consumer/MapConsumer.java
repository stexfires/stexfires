package stexfires.core.consumer;

import stexfires.core.record.KeyValueRecord;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MapConsumer<T extends Map<String, String>> implements RecordConsumer<KeyValueRecord> {

    protected final Object lock = new Object();

    protected final T map;

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public MapConsumer(T map) {
        Objects.requireNonNull(map);
        this.map = map;
    }

    @Override
    public void consume(KeyValueRecord record) throws UncheckedConsumerException {
        synchronized (lock) {
            map.put(record.getValueOfKeyField(), record.getValueOfValueField());
        }
    }

    public T getMap() {
        return map;
    }

}
