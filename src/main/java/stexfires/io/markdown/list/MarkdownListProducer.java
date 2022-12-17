package stexfires.io.markdown.list;

import stexfires.io.internal.AbstractInternalReadableProducer;
import stexfires.io.producer.AbstractRecordRawDataIterator;
import stexfires.io.producer.RecordRawData;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.record.producer.UncheckedProducerException;
import stexfires.util.function.StringUnaryOperators;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class MarkdownListProducer extends AbstractInternalReadableProducer<ValueRecord> {

    private final MarkdownListFileSpec fileSpec;

    public MarkdownListProducer(BufferedReader bufferedReader, MarkdownListFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void readBefore() throws ProducerException, UncheckedProducerException, IOException {
        // Skip first lines by reading lines from the buffer without reading Records with the Iterator.
        if (fileSpec.producerSkipFirstLines() > 0) {
            for (int i = 0; i < fileSpec.producerSkipFirstLines(); i++) {
                bufferedReader().readLine();
            }
        }

        super.readBefore();
    }

    @Override
    protected AbstractRecordRawDataIterator createIterator() {
        return new MarkdownListIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<ValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        ValueRecord record;

        // TODO Check listMarker and remove listMarker

        String valueText = recordRawData.rawData();
        if (fileSpec.producerTrimValueToEmpty()) {
            valueText = StringUnaryOperators.trimToEmpty().apply(valueText);
        }

        boolean skipEmptyValue = fileSpec.producerSkipEmptyValue() && valueText.isEmpty();

        if (skipEmptyValue) {
            record = null;
        } else {
            record = new ValueFieldRecord(recordRawData.category(), recordRawData.recordId(), valueText);
        }

        return Optional.ofNullable(record);
    }

    private static final class MarkdownListIterator extends AbstractRecordRawDataIterator {

        private final MarkdownListFileSpec fileSpec;

        private MarkdownListIterator(BufferedReader reader, MarkdownListFileSpec fileSpec) {
            super(reader, fileSpec.producerIgnoreFirstRecords(), fileSpec.producerIgnoreLastRecords());
            this.fileSpec = fileSpec;
        }

        @Override
        protected Optional<RecordRawData> readNext(BufferedReader reader, long recordIndex) throws UncheckedProducerException {
            String rawData = fileSpec.producerReadLineHandling().readAndHandleLine(reader);
            return RecordRawData.asOptional(null, recordIndex, rawData);
        }

    }

}
