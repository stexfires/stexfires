package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.logger.RecordLogger;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.RecordMessage;
import stexfires.util.function.Suppliers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods
 * for operating on {@link TextRecord}s.
 *
 * @see TextRecord
 * @see TextRecordStreams
 * @since 0.1
 */
public final class TextRecords {

    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;

    private static final EmptyRecord EMPTY_RECORD = new EmptyRecord();

    private TextRecords() {
    }

    public static EmptyRecord empty() {
        return EMPTY_RECORD;
    }

    public static ValueRecord ofText(@Nullable String text) {
        return new ValueFieldRecord(text);
    }

    public static TextRecord ofTexts(@NotNull Collection<String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTexts(@NotNull Stream<String> texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static TextRecord ofTexts(String... texts) {
        Objects.requireNonNull(texts);
        return new ManyFieldsRecord(texts);
    }

    public static @NotNull TextRecord ofNullable(@Nullable String category, @Nullable Long recordId, @Nullable List<String> texts) {
        if (category == null && recordId == null) {
            if (texts == null || texts.isEmpty()) {
                return empty();
            } else if (texts.size() == 1) {
                return new ValueFieldRecord(texts.getFirst());
            } else if (texts.size() == 2) {
                return new TwoFieldsRecord(texts.getFirst(), texts.getLast());
            } else {
                return new ManyFieldsRecord(texts);
            }
        } else {
            if (texts == null || texts.isEmpty()) {
                return new ManyFieldsRecord(category, recordId);
            } else if (texts.size() == 1) {
                return new ValueFieldRecord(category, recordId, texts.getFirst());
            } else if (texts.size() == 2) {
                return new TwoFieldsRecord(category, recordId, texts.getFirst(), texts.getLast());
            } else {
                return new ManyFieldsRecord(category, recordId, texts);
            }
        }
    }

    public static <T extends TextRecord> List<T> list(@NotNull T record) {
        Objects.requireNonNull(record);
        return Collections.singletonList(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> List<T> list(T... records) {
        Objects.requireNonNull(records);
        return Arrays.stream(records).collect(Collectors.toList());
    }

    public static <T extends TextRecord> Stream<T> stream(@NotNull T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> Stream<T> stream(T... records) {
        Objects.requireNonNull(records);
        return Stream.of(records);
    }

    public static Supplier<Long> recordIdSequence() {
        return Suppliers.sequenceAsLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> recordIdSequence(long initialValue) {
        return Suppliers.sequenceAsLong(initialValue);
    }

    public static LongSupplier recordIdPrimitiveSequence() {
        return Suppliers.sequenceAsPrimitiveLong(DEFAULT_INITIAL_RECORD_ID);
    }

    public static LongSupplier recordIdPrimitiveSequence(long initialValue) {
        return Suppliers.sequenceAsPrimitiveLong(initialValue);
    }

    public static <P extends TextRecord, T extends P> RecordConsumer<P> consume(@NotNull T record,
                                                                                @NotNull RecordConsumer<P> recordConsumer)
            throws UncheckedConsumerException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordConsumer);
        recordConsumer.consume(record);
        return recordConsumer;
    }

    public static <P extends TextRecord, T extends P> boolean isValid(@NotNull T record,
                                                                      @NotNull RecordFilter<P> recordFilter) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordFilter);
        return recordFilter.isValid(record);
    }

    public static <P extends TextRecord, T extends P> RecordLogger<P> log(@NotNull T record,
                                                                          @NotNull RecordLogger<P> recordLogger) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordLogger);
        recordLogger.log(record);
        return recordLogger;
    }

    public static <R extends TextRecord, T extends TextRecord> R map(@NotNull T record,
                                                                     @NotNull RecordMapper<T, R> recordMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordMapper);
        return recordMapper.map(record);
    }

    public static <P extends TextRecord, T extends P> String createMessage(@NotNull T record,
                                                                           @NotNull RecordMessage<P> recordMessage) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordMessage);
        return recordMessage.createMessage(record);
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("ParameterHidesMemberVariable")
    public static final class Builder implements Consumer<String> {
        private String category;
        private Long recordId;
        private List<String> textList;

        private Builder() {
            textList = new ArrayList<>();
        }

        public synchronized Builder category(@Nullable String category) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.category = category;
            return this;
        }

        public synchronized Builder recordId(@Nullable Long recordId) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.recordId = recordId;
            return this;
        }

        @Override
        public synchronized void accept(@Nullable String text) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            textList.add(text);
        }

        public synchronized Builder add(@Nullable String text) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            textList.add(text);
            return this;
        }

        public synchronized Builder addAll(@NotNull Collection<String> texts) {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(texts);
            textList.addAll(texts);
            return this;
        }

        public synchronized TextRecord build() {
            if (textList == null) {
                throw new IllegalStateException("build() already called");
            }
            TextRecord record =
                    switch (textList.size()) {
                        case ValueFieldRecord.FIELD_SIZE -> new ValueFieldRecord(category, recordId, textList.get(0));
                        case TwoFieldsRecord.FIELD_SIZE ->
                                new TwoFieldsRecord(category, recordId, textList.get(0), textList.get(1));
                        default -> new ManyFieldsRecord(category, recordId, textList);
                    };
            category = null;
            recordId = null;
            textList = null;
            return record;
        }

    }

}
