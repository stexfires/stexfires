package stexfires.core.mapper;

import stexfires.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityMapper<T extends Record> implements RecordMapper<T, T> {

    @Override
    public T map(T record) {
        return record;
    }

}
