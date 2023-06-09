package stexfires.record.logger;

import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;
import stexfires.record.message.ToStringMessage;

import java.util.Objects;

/**
 * @since 0.1
 */
public class SystemErrLogger<T extends TextRecord> extends PrintStreamLogger<T> {

    public SystemErrLogger() {
        this(new ToStringMessage<>(), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix) {
        this(new ToStringMessage<>().prepend(prefix), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<? super T> recordMessage) {
        this(recordMessage, DEFAULT_TERMINATE_LINE);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public SystemErrLogger(RecordMessage<? super T> recordMessage, boolean terminateLine) {
        super(System.err, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
