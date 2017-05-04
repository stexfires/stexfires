package stexfires.io.fixedwidth;

import stexfires.core.Record;
import stexfires.io.BaseRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class FixedWidthFile extends BaseRecordFile<Record, Record, FixedWidthFileSpec> {

    public FixedWidthFile(Path path, FixedWidthFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public ReadableRecordProducer<Record> openProducer() throws IOException {
        return fileSpec.producer(newInputStream());
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
