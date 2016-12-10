package stexfires.io.combined;

import stexfires.core.Record;
import stexfires.io.WritableRecordConsumer;
import stexfires.io.WritableRecordFile;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CombinedWritableRecordFile<T extends Record> implements WritableRecordFile<T> {

    protected final WritableRecordFile<T> firstFile;
    protected final WritableRecordFile<T> secondFile;

    public CombinedWritableRecordFile(WritableRecordFile<T> firstFile,
                                      WritableRecordFile<T> secondFile) {
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
    public WritableRecordConsumer<T> openConsumer(OpenOption... writeOptions) throws IOException {
        return new CombinedWritableRecordConsumer<>(
                firstFile.openConsumer(writeOptions),
                secondFile.openConsumer(writeOptions));
    }

}
