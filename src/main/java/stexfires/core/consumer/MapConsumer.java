package stexfires.core.consumer;

import stexfires.core.record.KeyValueRecord;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
public class MapConsumer<T extends KeyValueRecord, R extends Map<String, String>> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    protected final R map;

    public MapConsumer(R map) {
        Objects.requireNonNull(map);
        this.map = map;
    }

    @Override
    public void consume(T record) throws UncheckedConsumerException {
        synchronized (lock) {
            map.put(record.getValueOfKeyField(), record.getValueOfValueField());
        }
    }

    public R getMap() {
        return map;
    }

}
