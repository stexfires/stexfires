package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.message.ToStringMessage;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SystemOutLogger extends PrintStreamLogger<Record> {

    public SystemOutLogger() {
        this(null, new ToStringMessage(), null, true);
    }

    public SystemOutLogger(String prefix) {
        this(prefix, new ToStringMessage(), null, true);
    }

    public SystemOutLogger(RecordMessage<Record> recordMessage) {
        this(null, recordMessage, null, true);
    }

    public SystemOutLogger(String prefix, RecordMessage<Record> recordMessage) {
        this(prefix, recordMessage, null, true);
    }

    public SystemOutLogger(String prefix, RecordMessage<Record> recordMessage, String postfix, boolean terminateLine) {
        super(System.out, RecordMessage.addPrefixAndPostfix(prefix, recordMessage, postfix), terminateLine);
    }

}
