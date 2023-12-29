package stexfires.record.producer;

import org.jspecify.annotations.Nullable;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.util.Strings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class KeyValueRecordProducer implements RecordProducer<KeyValueRecord> {

    private final List<KeyValueRecord> records;

    public <K, V> KeyValueRecordProducer(Map<K, V> keyValueMap) {
        this(null, TextRecords.recordIdSequence(), keyValueMap, Strings::toNullableString, Strings::toNullableString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category,
                                         Map<K, V> keyValueMap) {
        this(category, TextRecords.recordIdSequence(), keyValueMap, Strings::toNullableString, Strings::toNullableString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category,
                                         Supplier<@Nullable Long> recordIdSupplier,
                                         Map<K, V> keyValueMap) {
        this(category, recordIdSupplier, keyValueMap, Strings::toNullableString, Strings::toNullableString);
    }

    public <K, V> KeyValueRecordProducer(@Nullable String category,
                                         Supplier<@Nullable Long> recordIdSupplier,
                                         Map<K, V> keyValueMap,
                                         Function<? super K, String> keyToStringFunction,
                                         Function<? super V, @Nullable String> valueToStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(keyValueMap);
        Objects.requireNonNull(keyToStringFunction);
        Objects.requireNonNull(valueToStringFunction);
        records = keyValueMap.entrySet()
                             .stream()
                             .map(entry ->
                                     new KeyValueFieldsRecord(category,
                                             recordIdSupplier.get(),
                                             keyToStringFunction.apply(entry.getKey()),
                                             valueToStringFunction.apply(entry.getValue())))
                             .collect(Collectors.toList());
    }

    @Override
    public Stream<KeyValueRecord> produceStream() {
        return records.stream();
    }

}
