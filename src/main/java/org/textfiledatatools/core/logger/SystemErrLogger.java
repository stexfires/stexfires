package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.message.ToStringMessage;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemErrLogger extends PrintStreamLogger<Record> {

    public SystemErrLogger() {
        this(null, new ToStringMessage(), null, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix) {
        this(prefix, new ToStringMessage(), null, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(RecordMessage<Record> recordMessage) {
        this(null, recordMessage, null, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix, RecordMessage<Record> recordMessage) {
        this(prefix, recordMessage, null, PrintStreamLogger.DEFAULT_TERMINATE_LINE);
    }

    public SystemErrLogger(String prefix, RecordMessage<Record> recordMessage, String postfix, boolean terminateLine) {
        super(System.err, RecordMessage.addPrefixAndPostfix(prefix, recordMessage, postfix), terminateLine);
    }

}
