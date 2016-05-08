package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.Objects;
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

    @Override
    public String mapToValue(Field field) {
        return valueSupplier.get();
    }

}
