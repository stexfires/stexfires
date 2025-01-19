package stexfires.record.producer;

import java.io.*;
import java.util.*;

/**
 * @since 0.1
 */
public class UncheckedProducerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UncheckedProducerException(ProducerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @Override
    public ProducerException getCause() {
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
