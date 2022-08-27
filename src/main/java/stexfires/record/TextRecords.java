package stexfires.record;

import org.jetbrains.annotations.Nullable;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.UncheckedConsumerException;
import stexfires.record.filter.RecordFilter;
import stexfires.record.logger.RecordLogger;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.message.RecordMessage;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.PairRecord;
import stexfires.record.impl.SingleRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.util.supplier.SequenceLongSupplier;
import stexfires.util.supplier.SequencePrimitiveLongSupplier;

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
 * @author Mathias Kalb
 * @see TextRecord
 * @see TextRecordStreams
 * @since 0.1
 */
public final class TextRecords {

    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final EmptyRecord EMPTY_RECORD = new EmptyRecord();

    private TextRecords() {
    }

    public static EmptyRecord empty() {
        return EMPTY_RECORD;
    }

    public static ValueRecord ofValue(@Nullable String value) {
        return new SingleRecord(value);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static TextRecord ofValues(String... values) {
        Objects.requireNonNull(values);
        return new StandardRecord(values);
    }

    public static TextRecord ofValues(Collection<String> values) {
        Objects.requireNonNull(values);
        return new StandardRecord(values);
    }

    public static <T extends TextRecord> List<T> list(T record) {
        Objects.requireNonNull(record);
        return Collections.singletonList(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends TextRecord> List<T> list(T... records) {
        Objects.requireNonNull(records);
        return Arrays.stream(records).collect(Collectors.toList());
    }

    public static <T extends TextRecord> Stream<T> stream(T record) {
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
        return new SequenceLongSupplier(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> recordIdSequence(long initialValue) {
        return new SequenceLongSupplier(initialValue);
    }

    public static LongSupplier recordIdPrimitiveSequence() {
        return new SequencePrimitiveLongSupplier(DEFAULT_INITIAL_RECORD_ID);
    }

    public static LongSupplier recordIdPrimitiveSequence(long initialValue) {
        return new SequencePrimitiveLongSupplier(initialValue);
    }

    public static <P extends TextRecord, T extends P> RecordConsumer<P> consume(T record,
                                                                                RecordConsumer<P> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordConsumer);
        recordConsumer.consume(record);
        return recordConsumer;
    }

    public static <P extends TextRecord, T extends P> boolean isValid(T record,
                                                                      RecordFilter<P> recordFilter) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordFilter);
        return recordFilter.isValid(record);
    }

    public static <P extends TextRecord, T extends P> RecordLogger<P> log(T record,
                                                                          RecordLogger<P> recordLogger) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordLogger);
        recordLogger.log(record);
        return recordLogger;
    }

    public static <R extends TextRecord, T extends TextRecord> R map(T record,
                                                                     RecordMapper<T, R> recordMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordMapper);
        return recordMapper.map(record);
    }

    public static <P extends TextRecord, T extends P> String createMessage(T record,
                                                                           RecordMessage<P> recordMessage) {
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
        private List<String> valueList;

        private Builder() {
            valueList = new ArrayList<>();
        }

        public synchronized Builder category(@Nullable String category) {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.category = category;
            return this;
        }

        public synchronized Builder recordId(@Nullable Long recordId) {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            this.recordId = recordId;
            return this;
        }

        @Override
        public synchronized void accept(@Nullable String value) {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            valueList.add(value);
        }

        public synchronized Builder add(@Nullable String value) {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            valueList.add(value);
            return this;
        }

        public synchronized Builder addAll(Collection<String> values) {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(values);
            valueList.addAll(values);
            return this;
        }

        public synchronized TextRecord build() {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            TextRecord record = switch (valueList.size()) {
                case SingleRecord.FIELD_SIZE -> new SingleRecord(category, recordId, valueList.get(0));
                case PairRecord.FIELD_SIZE -> new PairRecord(category, recordId, valueList.get(0), valueList.get(1));
                default -> new StandardRecord(category, recordId, valueList);
            };
            category = null;
            recordId = null;
            valueList = null;
            return record;
        }

    }

}
