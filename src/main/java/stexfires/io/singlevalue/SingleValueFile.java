package stexfires.io.singlevalue;

import stexfires.io.BaseRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.WritableRecordConsumer;
import stexfires.record.ValueRecord;
import stexfires.record.impl.OneValueRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleValueFile extends BaseRecordFile<ValueRecord, OneValueRecord, SingleValueFileSpec> {

    public SingleValueFile(Path path, SingleValueFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public ReadableRecordProducer<OneValueRecord> openProducer() throws IOException {
        return fileSpec.producer(newInputStream());
    }

    @Override
    public WritableRecordConsumer<ValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
