package stexfires.record.message;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
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
