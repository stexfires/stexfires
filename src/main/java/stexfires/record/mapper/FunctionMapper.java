package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @see AddTextMapper
 * @see stexfires.record.mapper.CategoryMapper
 * @see stexfires.record.mapper.RecordIdMapper
 * @see TextsMapper
 * @since 0.1
 */
public class FunctionMapper<T extends TextRecord> implements RecordMapper<T, TextRecord> {

    private final Function<? super T, String> categoryFunction;
    private final Function<? super T, Long> recordIdFunction;
    private final Function<? super T, Collection<String>> textsFunction;

    public FunctionMapper(Function<? super T, String> categoryFunction,
                          Function<? super T, Long> recordIdFunction,
                          Function<? super T, Collection<String>> textsFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        Objects.requireNonNull(textsFunction);
        this.categoryFunction = categoryFunction;
        this.recordIdFunction = recordIdFunction;
        this.textsFunction = textsFunction;
    }

    @SuppressWarnings("CallToSimpleGetterFromWithinClass")
    public static <T extends TextRecord> FunctionMapper<T> functionMappers(FunctionMapper<? super T> categoryMapper,
                                                                           FunctionMapper<? super T> recordIdMapper,
                                                                           FunctionMapper<? super T> textsMapper) {
        return new FunctionMapper<>(
                categoryMapper.getCategoryFunction(),
                recordIdMapper.getRecordIdFunction(),
                textsMapper.getTextsFunction());
    }

    protected final Function<? super T, String> getCategoryFunction() {
        return categoryFunction;
    }

    protected final Function<? super T, Long> getRecordIdFunction() {
        return recordIdFunction;
    }

    protected final Function<? super T, Collection<String>> getTextsFunction() {
        return textsFunction;
    }

    @Override
    public final @NotNull TextRecord map(@NotNull T record) {
        return new ManyFieldsRecord(
                categoryFunction.apply(record),
                recordIdFunction.apply(record),
                textsFunction.apply(record));
    }

}
