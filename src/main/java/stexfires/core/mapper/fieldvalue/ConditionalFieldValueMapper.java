package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalFieldValueMapper implements FieldValueMapper {

    protected final Predicate<Field> condition;
    protected final FieldValueMapper trueFieldValueMapper;
    protected final FieldValueMapper falseFieldValueMapper;

    public ConditionalFieldValueMapper(Predicate<Field> condition,
                                       FieldValueMapper trueFieldValueMapper,
                                       FieldValueMapper falseFieldValueMapper) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueFieldValueMapper);
        Objects.requireNonNull(falseFieldValueMapper);
        this.condition = condition;
        this.trueFieldValueMapper = trueFieldValueMapper;
        this.falseFieldValueMapper = falseFieldValueMapper;
    }

    @Override
    public final String mapToValue(Field field) {
        return condition.test(field) ? trueFieldValueMapper.mapToValue(field) : falseFieldValueMapper.mapToValue(field);
    }

}
