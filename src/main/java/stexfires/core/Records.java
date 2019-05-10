package stexfires.core;

import org.jetbrains.annotations.Nullable;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.UncheckedConsumerException;
import stexfires.core.filter.RecordFilter;
import stexfires.core.logger.RecordLogger;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.message.RecordMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;
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
 * for operating on {@link Record}s.
 *
 * @author Mathias Kalb
 * @see stexfires.core.Record
 * @see stexfires.core.RecordStreams
 * @since 0.1
 */
public final class Records {

    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final EmptyRecord EMPTY_RECORD = new EmptyRecord();

    private Records() {
    }

    public static EmptyRecord empty() {
        return EMPTY_RECORD;
    }

    public static ValueRecord ofValue(@Nullable String value) {
        return new SingleRecord(value);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static Record ofValues(String... values) {
        Objects.requireNonNull(values);
        return new StandardRecord(values);
    }

    public static Record ofValues(Collection<String> values) {
        Objects.requireNonNull(values);
        return new StandardRecord(values);
    }

    public static <T extends Record> List<T> list(T record) {
        Objects.requireNonNull(record);
        return Collections.singletonList(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends Record> List<T> list(T... records) {
        Objects.requireNonNull(records);
        return Arrays.stream(records).collect(Collectors.toList());
    }

    public static <T extends Record> Stream<T> stream(T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <T extends Record> Stream<T> stream(T... records) {
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

    public static <P extends Record, T extends P> RecordConsumer<P> consume(T record,
                                                                            RecordConsumer<P> recordConsumer) throws UncheckedConsumerException {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordConsumer);
        recordConsumer.consume(record);
        return recordConsumer;
    }

    public static <P extends Record, T extends P> boolean isValid(T record,
                                                                  RecordFilter<P> recordFilter) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordFilter);
        return recordFilter.isValid(record);
    }

    public static <P extends Record, T extends P> RecordLogger<P> log(T record,
                                                                      RecordLogger<P> recordLogger) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordLogger);
        recordLogger.log(record);
        return recordLogger;
    }

    public static <R extends Record, T extends Record> R map(T record,
                                                             RecordMapper<T, R> recordMapper) {
        Objects.requireNonNull(record);
        Objects.requireNonNull(recordMapper);
        return recordMapper.map(record);
    }

    public static <P extends Record, T extends P> String createMessage(T record,
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

        @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
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

        public synchronized Record build() {
            if (valueList == null) {
                throw new IllegalStateException("build() already called");
            }
            Record record;
            switch (valueList.size()) {
                case SingleRecord.FIELD_SIZE:
                    record = new SingleRecord(category, recordId, valueList.get(0));
                    break;
                case PairRecord.FIELD_SIZE:
                    record = new PairRecord(category, recordId, valueList.get(0), valueList.get(1));
                    break;
                default:
                    record = new StandardRecord(category, recordId, valueList);
                    break;
            }
            category = null;
            recordId = null;
            valueList = null;
            return record;
        }

    }

}
