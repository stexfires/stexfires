package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;

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

    static <T extends TextRecord> RecordIdGenerator<T> constant(@Nullable Long recordId) {
        return (context) -> recordId;
    }

    static <T extends TextRecord> RecordIdGenerator<T> constantNull() {
        return (context) -> null;
    }

    static <T extends TextRecord> RecordIdGenerator<T> longSupplier(@NotNull Supplier<Long> recordIdSupplier) {
        return (context) -> recordIdSupplier.get();
    }

    static <T extends TextRecord> RecordIdGenerator<T> primitiveLongSupplier(@NotNull LongSupplier recordIdSupplier) {
        return (context) -> recordIdSupplier.getAsLong();
    }

    static <T extends TextRecord> RecordIdGenerator<T> recordIndex() {
        return GeneratorContext::recordIndex;
    }

    static <T extends TextRecord> RecordIdGenerator<T> recordIndexAdjusted(@NotNull LongUnaryOperator recordIndexOperator) {
        Objects.requireNonNull(recordIndexOperator);
        return (context) -> recordIndexOperator.applyAsLong(context.recordIndex());
    }

    @SuppressWarnings("DataFlowIssue")
    static <T extends TextRecord> RecordIdGenerator<T> previousAdjusted(@NotNull LongSupplier firstRecordIdSupplier,
                                                                        @NotNull LongUnaryOperator previousRecordIdOperator) {
        Objects.requireNonNull(firstRecordIdSupplier);
        Objects.requireNonNull(previousRecordIdOperator);
        return (context) -> {
            if (context.first()) {
                return firstRecordIdSupplier.getAsLong();
            }
            return previousRecordIdOperator.applyAsLong(context.previousRecord().orElseThrow().recordId());
        };
    }

    @Nullable Long generateRecordId(@NotNull GeneratorContext<T> context);

}
