package stexfires.record.message;

import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ConstantMessage<T extends TextRecord> implements NotNullRecordMessage<T> {

    private final String constantMessage;

    public ConstantMessage(String constantMessage) {
        this.constantMessage = constantMessage;
    }

    @Override
    public final String createMessage(T record) {
        return constantMessage;
    }

}
