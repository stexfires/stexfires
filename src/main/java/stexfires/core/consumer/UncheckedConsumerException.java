package stexfires.core.consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Record;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UncheckedConsumerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UncheckedConsumerException(@Nullable String message, @Nullable Record record) {
        this(new ConsumerException(message, record));
    }

    public UncheckedConsumerException(@Nullable Record record, @Nullable Throwable cause) {
        this(new ConsumerException(record, cause));
    }

    public UncheckedConsumerException(@Nullable String message, @Nullable Record record, @Nullable Throwable cause) {
        this(new ConsumerException(message, record, cause));
    }

    public UncheckedConsumerException(@NotNull ConsumerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public @NotNull ConsumerException getCause() {
        return (ConsumerException) super.getCause();
    }

    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Throwable cause = super.getCause();
        if (!(cause instanceof ConsumerException))
            throw new InvalidObjectException("Cause must be a ConsumerException");
    }

}
