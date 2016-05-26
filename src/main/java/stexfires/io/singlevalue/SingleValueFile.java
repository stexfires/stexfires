package stexfires.io.singlevalue;

import stexfires.core.record.SingleRecord;
import stexfires.core.record.ValueRecord;
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
public class SingleValueFile extends BaseRecordFile<ValueRecord, SingleRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueFile(final Path path, SingleValueFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public ReadableRecordProducer<SingleRecord> openProducer() throws IOException {
        return new SingleValueProducer(newBufferedReader(fileSpec.getCharset()), fileSpec);
    }

    @Override
    public WritableRecordConsumer<ValueRecord> openConsumer() throws IOException {
        return new SingleValueConsumer(newBufferedWriter(fileSpec.getCharset()), fileSpec);
    }

}
