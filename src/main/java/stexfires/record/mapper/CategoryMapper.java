package stexfires.record.mapper;

import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.fieldvalue.FieldValueMapper;
import stexfires.record.message.RecordMessage;
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
 * @see stexfires.record.mapper.AddValueMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see stexfires.record.mapper.ValuesMapper
 * @since 0.1
 */
public class CategoryMapper<T extends TextRecord> extends FunctionMapper<T> {

    public CategoryMapper(Function<? super T, String> categoryFunction) {
        super(categoryFunction, TextRecord::recordId, Fields::collectValues);
    }

    public static <T extends TextRecord> CategoryMapper<T> identity() {
        return new CategoryMapper<>(TextRecord::category);
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> supplier(Supplier<String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> categorySupplier.get());
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> intSupplier(IntSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsInt()));
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> longSupplier(LongSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsLong()));
    }

    public static <T extends TextRecord> CategoryMapper<T> recordMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new CategoryMapper<>(recordMessage.asFunction());
    }

    public static <T extends TextRecord> CategoryMapper<T> constant(String category) {
        return new CategoryMapper<>(record -> category);
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T extends TextRecord> CategoryMapper<T> constantNull() {
        return new CategoryMapper<>(record -> null);
    }

    public static <T extends TextRecord> CategoryMapper<T> category() {
        return new CategoryMapper<>(TextRecord::category);
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryOrElse(String other) {
        return new CategoryMapper<>(record -> record.categoryOrElse(other));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryFunction(Function<String, String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new CategoryMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new CategoryMapper<>(record -> categoryOperator.operateString(record.category()));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator, Locale locale) {
        Objects.requireNonNull(categoryOperator);
        return new CategoryMapper<>(record -> categoryOperator.operateString(record.category(), locale));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryAsOptionalFunction(Function<Optional<String>, String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new CategoryMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> CategoryMapper<T> recordId() {
        return new CategoryMapper<>(record -> Strings.asString(record.recordId()));
    }

    public static <T extends TextRecord> CategoryMapper<T> valueAt(int index) {
        return new CategoryMapper<>(record -> record.valueAt(index));
    }

    public static <T extends TextRecord> CategoryMapper<T> valueAtOrElse(int index, String other) {
        return new CategoryMapper<>(record -> record.valueAtOrElse(index, other));
    }

    public static <T extends TextRecord> CategoryMapper<T> fieldAtOrElse(int index, FieldValueMapper fieldValueMapper, String other) {
        Objects.requireNonNull(fieldValueMapper);
        return new CategoryMapper<>(record -> record.isValidIndex(index) ? fieldValueMapper.mapToValue(record.fieldAt(index)) : other);
    }

    public static <T extends TextRecord> CategoryMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new CategoryMapper<>(record -> path.getFileName().toString());
    }

}