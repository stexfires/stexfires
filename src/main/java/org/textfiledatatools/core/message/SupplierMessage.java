package org.textfiledatatools.core.message;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.message.supplier.LocalTimeSupplier;
import org.textfiledatatools.core.message.supplier.ThreadNameSupplier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMessage implements RecordMessage<Record> {

    private final Supplier<String> messageSupplier;

    // Supplier must be thread-safe
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    public static final SupplierMessage getLocalTimeSupplier() {
        return new SupplierMessage(new LocalTimeSupplier());
    }

    public static final SupplierMessage getThreadNameSupplier() {
        return new SupplierMessage(new ThreadNameSupplier());
    }

    @Override
    public String createMessage(Record record) {
        return messageSupplier.get();
    }

}
