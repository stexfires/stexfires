package stexfires.io.singlevalue;

import stexfires.core.producer.ProducerException;
import stexfires.core.producer.UncheckedProducerException;
import stexfires.core.record.SingleRecord;
import stexfires.io.internal.AbstractReadableProducer;
import stexfires.io.internal.AbstractRecordRawDataIterator;
import stexfires.io.internal.RecordRawData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleValueProducer extends AbstractReadableProducer<SingleRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueProducer(BufferedReader reader, SingleValueFileSpec fileSpec) {
        super(reader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    protected Iterator<RecordRawData> createIterator() throws UncheckedProducerException {
        return new SingleValueIterator(reader, fileSpec);
    }

    @Override
    protected SingleRecord createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        String value = recordRawData.getRawData();
        // TODO Change value (substitute, DataType)
        return new SingleRecord(recordRawData.getCategory(), recordRawData.getRecordId(), value);
    }

    private static class SingleValueIterator extends AbstractRecordRawDataIterator {

        SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
            super(reader, fileSpec.getIgnoreFirstLines(), fileSpec.getIgnoreLastLines());
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
