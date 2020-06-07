package stexfires.io;

import stexfires.core.TextRecord;
import stexfires.io.spec.RecordFileSpec;

import java.io.IOException;
import java.nio.file.OpenOption;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFile<T extends TextRecord, S extends RecordFileSpec> extends RecordFile<S> {

    WritableRecordConsumer<T> openConsumer(OpenOption... writeOptions) throws IOException;

}
