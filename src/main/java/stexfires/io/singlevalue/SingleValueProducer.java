package stexfires.io.singlevalue;

import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;
import stexfires.core.record.SingleRecord;
import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleValueProducer extends AbstractReadableProducer<SingleRecord> {

    protected final SingleValueFileSpec fileSpec;

    public SingleValueProducer(BufferedReader reader, SingleValueFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() throws UncheckedProducerException {
        return new SingleValueIterator(reader, fileSpec);
    }

    @Override
    protected Optional<SingleRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        boolean skipEmptyLine = fileSpec.isSkipEmptyLines() && recordRawData.getRawData().isEmpty();

        SingleRecord record = null;
        if (!skipEmptyLine) {
            record = new SingleRecord(recordRawData.getCategory(), recordRawData.getRecordId(),
                    recordRawData.getRawData());
        }

        return Optional.ofNullable(record);
    }

    protected static final class SingleValueIterator extends AbstractRecordRawDataIterator {

        protected SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirst(), fileSpec.getIgnoreLast());
        }

        @Override
        protected RecordRawData readNext(BufferedReader reader, long recordIndex) throws ProducerException, IOException {
            String rawData = reader.readLine();
            if (rawData == null) {
                return null;
            }
            return new RecordRawData(null, recordIndex, rawData);
        }
    }

}
