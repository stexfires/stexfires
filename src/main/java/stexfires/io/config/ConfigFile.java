package stexfires.io.config;

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
public class ConfigFile extends BaseRecordFile<KeyValueRecord, KeyValueRecord> {

    protected final ConfigFileSpec fileSpec;

    public ConfigFile(final Path path, ConfigFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public ReadableRecordProducer<KeyValueRecord> openProducer() throws IOException {
        return new ConfigProducer(
                newBufferedReader(newCharsetDecoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())),
                fileSpec);
    }

    @Override
    public WritableRecordConsumer<KeyValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return new ConfigConsumer(
                newBufferedWriter(newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction()),
                        writeOptions),
                fileSpec);
    }

}
