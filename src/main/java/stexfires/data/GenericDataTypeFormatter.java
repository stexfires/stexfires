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
public final class GenericDataTypeFormatter<T> implements DataTypeFormatter<T> {

    private final Function<T, String> formatFunction;
    private final Supplier<String> nullSourceSupplier;

    public GenericDataTypeFormatter(@NotNull Function<T, String> formatFunction,
                                    @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(formatFunction);
        this.formatFunction = formatFunction;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static GenericDataTypeFormatter<Locale> newLocaleDataTypeFormatter(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Locale::toLanguageTag, nullSourceSupplier);
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            return formatFunction.apply(source);
        }
    }

}
