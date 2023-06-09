package stexfires.record.consumer;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.util.Map;
import java.util.Objects;

/**
 * @since 0.1
 */
public class MapConsumer<T extends TextRecord, R extends Map<String, String>> implements RecordConsumer<T> {

    protected final Object lock = new Object();

    private final R map;
    private final RecordMessage<? super T> keyMessage;
    private final RecordMessage<? super T> valueMessage;

    public MapConsumer(R map, RecordMessage<? super T> keyMessage, RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(keyMessage);
        Objects.requireNonNull(valueMessage);
        this.map = map;
        this.keyMessage = keyMessage;
        this.valueMessage = valueMessage;
    }

    @Override
    public final void consume(T record) throws UncheckedConsumerException {
        String key = keyMessage.createMessage(record);
        String value = valueMessage.createMessage(record);
        synchronized (lock) {
            map.put(key, value);
        }
    }

    public final R getMap() {
        return map;
    }

}
