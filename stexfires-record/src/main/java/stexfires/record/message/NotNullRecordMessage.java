package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * A NotNullRecordMessage creates a non-null text message from a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #createMessage(stexfires.record.TextRecord)}.
 * <p>
 * It extends {@link RecordMessage}. The created message must not be {@code null}. It overrides the default methods to return or accept a {@code NotNullRecordMessage}.
 *
 * @see java.util.function.Function
 * @see stexfires.record.message.RecordMessage
 * @see stexfires.record.logger.RecordLogger
 * @see stexfires.record.consumer.RecordConsumer
 * @since 0.1
 */
@FunctionalInterface
public interface NotNullRecordMessage<T extends TextRecord> extends RecordMessage<T> {

    static <T extends TextRecord> NotNullRecordMessage<T> ofFunction(Function<T, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    static <T extends TextRecord> NotNullRecordMessage<T> wrapRecordMessage(RecordMessage<? super T> recordMessage,
                                                                            String nullValue) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(nullValue);
        return record -> {
            String message = recordMessage.createMessage(record);
            return (message == null) ? nullValue : message;
        };
    }

    static <T extends TextRecord> NotNullRecordMessage<T> wrapFunction(Function<T, @Nullable String> function,
                                                                       String nullValue) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(nullValue);
        return record -> {
            String message = function.apply(record);
            return (message == null) ? nullValue : message;
        };
    }

    @Override
    String createMessage(T record);

    @Override
    default Function<T, String> asFunction() {
        return this::createMessage;
    }

    @Override
    default NotNullRecordMessage<T> prepend(String message) {
        Objects.requireNonNull(message);
        return record -> message + createMessage(record);
    }

    @Override
    default NotNullRecordMessage<T> prepend(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return record -> messageSupplier.get() + createMessage(record);
    }

    @Override
    default NotNullRecordMessage<T> prepend(Supplier<String> messageSupplier, String delimiter) {
        Objects.requireNonNull(messageSupplier);
        Objects.requireNonNull(delimiter);
        return record -> messageSupplier.get() + delimiter + createMessage(record);
    }

    @Override
    default NotNullRecordMessage<T> append(String message) {
        Objects.requireNonNull(message);
        return record -> createMessage(record) + message;
    }

    @Override
    default NotNullRecordMessage<T> append(Supplier<String> messageSupplier) {
        Objects.requireNonNull(messageSupplier);
        return record -> createMessage(record) + messageSupplier.get();
    }

    @Override
    default NotNullRecordMessage<T> append(String delimiter, Supplier<String> messageSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(messageSupplier);
        return record -> createMessage(record) + delimiter + messageSupplier.get();
    }

    default NotNullRecordMessage<T> appendNotNull(NotNullRecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return record -> createMessage(record) + recordMessage.createMessage(record);
    }

    default NotNullRecordMessage<T> appendNotNull(String delimiter, NotNullRecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(recordMessage);
        return record -> createMessage(record) + delimiter + recordMessage.createMessage(record);
    }

    default NotNullRecordMessage<T> andThenNotNull(UnaryOperator<String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return record -> stringUnaryOperator.apply(createMessage(record));
    }

}
