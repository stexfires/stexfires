package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class BooleanDataTypeParser implements DataTypeParser<Boolean> {

    private final NavigableSet<String> trueValues;
    private final NavigableSet<String> falseValues;
    private final Supplier<Boolean> nullSourceSupplier;
    private final Supplier<Boolean> emptySourceSupplier;

    public BooleanDataTypeParser(@NotNull Collection<String> trueValues,
                                 @NotNull Collection<String> falseValues,
                                 @Nullable Supplier<Boolean> nullSourceSupplier,
                                 @Nullable Supplier<Boolean> emptySourceSupplier) {
        Objects.requireNonNull(trueValues);
        Objects.requireNonNull(falseValues);
        // Use TreeSet so that duplicate values are removed and 'null' is prevented.
        this.trueValues = new TreeSet<>(trueValues);
        this.falseValues = new TreeSet<>(falseValues);
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable Boolean parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            if (trueValues.contains(source)) {
                return Boolean.TRUE;
            } else if (falseValues.contains(source)) {
                return Boolean.FALSE;
            } else {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "The source \"" + source + "\" is invalid.");
            }
        }
    }

}
