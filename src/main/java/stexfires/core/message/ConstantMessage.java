package stexfires.core.message;

import stexfires.core.Record;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMessage<T extends Record> implements RecordMessage<T> {

    private final String constantMessage;

    public ConstantMessage(String constantMessage) {
        Objects.requireNonNull(constantMessage);
        this.constantMessage = constantMessage;
    }

    @Override
    public final String createMessage(T record) {
        return constantMessage;
    }

}
