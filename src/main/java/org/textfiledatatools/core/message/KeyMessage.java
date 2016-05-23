package org.textfiledatatools.core.message;

import org.textfiledatatools.core.record.KeyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyMessage implements RecordMessage<KeyRecord> {

    @Override
    public String createMessage(KeyRecord record) {
        return record.getValueOfKeyField();
    }

}
