package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A CategoryGenerator generates a category of a {@link stexfires.record.TextRecord}.
 * It takes a {@link GeneratorContext} as input and returns a {@code String}.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #generateCategory(GeneratorContext)}.
 *
 * @see java.util.function.Function
 * @see stexfires.record.generator.RecordGenerator
 * @see stexfires.record.generator.RecordIdGenerator
 * @since 0.1
 */
@FunctionalInterface
public interface CategoryGenerator<T extends TextRecord> {

    static <T extends TextRecord> CategoryGenerator<T> constant(@Nullable String category) {
        return (context) -> category;
    }

    static <T extends TextRecord> CategoryGenerator<T> constantNull() {
        return (context) -> null;
    }

    static <T extends TextRecord> CategoryGenerator<T> stringSupplier(@NotNull Supplier<String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return (context) -> categorySupplier.get();
    }

    static <T extends TextRecord> CategoryGenerator<T> previousAdjusted(@NotNull Supplier<String> firstCategorySupplier,
                                                                        @NotNull UnaryOperator<String> previousCategoryOperator) {
        Objects.requireNonNull(firstCategorySupplier);
        Objects.requireNonNull(previousCategoryOperator);
        return (context) -> {
            if (context.first()) {
                return firstCategorySupplier.get();
            }
            return previousCategoryOperator.apply(context.previousRecord().orElseThrow().category());
        };
    }

    @Nullable String generateCategory(@NotNull GeneratorContext<T> context);

}
