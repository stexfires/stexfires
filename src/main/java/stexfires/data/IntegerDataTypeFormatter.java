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
public final class IntegerDataTypeFormatter implements DataTypeFormatter<Integer> {

    private final NumberFormat numberFormat;
    private final Supplier<String> nullSourceSupplier;

    public IntegerDataTypeFormatter(@NotNull NumberFormat numberFormat,
                                    @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(numberFormat);
        this.numberFormat = numberFormat;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public String format(Integer source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            return numberFormat.format(source);
        }
    }

}
