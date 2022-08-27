package stexfires.record.modifier;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.message.CompareMessageBuilder;
import stexfires.record.message.RecordMessage;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Returns a stream consisting of the distinct records of the record stream.
 * It removes the duplicates based on the compare message.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public class DistinctModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    private final RecordMessage<? super T> recordCompareMessage;

    public DistinctModifier(CompareMessageBuilder compareMessageBuilder) {
        this(compareMessageBuilder.build());
    }

    public DistinctModifier(RecordMessage<? super T> recordCompareMessage) {
        Objects.requireNonNull(recordCompareMessage);
        this.recordCompareMessage = recordCompareMessage;
    }

    @Override
    public final Stream<T> modify(Stream<T> recordStream) {
        return recordStream
                .map(record -> new DistinctRecordWrapper<>(record, recordCompareMessage.createMessage(record)))
                .distinct()
                .map(DistinctModifier.DistinctRecordWrapper::getRecord);
    }

    protected static final class DistinctRecordWrapper<T extends TextRecord> {

        private final T record;
        private final String equalsString;
        private final int hashCode;

        public DistinctRecordWrapper(T record, String equalsString) {
            Objects.requireNonNull(record);
            Objects.requireNonNull(equalsString);
            this.record = record;
            this.equalsString = equalsString;
            hashCode = equalsString.hashCode();
        }

        public T getRecord() {
            return record;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;

            DistinctRecordWrapper<?> wrapper = (DistinctRecordWrapper<?>) obj;
            return Objects.equals(equalsString, wrapper.equalsString);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "DistinctRecordWrapper{" +
                    "equalsString='" + equalsString + '\'' +
                    '}';
        }

    }

}
