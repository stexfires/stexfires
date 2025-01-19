package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class EnumDataTypeParser<T extends Enum<T>> implements DataTypeParser<T> {

    private final Class<T> enumClass;
    private final UnaryOperator<String> sourceOperator;

    private final @Nullable Supplier<@Nullable T> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable T> emptySourceSupplier;

    public EnumDataTypeParser(Class<T> enumClass,
                              UnaryOperator<String> sourceOperator,
                              @Nullable Supplier<@Nullable T> nullSourceSupplier,
                              @Nullable Supplier<@Nullable T> emptySourceSupplier) {
        Objects.requireNonNull(enumClass);
        Objects.requireNonNull(sourceOperator);
        this.enumClass = enumClass;
        this.sourceOperator = sourceOperator;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                return Enum.valueOf(enumClass, sourceOperator.apply(source));
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, e);
            }
        }
    }

    public Class<T> getEnumClass() {
        return enumClass;
    }

}
