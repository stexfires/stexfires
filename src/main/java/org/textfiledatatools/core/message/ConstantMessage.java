package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMessage implements RecordMessage<Record> {

    private final String message;

    public ConstantMessage(String message) {
        Objects.requireNonNull(message);
        this.message = message;
    }

    @Override
    public String createMessage(Record record) {
        return message;
    }

}
