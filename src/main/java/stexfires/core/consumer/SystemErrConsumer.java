package stexfires.core.consumer;

import stexfires.core.TextRecord;
import stexfires.core.message.RecordMessage;
import stexfires.core.message.ToStringMessage;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemErrConsumer<T extends TextRecord> extends PrintStreamConsumer<T> {

    public SystemErrConsumer() {
        this(new ToStringMessage<>(), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrConsumer(String prefix) {
        this(new ToStringMessage<>().prepend(prefix), DEFAULT_TERMINATE_LINE);
    }

    public SystemErrConsumer(RecordMessage<? super T> recordMessage) {
        this(recordMessage, DEFAULT_TERMINATE_LINE);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public SystemErrConsumer(RecordMessage<? super T> recordMessage, boolean terminateLine) {
        super(System.err, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
