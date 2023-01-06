package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class NumberDataTypeFormatter<T extends Number> implements DataTypeFormatter<T> {

    private final NumberFormat numberFormat;
    private final Supplier<String> nullSourceSupplier;
    private final Object lock = new Object();

    public NumberDataTypeFormatter(@NotNull NumberFormat numberFormat,
                                   @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(numberFormat);
        this.numberFormat = numberFormat;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                synchronized (lock) {
                    return numberFormat.format(source);
                }
            } catch (IllegalArgumentException e) {
                throw new DataTypeFormatException(e.getMessage());
            }
        }
    }

}
