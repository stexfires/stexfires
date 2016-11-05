package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @see stexfires.core.mapper.AddValueMapper
 * @see stexfires.core.mapper.CategoryMapper
 * @since 0.1
 */
public class RecordIdMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final Function<? super T, Long> recordIdFunction;

    public RecordIdMapper(Function<? super T, Long> recordIdFunction) {
        Objects.requireNonNull(recordIdFunction);
        this.recordIdFunction = recordIdFunction;
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends Record> RecordIdMapper<T> supplier(Supplier<Long> recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> recordIdSupplier.get());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends Record> RecordIdMapper<T> intSupplier(IntSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> (long) recordIdSupplier.getAsInt());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends Record> RecordIdMapper<T> longSupplier(LongSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> (Long) recordIdSupplier.getAsLong());
    }

    public static <T extends Record> RecordIdMapper<T> constant(Long recordId) {
        return new RecordIdMapper<>(record -> recordId);
    }

    public static <T extends Record> RecordIdMapper<T> constantNull() {
        return new RecordIdMapper<>(record -> null);
    }

    public static <T extends Record> RecordIdMapper<T> category(Function<String, Long> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new RecordIdMapper<>(record -> categoryFunction.apply(record.getCategory()));
    }

    public static <T extends Record> RecordIdMapper<T> categoryAsOptional(Function<Optional<String>, Long> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new RecordIdMapper<>(record -> categoryAsOptionalFunction.apply(record.getCategoryAsOptional()));
    }

    public static <T extends Record> RecordIdMapper<T> recordId() {
        return new RecordIdMapper<>(Record::getRecordId);
    }

    public static <T extends Record> RecordIdMapper<T> valueAt(int index, Function<String, Long> valueFunction) {
        Objects.requireNonNull(valueFunction);
        return new RecordIdMapper<>(record -> valueFunction.apply(record.getValueAt(index)));
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(record.getCategory(),
                recordIdFunction.apply(record),
                Fields.collectValues(record));
    }

}
