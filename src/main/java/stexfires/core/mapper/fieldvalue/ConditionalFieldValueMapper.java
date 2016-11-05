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
    protected final FieldValueMapper trueMapper;
    protected final FieldValueMapper falseMapper;

    public ConditionalFieldValueMapper(Predicate<Field> condition,
                                       FieldValueMapper trueMapper,
                                       FieldValueMapper falseMapper) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMapper);
        Objects.requireNonNull(falseMapper);
        this.condition = condition;
        this.trueMapper = trueMapper;
        this.falseMapper = falseMapper;
    }

    @Override
    public String mapToValue(Field field) {
        return condition.test(field) ? trueMapper.mapToValue(field) : falseMapper.mapToValue(field);
    }

}
