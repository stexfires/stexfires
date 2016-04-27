package org.textfiledatatools.core.mapper;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.filter.RecordFilter;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UnaryConditionalMapper<T extends Record> extends ConditionalMapper<T, T> {

    public UnaryConditionalMapper(RecordFilter<? super T> condition,
                                  UnaryRecordMapper<T> trueMapper) {
        this(condition, trueMapper, new IdentityMapper<>());
    }

    public UnaryConditionalMapper(RecordFilter<? super T> condition,
                                  UnaryRecordMapper<T> trueMapper,
                                  UnaryRecordMapper<T> falseMapper) {
        super(condition, trueMapper, falseMapper);
    }

}
