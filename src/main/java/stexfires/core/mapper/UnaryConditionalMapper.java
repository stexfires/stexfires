package stexfires.core.mapper;

import stexfires.core.Record;
import stexfires.core.filter.RecordFilter;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class UnaryConditionalMapper<T extends Record> extends ConditionalMapper<T, T> implements UnaryRecordMapper<T> {

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