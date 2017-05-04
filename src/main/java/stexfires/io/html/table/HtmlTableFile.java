package stexfires.io.html.table;

import stexfires.core.Record;
import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class HtmlTableFile extends BaseRecordFile<Record, Record, HtmlTableFileSpec> {

    public HtmlTableFile(Path path, HtmlTableFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public WritableRecordConsumer<Record> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
