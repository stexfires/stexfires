package stexfires.io.delimited.simple;

import stexfires.core.Record;
import stexfires.io.BaseRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SimpleDelimitedFile extends BaseRecordFile<Record, Record> {

    protected final SimpleDelimitedFileSpec fileSpec;

    public SimpleDelimitedFile(final Path path, SimpleDelimitedFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public ReadableRecordProducer<Record> openProducer() throws IOException {
        return new SimpleDelimitedProducer(newBufferedReader(
                newCharsetDecoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer() throws IOException {
        return new SimpleDelimitedConsumer(newBufferedWriter(
                newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

}
