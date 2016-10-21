package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueAsCategoryMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final int index;

    public ValueAsCategoryMapper(int index) {
        this.index = index;
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(record.getValueAt(index), record.getRecordId(), Fields.collectValues(record));
    }

}
