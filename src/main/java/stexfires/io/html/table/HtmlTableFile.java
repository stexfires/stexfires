package stexfires.io.html.table;

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
public class HtmlTableFile extends BaseRecordFile<TextRecord, TextRecord, HtmlTableFileSpec> {

    public HtmlTableFile(Path path, HtmlTableFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public WritableRecordConsumer<TextRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
