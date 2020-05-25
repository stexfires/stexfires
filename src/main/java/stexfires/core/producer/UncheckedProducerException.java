package stexfires.core.producer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UncheckedProducerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("NewExceptionWithoutArguments")
    public UncheckedProducerException() {
        this(new ProducerException());
    }

    public UncheckedProducerException(@Nullable String message) {
        this(new ProducerException(message));
    }

    public UncheckedProducerException(@Nullable Throwable cause) {
        this(new ProducerException(cause));
    }

    public UncheckedProducerException(@Nullable String message, @Nullable Throwable cause) {
        this(new ProducerException(message, cause));
    }

    public UncheckedProducerException(@NotNull ProducerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public @NotNull ProducerException getCause() {
        return (ProducerException) super.getCause();
    }

    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Throwable cause = super.getCause();
        if (!(cause instanceof ProducerException))
            throw new InvalidObjectException("Cause must be a ProducerException");
    }

}
