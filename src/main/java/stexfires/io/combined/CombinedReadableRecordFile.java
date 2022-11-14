package stexfires.io.combined;

import stexfires.io.ReadableRecordFile;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.ReadableRecordProducer;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CombinedReadableRecordFile<PTR extends TextRecord> implements ReadableRecordFile<PTR, ReadableRecordFileSpec<PTR>> {

    private final ReadableRecordFile<? extends PTR, ?> firstFile;
    private final ReadableRecordFile<? extends PTR, ?> secondFile;

    public CombinedReadableRecordFile(ReadableRecordFile<? extends PTR, ?> firstFile,
                                      ReadableRecordFile<? extends PTR, ?> secondFile) {
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
    public ReadableRecordFileSpec<PTR> fileSpec() {
        throw new UnsupportedOperationException("fileSpec() not available for combined files");
    }

    @Override
    public ReadableRecordProducer<PTR> openProducer(OpenOption... readOptions) throws IOException {
        return new CombinedReadableRecordProducer<>(
                firstFile.openProducer(readOptions),
                secondFile.openProducer(readOptions));
    }

}
