package stexfires.record.mapper;

import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see TextsMapper
 * @since 0.1
 */
public class RecordIdMapper<T extends TextRecord> extends FunctionMapper<T> {

    public RecordIdMapper(Function<? super T, Long> recordIdFunction) {
        super(TextRecord::category, recordIdFunction, Fields::collectTexts);
    }

    public static <T extends TextRecord> RecordIdMapper<T> identity() {
        return new RecordIdMapper<>(TextRecord::recordId);
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> supplier(Supplier<Long> recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> recordIdSupplier.get());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> intSupplier(IntSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> (long) recordIdSupplier.getAsInt());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> longSupplier(LongSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> recordIdSupplier.getAsLong());
    }

    public static <T extends TextRecord> RecordIdMapper<T> constant(Long recordId) {
        return new RecordIdMapper<>(record -> recordId);
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T extends TextRecord> RecordIdMapper<T> constantNull() {
        return new RecordIdMapper<>(record -> null);
    }

    public static <T extends TextRecord> RecordIdMapper<T> categoryFunction(Function<String, Long> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new RecordIdMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> RecordIdMapper<T> categoryAsOptionalFunction(Function<Optional<String>, Long> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new RecordIdMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> RecordIdMapper<T> recordId() {
        return new RecordIdMapper<>(TextRecord::recordId);
    }

    public static <T extends TextRecord> RecordIdMapper<T> textAt(int index, Function<String, Long> textFunction) {
        Objects.requireNonNull(textFunction);
        return new RecordIdMapper<>(record -> textFunction.apply(record.textAt(index)));
    }

}
