package stexfires.record.producer;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueRecord;
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
public class KeyValueRecordProducer implements RecordProducer<KeyValueRecord> {

    private final List<KeyValueRecord> records;

    public <K, V> KeyValueRecordProducer(Map<K, V> keyValueMap) {
        this(null, TextRecords.recordIdSequence(), keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category, Map<K, V> keyValueMap) {
        this(category, TextRecords.recordIdSequence(), keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Map<K, V> keyValueMap) {
        this(category, recordIdSupplier, keyValueMap, Strings::asString, Strings::asString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Map<K, V> keyValueMap,
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