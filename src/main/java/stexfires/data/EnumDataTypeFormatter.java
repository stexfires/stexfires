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
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            return source.name();
        }
    }

}
