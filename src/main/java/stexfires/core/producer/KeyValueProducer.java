package stexfires.core.producer;

import org.jetbrains.annotations.Nullable;
import stexfires.core.TextRecords;
import stexfires.core.impl.KeyValueRecord;
import stexfires.util.Strings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyValueProducer implements RecordProducer<KeyValueRecord> {

    private final List<KeyValueRecord> records;

    public <K, V> KeyValueProducer(Map<K, V> keyValueMap) {
        this(null, TextRecords.recordIdSequence(), keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueProducer(@Nullable String category, Map<K, V> keyValueMap) {
        this(category, TextRecords.recordIdSequence(), keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Map<K, V> keyValueMap) {
        this(category, recordIdSupplier, keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Map<K, V> keyValueMap,
                                   Function<? super K, String> keyToStringFunction,
                                   Function<? super V, String> valueToStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(keyValueMap);
        Objects.requireNonNull(keyToStringFunction);
        Objects.requireNonNull(valueToStringFunction);
        records = keyValueMap.entrySet()
                             .stream()
                             .map(entry -> new KeyValueRecord(category, recordIdSupplier.get(),
                                     keyToStringFunction.apply(entry.getKey()),
                                     valueToStringFunction.apply(entry.getValue())))
                             .collect(Collectors.toList());
    }

    @Override
    public final Stream<KeyValueRecord> produceStream() {
        return records.stream();
    }

}
