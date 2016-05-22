package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.record.KeyValueRecord;
import org.textfiledatatools.util.Strings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(keyValueMap);
        records = keyValueMap.entrySet()
                .stream()
                .map(entry -> new KeyValueRecord(category, recordIdSupplier.get(), entry.getKey().toString(), Strings.asString(entry.getValue())))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<KeyValueRecord> produceStream() {
        return records.stream();
    }

}
