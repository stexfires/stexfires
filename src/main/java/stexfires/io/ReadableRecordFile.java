package stexfires.io;

import stexfires.core.Record;

import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFile<T extends Record> extends RecordFile {

    ReadableRecordProducer<T> openProducer() throws IOException;

}
