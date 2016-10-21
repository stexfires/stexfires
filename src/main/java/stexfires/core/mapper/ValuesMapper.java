package stexfires.core.mapper;

import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.StandardRecord;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
// TODO Rename static getInstance methods
public class ValuesMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final Function<? super T, List<String>> valuesFunction;

    public ValuesMapper(Function<T, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        this.valuesFunction = valuesFunction;
    }

    public static <T extends Record> ValuesMapper<T> getInstance(List<Function<Record, String>> functions) {
        Objects.requireNonNull(functions);
        return new ValuesMapper<>(r -> functions.stream().map(f -> f.apply(r)).collect(Collectors.toList()));
    }

    public static <T extends Record> ValuesMapper<T> getInstance(Function<List<Field>, List<String>> function) {
        Objects.requireNonNull(function);
        return new ValuesMapper<>(r -> function.apply(r.listOfFields()));
    }

    public static <T extends Record> ValuesMapper<T> getInstance(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValuesMapper<>(r -> Fields.collectValues(r, fieldValueMapper));
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(record.getCategory(), record.getRecordId(), valuesFunction.apply(record));
    }

}
