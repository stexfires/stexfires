package org.textfiledatatools.core.mapper.to;

import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.record.EmptyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToEmptyMapper extends ToConstantMapper<EmptyRecord> {

    public ToEmptyMapper() {
        super(Records.empty());
    }

}
