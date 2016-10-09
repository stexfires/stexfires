package stexfires.io.properties;

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
public class PropertiesFile extends BaseRecordFile<KeyValueRecord, KeyValueRecord> {

    private final PropertiesFileSpec fileSpec;

    public PropertiesFile(final Path path, PropertiesFileSpec fileSpec) {
        super(path);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public WritableRecordConsumer<KeyValueRecord> openConsumer() throws IOException {
        return new PropertiesConsumer(newBufferedWriter(
                newCharsetEncoder(fileSpec.getCharset(), fileSpec.getCodingErrorAction())), fileSpec);
    }

}
