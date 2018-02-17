package stexfires.core.logger;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullLogger<T extends Record> implements RecordLogger<T> {

    public NullLogger() {
    }

    @Override
    public void log(T record) {
        // Do nothing.
    }

}
