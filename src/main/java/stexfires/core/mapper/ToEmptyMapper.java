package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.TextRecord;
import stexfires.core.TextRecords;
import stexfires.core.record.EmptyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToEmptyMapper<T extends TextRecord> implements RecordMapper<T, EmptyRecord> {

    public ToEmptyMapper() {
    }

    @Override
    public final @NotNull EmptyRecord map(@NotNull T record) {
        return TextRecords.empty();
    }

}
