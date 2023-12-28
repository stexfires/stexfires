package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public class SupplierMessage<T extends TextRecord> implements RecordMessage<T> {

    private final Supplier<String> messageSupplier;

    /**
     * @param messageSupplier must be thread-safe
     */
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return messageSupplier.get();
    }

}
