package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.function.Suppliers;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class StringDataTypeFormatter implements DataTypeFormatter<String> {

    private final UnaryOperator<String> formatter;
    private final Supplier<String> nullSourceSupplier;

    public StringDataTypeFormatter(@Nullable UnaryOperator<String> formatter,
                                   @Nullable Supplier<String> nullSourceSupplier) {
        this.formatter = formatter;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static StringDataTypeFormatter newFormatter(@NotNull UnaryOperator<String> formatter) {
        Objects.requireNonNull(formatter);
        return new StringDataTypeFormatter(
                formatter,
                Suppliers.constantNull());
    }

    public static StringDataTypeFormatter newIdentityFormatter() {
        return new StringDataTypeFormatter(
                null,
                Suppliers.constantNull());
    }

    @Override
    public @Nullable String format(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            if (formatter != null) {
                return formatter.apply(source);
            }
            return source;
        }
    }

}
