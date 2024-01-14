package stexfires.data;

import org.jspecify.annotations.Nullable;
import stexfires.util.function.Suppliers;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
public final class StringDataTypeFormatter implements DataTypeFormatter<String> {

    private final @Nullable UnaryOperator<String> formatter;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public StringDataTypeFormatter(@Nullable UnaryOperator<String> formatter,
                                   @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        this.formatter = formatter;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static StringDataTypeFormatter passingNull(UnaryOperator<String> formatter) {
        Objects.requireNonNull(formatter);
        return new StringDataTypeFormatter(
                formatter,
                Suppliers.constantNull());
    }

    public static StringDataTypeFormatter identity() {
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
