package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReadableWritableRecordFileSpec<CTR extends TextRecord, PTR extends CTR> extends BaseRecordFileSpec {

    private final String writeBefore;
    private final String writeAfter;

    protected ReadableWritableRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                             @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                                             LineSeparator lineSeparator,
                                             @Nullable String writeBefore, @Nullable String writeAfter) {
        super(charset, codingErrorAction,
                decoderReplacement, encoderReplacement,
                lineSeparator);
        this.writeBefore = writeBefore;
        this.writeAfter = writeAfter;
    }

    protected ReadableWritableRecordFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                                             @Nullable String decoderReplacement, @Nullable String encoderReplacement,
                                             LineSeparator lineSeparator) {
        this(charset, codingErrorAction,
                decoderReplacement, encoderReplacement,
                lineSeparator, null, null);
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

    public final @Nullable String getWriteBefore() {
        return writeBefore;
    }

    public final @Nullable String getWriteAfter() {
        return writeAfter;
    }

}
