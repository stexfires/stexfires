package stexfires.record.modifier;

import stexfires.record.TextRecord;
import stexfires.record.logger.RecordLogger;

import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public class LogModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    private final RecordLogger<? super T> recordLogger;

    public LogModifier(RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
    }

    @Override
    public final Stream<T> modify(Stream<T> recordStream) {
        return recordStream.peek(recordLogger::log);
    }

}
