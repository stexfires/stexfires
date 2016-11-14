package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

import java.util.Collections;
import java.util.List;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReverseValuesMapper<T extends Record> implements RecordMapper<T, Record> {

    @Override
    public Record map(T record) {
        if (record.size() < 2) {
            return record;
        }

        List<String> values = Fields.collectValues(record);
        Collections.reverse(values);

        return new StandardRecord(record.getCategory(), record.getRecordId(), values);
    }

}
