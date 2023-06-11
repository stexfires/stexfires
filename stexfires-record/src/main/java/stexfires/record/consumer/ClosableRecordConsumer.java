package stexfires.record.consumer;

import stexfires.record.TextRecord;

import java.io.Closeable;

/**
 * @see java.util.function.Consumer
 * @since 0.1
 */
public interface ClosableRecordConsumer<T extends TextRecord> extends RecordConsumer<T>, Closeable {

}
