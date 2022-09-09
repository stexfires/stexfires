package stexfires.record.mapper;

import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.message.RecordMessage;
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
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see TextsMapper
 * @since 0.1
 */
public class AddTextMapper<T extends TextRecord> extends TextsMapper<T> {

    public AddTextMapper(Function<? super T, String> textFunction) {
        super(record -> {
            List<String> newTexts = new ArrayList<>(record.size() + 1);
            newTexts.addAll(Fields.collectTexts(record));
            newTexts.add(textFunction.apply(record));
            return newTexts;
        });
        Objects.requireNonNull(textFunction);
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> supplier(Supplier<String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        return new AddTextMapper<>(record -> textSupplier.get());
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> intSupplier(IntSupplier textSupplier) {
        Objects.requireNonNull(textSupplier);
        return new AddTextMapper<>(record -> String.valueOf(textSupplier.getAsInt()));
    }

    /**
     * @param textSupplier must be thread-safe
     */
    public static <T extends TextRecord> AddTextMapper<T> longSupplier(LongSupplier textSupplier) {
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
        return new AddTextMapper<>(record -> record.categoryOrElse(other));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryFunction(Function<String, String> categoryFunction) {
        Objects.requireNonNull(categoryFunction);
        return new AddTextMapper<>(record -> categoryFunction.apply(record.category()));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator) {
        Objects.requireNonNull(categoryOperator);
        return new AddTextMapper<>(record -> categoryOperator.operateString(record.category()));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryOperator(StringUnaryOperatorType categoryOperator, Locale locale) {
        Objects.requireNonNull(categoryOperator);
        return new AddTextMapper<>(record -> categoryOperator.operateString(record.category(), locale));
    }

    public static <T extends TextRecord> AddTextMapper<T> categoryAsOptionalFunction(Function<Optional<String>, String> categoryAsOptionalFunction) {
        Objects.requireNonNull(categoryAsOptionalFunction);
        return new AddTextMapper<>(record -> categoryAsOptionalFunction.apply(record.categoryAsOptional()));
    }

    public static <T extends TextRecord> AddTextMapper<T> recordId() {
        return new AddTextMapper<>(record -> Strings.asString(record.recordId()));
    }

    public static <T extends TextRecord> AddTextMapper<T> textAt(int index) {
        return new AddTextMapper<>(record -> record.textAt(index));
    }

    public static <T extends TextRecord> AddTextMapper<T> textAtOrElse(int index, String other) {
        return new AddTextMapper<>(record -> record.textAtOrElse(index, other));
    }

    public static <T extends TextRecord> AddTextMapper<T> fieldAtOrElse(int index, FieldTextMapper fieldTextMapper, String other) {
        Objects.requireNonNull(fieldTextMapper);
        return new AddTextMapper<>(record -> record.isValidIndex(index) ? fieldTextMapper.mapToText(record.fieldAt(index)) : other);
    }

    public static <T extends TextRecord> AddTextMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new AddTextMapper<>(record -> path.getFileName().toString());
    }

}
