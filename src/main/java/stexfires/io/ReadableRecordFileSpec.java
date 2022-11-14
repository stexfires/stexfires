package stexfires.io;

import stexfires.record.TextRecord;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFileSpec<PTR extends TextRecord> extends RecordFileSpec {

    ReadableRecordFile<PTR, ? extends ReadableRecordFileSpec<PTR>> readableFile(Path path);

    ReadableRecordProducer<PTR> producer(InputStream inputStream);

}
