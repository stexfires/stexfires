package stexfires.record.consumer;

import java.io.*;
import java.util.*;

/**
 * @since 0.1
 */
public class UncheckedConsumerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UncheckedConsumerException(ConsumerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public ConsumerException getCause() {
        return (ConsumerException) super.getCause();
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    @Serial
    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Throwable cause = super.getCause();
        if (!(cause instanceof ConsumerException))
            throw new InvalidObjectException("Cause must be a ConsumerException");
    }

}
