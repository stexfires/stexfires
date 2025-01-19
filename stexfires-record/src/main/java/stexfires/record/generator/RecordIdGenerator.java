package stexfires.record.generator;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * A RecordIdGenerator generates a recordId of a {@link stexfires.record.TextRecord}.
 * It takes a {@link GeneratorContext} as input and returns a {@code Long}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #generateRecordId(GeneratorContext)}.
 *
 * @see java.util.function.Function
 * @see stexfires.record.generator.RecordGenerator
 * @see stexfires.record.generator.CategoryGenerator
 * @since 0.1
 */
@FunctionalInterface
public interface RecordIdGenerator<T extends TextRecord> {

    static <T extends TextRecord> RecordIdGenerator<T> constant(Long recordId) {
        Objects.requireNonNull(recordId);
        return (context) -> recordId;
    }

    static <T extends TextRecord> RecordIdGenerator<T> constantNull() {
        return (context) -> null;
    }

    static <T extends TextRecord> RecordIdGenerator<T> longSupplier(Supplier<@Nullable Long> recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return (context) -> recordIdSupplier.get();
    }

    static <T extends TextRecord> RecordIdGenerator<T> primitiveLongSupplier(LongSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return (context) -> recordIdSupplier.getAsLong();
    }

    static <T extends TextRecord> RecordIdGenerator<T> recordIndex() {
        return GeneratorContext::recordIndex;
    }

    static <T extends TextRecord> RecordIdGenerator<T> recordIndexAdjusted(LongUnaryOperator recordIndexOperator) {
        Objects.requireNonNull(recordIndexOperator);
        return (context) -> recordIndexOperator.applyAsLong(context.recordIndex());
    }

    static <T extends TextRecord> RecordIdGenerator<T> previousAdjusted(LongSupplier firstRecordIdSupplier,
                                                                        LongUnaryOperator previousRecordIdOperator) {
        Objects.requireNonNull(firstRecordIdSupplier);
        Objects.requireNonNull(previousRecordIdOperator);
        return (context) -> {
            if (context.first()) {
                return firstRecordIdSupplier.getAsLong();
            }
            return previousRecordIdOperator.applyAsLong(context.previousRecord().orElseThrow().recordIdOrElseThrow());
        };
    }

    @Nullable
    Long generateRecordId(GeneratorContext<T> context);

}
