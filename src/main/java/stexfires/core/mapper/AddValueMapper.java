package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.message.RecordMessage;
import stexfires.util.StringUnaryOperatorType;
import stexfires.util.Strings;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @see stexfires.core.mapper.CategoryMapper
 * @see stexfires.core.mapper.RecordIdMapper
 * @see stexfires.core.mapper.ValuesMapper
 * @since 0.1
 */
public class AddValueMapper<T extends TextRecord> extends ValuesMapper<T> {

    public AddValueMapper(Function<? super T, String> valueFunction) {
        super(record -> {
            List<String> newValues = new ArrayList<>(record.size() + 1);
            newValues.addAll(Fields.collectValues(record));
            newValues.add(valueFunction.apply(record));
            return newValues;
        });
        Objects.requireNonNull(valueFunction);
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddValueMapper<T> supplier(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return new AddValueMapper<>(record -> valueSupplier.get());
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddValueMapper<T> intSupplier(IntSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return new AddValueMapper<>(record -> String.valueOf(valueSupplier.getAsInt()));
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddValueMapper<T> longSupplier(LongSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return new AddValueMapper<>(record -> String.valueOf(valueSupplier.getAsLong()));
    }

    public static <T extends TextRecord> AddValueMapper<T> recordMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new AddValueMapper<>(recordMessage.asFunction());
    }

    public static <T extends TextRecord> AddValueMapper<T> constant(String value) {
        return new AddValueMapper<>(record -> value);
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T extends TextRecord> AddValueMapper<T> constantNull() {
        return new AddValueMapper<>(record -> null);
    }

    public static <T extends TextRecord> AddValueMapper<T> category() {
        return new AddValueMapper<>(TextRecord::category);
    }

    public static <T extends TextRecord> AddValueMapper<T> categoryOrElse(String other) {
        return new AddValueMapper<>(record -> record.categoryOrElse(other));
    }

    public static <T extends TextRecord> AddValueMapper<T> categoryFunction(Function<String, String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new AddValueMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> AddValueMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new AddValueMapper<>(record -> categoryOperator.operateString(record.category()));
    }

    public static <T extends TextRecord> AddValueMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator, Locale locale) {
        Objects.requireNonNull(categoryOperator);
        return new AddValueMapper<>(record -> categoryOperator.operateString(record.category(), locale));
    }

    public static <T extends TextRecord> AddValueMapper<T> categoryAsOptionalFunction(Function<Optional<String>, String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new AddValueMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> AddValueMapper<T> recordId() {
        return new AddValueMapper<>(record -> Strings.asString(record.recordId()));
    }

    public static <T extends TextRecord> AddValueMapper<T> valueAt(int index) {
        return new AddValueMapper<>(record -> record.getValueAt(index));
    }

    public static <T extends TextRecord> AddValueMapper<T> valueAtOrElse(int index, String other) {
        return new AddValueMapper<>(record -> record.getValueAtOrElse(index, other));
    }

    public static <T extends TextRecord> AddValueMapper<T> fieldAtOrElse(int index, FieldValueMapper fieldValueMapper, String other) {
        Objects.requireNonNull(fieldValueMapper);
        return new AddValueMapper<>(record -> record.isValidIndex(index) ? fieldValueMapper.mapToValue(record.getFieldAt(index)) : other);
    }

    public static <T extends TextRecord> AddValueMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new AddValueMapper<>(record -> path.getFileName().toString());
    }

}
