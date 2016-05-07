package org.textfiledatatools.core.mapper;

import org.textfiledatatools.core.Fields;
import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.mapper.fieldvalue.FieldValueMapper;
import org.textfiledatatools.core.record.StandardRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValuesMapper implements UnaryRecordMapper<Record> {

    protected final FieldValueMapper fieldValueMapper;

    public ValuesMapper(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        this.fieldValueMapper = fieldValueMapper;
    }

    @Override
    public Record map(Record record) {
        if (record.isEmpty()) {
            return record;
        }
        return new StandardRecord(record.getCategory(), record.getRecordId(),
                Fields.collectValues(record, fieldValueMapper));
    }

}
