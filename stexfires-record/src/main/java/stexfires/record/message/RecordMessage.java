package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * A RecordMessage creates a nullable text message from a {@link stexfires.record.TextRecord}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #createMessage(stexfires.record.TextRecord)}.
 *
 * @see java.util.function.Function
 * @see stexfires.record.message.NotNullRecordMessage
 * @see stexfires.record.logger.RecordLogger
 * @see stexfires.record.consumer.RecordConsumer
 * @since 0.1
 */
@FunctionalInterface
public interface RecordMessage<T extends TextRecord> {

    static <T extends TextRecord> RecordMessage<T> ofFunction(Function<T, @Nullable String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    static <T extends TextRecord> RecordMessage<T> category() {
        return TextRecord::category;
    }

    static <T extends TextRecord> RecordMessage<T> firstText() {
        return TextRecord::firstText;
    }

    static <T extends TextRecord> RecordMessage<T> lastText() {
        return TextRecord::lastText;
    }

    @Nullable
    String createMessage(T record);

    default Function<T, @Nullable String> asFunction() {
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

    default RecordMessage<T> andThen(UnaryOperator<@Nullable String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return record -> stringUnaryOperator.apply(createMessage(record));
    }

}
