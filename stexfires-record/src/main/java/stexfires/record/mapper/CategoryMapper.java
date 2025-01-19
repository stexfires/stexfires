package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.message.RecordMessage;

import java.nio.file.Path;
import java.util.*;
import java.util.function.*;

/**
 * @see AddTextMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see TextsMapper
 * @since 0.1
 */
public class CategoryMapper<T extends TextRecord> extends FunctionMapper<T> {

    public CategoryMapper(Function<? super T, @Nullable String> categoryFunction) {
        super(categoryFunction, TextRecord::recordId, TextFields::collectTexts);
    }

    public static <T extends TextRecord> CategoryMapper<T> identity() {
        return new CategoryMapper<>(TextRecord::category);
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> supplier(Supplier<@Nullable String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> categorySupplier.get());
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> primitiveIntSupplier(IntSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsInt()));
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public static <T extends TextRecord> CategoryMapper<T> primitiveLongSupplier(LongSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        return new CategoryMapper<>(record -> String.valueOf(categorySupplier.getAsLong()));
    }

    public static <T extends TextRecord> CategoryMapper<T> recordMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new CategoryMapper<>(recordMessage.asFunction());
    }

    public static <T extends TextRecord> CategoryMapper<T> constant(String category) {
        Objects.requireNonNull(category);
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
        Objects.requireNonNull(other);
        return new CategoryMapper<>(record -> record.categoryAsOptional().orElse(other));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryFunction(Function<@Nullable String, @Nullable String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new CategoryMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryOperator(UnaryOperator<@Nullable String> categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new CategoryMapper<>(record -> categoryOperator.apply(record.category()));
    }

    public static <T extends TextRecord> CategoryMapper<T> categoryAsOptionalFunction(Function<Optional<String>, @Nullable String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new CategoryMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> CategoryMapper<T> recordIdAsString() {
        return new CategoryMapper<>(TextRecord::recordIdAsString);
    }

    public static <T extends TextRecord> CategoryMapper<T> textAt(int index) {
        return new CategoryMapper<>(record -> record.textAt(index));
    }

    public static <T extends TextRecord> CategoryMapper<T> textAtOrElse(int index, String other) {
        Objects.requireNonNull(other);
        return new CategoryMapper<>(record -> record.textAtOrElse(index, other));
    }

    public static <T extends TextRecord> CategoryMapper<T> fieldAtOrElse(int index, FieldTextMapper fieldTextMapper, @Nullable String other) {
        Objects.requireNonNull(fieldTextMapper);
        return new CategoryMapper<>(record -> {
            TextField field = record.fieldAt(index);
            return (field != null) ? fieldTextMapper.mapToText(field) : other;
        });
    }

    public static <T extends TextRecord> CategoryMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new CategoryMapper<>(record -> path.getFileName().toString());
    }

}
