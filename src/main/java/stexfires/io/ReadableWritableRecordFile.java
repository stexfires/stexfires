package stexfires.io;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record ReadableWritableRecordFile<CTR extends TextRecord, PTR extends CTR,
        RFS extends ReadableRecordFileSpec<PTR> & WritableRecordFileSpec<CTR>>
        (@NotNull Path path, @NotNull RFS fileSpec)
        implements ReadableRecordFile<PTR, RFS>, WritableRecordFile<CTR, RFS> {

    public ReadableWritableRecordFile {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileSpec);
    }

}
