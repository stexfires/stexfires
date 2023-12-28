package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class ConstantMessage<T extends TextRecord> implements RecordMessage<T> {

    private final String constantMessage;

    public ConstantMessage(@Nullable String constantMessage) {
        this.constantMessage = constantMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return constantMessage;
    }

}
