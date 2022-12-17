package stexfires.io.singlevalue;

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
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SingleValueProducer extends AbstractInternalReadableProducer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;
    private final UnaryOperator<String> rawDataOperator;

    public SingleValueProducer(BufferedReader bufferedReader, SingleValueFileSpec fileSpec) {
        super(bufferedReader);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;

        UnaryOperator<String> combinedOperator = null;
        if (fileSpec.linePrefix() != null) {
            combinedOperator = StringUnaryOperators.removeStringFromStart(fileSpec.linePrefix());
        }
        if (fileSpec.producerTrimValueToEmpty()) {
            if (combinedOperator == null) {
                combinedOperator = StringUnaryOperators.trimToEmpty();
            } else {
                combinedOperator = StringUnaryOperators.concat(combinedOperator, StringUnaryOperators.trimToEmpty());
            }
        }
        rawDataOperator = Objects.requireNonNullElseGet(combinedOperator, StringUnaryOperators::identity);
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
        return new SingleValueIterator(bufferedReader(), fileSpec);
    }

    @Override
    protected Optional<ValueRecord> createRecord(RecordRawData recordRawData) throws UncheckedProducerException {
        ValueRecord record;

        // Check missing linePrefix
        if ((fileSpec.linePrefix() != null) && !recordRawData.rawData().startsWith(fileSpec.linePrefix())) {
            throw new UncheckedProducerException(new ProducerException("Missing linePrefix! " + recordRawData));
        }

        String valueText = rawDataOperator.apply(recordRawData.rawData());

        boolean skipEmptyValue = fileSpec.producerSkipEmptyValue() && valueText.isEmpty();

        if (skipEmptyValue) {
            record = null;
        } else {
            record = new ValueFieldRecord(recordRawData.category(), recordRawData.recordId(), valueText);
        }

        return Optional.ofNullable(record);
    }

    private static final class SingleValueIterator extends AbstractRecordRawDataIterator {

        private final SingleValueFileSpec fileSpec;

        private SingleValueIterator(BufferedReader reader, SingleValueFileSpec fileSpec) {
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
