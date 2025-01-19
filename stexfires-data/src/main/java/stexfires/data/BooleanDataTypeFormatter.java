package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public final class BooleanDataTypeFormatter implements DataTypeFormatter<Boolean> {

    private final Supplier<@Nullable String> trueSourceSupplier;
    private final Supplier<@Nullable String> falseSourceSupplier;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public BooleanDataTypeFormatter(Supplier<@Nullable String> trueSourceSupplier,
                                    Supplier<@Nullable String> falseSourceSupplier,
                                    @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(trueSourceSupplier);
        Objects.requireNonNull(falseSourceSupplier);
        this.trueSourceSupplier = trueSourceSupplier;
        this.falseSourceSupplier = falseSourceSupplier;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static BooleanDataTypeFormatter of(String trueValue,
                                              String falseValue,
                                              @Nullable String nullValue) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        if (trueValue.equals(falseValue)) {
            throw new IllegalArgumentException("trueValue and falseValue must be different");
        }
        return new BooleanDataTypeFormatter(
                () -> trueValue,
                () -> falseValue,
                () -> nullValue);
    }

    @Override
    public @Nullable String format(@Nullable Boolean source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            return source ? trueSourceSupplier.get() : falseSourceSupplier.get();
        }
    }

}
