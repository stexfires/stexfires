package stexfires.io.singlevalue;

import stexfires.core.record.SingleRecord;
import stexfires.core.ValueRecord;
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
public class SingleValueFile extends BaseRecordFile<ValueRecord, SingleRecord, SingleValueFileSpec> {

    public SingleValueFile(Path path, SingleValueFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public ReadableRecordProducer<SingleRecord> openProducer() throws IOException {
        return fileSpec.producer(newInputStream());
    }

    @Override
    public WritableRecordConsumer<ValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
