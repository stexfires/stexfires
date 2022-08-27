package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityMapper<T extends TextRecord> implements RecordMapper<T, T> {

    public IdentityMapper() {
    }

    @Override
    public final @NotNull T map(@NotNull T record) {
        return record;
    }

}
