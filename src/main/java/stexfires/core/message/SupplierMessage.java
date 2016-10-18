package stexfires.core.message;

import stexfires.core.Record;
import stexfires.core.message.supplier.LocalTimeSupplier;
import stexfires.core.message.supplier.ThreadNameSupplier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMessage<T extends Record> implements RecordMessage<T> {

    protected final Supplier<String> messageSupplier;

    /**
     * @param messageSupplier must be thread-safe
     */
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    public static <T extends Record> SupplierMessage<T> localTime() {
        return new SupplierMessage<>(new LocalTimeSupplier());
    }

    public static <T extends Record> SupplierMessage<T> threadName() {
        return new SupplierMessage<>(new ThreadNameSupplier());
    }

    @Override
    public String createMessage(T record) {
        return messageSupplier.get();
    }

}
