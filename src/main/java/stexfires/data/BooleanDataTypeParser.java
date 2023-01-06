package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class BooleanDataTypeParser implements DataTypeParser<Boolean> {

    private final SortedSet<String> trueValues;
    private final SortedSet<String> falseValues;
    private final Supplier<Boolean> nullSourceSupplier;
    private final Supplier<Boolean> emptySourceSupplier;

    public BooleanDataTypeParser(@NotNull Set<String> trueValues,
                                 @NotNull Set<String> falseValues,
                                 @Nullable Supplier<Boolean> nullSourceSupplier,
                                 @Nullable Supplier<Boolean> emptySourceSupplier) {
        Objects.requireNonNull(trueValues);
        Objects.requireNonNull(falseValues);
        this.trueValues = new TreeSet<>(trueValues);
        this.falseValues = new TreeSet<>(falseValues);
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable Boolean parse(@Nullable String source) throws DataTypeParseException {
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
                throw new DataTypeParseException("The source \"" + source + "\" is invalid.");
            }
        }
    }

}
