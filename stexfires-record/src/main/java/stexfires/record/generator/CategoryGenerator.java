package stexfires.record.generator;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

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

    static <T extends TextRecord> CategoryGenerator<T> constant(String category) {
        Objects.requireNonNull(category);
        return (context) -> category;
    }

    static <T extends TextRecord> CategoryGenerator<T> constantNull() {
        return (context) -> null;
    }

    static <T extends TextRecord> CategoryGenerator<T> stringSupplier(Supplier<@Nullable String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return (context) -> categorySupplier.get();
    }

    static <T extends TextRecord> CategoryGenerator<T> previousAdjusted(Supplier<@Nullable String> firstCategorySupplier,
                                                                        UnaryOperator<@Nullable String> previousCategoryOperator) {
        Objects.requireNonNull(firstCategorySupplier);
        Objects.requireNonNull(previousCategoryOperator);
        return (context) -> {
            if (context.first()) {
                return firstCategorySupplier.get();
            }
            // if it is not the first record a previous record always exists
            return previousCategoryOperator.apply(context.previousRecord().orElseThrow().category());
        };
    }

    @Nullable
    String generateCategory(GeneratorContext<T> context);

}
