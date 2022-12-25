package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class EnumDataTypeFormatter<T extends Enum<T>> implements DataTypeFormatter<T> {

    private final Supplier<String> nullSourceSupplier;

    public EnumDataTypeFormatter(@Nullable Supplier<String> nullSourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public String format(T source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            return source.name();
        }
    }

}
