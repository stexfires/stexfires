package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @since 0.1
 */
public class ConditionalFieldTextMapper implements FieldTextMapper {

    private final Predicate<TextField> condition;
    private final FieldTextMapper trueFieldTextMapper;
    private final FieldTextMapper falseFieldTextMapper;

    public ConditionalFieldTextMapper(Predicate<TextField> condition,
                                      FieldTextMapper trueFieldTextMapper,
                                      FieldTextMapper falseFieldTextMapper) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueFieldTextMapper);
        Objects.requireNonNull(falseFieldTextMapper);
        this.condition = condition;
        this.trueFieldTextMapper = trueFieldTextMapper;
        this.falseFieldTextMapper = falseFieldTextMapper;
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return condition.test(field) ? trueFieldTextMapper.mapToText(field) : falseFieldTextMapper.mapToText(field);
    }

}
