package stexfires.core.modifier;

import stexfires.core.Record;
import stexfires.core.message.CompareMessageBuilder;
import stexfires.core.message.RecordMessage;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Returns a stream consisting of the distinct records of the record stream.
 * It removes the duplicates based on the compare message.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public class DistinctModifier<T extends Record> implements RecordStreamModifier<T, T> {

    protected final RecordMessage<? super T> recordCompareMessage;

    public DistinctModifier(CompareMessageBuilder compareMessageBuilder) {
        this(compareMessageBuilder.build());
    }

    public DistinctModifier(RecordMessage<? super T> recordCompareMessage) {
        Objects.requireNonNull(recordCompareMessage);
        this.recordCompareMessage = recordCompareMessage;
    }

    @Override
    public Stream<T> modify(Stream<T> recordStream) {
        return recordStream
                .map(record -> new DistinctRecordWrapper<>(record, recordCompareMessage.createMessage(record)))
                .distinct()
                .map(DistinctModifier.DistinctRecordWrapper::getRecord);
    }

    protected static final class DistinctRecordWrapper<T extends Record> {

        private final T record;
        private final String equalsString;

        protected DistinctRecordWrapper(T record, String equalsString) {
            Objects.requireNonNull(record);
            Objects.requireNonNull(equalsString);
            this.record = record;
            this.equalsString = equalsString;
        }

        protected T getRecord() {
            return record;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DistinctModifier.DistinctRecordWrapper<?> that = (DistinctModifier.DistinctRecordWrapper<?>) o;
            return equalsString.equals(that.equalsString);
        }

        @Override
        public int hashCode() {
            return equalsString.hashCode();
        }

    }

}
