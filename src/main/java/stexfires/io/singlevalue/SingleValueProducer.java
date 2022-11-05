package stexfires.io.singlevalue;

import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueProducer extends AbstractReadableProducer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueProducer(BufferedReader bufferedReader, SingleValueFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new SingleValueIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<ValueRecord> createRecord(RecordRawData recordRawData) {
        ValueRecord record;
        if (fileSpec.skipEmptyLines() && recordRawData.rawData().isEmpty()) {
            // skip empty line
            record = null;
        } else {
            record = new ValueFieldRecord(recordRawData.category(), recordRawData.recordId(),
                    recordRawData.rawData());
        }

        return Optional.ofNullable(record);
    }

    private static final class SingleValueIterator extends AbstractRecordRawDataIterator {

        private SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
            super(reader, fileSpec.ignoreFirst(), fileSpec.ignoreLast());
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws IOException {
            String rawData = reader.readLine();
            return RecordRawData.asOptional(null, recordIndex, rawData);
        }
    }

}
