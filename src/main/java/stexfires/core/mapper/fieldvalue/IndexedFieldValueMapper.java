package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IndexedFieldValueMapper implements FieldValueMapper {

    protected final IntFunction<Optional<FieldValueMapper>> valueMappers;
    protected final FieldValueMapper orElseValueMapper;

    public IndexedFieldValueMapper(IntFunction<Optional<FieldValueMapper>> valueMappers,
                                   FieldValueMapper orElseValueMapper) {
        Objects.requireNonNull(valueMappers);
        Objects.requireNonNull(orElseValueMapper);
        this.valueMappers = valueMappers;
        this.orElseValueMapper = orElseValueMapper;
    }

    public static IndexedFieldValueMapper byArray(FieldValueMapper... fieldValueMappers) {
        Objects.requireNonNull(fieldValueMappers);
        return new IndexedFieldValueMapper(
                i -> (i < fieldValueMappers.length) ? Optional.of(fieldValueMappers[i]) : Optional.empty(),
                new IdentityFieldValueMapper());
    }

    public static IndexedFieldValueMapper byList(List<FieldValueMapper> fieldValueMappers) {
        Objects.requireNonNull(fieldValueMappers);
        return new IndexedFieldValueMapper(
                i -> (i < fieldValueMappers.size()) ? Optional.of(fieldValueMappers.get(i)) : Optional.empty(),
                new IdentityFieldValueMapper());
    }

    public static IndexedFieldValueMapper byMap(Map<Integer, FieldValueMapper> fieldValueMappers) {
        Objects.requireNonNull(fieldValueMappers);
        return new IndexedFieldValueMapper(
                i -> Optional.ofNullable(fieldValueMappers.get(i)),
                new IdentityFieldValueMapper());
    }

    @Override
    public String mapToValue(Field field) {
        return valueMappers.apply(field.getIndex())
                           .orElse(orElseValueMapper)
                           .mapToValue(field);
    }

}
