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
public class ReadableWritableRecordFileSpec<CTR extends TextRecord, PTR extends CTR> implements RecordFileSpec {

    private final CharsetCoding charsetCoding;

    private final LineSeparator lineSeparator;
    private final String textBefore;
    private final String textAfter;

    protected ReadableWritableRecordFileSpec(CharsetCoding charsetCoding,
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

    protected ReadableWritableRecordFileSpec(CharsetCoding charsetCoding,
                                             LineSeparator lineSeparator) {
        this(charsetCoding,
                lineSeparator,
                null,
                null);
    }

    public final ReadableWritableRecordFile<CTR, PTR, ?> file(Path path) {
        return new ReadableWritableRecordFile<>(path, this);
    }

    public ReadableRecordProducer<PTR> producer(InputStream inputStream) {
        throw new UnsupportedOperationException("producer(InputStream) not implemented");
    }

    public WritableRecordConsumer<CTR> consumer(OutputStream outputStream) {
        throw new UnsupportedOperationException("consumer(OutputStream) not implemented");
    }

    @Override
    public final CharsetCoding charsetCoding() {
        return charsetCoding;
    }

    @Override
    public final LineSeparator lineSeparator() {
        return lineSeparator;
    }

    public final @Nullable String textBefore() {
        return textBefore;
    }

    public final @Nullable String textAfter() {
        return textAfter;
    }

}
