package stexfires.core.mapper;

import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMapper<R extends Record> implements RecordMapper<Record, R> {

    protected final Supplier<R> recordSupplier;

    /**
     * @param recordSupplier must be thread-safe
     */
    public SupplierMapper(Supplier<R> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        this.recordSupplier = recordSupplier;
    }

    @Override
    public R map(Record record) {
        return recordSupplier.get();
    }

}
