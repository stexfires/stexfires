package stexfires.io.combined;

import stexfires.io.WritableRecordConsumer;
import stexfires.io.WritableRecordFile;
import stexfires.io.spec.RecordFileSpec;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CombinedWritableRecordFile<T extends TextRecord> implements WritableRecordFile<T, RecordFileSpec> {

    protected final WritableRecordFile<? super T, ?> firstFile;
    protected final WritableRecordFile<? super T, ?> secondFile;

    public CombinedWritableRecordFile(WritableRecordFile<? super T, ?> firstFile,
                                      WritableRecordFile<? super T, ?> secondFile) {
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
    public WritableRecordConsumer<T> openConsumer(OpenOption... writeOptions) throws IOException {
        return new CombinedWritableRecordConsumer<>(
                firstFile.openConsumer(writeOptions),
                secondFile.openConsumer(writeOptions));
    }

}
