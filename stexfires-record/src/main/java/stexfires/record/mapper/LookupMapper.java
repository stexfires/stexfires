package stexfires.record.mapper;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.NotNullRecordMessage;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class LookupMapper<T extends TextRecord, R extends TextRecord, K> implements RecordMapper<T, R> {

    private final Function<? super T, K> keyFunction;
    private final Function<K, @Nullable RecordMapper<? super T, ? extends R>> mapperFunction;
    private final RecordMapper<? super T, ? extends R> defaultMapper;

    public LookupMapper(Function<? super T, K> keyFunction,
                        Function<K, @Nullable RecordMapper<? super T, ? extends R>> mapperFunction,
                        RecordMapper<? super T, ? extends R> defaultMapper) {
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(mapperFunction);
        Objects.requireNonNull(defaultMapper);
        this.keyFunction = keyFunction;
        this.mapperFunction = mapperFunction;
        this.defaultMapper = defaultMapper;
    }

    public static <T extends TextRecord> LookupMapper<T, TextRecord, String> messageMap(NotNullRecordMessage<? super T> recordMessage,
                                                                                        Map<String, @Nullable RecordMapper<? super T, ? extends TextRecord>> recordMapperMap) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(recordMapperMap);
        Function<String, @Nullable RecordMapper<? super T, ? extends TextRecord>> mapperFunction = recordMapperMap::get;
        return new LookupMapper<>(
                recordMessage.asFunction(),
                mapperFunction,
                new IdentityMapper<>());
    }

    @Override
    public final R map(T record) {
        RecordMapper<? super T, ? extends R> recordMapper =
                mapperFunction.apply(Objects.requireNonNull(keyFunction.apply(record)));
        return (recordMapper == null) ? defaultMapper.map(record) : recordMapper.map(record);
    }

}
