package stexfires.io;

import stexfires.core.Record;

import java.io.IOException;
import java.nio.file.OpenOption;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFile<T extends Record> extends RecordFile {

    WritableRecordConsumer<T> openConsumer(OpenOption... writeOptions) throws IOException;

}
