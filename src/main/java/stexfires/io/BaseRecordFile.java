package stexfires.io;

import stexfires.core.Record;
import stexfires.io.spec.RecordFileSpec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class BaseRecordFile<W extends Record, R extends W, S extends RecordFileSpec>
        implements ReadableRecordFile<R, S>, WritableRecordFile<W, S> {

    protected final Path path;
    protected final S fileSpec;

    public BaseRecordFile(Path path, S fileSpec) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileSpec);
        this.path = path;
        this.fileSpec = fileSpec;
    }

    @Override
    public final Path getPath() {
        return path;
    }

    @Override
    public final S getFileSpec() {
        return fileSpec;
    }

    @Override
    public ReadableRecordProducer<R> openProducer() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WritableRecordConsumer<W> openConsumer(OpenOption... writeOptions) throws IOException {
        throw new UnsupportedOperationException();
    }

    protected final InputStream newInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    protected final OutputStream newOutputStream(OpenOption... writeOptions) throws IOException {
        return Files.newOutputStream(path, writeOptions);
    }

}
