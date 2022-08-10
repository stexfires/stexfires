package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.record.PairRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToPairMapper<T extends TextRecord> implements RecordMapper<T, PairRecord> {

    private final int firstIndex;
    private final int secondIndex;

    public ToPairMapper() {
        this(Fields.FIRST_FIELD_INDEX, Fields.FIRST_FIELD_INDEX + 1);
    }

    public ToPairMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public final @NotNull PairRecord map(@NotNull T record) {
        return new PairRecord(record.category(), record.getRecordId(),
                record.getValueAt(firstIndex), record.getValueAt(secondIndex));
    }

}
