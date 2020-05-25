package stexfires.core.message;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Record;
import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMessage<T extends Record> implements RecordMessage<T> {

    private final Supplier<String> messageSupplier;

    /**
     * @param messageSupplier must be thread-safe
     */
    public SupplierMessage(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        this.messageSupplier = messageSupplier;
    }

    public static <T extends Record> SupplierMessage<T> localTime() {
        return new SupplierMessage<>(new LocalTimeStringSupplier());
    }

    public static <T extends Record> SupplierMessage<T> threadName() {
        return new SupplierMessage<>(new ThreadNameStringSupplier());
    }

    public static <T extends Record> SupplierMessage<T> sequence(long initialValue) {
        return new SupplierMessage<>(new SequenceStringSupplier(initialValue));
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return messageSupplier.get();
    }

}
