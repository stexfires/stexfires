package stexfires.io.markdown.list;

import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;
import stexfires.record.ValueRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MarkdownListFile extends BaseRecordFile<ValueRecord, ValueRecord, MarkdownListFileSpec> {

    public MarkdownListFile(Path path, MarkdownListFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public WritableRecordConsumer<ValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
