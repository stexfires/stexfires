package stexfires.io;

import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFile<CTR extends TextRecord, RFS extends WritableRecordFileSpec<CTR>> extends RecordFile<RFS> {

    default WritableRecordConsumer<CTR> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec().consumer(Files.newOutputStream(path(), writeOptions));
    }

}
