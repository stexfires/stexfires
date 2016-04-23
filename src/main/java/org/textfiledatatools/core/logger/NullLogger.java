package org.textfiledatatools.core.logger;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullLogger implements RecordLogger<Record> {

    @Override
    public void log(Record record) {
    }

}
