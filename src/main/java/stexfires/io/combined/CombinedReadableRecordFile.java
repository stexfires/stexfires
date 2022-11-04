package stexfires.io.combined;

import stexfires.io.ReadableRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.spec.RecordFileSpec;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CombinedReadableRecordFile<T extends TextRecord> implements ReadableRecordFile<T, RecordFileSpec> {

    private final ReadableRecordFile<? extends T, ?> firstFile;
    private final ReadableRecordFile<? extends T, ?> secondFile;

    public CombinedReadableRecordFile(ReadableRecordFile<? extends T, ?> firstFile,
                                      ReadableRecordFile<? extends T, ?> secondFile) {
        Objects.requireNonNull(firstFile);
        Objects.requireNonNull(secondFile);
        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    @Override
    public Path path() {
        throw new UnsupportedOperationException("path() not available for combined files");
    }

    @Override
    public RecordFileSpec fileSpec() {
        throw new UnsupportedOperationException("fileSpec() not available for combined files");
    }

    @Override
    public ReadableRecordProducer<T> openProducer() throws IOException {
        return new CombinedReadableRecordProducer<>(
                firstFile.openProducer(),
                secondFile.openProducer());
    }

}
