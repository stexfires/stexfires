package stexfires.io.config;

import stexfires.core.record.KeyValueRecord;
import stexfires.io.BaseRecordFile;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConfigFile extends BaseRecordFile<KeyValueRecord, KeyValueRecord> {

    private final ConfigFileSpec fileSpec;

    public ConfigFile(final Path path, ConfigFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public WritableRecordConsumer<KeyValueRecord> openConsumer() throws IOException {
        return new ConfigConsumer(newBufferedWriter(
                newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

}
