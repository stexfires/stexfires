package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("SameReturnValue")
public interface WritableRecordFileSpec<CTR extends TextRecord> extends RecordFileSpec {

    LineSeparator DEFAULT_LINE_SEPARATOR = LineSeparator.LF;

    String DEFAULT_TEXT_BEFORE = null;

    String DEFAULT_TEXT_AFTER = null;

    LineSeparator lineSeparator();

    @Nullable String textBefore();

    @Nullable String textAfter();

    WritableRecordConsumer<CTR> consumer(BufferedWriter bufferedWriter);

    WritableRecordConsumer<CTR> consumer(OutputStream outputStream);

    /**
     * @see WritableRecordFileSpec#consumer(java.io.OutputStream)
     * @see java.nio.file.Files#newOutputStream(java.nio.file.Path, java.nio.file.OpenOption...)
     */
    default WritableRecordConsumer<CTR> openConsumer(Path path, OpenOption... writeOptions) throws IOException {
        return consumer(Files.newOutputStream(path, writeOptions));
    }

}
