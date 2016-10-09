package stexfires.io.htmltable;

import stexfires.core.Record;
import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class HtmlTableFile extends BaseRecordFile<Record, Record> {

    private final HtmlTableFileSpec fileSpec;

    public HtmlTableFile(final Path path, HtmlTableFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer() throws IOException {
        return new HtmlTableConsumer(newBufferedWriter(
                newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

}
