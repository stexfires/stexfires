package stexfires.io.combined;

import stexfires.core.Record;
import stexfires.io.ReadableRecordFile;
import stexfires.io.ReadableRecordProducer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CombinedReadableRecordFile<T extends Record> implements ReadableRecordFile<T> {

    protected final ReadableRecordFile<? extends T> firstFile;
    protected final ReadableRecordFile<? extends T> secondFile;

    public CombinedReadableRecordFile(ReadableRecordFile<? extends T> firstFile,
                                      ReadableRecordFile<? extends T> secondFile) {
        Objects.requireNonNull(firstFile);
        Objects.requireNonNull(secondFile);
        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    @Override
    public Path getPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReadableRecordProducer<T> openProducer() throws IOException {
        return new CombinedReadableRecordProducer<>(
                firstFile.openProducer(),
                secondFile.openProducer());
    }

}
