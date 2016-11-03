package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.StandardRecord;
import stexfires.util.Strings;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddValueMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final RecordMessage<? super T> valueMessage;

    public AddValueMapper(RecordMessage<? super T> valueMessage) {
        Objects.requireNonNull(valueMessage);
        this.valueMessage = valueMessage;
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public AddValueMapper(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        this.valueMessage = record -> valueSupplier.get();
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public AddValueMapper(IntSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        this.valueMessage = record -> String.valueOf(valueSupplier.getAsInt());
    }

    /**
     * @param valueSupplier must be thread-safe
     */
    public AddValueMapper(LongSupplier valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        this.valueMessage = record -> String.valueOf(valueSupplier.getAsLong());
    }

    public AddValueMapper(String value) {
        this.valueMessage = record -> value;
    }

    public AddValueMapper() {
        this.valueMessage = record -> (String) null;
    }

    public static <T extends Record> AddValueMapper<T> category() {
        return new AddValueMapper<>(Record::getCategory);
    }

    public static <T extends Record> AddValueMapper<T> recordId() {
        return new AddValueMapper<>(record -> Strings.asString(record.getRecordId()));
    }

    public static <T extends Record> AddValueMapper<T> recordIdSequence() {
        return new AddValueMapper<>(Records.recordIdPrimitiveSequence());
    }

    public static <T extends Record> AddValueMapper<T> fileName(Path path) {
        Objects.requireNonNull(path);
        return new AddValueMapper<>(record -> path.getFileName().toString());
    }

    @Override
    public Record map(T record) {
        List<String> newValues = new ArrayList<>(record.size() + 1);
        newValues.addAll(Fields.collectValues(record));
        newValues.add(valueMessage.createMessage(record));
        return new StandardRecord(record.getCategory(), record.getRecordId(), newValues);
    }

}
