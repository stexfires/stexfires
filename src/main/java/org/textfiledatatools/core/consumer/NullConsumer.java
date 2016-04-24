package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;

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
