package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.function.IntFunction;

/**
 * A GeneratorInterimResult contains the interim result of a {@link stexfires.record.TextRecord} generation.
 * It is created by a {@link stexfires.record.generator.RecordGenerator} and passed to the text functions.
 *
 * @see stexfires.record.generator.RecordGenerator
 * @since 0.1
 */
public record GeneratorInterimResult<T extends TextRecord>(
        @NotNull GeneratorContext<T> context,
        @Nullable String category,
        @Nullable Long recordId,
        @Nullable IntFunction<String> textFunction
) {

    public GeneratorInterimResult {
        Objects.requireNonNull(context);
    }

    @Nullable
    public String textAt(int index) {
        return textFunction == null ? null : textFunction.apply(index);
    }

}
