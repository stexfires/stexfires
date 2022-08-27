package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

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

    private final IntFunction<Optional<FieldValueMapper>> valueMappers;
    private final FieldValueMapper orElseFieldValueMapper;

    public IndexedFieldValueMapper(IntFunction<Optional<FieldValueMapper>> valueMappers,
                                   FieldValueMapper orElseFieldValueMapper) {
        Objects.requireNonNull(valueMappers);
        Objects.requireNonNull(orElseFieldValueMapper);
        this.valueMappers = valueMappers;
        this.orElseFieldValueMapper = orElseFieldValueMapper;
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
    public final String mapToValue(@NotNull Field field) {
        return valueMappers.apply(field.index())
                           .orElse(orElseFieldValueMapper)
                           .mapToValue(field);
    }

}
