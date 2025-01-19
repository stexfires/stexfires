package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.function.*;

/**
 * @since 0.1
 */
public final class EnumDataTypeFormatter<T extends Enum<T>> implements DataTypeFormatter<T> {

    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public EnumDataTypeFormatter(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
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
