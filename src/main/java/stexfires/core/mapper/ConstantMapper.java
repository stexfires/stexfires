package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.TextRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantMapper<T extends TextRecord, R extends TextRecord> implements RecordMapper<T, R> {

    private final R constantRecord;

    public ConstantMapper(R constantRecord) {
        Objects.requireNonNull(constantRecord);
        this.constantRecord = constantRecord;
    }

    @Override
    public final @NotNull R map(@NotNull T record) {
        return constantRecord;
    }

}
