package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.text.NumberFormat;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class NumberDataTypeFormatter<T extends Number> implements DataTypeFormatter<T> {

    private final NumberFormat numberFormat;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;
    private final Object lock = new Object();

    public NumberDataTypeFormatter(NumberFormat numberFormat,
                                   @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(numberFormat);
        this.numberFormat = numberFormat;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                synchronized (lock) {
                    return numberFormat.format(source);
                }
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, e);
            }
        }
    }

}
