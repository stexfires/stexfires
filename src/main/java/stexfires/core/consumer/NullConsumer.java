package stexfires.core.consumer;

import stexfires.core.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullConsumer<T extends TextRecord> implements RecordConsumer<T> {

    public NullConsumer() {
    }

    @Override
    public final void consume(T record) {
        // Do nothing.
    }

}
