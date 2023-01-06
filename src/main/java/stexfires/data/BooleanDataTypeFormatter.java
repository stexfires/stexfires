package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class BooleanDataTypeFormatter implements DataTypeFormatter<Boolean> {

    private final Supplier<String> trueSourceSupplier;
    private final Supplier<String> falseSourceSupplier;
    private final Supplier<String> nullSourceSupplier;

    public BooleanDataTypeFormatter(@NotNull Supplier<String> trueSourceSupplier,
                                    @NotNull Supplier<String> falseSourceSupplier,
                                    @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(trueSourceSupplier);
        Objects.requireNonNull(falseSourceSupplier);
        this.trueSourceSupplier = trueSourceSupplier;
        this.falseSourceSupplier = falseSourceSupplier;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable Boolean source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            return source ? trueSourceSupplier.get() : falseSourceSupplier.get();
        }
    }

}
