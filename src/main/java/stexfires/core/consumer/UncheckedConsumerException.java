package stexfires.core.consumer;

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

    public UncheckedConsumerException(String message, Record record) {
        this(new ConsumerException(message, record));
    }

    public UncheckedConsumerException(Record record, Throwable cause) {
        this(new ConsumerException(record, cause));
    }

    public UncheckedConsumerException(String message, Record record, Throwable cause) {
        this(new ConsumerException(message, record, cause));
    }

    public UncheckedConsumerException(ConsumerException cause) {
        super(Objects.requireNonNull(cause));
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public ConsumerException getCause() {
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
