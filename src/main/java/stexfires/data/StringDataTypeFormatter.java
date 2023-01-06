package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.util.Strings;
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
    private final Supplier<String> emptySourceSupplier;

    public StringDataTypeFormatter(@Nullable UnaryOperator<String> formatter,
                                   @Nullable Supplier<String> nullSourceSupplier,
                                   @Nullable Supplier<String> emptySourceSupplier) {
        this.formatter = formatter;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static StringDataTypeFormatter newFormatter(@NotNull UnaryOperator<String> formatter) {
        Objects.requireNonNull(formatter);
        return new StringDataTypeFormatter(
                formatter,
                Suppliers.constantNull(),
                Suppliers.constant(Strings.EMPTY));
    }

    public static StringDataTypeFormatter newIdentityFormatter() {
        return new StringDataTypeFormatter(
                null,
                Suppliers.constantNull(),
                Suppliers.constant(Strings.EMPTY));
    }

    @Override
    public @Nullable String format(@Nullable String source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeFormatException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            if (formatter != null) {
                return formatter.apply(source);
            }
            return source;
        }
    }

}
