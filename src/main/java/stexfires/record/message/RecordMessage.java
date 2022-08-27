package stexfires.record.message;

import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A RecordMessage creates a text message from a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #createMessage(stexfires.record.TextRecord)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see stexfires.record.logger.RecordLogger
 * @see stexfires.record.consumer.RecordConsumer
 * @since 0.1
 */
@FunctionalInterface
public interface RecordMessage<T extends TextRecord> {

    static <T extends TextRecord> RecordMessage<T> of(Function<T, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    String createMessage(T record);

    default Function<T, String> asFunction() {
        return this::createMessage;
    }

    default RecordMessage<T> prepend(String message) {
        Objects.requireNonNull(message);
        return record -> message + createMessage(record);
    }

    default RecordMessage<T> prepend(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return record -> messageSupplier.get() + createMessage(record);
    }

    default RecordMessage<T> prepend(Supplier<String> messageSupplier, String delimiter) {
        Objects.requireNonNull(messageSupplier);
        Objects.requireNonNull(delimiter);
        return record -> messageSupplier.get() + delimiter + createMessage(record);
    }

    default RecordMessage<T> append(String message) {
        Objects.requireNonNull(message);
        return record -> createMessage(record) + message;
    }

    default RecordMessage<T> append(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return record -> createMessage(record) + messageSupplier.get();
    }

    default RecordMessage<T> append(String delimiter, Supplier<String> messageSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(messageSupplier);
        return record -> createMessage(record) + delimiter + messageSupplier.get();
    }

    default RecordMessage<T> append(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return record -> createMessage(record) + recordMessage.createMessage(record);
    }

    default RecordMessage<T> append(String delimiter, RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(recordMessage);
        return record -> createMessage(record) + delimiter + recordMessage.createMessage(record);
    }

}
