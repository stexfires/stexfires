package stexfires.core;

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
 * This class consists of {@code static} utility methods for operating on records.
 *
 * @author Mathias Kalb
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

    public static ValueRecord ofValue(String value) {
        return new SingleRecord(value);
    }

    public static Record ofValues(String... values) {
        return new StandardRecord(values);
    }

    public static Record ofValues(Collection<String> values) {
        return new StandardRecord(values);
    }

    public static <T extends Record> List<T> list(T record) {
        Objects.requireNonNull(record);
        return Collections.singletonList(record);
    }

    @SafeVarargs
    public static <T extends Record> List<T> list(T... records) {
        Objects.requireNonNull(records);
        return Arrays.stream(records).collect(Collectors.toList());
    }

    public static <T extends Record> Stream<T> stream(T record) {
        Objects.requireNonNull(record);
        return Stream.of(record);
    }

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

    public static <T extends Record> void consume(T record, RecordConsumer<? super T> recordConsumer) throws UncheckedConsumerException {
        recordConsumer.consume(record);
    }

    public static <T extends Record> boolean isValid(T record, RecordFilter<? super T> recordFilter) {
        return recordFilter.isValid(record);
    }

    public static <T extends Record> void log(T record, RecordLogger<? super T> recordLogger) {
        recordLogger.log(record);
    }

    public static <T extends Record, R extends Record> R map(T record, RecordMapper<? super T, ? extends R> recordMapper) {
        return recordMapper.map(record);
    }

    public static <T extends Record> String createMessage(T record, RecordMessage<? super T> recordMessage) {
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

        public synchronized Builder category(String category) {
            if (valueList == null) {
                throw new IllegalStateException();
            }
            this.category = category;
            return this;
        }

        public synchronized Builder recordId(Long recordId) {
            if (valueList == null) {
                throw new IllegalStateException();
            }
            this.recordId = recordId;
            return this;
        }

        @Override
        public synchronized void accept(String value) {
            if (valueList == null) {
                throw new IllegalStateException();
            }
            valueList.add(value);
        }

        public synchronized Builder add(String value) {
            if (valueList == null) {
                throw new IllegalStateException();
            }
            valueList.add(value);
            return this;
        }

        public synchronized Builder addAll(Collection<String> values) {
            if (values == null) {
                throw new IllegalStateException();
            }
            values.addAll(valueList);
            return this;
        }

        public synchronized Record build() {
            if (valueList == null) {
                throw new IllegalStateException();
            }
            Record record;
            if (valueList.size() == 1) {
                record = new SingleRecord(category, recordId, valueList.get(0));
            } else if (valueList.size() == 2) {
                record = new PairRecord(category, recordId, valueList.get(0), valueList.get(1));
            } else {
                record = new StandardRecord(category, recordId, valueList);
            }
            category = null;
            recordId = null;
            valueList = null;
            return record;
        }

    }

}
