package stexfires.io.delimited.simple;

import stexfires.record.TextRecord;
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
public class SimpleDelimitedFile extends BaseRecordFile<TextRecord, TextRecord, SimpleDelimitedFileSpec> {

    public SimpleDelimitedFile(Path path, SimpleDelimitedFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public ReadableRecordProducer<TextRecord> openProducer() throws IOException {
        return fileSpec.producer(newInputStream());
    }

    @Override
    public WritableRecordConsumer<TextRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
