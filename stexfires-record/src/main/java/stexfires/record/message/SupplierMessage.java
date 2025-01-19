package stexfires.record.message;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class SupplierMessage<T extends TextRecord> implements NotNullRecordMessage<T> {

    private final Supplier<String> messageSupplier;

    /**
     * @param messageSupplier must be thread-safe
     */
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    @Override
    public final String createMessage(T record) {
        return messageSupplier.get();
    }

}
