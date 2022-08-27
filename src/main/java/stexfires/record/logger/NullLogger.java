package stexfires.record.logger;

import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullLogger<T extends TextRecord> implements RecordLogger<T> {

    public NullLogger() {
    }

    @Override
    public final void log(T record) {
        // Do nothing.
    }

}
