package stexfires.io;

import stexfires.record.TextRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
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

    default ReadableRecordProducer<PTR> producer(String sourceString) {
        return producer(new BufferedReader(new StringReader(sourceString)));
    }

    /**
     * @see ReadableRecordFileSpec#producer(java.io.InputStream)
     * @see java.nio.file.Files#newInputStream(java.nio.file.Path, java.nio.file.OpenOption...)
     */
    default ReadableRecordProducer<PTR> openFileAsProducer(Path filePath, OpenOption... readOptions) throws IOException {
        return producer(Files.newInputStream(filePath, readOptions));
    }

}
