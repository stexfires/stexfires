package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMapper<T extends Record, R extends Record> implements RecordMapper<T, R> {

    protected final Supplier<? extends R> recordSupplier;

    /**
     * @param recordSupplier must be thread-safe
     */
    public SupplierMapper(Supplier<? extends R> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        this.recordSupplier = recordSupplier;
    }

    @Override
    public final R map(T record) {
        return recordSupplier.get();
    }

}
