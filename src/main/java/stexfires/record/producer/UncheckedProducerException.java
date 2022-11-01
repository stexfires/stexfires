package stexfires.record.producer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UncheckedProducerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UncheckedProducerException(@NotNull ProducerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public @NotNull ProducerException getCause() {
        return (ProducerException) super.getCause();
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    @Serial
    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Throwable cause = super.getCause();
        if (!(cause instanceof ProducerException))
            throw new InvalidObjectException("Cause must be a ProducerException");
    }

}
