package stexfires.io.properties;

import stexfires.core.record.KeyValueRecord;
import stexfires.io.BaseRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PropertiesFile extends BaseRecordFile<KeyValueRecord, KeyValueRecord> {

    protected final PropertiesFileSpec fileSpec;

    public PropertiesFile(Path path, PropertiesFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public ReadableRecordProducer<KeyValueRecord> openProducer() throws IOException {
        return new PropertiesProducer(
                newBufferedReader(newCharsetDecoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())),
                fileSpec);
    }

    @Override
    public WritableRecordConsumer<KeyValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return new PropertiesConsumer(
                newBufferedWriter(newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction()),
                        writeOptions),
                fileSpec);
    }

}
