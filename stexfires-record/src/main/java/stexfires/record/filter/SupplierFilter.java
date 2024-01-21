package stexfires.record.filter;

import stexfires.record.TextRecord;
import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.RepeatingPatternBooleanSupplier;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
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
        Objects.requireNonNull(validitySupplier);
        return new SupplierFilter<>(validitySupplier::getAsBoolean);
    }

    public static <T extends TextRecord> SupplierFilter<T> random(int percent, RandomGenerator randomGenerator) {
        Objects.requireNonNull(randomGenerator);
        return new SupplierFilter<>(new RandomBooleanSupplier(percent, randomGenerator));
    }

    public static <T extends TextRecord> SupplierFilter<T> pattern(boolean... pattern) {
        Objects.requireNonNull(pattern);
        return new SupplierFilter<>(RepeatingPatternBooleanSupplier.primitiveBooleans(pattern));
    }

    @Override
    public final boolean isValid(T record) {
        return validitySupplier.get();
    }

}
