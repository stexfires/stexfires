package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFieldValueMapper implements FieldValueMapper {

    protected final Supplier<String> valueSupplier;

    /**
     * @param valueSupplier must be thread-safe
     */
    public SupplierFieldValueMapper(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        this.valueSupplier = valueSupplier;
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public static SupplierFieldValueMapper intSupplier(IntSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return new SupplierFieldValueMapper(() -> String.valueOf(valueSupplier.getAsInt()));
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public static SupplierFieldValueMapper longSupplier(LongSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return new SupplierFieldValueMapper(() -> String.valueOf(valueSupplier.getAsLong()));
    }

    @Override
    public String mapToValue(Field field) {
        return valueSupplier.get();
    }

}
