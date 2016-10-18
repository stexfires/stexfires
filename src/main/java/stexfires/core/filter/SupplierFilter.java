package stexfires.core.filter;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFilter<T extends Record> implements RecordFilter<T> {

    protected final BooleanSupplier validitySupplier;

    /**
     * @param validitySupplier must be thread-safe
     */
    public SupplierFilter(BooleanSupplier validitySupplier) {
        Objects.requireNonNull(validitySupplier);
        this.validitySupplier = validitySupplier;
    }

    @Override
    public boolean isValid(T record) {
        return validitySupplier.getAsBoolean();
    }

}
