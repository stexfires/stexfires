package stexfires.io.singlevalue;

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
    protected AbstractRecordRawDataIterator createIterator() {
        return new SingleValueIterator(reader, fileSpec);
    }

    @Override
    protected Optional<SingleRecord> createRecord(RecordRawData recordRawData) {
        boolean skipEmptyLine = fileSpec.isSkipEmptyLines() && recordRawData.getRawData().isEmpty();

        SingleRecord record = null;
        if (!skipEmptyLine) {
            record = new SingleRecord(recordRawData.getCategory(), recordRawData.getRecordId(),
                    recordRawData.getRawData());
        }

        return Optional.ofNullable(record);
    }

    protected static final class SingleValueIterator extends AbstractRecordRawDataIterator {

        public SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirst(), fileSpec.getIgnoreLast());
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            if (rawData == null) {
                return Optional.empty();
            }
            return Optional.of(new RecordRawData(null, recordIndex, rawData));
        }
    }

}
