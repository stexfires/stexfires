package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.StandardRecord;
import stexfires.util.Strings;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final RecordMessage<? super T> categoryMessage;

    public CategoryMapper(RecordMessage<? super T> categoryMessage) {
        Objects.requireNonNull(categoryMessage);
        this.categoryMessage = categoryMessage;
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public CategoryMapper(Supplier<String> categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        this.categoryMessage = record -> categorySupplier.get();
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public CategoryMapper(IntSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        this.categoryMessage = record -> String.valueOf(categorySupplier.getAsInt());
    }

    /**
     * @param categorySupplier must be thread-safe
     */
    public CategoryMapper(LongSupplier categorySupplier) {
        Objects.requireNonNull(categorySupplier);
        this.categoryMessage = record -> String.valueOf(categorySupplier.getAsLong());
    }

    public CategoryMapper(String value) {
        this.categoryMessage = record -> value;
    }

    public CategoryMapper() {
        this.categoryMessage = record -> (String) null;
    }

    public static <T extends Record> CategoryMapper<T> valueAt(int index) {
        return new CategoryMapper<>(record -> record.getValueAt(index));
    }

    public static <T extends Record> CategoryMapper<T> recordId() {
        return new CategoryMapper<>(record -> Strings.asString(record.getRecordId()));
    }

    public static <T extends Record> CategoryMapper<T> recordIdSequence() {
        return new CategoryMapper<>(Records.recordIdPrimitiveSequence());
    }

    public static <T extends Record> CategoryMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new CategoryMapper<>(record -> path.getFileName().toString());
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(categoryMessage.createMessage(record),
                record.getRecordId(),
                Fields.collectValues(record));
    }

}
