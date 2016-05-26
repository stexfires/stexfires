package stexfires.core.consumer;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullConsumer implements RecordConsumer<Record> {

    @Override
    public void consume(Record record) {
        // Do nothing.
    }

}
