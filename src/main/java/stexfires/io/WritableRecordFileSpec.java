package stexfires.io;

import stexfires.record.TextRecord;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFileSpec<CTR extends TextRecord> extends RecordFileSpec {

    WritableRecordFile<CTR, ? extends WritableRecordFileSpec<CTR>> writableFile(Path path);

    WritableRecordConsumer<CTR> consumer(BufferedWriter bufferedWriter);

    WritableRecordConsumer<CTR> consumer(OutputStream outputStream);

}
