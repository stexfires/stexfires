package stexfires.core.message;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A RecordMessage creates a text message from a {@link Record}.
 * <p>
 * It must be <code>thread-safe</code> and <code>non-interfering</code>.
 * It should be <code>immutable</code> and <code>stateless</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #createMessage(Record)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see stexfires.core.logger.RecordLogger
 * @since 0.1
 */
@FunctionalInterface
public interface RecordMessage<T extends Record> {

    static <T extends Record> RecordMessage<T> of(Function<T, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    String createMessage(T record);

    default Function<T, String> asFunction() {
        return this::createMessage;
    }

    default RecordMessage<T> prepend(String message) {
        Objects.requireNonNull(message);
        return (T record) -> message + createMessage(record);
    }

    default RecordMessage<T> prepend(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return (T record) -> messageSupplier.get() + createMessage(record);
    }

    default RecordMessage<T> prepend(Supplier<String> messageSupplier, String delimiter) {
        Objects.requireNonNull(messageSupplier);
        Objects.requireNonNull(delimiter);
        return (T record) -> messageSupplier.get() + delimiter + createMessage(record);
    }

    default RecordMessage<T> append(String message) {
        Objects.requireNonNull(message);
        return (T record) -> createMessage(record) + message;
    }

    default RecordMessage<T> append(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return (T record) -> createMessage(record) + messageSupplier.get();
    }

    default RecordMessage<T> append(String delimiter, Supplier<String> messageSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(messageSupplier);
        return (T record) -> createMessage(record) + delimiter + messageSupplier.get();
    }

    default RecordMessage<T> append(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return (T record) -> createMessage(record) + recordMessage.createMessage(record);
    }

    default RecordMessage<T> append(String delimiter, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(recordMessage);
        return (T record) -> createMessage(record) + delimiter + recordMessage.createMessage(record);
    }

}
