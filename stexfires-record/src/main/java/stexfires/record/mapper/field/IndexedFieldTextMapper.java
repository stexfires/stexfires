package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class IndexedFieldTextMapper implements FieldTextMapper {

    private final IntFunction<Optional<FieldTextMapper>> fieldTextMapperFunction;
    private final FieldTextMapper orElseFieldTextMapper;

    public IndexedFieldTextMapper(IntFunction<Optional<FieldTextMapper>> fieldTextMapperFunction,
                                  FieldTextMapper orElseFieldTextMapper) {
        Objects.requireNonNull(fieldTextMapperFunction);
        Objects.requireNonNull(orElseFieldTextMapper);
        this.fieldTextMapperFunction = fieldTextMapperFunction;
        this.orElseFieldTextMapper = orElseFieldTextMapper;
    }

    public static IndexedFieldTextMapper byArray(FieldTextMapper... fieldTextMappers) {
        Objects.requireNonNull(fieldTextMappers);
        return new IndexedFieldTextMapper(
                i -> (i < fieldTextMappers.length) ? Optional.of(fieldTextMappers[i]) : Optional.empty(),
                new IdentityFieldTextMapper());
    }

    public static IndexedFieldTextMapper byList(List<FieldTextMapper> fieldTextMappers) {
        Objects.requireNonNull(fieldTextMappers);
        return new IndexedFieldTextMapper(
                i -> (i < fieldTextMappers.size()) ? Optional.of(fieldTextMappers.get(i)) : Optional.empty(),
                new IdentityFieldTextMapper());
    }

    public static IndexedFieldTextMapper byMap(Map<Integer, FieldTextMapper> fieldTextMappers) {
        Objects.requireNonNull(fieldTextMappers);
        return new IndexedFieldTextMapper(
                i -> Optional.ofNullable(fieldTextMappers.get(i)),
                new IdentityFieldTextMapper());
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return fieldTextMapperFunction.apply(field.index())
                                      .orElse(orElseFieldTextMapper)
                                      .mapToText(field);
    }

}
