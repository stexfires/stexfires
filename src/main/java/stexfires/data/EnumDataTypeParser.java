package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class EnumDataTypeParser<T extends Enum<T>> implements DataTypeParser<T> {

    private final Class<T> enumClass;
    private final UnaryOperator<String> sourceOperator;

    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public EnumDataTypeParser(@NotNull Class<T> enumClass,
                              @NotNull UnaryOperator<String> sourceOperator,
                              @Nullable Supplier<T> nullSourceSupplier,
                              @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(enumClass);
        Objects.requireNonNull(sourceOperator);
        this.enumClass = enumClass;
        this.sourceOperator = sourceOperator;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public T parse(String source) throws DataTypeParseException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeParseException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeParseException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            try {
                return Enum.valueOf(enumClass, sourceOperator.apply(source));
            } catch (IllegalArgumentException e) {
                throw new DataTypeParseException(e.getMessage());
            }
        }
    }

    public Class<T> getEnumClass() {
        return enumClass;
    }

}
