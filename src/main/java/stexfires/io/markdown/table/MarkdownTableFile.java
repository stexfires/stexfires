package stexfires.io.markdown.table;

import stexfires.core.Record;
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
public class MarkdownTableFile extends BaseRecordFile<Record, Record> {

    protected final MarkdownTableFileSpec fileSpec;

    public MarkdownTableFile(Path path, MarkdownTableFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer(OpenOption... writeOptions) throws IOException {
        return new MarkdownTableConsumer(
                newBufferedWriter(newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction()),
                        writeOptions),
                fileSpec);
    }

}
