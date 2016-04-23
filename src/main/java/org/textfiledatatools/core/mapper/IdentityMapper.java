package org.textfiledatatools.core.mapper;

import org.textfiledatatools.core.Record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityMapper<T extends Record> implements UnaryRecordMapper<T> {

    @Override
    public T map(T record) {
        return record;
    }

}
