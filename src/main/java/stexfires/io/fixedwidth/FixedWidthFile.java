package stexfires.io.fixedwidth;

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
public class FixedWidthFile extends BaseRecordFile<Record, Record> {

    protected final FixedWidthFileSpec fileSpec;

    public FixedWidthFile(final Path path, FixedWidthFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public ReadableRecordProducer<Record> openProducer() throws IOException {
        return new FixedWidthProducer(newBufferedReader(
                newCharsetDecoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer() throws IOException {
        return new FixedWidthConsumer(newBufferedWriter(
                newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

}
