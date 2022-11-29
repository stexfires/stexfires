package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

import java.nio.file.Path;
import java.util.Objects;

public interface ReadableWritableRecordFileSpec<CTR extends TextRecord, PTR extends CTR>
        extends ReadableRecordFileSpec<PTR>, WritableRecordFileSpec<CTR> {

    default ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> file(Path path) {
        Objects.requireNonNull(path);
        return new ReadableWritableRecordFile<>(path, this);
    }

    @Override
    default ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> readableFile(Path path) {
        Objects.requireNonNull(path);
        return file(path);
    }

    @Override
    default ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> writableFile(Path path) {
        Objects.requireNonNull(path);
        return file(path);
    }

    @Override
    default @Nullable String textBefore() {
        return null;
    }

    @Override
    default @Nullable String textAfter() {
        return null;
    }

}
