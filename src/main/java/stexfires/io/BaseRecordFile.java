package stexfires.io;

import org.jetbrains.annotations.NotNull;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.record.TextRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record BaseRecordFile<CTR extends TextRecord, PTR extends CTR, RFS extends AbstractRecordFileSpec<CTR, PTR>>
        (@NotNull Path path, @NotNull RFS fileSpec)
        implements ReadableRecordFile<PTR, RFS>, WritableRecordFile<CTR, RFS> {

    public BaseRecordFile {
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileSpec);
    }

    @Override
    public ReadableRecordProducer<PTR> openProducer() throws IOException {
        return fileSpec().producer(Files.newInputStream(path));
    }

    @Override
    public WritableRecordConsumer<CTR> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec().consumer(Files.newOutputStream(path, writeOptions));
    }

}
