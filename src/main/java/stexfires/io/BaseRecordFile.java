package stexfires.io;

import stexfires.core.Record;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class BaseRecordFile<W extends Record, R extends W> implements ReadableRecordFile<R>, WritableRecordFile<W> {

    protected final Path path;

    public BaseRecordFile(final Path path) {
        Objects.requireNonNull(path);
        this.path = path;
    }

    @Override
    public final Path getPath() {
        return path;
    }

    @Override
    public ReadableRecordProducer<R> openProducer() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WritableRecordConsumer<W> openConsumer() throws IOException {
        throw new UnsupportedOperationException();
    }

    protected BufferedWriter newBufferedWriter(Charset charset, OpenOption... options) throws IOException {
        return Files.newBufferedWriter(path, charset, options);
    }

    protected BufferedReader newBufferedReader(Charset charset) throws IOException {
        return Files.newBufferedReader(path, charset);
    }

}
