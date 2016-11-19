package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.logger.RecordLogger;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LogModifier<T extends Record> implements RecordStreamModifier<T, T> {

    protected final RecordLogger<? super T> recordLogger;

    public LogModifier(RecordLogger<? super T> recordLogger) {
        Objects.requireNonNull(recordLogger);
        this.recordLogger = recordLogger;
    }

    @Override
    public Stream<T> modify(Stream<T> recordStream) {
        return recordStream.peek(recordLogger::log);
    }

}
