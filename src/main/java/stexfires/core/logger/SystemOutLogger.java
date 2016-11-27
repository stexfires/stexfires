package stexfires.core.logger;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.core.message.ToStringMessage;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemOutLogger<T extends Record> extends PrintStreamLogger<T> {

    public SystemOutLogger() {
        this(new ToStringMessage<>(), DEFAULT_TERMINATE_LINE);
    }

    public SystemOutLogger(String prefix) {
        this(new ToStringMessage<>().prepend(prefix), DEFAULT_TERMINATE_LINE);
    }

    public SystemOutLogger(RecordMessage<? super T> recordMessage) {
        this(recordMessage, DEFAULT_TERMINATE_LINE);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public SystemOutLogger(RecordMessage<? super T> recordMessage, boolean terminateLine) {
        super(System.out, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
