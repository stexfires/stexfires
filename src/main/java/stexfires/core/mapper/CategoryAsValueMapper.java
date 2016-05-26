package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.StandardRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoryAsValueMapper implements UnaryRecordMapper<Record> {

    protected final String nullCategoryValue;

    public CategoryAsValueMapper() {
        this(null);
    }

    public CategoryAsValueMapper(String nullCategoryValue) {
        this.nullCategoryValue = nullCategoryValue;
    }

    @Override
    public Record map(Record record) {
        List<String> newValues = new ArrayList<>(record.size() + 1);
        newValues.add(record.getCategoryOrElse(nullCategoryValue));
        newValues.addAll(Fields.collectValues(record));
        return new StandardRecord(null, record.getRecordId(), newValues);
    }

}
