package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.message.RecordMessage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see TextsMapper
 * @since 0.1
 */
public class AddTextMapper<T extends TextRecord> extends TextsMapper<T> {

    public AddTextMapper(Function<? super T, @Nullable String> textFunction) {
        super(record -> {
            List<@Nullable String> newTexts = new ArrayList<>(record.size() + 1);
            newTexts.addAll(TextFields.collectTexts(record));
            newTexts.add(textFunction.apply(record));
            return newTexts;
        });
        Objects.requireNonNull(textFunction);
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> supplier(Supplier<@Nullable String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        return new AddTextMapper<>(record -> textSupplier.get());
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> primitiveIntSupplier(IntSupplier textSupplier) {
        Objects.requireNonNull(textSupplier);
        return new AddTextMapper<>(record -> String.valueOf(textSupplier.getAsInt()));
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> primitiveLongSupplier(LongSupplier textSupplier) {
        Objects.requireNonNull(textSupplier);
        return new AddTextMapper<>(record -> String.valueOf(textSupplier.getAsLong()));
    }

    public static <T extends TextRecord> AddTextMapper<T> recordMessage(RecordMessage<? super T> recordMessage) {
        Objects.requireNonNull(recordMessage);
        return new AddTextMapper<>(recordMessage.asFunction());
    }

    public static <T extends TextRecord> AddTextMapper<T> constant(String text) {
        return new AddTextMapper<>(record -> text);
    }

    @SuppressWarnings("ReturnOfNull")
    public static <T extends TextRecord> AddTextMapper<T> constantNull() {
        return new AddTextMapper<>(record -> null);
    }

    public static <T extends TextRecord> AddTextMapper<T> category() {
        return new AddTextMapper<>(TextRecord::category);
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryOrElse(String other) {
        return new AddTextMapper<>(record -> record.categoryAsOptional().orElse(other));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryFunction(Function<@Nullable String, @Nullable String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new AddTextMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryOperator(UnaryOperator<@Nullable String> categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new AddTextMapper<>(record -> categoryOperator.apply(record.category()));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryAsOptionalFunction(Function<Optional<String>, @Nullable String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new AddTextMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> AddTextMapper<T> recordId() {
        return new AddTextMapper<>(TextRecord::recordIdAsString);
    }

    public static <T extends TextRecord> AddTextMapper<T> textAt(int index) {
        return new AddTextMapper<>(record -> record.textAt(index));
    }

    public static <T extends TextRecord> AddTextMapper<T> textAtOrElse(int index, String other) {
        return new AddTextMapper<>(record -> record.textAtOrElse(index, other));
    }

    public static <T extends TextRecord> AddTextMapper<T> fieldAtOrElse(int index, FieldTextMapper fieldTextMapper, String other) {
        Objects.requireNonNull(fieldTextMapper);
        return new AddTextMapper<>(record -> {
            TextField field = record.fieldAt(index);
            return field != null ? fieldTextMapper.mapToText(field) : other;
        });
    }

    public static <T extends TextRecord> AddTextMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new AddTextMapper<>(record -> path.getFileName().toString());
    }

}
