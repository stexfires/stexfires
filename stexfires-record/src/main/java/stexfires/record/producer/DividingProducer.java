package stexfires.record.producer;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.ManyFieldsRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class DividingProducer implements RecordProducer<TextRecord> {

    private final List<TextRecord> records;

    public DividingProducer(int recordSize, String... texts) {
        this(null, TextRecords.recordIdSequence(), recordSize, texts);
    }

    public DividingProducer(@Nullable String category,
                            Supplier<@Nullable Long> recordIdSupplier,
                            int recordSize,
                            String... texts) {
        Objects.requireNonNull(recordIdSupplier);
        if (recordSize <= 0) {
            throw new IllegalArgumentException("Illegal recordSize! recordSize=" + recordSize);
        }
        Objects.requireNonNull(texts);
        int capacity = (texts.length + recordSize - 1) / recordSize;
        records = new ArrayList<>(capacity);

        for (int recordIndex = 0; recordIndex < capacity; recordIndex++) {
            List<@Nullable String> newRecordTexts = new ArrayList<>(recordSize);
            for (int newTextIndex = 0; newTextIndex < recordSize; newTextIndex++) {
                int originalTextIndex = recordIndex * recordSize + newTextIndex;
                if (originalTextIndex < texts.length) {
                    newRecordTexts.add(texts[originalTextIndex]);
                } else {
                    newRecordTexts.add(null);
                }
            }

            records.add(new ManyFieldsRecord(category, recordIdSupplier.get(), newRecordTexts));
        }
    }

    @Override
    public Stream<TextRecord> produceStream() {
        return records.stream();
    }

}
