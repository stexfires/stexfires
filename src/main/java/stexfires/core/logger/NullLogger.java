package stexfires.core.logger;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullLogger implements RecordLogger<Record> {

    @Override
    public void log(Record record) {
        // Do nothing.
    }

}
