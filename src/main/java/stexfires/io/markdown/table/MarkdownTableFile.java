package stexfires.io.markdown.table;

import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MarkdownTableFile extends BaseRecordFile<TextRecord, TextRecord, MarkdownTableFileSpec> {

    public MarkdownTableFile(Path path, MarkdownTableFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public WritableRecordConsumer<TextRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
