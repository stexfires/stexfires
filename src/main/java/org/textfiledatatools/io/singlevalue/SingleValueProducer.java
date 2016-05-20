package org.textfiledatatools.io.singlevalue;

import org.textfiledatatools.core.producer.ProducerException;
import org.textfiledatatools.core.producer.UncheckedProducerException;
import org.textfiledatatools.core.record.SingleRecord;
import org.textfiledatatools.io.internal.AbstractReadableProducer;
import org.textfiledatatools.io.internal.AbstractRecordRawDataIterator;
import org.textfiledatatools.io.internal.RecordRawData;

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

        SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileType) {
            super(reader, fileType.getIgnoreFirstLines(), fileType.getIgnoreLastLines());
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
