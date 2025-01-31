package stexfires.io.producer;

import stexfires.io.RecordFileSpec;
import stexfires.record.TextRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;

/**
 * @since 0.1
 */
public non-sealed interface ReadableRecordFileSpec<PTR extends TextRecord, RRP extends ReadableRecordProducer<PTR>>
        extends RecordFileSpec {

    RRP producer(BufferedReader bufferedReader);

    /**
     * @see ReadableRecordFileSpec#producer(java.io.BufferedReader)
     * @see stexfires.util.CharsetCoding#newBufferedReader(java.io.InputStream)
     */
    default RRP producer(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        return producer(charsetCoding().newBufferedReader(inputStream));
    }

    /**
     * @see ReadableRecordFileSpec#producer(java.io.BufferedReader)
     * @see java.io.StringReader#StringReader(String)
     */
    default RRP producer(String sourceString) {
        Objects.requireNonNull(sourceString);
        return producer(new BufferedReader(new StringReader(sourceString)));
    }

    /**
     * @see ReadableRecordFileSpec#producer(java.io.InputStream)
     * @see java.nio.file.Files#newInputStream(java.nio.file.Path, java.nio.file.OpenOption...)
     */
    default RRP openFileAsProducer(Path filePath, OpenOption... readOptions) throws IOException {
        Objects.requireNonNull(filePath);
        Objects.requireNonNull(readOptions);
        return producer(Files.newInputStream(filePath, readOptions));
    }

}
