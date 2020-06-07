package stexfires.io.combined;

import stexfires.core.TextRecord;
import stexfires.io.ReadableRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.spec.RecordFileSpec;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CombinedReadableRecordFile<T extends TextRecord> implements ReadableRecordFile<T, RecordFileSpec> {

    protected final ReadableRecordFile<? extends T, ?> firstFile;
    protected final ReadableRecordFile<? extends T, ?> secondFile;

    public CombinedReadableRecordFile(ReadableRecordFile<? extends T, ?> firstFile,
                                      ReadableRecordFile<? extends T, ?> secondFile) {
        Objects.requireNonNull(firstFile);
        Objects.requireNonNull(secondFile);
        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    @Override
    public Path getPath() {
        throw new UnsupportedOperationException("getPath() not available for combined files");
    }

    @Override
    public final RecordFileSpec getFileSpec() {
        throw new UnsupportedOperationException("getFileSpec() not available for combined files");
    }

    @Override
    public ReadableRecordProducer<T> openProducer() throws IOException {
        return new CombinedReadableRecordProducer<>(
                firstFile.openProducer(),
                secondFile.openProducer());
    }

}
