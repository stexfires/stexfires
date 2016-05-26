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
public class ReverseMapper implements UnaryRecordMapper<Record> {

    @Override
    public Record map(Record record) {
        if (record.size() < 2) {
            return record;
        }

        List<String> values = Fields.collectValues(record);
        Collections.reverse(values);

        return new StandardRecord(record.getCategory(), record.getRecordId(), values);
    }

}
