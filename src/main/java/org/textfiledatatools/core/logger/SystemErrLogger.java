package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.message.ToStringMessage;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemErrLogger extends PrintStreamLogger<Record> {

    public SystemErrLogger() {
        this(new ToStringMessage(), PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix) {
        this(new ToStringMessage().prepend(prefix), PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<Record> recordMessage) {
        this(recordMessage, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<Record> recordMessage, boolean terminateLine) {
        super(System.err, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
