package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.message.RecordMessage;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LookupMapper<T extends TextRecord, R extends TextRecord, K> implements RecordMapper<T, R> {

    private final Function<? super T, K> keyFunction;
    private final Function<K, RecordMapper<? super T, ? extends R>> mapperFunction;
    private final RecordMapper<? super T, ? extends R> defaultMapper;

    public LookupMapper(Function<? super T, K> keyFunction,
                        Function<K, RecordMapper<? super T, ? extends R>> mapperFunction,
                        RecordMapper<? super T, ? extends R> defaultMapper) {
        Objects.requireNonNull(keyFunction);
        Objects.requireNonNull(mapperFunction);
        Objects.requireNonNull(defaultMapper);
        this.keyFunction = keyFunction;
        this.mapperFunction = mapperFunction;
        this.defaultMapper = defaultMapper;
    }

    public static <T extends TextRecord> LookupMapper<T, TextRecord, String> messageMap(RecordMessage<? super T> recordMessage,
                                                                                        Map<String, RecordMapper<? super T, TextRecord>> recordMapperMap) {
        Objects.requireNonNull(recordMessage);
        Objects.requireNonNull(recordMapperMap);
        return new LookupMapper<>(recordMessage.asFunction(), recordMapperMap::get, new IdentityMapper<>());
    }

    @Override
    public final @NotNull R map(@NotNull T record) {
        RecordMapper<? super T, ? extends R> recordMapper = mapperFunction.apply(keyFunction.apply(record));
        return (recordMapper == null) ? defaultMapper.map(record) : recordMapper.map(record);
    }

}
