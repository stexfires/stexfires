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
public class SupplierMessage implements RecordMessage<Record> {

    protected final Supplier<String> messageSupplier;

    /**
     * @param messageSupplier must be thread-safe
     */
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    public static SupplierMessage getLocalTimeSupplier() {
        return new SupplierMessage(new LocalTimeSupplier());
    }

    public static SupplierMessage getThreadNameSupplier() {
        return new SupplierMessage(new ThreadNameSupplier());
    }

    @Override
    public String createMessage(Record record) {
        return messageSupplier.get();
    }

}