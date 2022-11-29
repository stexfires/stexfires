package stexfires.io;

import stexfires.record.TextRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ReadableRecordFileSpec<PTR extends TextRecord> extends RecordFileSpec {

    ReadableRecordProducer<PTR> producer(BufferedReader bufferedReader);

    ReadableRecordProducer<PTR> producer(InputStream inputStream);

    /**
     * @see ReadableRecordFileSpec#producer(java.io.InputStream)
     * @see java.nio.file.Files#newInputStream(java.nio.file.Path, java.nio.file.OpenOption...)
     */
    default ReadableRecordProducer<PTR> openProducer(Path path, OpenOption... readOptions) throws IOException {
        return producer(Files.newInputStream(path, readOptions));
    }

}
