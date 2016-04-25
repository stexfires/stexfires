package org.textfiledatatools.core.consumer;

import org.textfiledatatools.core.Record;

import java.io.Closeable;

/**
 * @author Mathias Kalb
 * @see java.util.function.Consumer
 * @since 0.1
 */
public interface ClosableRecordConsumer<T extends Record> extends RecordConsumer<T>, Closeable {

}
