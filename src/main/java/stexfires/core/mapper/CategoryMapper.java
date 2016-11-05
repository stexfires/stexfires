package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.StandardRecord;
import stexfires.util.StringUnaryOperatorType;
import stexfires.util.Strings;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @see stexfires.core.mapper.AddValueMapper
 * @see stexfires.core.mapper.RecordIdMapper
 * @since 0.1
 */
public class CategoryMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final Function<? super T, String> categoryFunction;

    public CategoryMapper(Function<? super T, String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        this.categoryFunction = categoryFunction;
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends Record> CategoryMapper<T> supplier(Supplier<String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> categorySupplier.get());
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends Record> CategoryMapper<T> intSupplier(IntSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsInt()));
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends Record> CategoryMapper<T> longSupplier(LongSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsLong()));
    }

    public static <T extends Record> CategoryMapper<T> recordMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new CategoryMapper<>(recordMessage.asFunction());
    }

    public static <T extends Record> CategoryMapper<T> constant(String category) {
        return new CategoryMapper<>(record -> category);
    }

    public static <T extends Record> CategoryMapper<T> constantNull() {
        return new CategoryMapper<>(record -> null);
    }

    public static <T extends Record> CategoryMapper<T> category() {
        return new CategoryMapper<>(Record::getCategory);
    }

    public static <T extends Record> CategoryMapper<T> categoryOrElse(String other) {
        return new CategoryMapper<>(record -> record.getCategoryOrElse(other));
    }

    public static <T extends Record> CategoryMapper<T> category(Function<String, String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new CategoryMapper<>(record -> categoryFunction.apply(record.getCategory()));
    }

    public static <T extends Record> CategoryMapper<T> category(StringUnaryOperatorType categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new CategoryMapper<>(record -> categoryOperator.operate(record.getCategory()));
    }

    public static <T extends Record> CategoryMapper<T> category(StringUnaryOperatorType categoryOperator, Locale locale) {
        Objects.requireNonNull(categoryOperator);
        return new CategoryMapper<>(record -> categoryOperator.operate(record.getCategory(), locale));
    }

    public static <T extends Record> CategoryMapper<T> categoryAsOptional(Function<Optional<String>, String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new CategoryMapper<>(record -> categoryAsOptionalFunction.apply(record.getCategoryAsOptional()));
    }

    public static <T extends Record> CategoryMapper<T> recordId() {
        return new CategoryMapper<>(record -> Strings.asString(record.getRecordId()));
    }

    public static <T extends Record> CategoryMapper<T> valueAt(int index) {
        return new CategoryMapper<>(record -> record.getValueAt(index));
    }

    public static <T extends Record> CategoryMapper<T> valueAtOrElse(int index, String other) {
        return new CategoryMapper<>(record -> record.getValueAtOrElse(index, other));
    }

    public static <T extends Record> CategoryMapper<T> fieldAtOrElse(int index, FieldValueMapper fieldValueMapper, String other) {
        Objects.requireNonNull(fieldValueMapper);
        return new CategoryMapper<>(record -> record.isValidIndex(index) ? fieldValueMapper.mapToValue(record.getFieldAt(index)) : other);
    }

    public static <T extends Record> CategoryMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new CategoryMapper<>(record -> path.getFileName().toString());
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(categoryFunction.apply(record),
                record.getRecordId(),
                Fields.collectValues(record));
    }

}
