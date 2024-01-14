package stexfires.record.generator;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * A GeneratorInterimResult contains the interim result of a {@link stexfires.record.TextRecord} generation.
 * It is created by a {@link stexfires.record.generator.RecordGenerator} and passed to the text functions.
 *
 * @see stexfires.record.generator.RecordGenerator
 * @since 0.1
 */
public record GeneratorInterimResult<T extends TextRecord>(
        GeneratorContext<T> context,
        @Nullable String category,
        @Nullable Long recordId,
        @Nullable IntFunction<@Nullable String> textFunction
) {

    public GeneratorInterimResult {
        Objects.requireNonNull(context);
    }

    public Optional<String> textAt(int index) {
        return ((textFunction == null) || (index < 0)) ? Optional.empty() : Optional.ofNullable(textFunction.apply(index));
    }

    public <D> Optional<D> parsedTextAt(int index, Function<@Nullable String, @Nullable D> textParser) {
        Objects.requireNonNull(textParser);
        return ((textFunction == null) || (index < 0)) ? Optional.empty() : Optional.ofNullable(textParser.apply(textFunction.apply(index)));
    }

    public <D> D parsedTextAtOrElseThrow(int index, Function<String, D> textParser) throws NullPointerException {
        Objects.requireNonNull(textParser);
        // throws NullPointerException if any value is null
        return Objects.requireNonNull(textParser.apply(Objects.requireNonNull(Objects.requireNonNull(textFunction).apply(index))));
    }

}
