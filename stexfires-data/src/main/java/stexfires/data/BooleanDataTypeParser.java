package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class BooleanDataTypeParser implements DataTypeParser<Boolean> {

    private final Set<String> trueValues;
    private final Set<String> falseValues;
    private final @Nullable Supplier<@Nullable Boolean> nullSourceSupplier;
    private final @Nullable Supplier<@Nullable Boolean> emptySourceSupplier;

    public BooleanDataTypeParser(Collection<String> trueValues,
                                 Collection<String> falseValues,
                                 @Nullable Supplier<@Nullable Boolean> nullSourceSupplier,
                                 @Nullable Supplier<@Nullable Boolean> emptySourceSupplier) {
        Objects.requireNonNull(trueValues);
        Objects.requireNonNull(falseValues);
        // Use 'Set.copyOf' so that duplicate values are removed, 'null' is prevented and unmodifiable sets ('Set.of') are reused.
        this.trueValues = Set.copyOf(trueValues);
        this.falseValues = Set.copyOf(falseValues);
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static BooleanDataTypeParser of(String trueValue,
                                           String falseValue,
                                           @Nullable Boolean nullOrEmptyValue) {
        return of(trueValue, falseValue, nullOrEmptyValue, nullOrEmptyValue);
    }

    public static BooleanDataTypeParser of(String trueValue,
                                           String falseValue,
                                           @Nullable Boolean nullValue,
                                           @Nullable Boolean emptyValue) {
        Objects.requireNonNull(trueValue);
        Objects.requireNonNull(falseValue);
        if (trueValue.equals(falseValue)) {
            throw new IllegalArgumentException("trueValue and falseValue must be different");
        }
        return new BooleanDataTypeParser(
                Set.of(trueValue),
                Set.of(falseValue),
                () -> nullValue,
                () -> emptyValue);
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
