package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see TextsMapper
 * @since 0.1
 */
public class RecordIdMapper<T extends TextRecord> extends FunctionMapper<T> {

    public RecordIdMapper(Function<? super T, @Nullable Long> recordIdFunction) {
        super(TextRecord::category, recordIdFunction, TextFields::collectTexts);
    }

    public static <T extends TextRecord> RecordIdMapper<T> identity() {
        return new RecordIdMapper<>(TextRecord::recordId);
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> supplier(Supplier<@Nullable Long> recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> recordIdSupplier.get());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> primitiveIntSupplier(IntSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> (long) recordIdSupplier.getAsInt());
    }

    /**
     * @param recordIdSupplier must be thread-safe
     */
    public static <T extends TextRecord> RecordIdMapper<T> primitiveLongSupplier(LongSupplier recordIdSupplier) {
        Objects.requireNonNull(recordIdSupplier);
        return new RecordIdMapper<>(record -> recordIdSupplier.getAsLong());
    }

    public static <T extends TextRecord> RecordIdMapper<T> constant(Long recordId) {
        Objects.requireNonNull(recordId);
        return new RecordIdMapper<>(record -> recordId);
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T extends TextRecord> RecordIdMapper<T> constantNull() {
        return new RecordIdMapper<>(record -> null);
    }

    public static <T extends TextRecord> RecordIdMapper<T> categoryFunction(Function<@Nullable String, @Nullable Long> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new RecordIdMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> RecordIdMapper<T> categoryAsOptionalFunction(Function<Optional<String>, @Nullable Long> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new RecordIdMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> RecordIdMapper<T> recordId() {
        return new RecordIdMapper<>(TextRecord::recordId);
    }

    public static <T extends TextRecord> RecordIdMapper<T> textAt(int index, Function<@Nullable String, @Nullable Long> textFunction) {
        Objects.requireNonNull(textFunction);
        return new RecordIdMapper<>(record -> textFunction.apply(record.textAt(index)));
    }

}
