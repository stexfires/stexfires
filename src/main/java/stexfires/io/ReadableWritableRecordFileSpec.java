package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReadableWritableRecordFileSpec<CTR extends TextRecord, PTR extends CTR>
        implements ReadableRecordFileSpec<PTR>, WritableRecordFileSpec<CTR> {

    private final CharsetCoding charsetCoding;
    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;

    public ReadableWritableRecordFileSpec(CharsetCoding charsetCoding,
                                          LineSeparator lineSeparator,
                                          @Nullable String textBefore,
                                          @Nullable String textAfter) {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(lineSeparator);
        this.charsetCoding = charsetCoding;
        this.lineSeparator = lineSeparator;
        this.textBefore = textBefore;
        this.textAfter = textAfter;
    }

    public ReadableWritableRecordFileSpec(CharsetCoding charsetCoding,
                                          LineSeparator lineSeparator) {
        this(charsetCoding,
                lineSeparator,
                null,
                null);
    }

    @Override
    public final CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public final LineSeparator lineSeparator() {
        return lineSeparator;
    }

    @Override
    public final @Nullable String textBefore() {
        return textBefore;
    }

    @Override
    public final @Nullable String textAfter() {
        return textAfter;
    }

    public final ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> file(Path path) {
        return new ReadableWritableRecordFile<>(path, this);
    }

    @Override
    public final ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> readableFile(Path path) {
        return file(path);
    }

    @Override
    public final ReadableWritableRecordFile<CTR, PTR, ? extends ReadableWritableRecordFileSpec<CTR, PTR>> writableFile(Path path) {
        return file(path);
    }

    @Override
    public ReadableRecordProducer<PTR> producer(InputStream inputStream) {
        throw new UnsupportedOperationException("producer(InputStream) not implemented");
    }

    @Override
    public WritableRecordConsumer<CTR> consumer(OutputStream outputStream) {
        throw new UnsupportedOperationException("consumer(OutputStream) not implemented");
    }

}
