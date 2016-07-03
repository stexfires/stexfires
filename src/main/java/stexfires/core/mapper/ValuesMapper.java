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
public class ValuesMapper implements UnaryRecordMapper<Record> {

    protected final Function<Record, List<String>> valuesFunction;

    public ValuesMapper(Function<Record, List<String>> valuesFunction) {
        Objects.requireNonNull(valuesFunction);
        this.valuesFunction = valuesFunction;
    }

    public static ValuesMapper getInstance(List<Function<Record, String>> functions) {
        Objects.requireNonNull(functions);
        return new ValuesMapper(r -> functions.stream().map(f -> f.apply(r)).collect(Collectors.toList()));
    }

    public static ValuesMapper getInstance(Function<List<Field>, List<String>> function) {
        Objects.requireNonNull(function);
        return new ValuesMapper(r -> function.apply(r.listOfFields()));
    }

    public static ValuesMapper getInstance(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValuesMapper(r -> Fields.collectValues(r, fieldValueMapper));
    }

    @Override
    public Record map(Record record) {
        return new StandardRecord(record.getCategory(), record.getRecordId(), valuesFunction.apply(record));
    }

}
