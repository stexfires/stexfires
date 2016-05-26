package stexfires.core.logger;

import stexfires.core.Record;
import stexfires.core.message.RecordMessage;
import stexfires.core.message.ToStringMessage;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemErrLogger extends PrintStreamLogger<Record> {

    public SystemErrLogger() {
        this(new ToStringMessage(), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix) {
        this(new ToStringMessage().prepend(prefix), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<Record> recordMessage) {
        this(recordMessage, DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<Record> recordMessage, boolean terminateLine) {
        super(System.err, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
