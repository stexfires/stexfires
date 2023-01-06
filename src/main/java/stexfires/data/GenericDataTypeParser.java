package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class GenericDataTypeParser<T> implements DataTypeParser<T> {

    private final Function<String, T> parseFunction;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public GenericDataTypeParser(@NotNull Function<String, T> parseFunction,
                                 @Nullable Supplier<T> nullSourceSupplier,
                                 @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(parseFunction);
        this.parseFunction = parseFunction;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static GenericDataTypeParser<Locale> newLocaleDataTypeParser(@Nullable Supplier<Locale> nullSourceSupplier,
                                                                        @Nullable Supplier<Locale> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Locale::forLanguageTag, nullSourceSupplier, emptySourceSupplier);
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeParseException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeParseException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            return parseFunction.apply(source);
        }
    }

}
