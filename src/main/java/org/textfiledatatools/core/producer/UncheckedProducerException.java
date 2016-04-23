package org.textfiledatatools.core.producer;

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

    public UncheckedProducerException() {
        this(new ProducerException());
    }

    public UncheckedProducerException(String message) {
        this(new ProducerException(message));
    }

    public UncheckedProducerException(Throwable cause) {
        this(new ProducerException(cause));
    }

    public UncheckedProducerException(String message, Throwable cause) {
        this(new ProducerException(message, cause));
    }

    public UncheckedProducerException(ProducerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @Override
    public ProducerException getCause() {
        return (ProducerException) super.getCause();
    }

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        Throwable cause = super.getCause();
        if (!(cause instanceof ProducerException))
            throw new InvalidObjectException("Cause must be a ProducerException");
    }

}
