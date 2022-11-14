package stexfires.io.combined;

import stexfires.io.WritableRecordConsumer;
import stexfires.io.WritableRecordFile;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CombinedWritableRecordFile<CTR extends TextRecord> implements WritableRecordFile<CTR, WritableRecordFileSpec<CTR>> {

    private final WritableRecordFile<? super CTR, ?> firstFile;
    private final WritableRecordFile<? super CTR, ?> secondFile;

    public CombinedWritableRecordFile(WritableRecordFile<? super CTR, ?> firstFile,
                                      WritableRecordFile<? super CTR, ?> secondFile) {
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
    public WritableRecordFileSpec<CTR> fileSpec() {
        throw new UnsupportedOperationException("fileSpec() not available for combined files");
    }

    @Override
    public WritableRecordConsumer<CTR> openConsumer(OpenOption... writeOptions) throws IOException {
        return new CombinedWritableRecordConsumer<>(
                firstFile.openConsumer(writeOptions),
                secondFile.openConsumer(writeOptions));
    }

}
