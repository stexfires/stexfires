package stexfires.io.consumer;

import stexfires.io.RecordFileSpec;
import stexfires.record.TextRecord;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @since 0.1
 */
@SuppressWarnings("SameReturnValue")
public interface WritableRecordFileSpec<CTR extends TextRecord, WRC extends WritableRecordConsumer<CTR>> extends RecordFileSpec {

    LineSeparator DEFAULT_CONSUMER_LINE_SEPARATOR = LineSeparator.LF;

    LineSeparator consumerLineSeparator();

    WRC consumer(BufferedWriter bufferedWriter);

    /**
     * @see WritableRecordFileSpec#consumer(java.io.BufferedWriter)
     * @see stexfires.util.CharsetCoding#newBufferedWriter(java.io.OutputStream)
     */
    default WRC consumer(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        return consumer(charsetCoding().newBufferedWriter(outputStream));
    }

    /**
     * @see WritableRecordFileSpec#consumer(java.io.OutputStream)
     * @see java.nio.file.Files#newOutputStream(java.nio.file.Path, java.nio.file.OpenOption...)
     */
    default WRC openFileAsConsumer(Path filePath, OpenOption... writeOptions) throws IOException {
        Objects.requireNonNull(filePath);
        return consumer(Files.newOutputStream(filePath, writeOptions));
    }

}
