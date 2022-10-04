package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.function.RandomBooleanSupplier;
import stexfires.util.function.RepeatingPatternBooleanSupplier;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierFilter<T extends TextRecord> implements RecordFilter<T> {

    private final Supplier<Boolean> validitySupplier;

    /**
     * @param validitySupplier must be thread-safe
     */
    public SupplierFilter(Supplier<Boolean> validitySupplier) {
        Objects.requireNonNull(validitySupplier);
        this.validitySupplier = validitySupplier;
    }

    /**
     * @param validitySupplier must be thread-safe
     */
    public static <T extends TextRecord> SupplierFilter<T> primitiveBooleanSupplier(BooleanSupplier validitySupplier) {
        return new SupplierFilter<>(validitySupplier::getAsBoolean);
    }

    public static <T extends TextRecord> SupplierFilter<T> random(int percent) {
        return new SupplierFilter<>(new RandomBooleanSupplier(percent));
    }

    public static <T extends TextRecord> SupplierFilter<T> pattern(boolean... pattern) {
        return new SupplierFilter<>(RepeatingPatternBooleanSupplier.primitiveBooleans(pattern));
    }

    @Override
    public final boolean isValid(T record) {
        return validitySupplier.get();
    }

}
