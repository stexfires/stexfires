package stexfires.io;

import stexfires.io.spec.RecordFileSpec;
import stexfires.record.TextRecord;

import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFile<T extends TextRecord, S extends RecordFileSpec> extends RecordFile<S> {

    ReadableRecordProducer<T> openProducer() throws IOException;

}
