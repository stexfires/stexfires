package stexfires.core.message;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMessage implements RecordMessage<Record> {

    protected final String constantMessage;

    public ConstantMessage(String constantMessage) {
        Objects.requireNonNull(constantMessage);
        this.constantMessage = constantMessage;
    }

    @Override
    public String createMessage(Record record) {
        return constantMessage;
    }

}
