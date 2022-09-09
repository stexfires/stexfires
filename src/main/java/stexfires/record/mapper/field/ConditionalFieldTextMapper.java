package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalFieldTextMapper implements FieldTextMapper {

    private final Predicate<Field> condition;
    private final FieldTextMapper trueFieldTextMapper;
    private final FieldTextMapper falseFieldTextMapper;

    public ConditionalFieldTextMapper(Predicate<Field> condition,
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
    public final String mapToText(@NotNull Field field) {
        return condition.test(field) ? trueFieldTextMapper.mapToText(field) : falseFieldTextMapper.mapToText(field);
    }

}
