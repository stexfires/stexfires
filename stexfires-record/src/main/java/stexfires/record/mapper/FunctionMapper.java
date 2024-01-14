package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see TextsMapper
 * @since 0.1
 */
public class FunctionMapper<T extends TextRecord> implements RecordMapper<T, TextRecord> {

    private final Function<? super T, @Nullable String> categoryFunction;
    private final Function<? super T, @Nullable Long> recordIdFunction;
    private final Function<? super T, Collection<@Nullable String>> textsFunction;

    public FunctionMapper(Function<? super T, @Nullable String> categoryFunction,
                          Function<? super T, @Nullable Long> recordIdFunction,
                          Function<? super T, Collection<@Nullable String>> textsFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        Objects.requireNonNull(textsFunction);
        this.categoryFunction = categoryFunction;
        this.recordIdFunction = recordIdFunction;
        this.textsFunction = textsFunction;
    }

    public static <T extends TextRecord> FunctionMapper<T> functionMappers(FunctionMapper<? super T> categoryMapper,
                                                                           FunctionMapper<? super T> recordIdMapper,
                                                                           FunctionMapper<? super T> textsMapper) {
        Objects.requireNonNull(categoryMapper);
        Objects.requireNonNull(recordIdMapper);
        Objects.requireNonNull(textsMapper);
        return new FunctionMapper<>(
                categoryMapper.getCategoryFunction(),
                recordIdMapper.getRecordIdFunction(),
                textsMapper.getTextsFunction());
    }

    protected final Function<? super T, @Nullable String> getCategoryFunction() {
        return categoryFunction;
    }

    protected final Function<? super T, @Nullable Long> getRecordIdFunction() {
        return recordIdFunction;
    }

    protected final Function<? super T, Collection<@Nullable String>> getTextsFunction() {
        return textsFunction;
    }

    @Override
    public final TextRecord map(T record) {
        return new ManyFieldsRecord(
                categoryFunction.apply(record),
                recordIdFunction.apply(record),
                textsFunction.apply(record));
    }

}
