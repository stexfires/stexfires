package stexfires.io;

import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.OpenOption;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFile<CTR extends TextRecord, RFS extends RecordFileSpec> extends RecordFile<RFS> {

    WritableRecordConsumer<CTR> openConsumer(OpenOption... writeOptions) throws IOException;

}
