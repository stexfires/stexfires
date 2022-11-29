package stexfires.io;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.util.LineSeparator;

import java.io.BufferedWriter;
import java.io.OutputStream;
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

    WritableRecordFile<CTR, ? extends WritableRecordFileSpec<CTR>> writableFile(Path path);

    WritableRecordConsumer<CTR> consumer(BufferedWriter bufferedWriter);

    WritableRecordConsumer<CTR> consumer(OutputStream outputStream);

}
