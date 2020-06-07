package stexfires.core.modifier;

import stexfires.core.TextRecord;
import stexfires.core.logger.RecordLogger;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
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
