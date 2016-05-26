package stexfires.core.producer;

import stexfires.core.Records;
import stexfires.core.record.KeyValueRecord;
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

    protected final List<KeyValueRecord> records;

    public KeyValueProducer(Map<?, ?> keyValueMap) {
        this(null, Records.recordIdSequence(), keyValueMap);
    }

    public KeyValueProducer(String category, Map<?, ?> keyValueMap) {
        this(category, Records.recordIdSequence(), keyValueMap);
    }

    public KeyValueProducer(String category, Supplier<Long> recordIdSupplier, Map<?, ?> keyValueMap) {
        this(category, recordIdSupplier, keyValueMap, Strings::asString);
    }

    public KeyValueProducer(String category, Supplier<Long> recordIdSupplier, Map<?, ?> keyValueMap, Function<Object, String> toStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(keyValueMap);
        records = keyValueMap.entrySet()
                .stream()
                .map(entry -> new KeyValueRecord(category, recordIdSupplier.get(), toStringFunction.apply(entry.getKey()), toStringFunction.apply(entry.getValue())))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<KeyValueRecord> produceStream() {
        return records.stream();
    }

}
