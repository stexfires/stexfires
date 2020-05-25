package stexfires.core.message;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMessage<T extends Record> implements RecordMessage<T> {

    private final String constantMessage;

    public ConstantMessage(@Nullable String constantMessage) {
        this.constantMessage = constantMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return constantMessage;
    }

}
