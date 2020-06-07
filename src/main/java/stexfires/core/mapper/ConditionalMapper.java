package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.TextRecord;
import stexfires.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalMapper<T extends TextRecord, R extends TextRecord> implements RecordMapper<T, R> {

    private final RecordFilter<? super T> condition;
    private final RecordMapper<? super T, ? extends R> trueMapper;
    private final RecordMapper<? super T, ? extends R> falseMapper;

    public ConditionalMapper(RecordFilter<? super T> condition,
                             RecordMapper<? super T, ? extends R> trueMapper,
                             RecordMapper<? super T, ? extends R> falseMapper) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMapper);
        Objects.requireNonNull(falseMapper);
        this.condition = condition;
        this.trueMapper = trueMapper;
        this.falseMapper = falseMapper;
    }

    @Override
    public final @NotNull R map(@NotNull T record) {
        return condition.isValid(record) ? trueMapper.map(record) : falseMapper.map(record);
    }

}
