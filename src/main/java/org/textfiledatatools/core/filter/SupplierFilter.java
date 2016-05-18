package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFilter implements RecordFilter<Record> {

    protected final BooleanSupplier validitySupplier;

    /**
     * @param validitySupplier must be thread-safe
     */
    public SupplierFilter(BooleanSupplier validitySupplier) {
        Objects.requireNonNull(validitySupplier);
        this.validitySupplier = validitySupplier;
    }

    @Override
    public boolean isValid(Record record) {
        return validitySupplier.getAsBoolean();
    }

}
