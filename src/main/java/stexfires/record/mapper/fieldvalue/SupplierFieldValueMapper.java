package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFieldValueMapper implements FieldValueMapper {

    private final Supplier<String> valueSupplier;

    /**
     * @param valueSupplier must be thread-safe
     */
    public SupplierFieldValueMapper(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        this.valueSupplier = valueSupplier;
    }

    @Override
    public final String mapToValue(@NotNull Field field) {
        return valueSupplier.get();
    }

}
