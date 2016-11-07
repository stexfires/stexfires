package stexfires.core.filter;

import stexfires.core.Record;
import stexfires.util.RandomBooleanSupplier;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFilter<T extends Record> implements RecordFilter<T> {

    protected final Supplier<Boolean> validitySupplier;

    /**
     * @param validitySupplier must be thread-safe
     */
    public SupplierFilter(Supplier<Boolean> validitySupplier) {
        Objects.requireNonNull(validitySupplier);
        this.validitySupplier = validitySupplier;
    }

    public static <T extends Record> SupplierFilter<T> booleanSupplier(BooleanSupplier booleanSupplier) {
        return new SupplierFilter<>(() -> booleanSupplier.getAsBoolean());
    }

    public static <T extends Record> SupplierFilter<T> random(int percent) {
        return new SupplierFilter<>(new RandomBooleanSupplier(percent));
    }

    @Override
    public boolean isValid(T record) {
        return validitySupplier.get();
    }

}
