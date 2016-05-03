package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.message.ToStringMessage;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemOutLogger extends PrintStreamLogger<Record> {

    public SystemOutLogger() {
        this(new ToStringMessage(), PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemOutLogger(String prefix) {
        this(new ToStringMessage().prepend(prefix), PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemOutLogger(RecordMessage<Record> recordMessage) {
        this(recordMessage, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemOutLogger(RecordMessage<Record> recordMessage, boolean terminateLine) {
        super(System.out, Objects.requireNonNull(recordMessage), terminateLine);
    }

}
