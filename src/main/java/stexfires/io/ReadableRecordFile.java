package stexfires.io;

import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFile<PTR extends TextRecord, RFS extends ReadableRecordFileSpec<PTR>> extends RecordFile<RFS> {

    default ReadableRecordProducer<PTR> openProducer(OpenOption... readOptions) throws IOException {
        return fileSpec().producer(Files.newInputStream(path(), readOptions));
    }

}
