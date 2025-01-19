package stexfires.record.mapper;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class SupplierMapper<T extends TextRecord, R extends TextRecord> implements RecordMapper<T, R> {

    private final Supplier<R> recordSupplier;

    /**
     * @param recordSupplier must be thread-safe
     */
    public SupplierMapper(Supplier<R> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        this.recordSupplier = recordSupplier;
    }

    @Override
    public final R map(T record) {
        return recordSupplier.get();
    }

}
