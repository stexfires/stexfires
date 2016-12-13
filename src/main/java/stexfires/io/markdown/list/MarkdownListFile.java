package stexfires.io.markdown.list;

import stexfires.core.record.ValueRecord;
import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class MarkdownListFile extends BaseRecordFile<ValueRecord, ValueRecord> {

    protected final MarkdownListFileSpec fileSpec;

    public MarkdownListFile(final Path path, MarkdownListFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public WritableRecordConsumer<ValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return new MarkdownListConsumer(
                newBufferedWriter(newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction()),
                        writeOptions),
                fileSpec);
    }

}
